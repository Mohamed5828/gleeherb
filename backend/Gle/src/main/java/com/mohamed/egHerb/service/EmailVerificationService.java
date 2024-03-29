package com.mohamed.egHerb.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mohamed.egHerb.entity.AppUser;
import com.mohamed.egHerb.errorExceptions.EmailSendingException;
import com.mohamed.egHerb.errorExceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class EmailVerificationService {
    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private final AppUserService appUserService;

    public EmailVerificationService(JavaMailSender emailSender, AppUserService appUserService) {
        this.emailSender = emailSender;
        this.appUserService = appUserService;
    }

    public void sendVerificationEmail(String email) throws UserNotFoundException, UnsupportedEncodingException {
        AppUser currentUser = appUserService.getCurrentUserByEmail(email);
        String vCode = currentUser.getVcode();
        String to = email;
        String verificationUrl = "https://backend.gleeherb.com/api/v1/auth/verify?vcode=" + vCode;
        String redirectUrl = "https://gleeherb.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@gleeherb.com");
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Thank you for registering! To verify your email address, please click the link below:\n" +
                verificationUrl + "&redirect=" + URLEncoder.encode(redirectUrl, "UTF-8") + "\n\nIf you have any questions or did not request this verification, please contact our support team. Thank you.");
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Processing email message for verification to {}", to);
        try {
            emailSender.send(message);
            logger.info("Email message sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email message to {}: {}", to, e.getMessage());
            throw new EmailSendingException("Failed to send email for verification", e);

        }
    }
    public void sendOrderConfirmationMail(ObjectNode objectNode) throws UserNotFoundException {
        AppUser currentUser = appUserService.getCurrentUser();
        String to = currentUser.getUsername();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("info@gleeherb.com");
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Your Order Has Been Placed:\nTheOrder:\n" + objectNode
                );
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Processing email message for verification to {}", to);
        try {
            emailSender.send(message);
            logger.info("Email message sent successfully to {}", to);
        } catch (MailException e) {
            logger.error("Failed to send email message to {}: {}", to, e.getMessage());
            throw new EmailSendingException("Failed to send email for verification", e);

        }
    }

}
