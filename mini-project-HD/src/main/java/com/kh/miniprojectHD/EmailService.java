package com.kh.miniprojectHD;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService{
    @Autowired
    JavaMailSender ms; //Bean  등록해둔 Mail

    private String authKey; //인증 코드

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {

        MimeMessage msg = ms.createMimeMessage();
        msg.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        msg.setSubject("허당 회원가입 이메일 인증"); // 제목


        String msgs = "";
        msgs += "<div style='margin:100px;'>";
        msgs += "<h1> 안녕하세요</h1>";
        msgs += "<h1> 평범한 식사도 허투루 할 수 없는 당신을 위해, 허당입니다</h1>";
        msgs += "<br>";
        msgs += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgs += "<br>";
        msgs += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgs += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgs += "<div style='font-size:130%'>";
        msgs += "CODE : <strong>";
        msgs += authKey + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgs += "</div>";
        msg.setText(msgs, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        msg.setFrom(new InternetAddress("heodangreview@naver.com", "Heodang_Admin"));// 보내는 사람

        return msg;
    }

    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }

    public String sendMessage(String to) throws Exception{
        authKey = createKey();
        MimeMessage msg = createMessage(to);
        try{
            ms.send(msg);
        }catch (MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authKey; // 메일로 보낸 인증키를 서버로 반환
    }



}
