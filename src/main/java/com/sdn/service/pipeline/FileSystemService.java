package com.sdn.service.pipeline;

import java.io.File;
import java.io.IOException;

public interface FileSystemService {
    void downloadFile(String remoteFilePath, String localFilePath) throws Exception;
    boolean deleteFile(String targetPath) throws Exception;
    void deleteAllFilesFromDirectory(File path) throws IOException;;
}