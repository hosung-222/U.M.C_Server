package com.example.umcmatchingcenter.service.EmailService;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class SendEmailService {
    private final JavaMailSender emailSender;

    //email 전송
    public void sendEmail(String toEmail,
                          String title,
                          String text){
        try {
            MimeMessage message = createEmailForm(toEmail, title, text);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new MemberHandler(ErrorStatus.FAIL_CREATE_EMAIL);
        }catch (UnsupportedEncodingException e){
            throw new MemberHandler(ErrorStatus.FAIL_CREATE_EMAIL);
        }
    }

    // 발신할 이메일 데이터 세팅
    private MimeMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) throws MessagingException, UnsupportedEncodingException {
        MimeMessage  message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        message.setSubject(title);
        String msgg="";
        msgg += "<div style='margin:100px; text-align: center;'>";
        msgg += "<h1>안녕하세요 Umc-Matching-Center입니다</h1>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h2 style='color:black;'>회원가입 인증 코드입니다.</h2>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<p style='text-align: center;'>감사합니다</p>";
        msgg += "CODE : <strong>";
        msgg += text+"</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("umc.matching.center.05@gmail.com","umcmatchingcenter"));//보내는 사람
        return message;
    }

}
