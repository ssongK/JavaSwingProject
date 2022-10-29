package com.example;

import java.util.ArrayList;
import java.sql.*;

public class Jdbc {
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement insertPstm = null;
    private Statement selectPstm = null;
    private String url = "jdbc:mysql://104.154.105.208:3306/user";
    
    public Jdbc(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,"myUser","myUser");
            System.out.println("DB 연결 성공");
        } catch (SQLException e) {
            System.out.println("DB 연결 실패");
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
            System.err.println(e);
        }
    }
    
    public void insert(String name){
        try {
            String sql = "insert into user(name) values (?)";
    
            insertPstm = conn.prepareStatement(sql);
            insertPstm.setString(1, name);
            insertPstm.executeUpdate();
    
        } catch (SQLException e) {
            System.out.println("중복된 이름입니다!");
            System.err.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (insertPstm != null) {
                    insertPstm.close();
                }
                if (selectPstm != null) {
                    selectPstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("가입 성공!");
        }
    }

    public ArrayList<String> searchName(){
        ArrayList<String> names = new ArrayList<>();

        try {
            // conn = DriverManager.getConnection(url,"myUser","myUser");
            String sql = "select * from user";
            selectPstm = conn.createStatement();
            rs = selectPstm.executeQuery(sql);
            while (rs.next()) {
                names.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("조회 실패!");
            System.err.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (insertPstm != null) {
                    insertPstm.close();
                }
                if (selectPstm != null) {
                    selectPstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return names;
    }
}
