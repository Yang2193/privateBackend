package com.kh.miniprojectHD.controller;


import com.kh.miniprojectHD.dao.InquiryDAO;
import com.kh.miniprojectHD.vo.InquiryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class InquiryController {

    @Autowired
    private InquiryDAO dao;
    //Get : 문의 조회
    @GetMapping("/inquiry")
    public ResponseEntity<List<InquiryVO>> inquiryList (@RequestParam String name) {
        List<InquiryVO> list = dao.inquirySelect(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //문의 추가
    @PostMapping("/restaurant/add/inquiry")
    public ResponseEntity<Boolean> addInquiry(@RequestBody Map<String, String> inquiryData) {
        String getRestId = inquiryData.get("restId");
        String getMemberId = inquiryData.get("memberId");
        String getTitle = inquiryData.get("title");
        String getContent = inquiryData.get("content");

        boolean list = dao.addInquiry(getRestId, getMemberId, getTitle, getContent);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
