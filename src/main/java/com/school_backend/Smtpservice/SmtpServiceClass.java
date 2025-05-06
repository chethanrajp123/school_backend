package com.school_backend.Smtpservice;

import com.school_backend.DTO.EmailData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SmtpServiceClass
{
    @Value("${spring.mail.username}") private String sender;

    @Autowired
    JavaMailSender javaMailSender;

    public String SendEmail(@RequestBody EmailData emailData)
    {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try
        {
            mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(emailData.getRecepient());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setSubject(emailData.getSubject());
            mimeMessageHelper.setText(emailData.getMessage());

            javaMailSender.send(mimeMessage);
            return "Mail sent successfully";

        }
        catch (MessagingException e1)
        {
            return "Mail can't send some error!!";
        }

    }

}
