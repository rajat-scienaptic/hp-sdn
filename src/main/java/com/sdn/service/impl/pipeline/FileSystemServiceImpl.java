package com.sdn.service.impl.pipeline;

import com.sdn.config.SftpProperties;
import com.sdn.exceptions.CustomException;
import com.sdn.service.pipeline.FileSystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service("fileSystemService")
public class FileSystemServiceImpl implements FileSystemService {

    protected static final String PATH = "src/main/resources/pipeline";

    @Autowired
    protected SftpProperties config;
    protected FTPClient ftp = null;

    /**
     * Create an SFTP connection
     */
    private FTPClient createSftp() throws IOException {
        log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use password[" + config.getPassword() + "]");
        return createSession(config.getHost().trim(), config.getUsername().trim(), config.getPassword());
    }

    /**
     * Create session
     */
    private FTPClient createSession(String host, String username, String pwd) throws IOException {
        ftp = new FTPClient();
        ftp.connect(host);

        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.disconnect();
            throw new CustomException("Exception in connecting to FTP Server", HttpStatus.BAD_GATEWAY);
        }

        ftp.login(username, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();

        log.info("connection successful!");

        return ftp;
    }

    /**
     * Download Files from FTP to AWS Workspace
     */
    @Override
    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        FTPClient sftp = this.createSftp();
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            sftp.retrieveFile(remoteFilePath, fos);
            log.info("File downloaded!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Disconnect from FTP server
     */
    public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
                log.info("Disconnected from FTP!");
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }

    @Override
    public void deleteAllFilesFromDirectory(File dir) throws IOException {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    deleteAllFilesFromDirectory(aFile);
                }
            }
        }
    }

    /**
     * Delete Files from the AWS Workspace
     */
    @Override
    public boolean deleteFile(String targetPath) throws Exception {
        try {
            ftp = this.createSftp();
            ftp.changeWorkingDirectory(config.getRoot());
            ftp.deleteFile(targetPath);
            log.info("File deleted!");
            return true;
        } catch (Exception e) {
            log.error("Delete file failure. TargetPath: {}", targetPath, e);
            throw new CustomException("Delete File failure", HttpStatus.BAD_GATEWAY);
        } finally {
            this.disconnect();
        }
    }
}