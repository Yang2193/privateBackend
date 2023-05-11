package com.kh.miniprojectHD;

import com.kh.miniprojectHD.dao.MemberDAO;
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

    private String authCode; //인증 코드

    //인증번호를 회원에게 보내는 메소드
    public MimeMessage authCodeMessage(String to) throws MessagingException, UnsupportedEncodingException {

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
        msgs += authCode + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgs += "</div>";
        msg.setText(msgs, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        msg.setFrom(new InternetAddress("heodangreview@naver.com", "Heodang_Admin"));// 보내는 사람
        return msg;
    }
    
    //ID찾기 시 ID를 회원에게 보내는 메소드
    public MimeMessage findIdMessage(String to) throws MessagingException, UnsupportedEncodingException {

        MemberDAO dao = new MemberDAO();
        MimeMessage msg = ms.createMimeMessage();
        String id = dao.findId(to);
        msg.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        msg.setSubject("허당 ID찾기 서비스"); // 제목
        String msgs = "";
        msgs += "<div style='margin:100px;'>";
        msgs += "<h1> 안녕하세요</h1>";
        msgs += "<h1> 평범한 식사도 허투루 할 수 없는 당신을 위해, 허당입니다</h1>";
        msgs += "<br>";
        msgs += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgs += "<h3 style='color:blue;'>회원님의 ID입니다.</h3>";
        msgs += "<div style='font-size:130%'>";
        msgs += "ID : <strong>";
        msgs += id + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgs += "</div>";
        msg.setText(msgs, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        msg.setFrom(new InternetAddress("heodangreview@naver.com", "Heodang_Admin"));// 보내는 사람
        return msg;
    }

    //PW찾기시 회원에게 보낼 메시지
    public MimeMessage findPwMessage(String to, String pwd) throws MessagingException, UnsupportedEncodingException {

        MimeMessage msg = ms.createMimeMessage();
        msg.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        msg.setSubject("허당 비밀번호 변경 이메일"); // 제목
        String msgs = "";
        msgs += "<div style='margin:100px;'>";
        msgs += "<h1> 안녕하세요</h1>";
        msgs += "<h1> 평범한 식사도 허투루 할 수 없는 당신을 위해, 허당입니다</h1>";
        msgs += "<br>";
        msgs += "<p>임시 비밀번호를 발급해드렸습니다. 마이페이지에서 비밀번호를 새롭게 변경해주세요.<p>";
        msgs += "<br>";
        msgs += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgs += "<h3 style='color:blue;'>임시 비밀번호 발급 </h3>";
        msgs += "<div style='font-size:130%'>";
        msgs += "임시 비밀번호 : <strong>";
        msgs += pwd + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgs += "</div>";
        msg.setText(msgs, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        msg.setFrom(new InternetAddress("heodangreview@naver.com", "Heodang_Admin"));// 보내는 사람
        return msg;
    }

    // 인증코드를 만드는 메소드
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

    public String authCodeSendMessage(String to) throws Exception{
        authCode = createKey();
        MimeMessage msg = authCodeMessage(to);
        try{
            ms.send(msg);
        }catch (MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return authCode; // 메일로 보낸 인증키를 서버로 반환
    }

    public Boolean findIdSendMessage(String to) throws Exception{
        MemberDAO dao = new MemberDAO();
        Boolean result = dao.regMemberCheckEmail(to);
        MimeMessage msg = findIdMessage(to);
        try{
            ms.send(msg);
        }catch (MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return result; // 서버에 ID찾기에 성공했다고 TRUE를 반환. TRUE일 때 이메일 확인하라는 문구 출력
    }

    public Boolean findPwSendMessage(String to, String id) throws Exception{
        String newPwd = createKey();
        MemberDAO dao = new MemberDAO();
        Boolean result = dao.findPw(newPwd, to, id);
        MimeMessage msg = findPwMessage(to, newPwd);
        try{
            ms.send(msg);
        }catch (MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return result; // 서버에 ID찾기에 성공했다고 TRUE를 반환. TRUE일 때 이메일 확인하라는 문구 출력
    }



}
