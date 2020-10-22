package com.sdn.service.impl;

import com.sdn.dto.EmailRequestDto;
import com.sdn.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    protected JavaMailSender mailSender;
    @Value("${senderEmail}")
    protected String senderEmail;
    @Value("${senderName}")
    protected String senderName;
    @Value("${adminEmail}")
    protected String adminEmail;
    @Value("${adminName}")
    protected String adminName;
    @Value("${receiverEmails}")
    protected String receiverEmails;
    @Value("${receiverNames}")
    protected String receiverNames;

    @Autowired
    protected Configuration configuration;

    protected Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    static final String TODAY_DATE = LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getYear();
    static final String EMAIL_TEMPLATE = "email.ftl";
    static final String EMAIL_SUBJECT = "SDN & Test Buy Data Analysis";
    static final String FILE_PATH = "src/main/resources/static";

    @Override
    public void sendEmail() throws IOException {
        String response;

        String[] receiverEmailsList = receiverEmails.split(",");
        String[] receiverNamesList = receiverNames.split(",");

        for (int i = 0; i < receiverEmailsList.length; i++) {

            EmailRequestDto request = EmailRequestDto.builder()
                    .from(senderEmail)
                    .to(receiverEmailsList[i])
                    .name(receiverNamesList[i])
                    .subject(EMAIL_SUBJECT)
                    .build();

            String messageBody = "Please find the attached file for Errors in SDN & Test Buy Data for " + TODAY_DATE + ".";

            Map<String, String> model = new HashMap<>();
            model.put("name", request.getName());
            model.put("date", TODAY_DATE);
            model.put("sender", senderName);
            model.put("messageBody", messageBody);

            MimeMessage message = mailSender.createMimeMessage();

            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                Template template = configuration.getTemplate(EMAIL_TEMPLATE);
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                helper.setTo(request.getTo());
                helper.setFrom(request.getFrom());
                helper.setSubject(request.getSubject());
                helper.setText(html, true);

                File folder = new File(FILE_PATH);

                if (Objects.requireNonNull(folder.listFiles()).length > 0) {
                    for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                        ClassPathResource xslx = new ClassPathResource("static/" + fileEntry.getName());
                        helper.addAttachment(fileEntry.getName(), xslx);
                    }

                    mailSender.send(message);
                    response = "Email has been sent to :" + request.getTo();
                    logger.info(response);
                } else {
                    logger.info("No Data Quality Check Report found for this week!");
                }
            } catch (Exception e) {
                sendAdminEmail(request.getTo(), e.getLocalizedMessage());
                e.printStackTrace();
                response = "Email send failure to :" + request.getTo();
                logger.info(response);
            }
        }
        sendAdminEmail(receiverNames, null);
    }

    private void sendAdminEmail(String receivers, String error) {
        String response;
        String messageBody = "Please find the attached file for Errors in SDN & Test Buy Data for " + TODAY_DATE + ". A copy of this has been to " + receivers + " as well.";

        if (error != null) {
            messageBody = "Could not send an email to " + receivers + " because of the following error : " + error;
        }

        EmailRequestDto request = EmailRequestDto.builder()
                .from(senderEmail)
                .to(adminEmail)
                .name(adminName)
                .subject(EMAIL_SUBJECT)
                .build();

        Map<String, String> model = new HashMap<>();
        model.put("name", request.getName());
        model.put("date", TODAY_DATE);
        model.put("sender", senderName);
        model.put("messageBody", messageBody);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template template = configuration.getTemplate(EMAIL_TEMPLATE);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(request.getTo());
            helper.setFrom(request.getFrom());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);

            File folder = new File(FILE_PATH);

            if (Objects.requireNonNull(folder.listFiles()).length > 0) {
                for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                    ClassPathResource xslx = new ClassPathResource("static/" + fileEntry.getName());
                    helper.addAttachment(fileEntry.getName(), xslx);
                }
                mailSender.send(message);
                response = "Email has been sent to admin:" + request.getTo();
                logger.info(response);
            } else {
                logger.info("No Data Quality Check Report found for this week!");
            }
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            response = "Email send failure to admin:" + request.getTo();
            logger.info(response);
        }
    }

}
