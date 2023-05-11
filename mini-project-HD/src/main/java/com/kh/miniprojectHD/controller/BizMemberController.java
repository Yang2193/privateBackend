package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.BizMemberDAO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class BizMemberController {

    @Autowired
    private BizMemberDAO dao = new BizMemberDAO();

    //신규 사업자회원가입
    @PostMapping("/newBizMember")
    public ResponseEntity<Boolean> bizMemberInsert(@RequestBody  bizMemberInfo bmi){
        System.out.println("사업자 회원가입 컨트롤러 작동");
//        boolean isTrue = dao.bizMemberInsert(bmi.getId(), bmi.getPwd(), bmi.getName(), bmi.getEmail(), bmi.getPhone());
       boolean isTrue = dao.bizMemberInsert(bmi.id, bmi.pwd, bmi.name, bmi.email, bmi.phone);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @PostMapping("/checkBizMember")
    public ResponseEntity<Boolean> memberCheck(@RequestBody Map<String, String> id) {
        System.out.println("중복ID 체크 컨트롤러 작동");
        String checkId = id.get("id");
        boolean isTrue = dao.regBizMemberCheck(checkId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @Getter
    @Setter
    private static class bizMemberInfo{
        private String id;
        private String pwd;
        private String name;
        private String email;
        private String phone;
    }
}
