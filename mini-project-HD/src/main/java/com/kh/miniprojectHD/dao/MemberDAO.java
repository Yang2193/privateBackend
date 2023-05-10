package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import com.kh.miniprojectHD.vo.MemberVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;
    // 로그인 체크
    public boolean loginCheck(String id, String pwd) {
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql ="SELECT * FROM MEMBER_INFO WHERE MEMBER_ID = "+ "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) { // 읽은 데이타가 있으면 true
                String sqlId = rs.getString("MEMBER_ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPwd = rs.getString("PASSWORD");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPwd);
                if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //회원 조회
    public List<MemberVO> memberSelect(String id) {
        List<MemberVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection(); //연결
            stmt = conn.createStatement(); //정적인 sql 사용
            System.out.println(id);
            String sql= "SELECT * FROM MEMBER_INFO WHERE MEMBER_ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql); //
            while(rs.next()){ //읽을 행이 있으면 참
                String memId = rs.getString("MEMBER_ID");
                String pwd = rs.getString("PASSWORD");
                String name = rs.getString("NAME");
                String nickName = rs.getString("NICKNAME");
                String eMail = rs.getString("EMAIL");
                String phoneNum = rs.getString("PHONE_NUM");
                String addr = rs.getString("ADDRESS");
                Date joinDate = rs.getDate("JOIN_DATE");
                String imgFileName = rs.getString("IMAGE_FILE_NAME");
                MemberVO vo = new MemberVO(memId,pwd,name,nickName,eMail,phoneNum,addr,joinDate,imgFileName); //하나의 행(레코드)에 대한 정보저장을 위한 객체생성
                list.add(vo);

            }
            Common.close(rs); // 연결과 역순으로 해제
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e){
            e.printStackTrace();

        }
        return list;
    }

    //회원 정보 변경하기(곽은지)
    public Boolean memberUpdate(MemberVO vo) {
        System.out.println(vo.getPwd());
        String sql = "UPDATE MEMBER_INFO SET PASSWORD = ?,NAME = ?, NICKNAME= ?, EMAIL = ? , PHONE_NUM= ?, ADDRESS= ?, IMAGE_FILE_NAME= ? WHERE MEMBER_ID = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, vo.getPwd());
            pStmt.setString(2, vo.getName());
            pStmt.setString(3, vo.getNickName());
            pStmt.setString(4, vo.getEMail());
            pStmt.setString(5, vo.getPhoneNum());
            pStmt.setString(6, vo.getAddr());
            pStmt.setString(7, vo.getImgFileName());
            pStmt.setString(8, vo.getMemId());
            pStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    return false;
    }

    //회원탈퇴 (곽은지)
    public Boolean memberDelete(String id) {
        try {
            String sql = "DELETE FROM MEMBER_INFO WHERE MEMBER_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,id);
            System.out.println(id);
            pStmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        return false;
    }

    //회원가입(박준하)
    public boolean memberInsert(String id, String pwd, String name, String email, String phone, String nickname){
        int result = 0;
        String sql = "INSERT INTO MEMBER_INFO(MEMBER_ID, PASSWORD, NAME, EMAIL, PHONE_NUM, NICKNAME) VALUES(?,?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, email);
            pStmt.setString(5, phone);
            pStmt.setString(6, nickname);

            result = pStmt.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    public boolean regMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM MEMBER_INFO WHERE MEMBER_ID = " + "'" + id +"'";
            rs = stmt.executeQuery(sql);
            if(rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

}
