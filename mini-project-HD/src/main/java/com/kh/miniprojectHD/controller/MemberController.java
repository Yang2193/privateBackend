package com.kh.miniprojectHD.controller;


import com.kh.miniprojectHD.dao.MemberDAO;
import com.kh.miniprojectHD.vo.MemberVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class MemberController {
    @Autowired
    private MemberDAO dao; //autowired로 의존성 주입
    //Get :회원조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String name){
        List<MemberVO> list = dao.memberSelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
    // POST : 로그인
    @PostMapping("/login")
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String user = loginData.get("id");
        String pwd = loginData.get("pwd");
        boolean result = dao.loginCheck(user, pwd);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST : 회원정보 업데이트(곽은지)
    @PostMapping("/update")
    public ResponseEntity<Boolean> memberUpdate(@RequestBody Map<String, MemberVO> data) {
        MemberVO vo = data.get("vo");
        boolean result = dao.memberUpdate(vo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // POST : 회원 탈퇴
    @PostMapping("/del")
    public ResponseEntity<Boolean> memberDelete(@RequestBody Map<String, String> delData) {
        System.out.println("컨트롤러" + delData);
        String getId = delData.get("id");
        boolean isTrue = dao.memberDelete(getId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @PostMapping("/newMember")
    public ResponseEntity<Boolean> memberInsert(@RequestBody memberInfo mem){
        System.out.println("회원가입 컨트롤러 작동");
        boolean isTrue = dao.memberInsert(mem.getId(), mem.getPwd(), mem.getName(), mem.getEmail(), mem.getPhone(), mem.getNickname());
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @PostMapping("/checkMember")
    public ResponseEntity<Boolean> memberCheck(@RequestBody Map<String, String> id) {
        System.out.println("중복ID 체크 컨트롤러 작동");
        String checkId = id.get("id");
        boolean isTrue = dao.regMemberCheck(checkId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @Getter
    @Setter
    private static class memberInfo{
        private String id;
        private String pwd;
        private String name;
        private String email;
        private String phone;
        private String nickname;
    }



}
