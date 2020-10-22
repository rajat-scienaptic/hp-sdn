package com.sdn.scheduler;

import com.sdn.constants.Constants;
import com.sdn.service.CSVConverterService;
import com.sdn.service.DataQualityCheckService;
import com.sdn.service.EmailService;
import com.sdn.service.pipeline.FileSystemService;
import com.sdn.service.pipeline.PipelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Component
public class SDNScheduler {
    @Autowired
    protected CSVConverterService csvConverterService;
    @Autowired
    protected PipelineService pipelineService;
    @Autowired
    protected FileSystemService fileSystemService;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected DataQualityCheckService dataQualityCheckService;

    protected DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyyMMdd");
    protected static final String PATH = "src/main/resources/pipeline/";
    protected static final String EMAIL_TEMPLATE_PATH = "src/main/resources/static/";
    protected Logger logger = LoggerFactory.getLogger(SDNScheduler.class);

    //@Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 17 * * MON")
    public void pushDataToSDNAndDoQualityCheck() throws Exception {
        String date = LocalDate.now().format(sdf);

        //Deleting all existing files in the directory
        logger.warn("Deleting Existing Files in the Directory");
        fileSystemService.deleteAllFilesFromDirectory(new File(PATH));
        logger.warn("Deleted All Files");

        //Download Latest SDN Excel Files from FTP
        logger.warn("Downloading Process Started");
        fileSystemService.downloadFile(date + Constants.SCMM_EXCEL_FILENAME, PATH + date + Constants.SCMM_EXCEL_FILENAME);
        fileSystemService.downloadFile(date + Constants.SCMG_EXCEL_FILENAME, PATH + date + Constants.SCMG_EXCEL_FILENAME);
        fileSystemService.downloadFile(date + Constants.STMM_EXCEL_FILENAME, PATH + date + Constants.STMM_EXCEL_FILENAME);
        fileSystemService.downloadFile(date + Constants.STMW_EXCEL_FILENAME, PATH + date + Constants.STMW_EXCEL_FILENAME);
        fileSystemService.downloadFile(date + Constants.CONTRACTUAL_ENFORCEMENT_EXCEL_FILENAME, PATH + date + Constants.CONTRACTUAL_ENFORCEMENT_EXCEL_FILENAME);
        logger.warn("Downloading Process Finished");

        //Convert Downloaded Excel Files to CSV
        logger.warn("Excel to CSV Conversion Process Started");
        csvConverterService.convertToCSV(PATH + date + Constants.SCMM_EXCEL_FILENAME, PATH + Constants.SCMM_CSV_FILENAME);
        csvConverterService.convertToCSV(PATH + date + Constants.SCMG_EXCEL_FILENAME, PATH + Constants.SCMG_CSV_FILENAME);
        csvConverterService.convertToCSV(PATH + date + Constants.STMM_EXCEL_FILENAME, PATH + Constants.STMM_CSV_FILENAME);
        csvConverterService.convertToCSV(PATH + date + Constants.STMW_EXCEL_FILENAME, PATH + Constants.STMW_CSV_FILENAME);
        csvConverterService.convertToCSV(PATH + date + Constants.CONTRACTUAL_ENFORCEMENT_EXCEL_FILENAME, PATH + Constants.CONTRACTUAL_ENFORCEMENT_CSV_FILENAME);
        logger.warn("Excel to CSV Conversion Process Finished");

        //Push Data to SDN Master Table
        logger.warn("Pushing Data To SDN Master Table");
        pipelineService.pushDataToSdn();
        logger.warn("Date pushed to SDN Master Table");

        //Do Quality Check on Data and Generate Report
        logger.warn("Doing Quality Check and Generating Report If Violated Data is Found");
        dataQualityCheckService.getQualityCheckViolatingRowsAndGenerateReport();
        logger.warn("Quality Check Over");


        //Checking If Quality Check Report Exists
        //If yes then sending reports via Email
        sendEmailIfQualityCheckReportWasGenerated(new File(EMAIL_TEMPLATE_PATH));

    }

    private void sendEmailIfQualityCheckReportWasGenerated(File dir) throws IOException {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                //Send Quality Check Report Via Email
                logger.warn("Sending Emails");
                emailService.sendEmail();
                logger.warn("Email Sent");
            } else {
                logger.warn("No report was generated this week!");
            }
        }
    }
}
