package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class EmailController {
    @Autowired
    private EmailService es = new EmailService();

    @PostMapping("/signup/email")
    public ResponseEntity<String> mailConfirm(@RequestBody Map<String, String> email) throws Exception{
        String mail = email.get("email");
        String authKey = es.sendMessage(mail);

        return new ResponseEntity<>(authKey, HttpStatus.OK);
    }
}
