package com.kth.project_dollarstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for sending email.
 * The email sent will contain a reset token.
 */
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final String fromEmail = "dollarstore@info.com";

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Sends an email to the specified recipient with a reset token.
     *
     * @param recipientEmail The email address of the recipient.
     * @param token The password reset token.
     * @throws RuntimeException if sending the email fails.
     */
    public void sendPasswordResetEmail(String recipientEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(recipientEmail);
            message.setSubject("Password Reset Request");
            message.setText(buildEmailContent(token));

            emailSender.send(message);
            logger.info("Password reset email sent to {}", recipientEmail);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to {}: {}", recipientEmail, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Creates a simple format for the content of the password reset email.
     *
     * @param token The password reset token to include in the email.
     * @return The content of the email as a String.
     */
    private String buildEmailContent(String token) {
        return "Hi,\n\n"
               + "We received a request to reset your password. Copy and use the code below:\n\n"
               + token + "\n\n"
               + "Thank you!\n"
               + "The DollarStore Team";
    }
}