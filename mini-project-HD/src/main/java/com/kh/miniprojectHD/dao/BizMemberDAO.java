package com.kh.miniprojectHD.dao;

import com.kh.miniprojectHD.common.Common;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class BizMemberDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pStmt = null;

    public boolean bizMemberInsert(String id, String pwd, String name, String email, String phone){
        int result = 0;
        String sql = "INSERT INTO B_MEMBER(MEMBER_ID, PASSWORD, NAME, EMAIL, PHONE_NUM) VALUES(?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, id);
            pStmt.setString(2, pwd);
            pStmt.setString(3, name);
            pStmt.setString(4, email);
            pStmt.setString(5, phone);

            result = pStmt.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if(result == 1) return true;
        else return false;
    }

    public boolean regBizMemberCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM B_MEMBER WHERE MEMBER_ID = " + "'" + id +"'";
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
