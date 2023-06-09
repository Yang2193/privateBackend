package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.InquiryDAO;
import com.kh.miniprojectHD.dao.ReservationDAO;
import com.kh.miniprojectHD.vo.InquiryVO;
import com.kh.miniprojectHD.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class ReservationController {

    @Autowired
    private ReservationDAO dao;
    //Get : 예약 조회
    @GetMapping("/resv")
    public ResponseEntity<List<ReservationVO>> resvList (@RequestParam String name, String stat) {
        System.out.println(name + stat);
        List<ReservationVO> list = dao.resvSelect(name,stat);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //예약 추가
    @PostMapping("/restaurant/add/reservation")
    public ResponseEntity<Boolean> addRes(@RequestBody Map<String, String> resData){
        String getRestId = resData.get("restId");
        String getMemberId = resData.get("memberId");
        Date getResDate = Date.valueOf(resData.get("resDate"));
        String getResReq = resData.get("resReq");
        int getResSeat = Integer.parseInt(resData.get("resSeat"));
        int getResPeo = Integer.parseInt(resData.get("resPeo"));

        boolean list = dao.addRes(getRestId,getMemberId,getResDate,getResReq,getResSeat,getResPeo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
