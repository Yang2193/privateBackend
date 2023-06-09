package com.kh.miniprojectHD.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter

public class ReviewJoinVO {
    private String nickName;
    private int reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImage;
    private Date reviewDate;
    private double reviewRating;
}
