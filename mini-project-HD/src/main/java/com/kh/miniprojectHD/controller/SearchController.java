package com.kh.miniprojectHD.controller;

import com.kh.miniprojectHD.dao.SearchDAO;
import com.kh.miniprojectHD.vo.RestListVO;
import com.kh.miniprojectHD.vo.ReviewVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000" )
@RestController
public class SearchController {
    @Autowired
    private SearchDAO dao = new SearchDAO();
    @PostMapping("/restaurantList")
    public ResponseEntity<List<RestListVO>> restList(@RequestBody Restaurant rst){

        List<RestListVO> list = dao.searchAndFilter(
                rst.getKeyword(),
                rst.getRegion(),
                rst.getCategory(),
                rst.getPrice(),
                rst.getRating()
        );
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/restaurantList")
    public ResponseEntity<List<RestListVO>> popularRest(@RequestParam("popular") String popular){
        List<RestListVO> list = dao.popularList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //주간 인기 매장 탑3
    @GetMapping("/weeklyTop3Rest")
    public ResponseEntity<List<RestListVO>> weeklyTop3Rest(){
        List<RestListVO> list = dao.weeklyTop3Rest();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //주간 인기 리뷰 탑3
    @GetMapping("weeklyTop3Review")
    public ResponseEntity<List<ReviewVO>> weeklyTop3Review(){
        List<ReviewVO> list = dao.weeklyTop3Review();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //JSON 파싱용 잭슨 라이브러리 사용
    @Getter
    @Setter
    private static class Restaurant {
        private String[] keyword;
        private Map<String, String[]> region;
        private String[] category;
        private String[] price;
        private String rating;
    }

}