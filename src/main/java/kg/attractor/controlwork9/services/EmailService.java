package kg.attractor.controlwork9.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String EMAIL_FROM;


    public void sendEmail(String toEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(EMAIL_FROM, "MovieReview Support");
        helper.setTo(toEmail);

        String subject = "Here's the link to reset your password";
        String content = String.format("""
                <p>Hello,</p>
                <p>You have requested to reset your password.</p>
                <p>Click the link below to change your password:</p>
                <p><a href="%s">Change my password</a></p>
                <br>"
                <p>Ignore this email if you do remember your password, or you have not made the request.</p>";
                """,link);

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
