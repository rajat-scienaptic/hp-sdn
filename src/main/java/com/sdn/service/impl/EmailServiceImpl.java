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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    static final String ATTACHMENT_NAME = "attachment.pdf";

    @Override
    public void sendEmail() {
        String response;

        String[] receiverEmailsList = receiverEmails.split(",");
        String[] receiverNamesList = receiverNames.split(",");

        for (int i = 0; i < receiverEmailsList.length; i++) {

            EmailRequestDto request = EmailRequestDto.builder()
                    .from(senderEmail)
                    .to(receiverEmailsList[i])
                    .name(receiverNamesList[i])
                    .subject("SDN & Test Buy Data Errors")
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

                ClassPathResource pdf = new ClassPathResource("static/" + ATTACHMENT_NAME);
                Template template = configuration.getTemplate(EMAIL_TEMPLATE);
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

                helper.setTo(request.getTo());
                helper.setFrom(request.getFrom());
                helper.setSubject(request.getSubject());
                helper.setText(html, true);
                helper.addAttachment(ATTACHMENT_NAME, pdf);

                mailSender.send(message);
                response = "Email has been sent to :" + request.getTo();
                logger.info(response);
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
                .subject("SDN & Test Buy Data Errors")
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

            ClassPathResource pdf = new ClassPathResource("static/" + ATTACHMENT_NAME);
            Template template = configuration.getTemplate(EMAIL_TEMPLATE);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(request.getTo());
            helper.setFrom(request.getFrom());
            helper.setSubject(request.getSubject());
            helper.setText(html, true);
            helper.addAttachment(ATTACHMENT_NAME, pdf);

            mailSender.send(message);
            response = "Email has been sent to admin:" + request.getTo();
            logger.info(response);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            response = "Email send failure to admin:" + request.getTo();
            logger.info(response);
        }
    }

}
