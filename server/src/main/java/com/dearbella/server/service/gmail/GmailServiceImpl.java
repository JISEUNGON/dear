package com.dearbella.server.service.gmail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Slf4j
public class GmailServiceImpl implements GmailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Transactional
    public void sendMail(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("Dearbella Alarm"); // 메일 제목
            mimeMessageHelper.setText("Your answer to your inquiry has been registered. Please check it on Dearbella My Page. https://dearbella.kr", false); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Success");
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }
}
