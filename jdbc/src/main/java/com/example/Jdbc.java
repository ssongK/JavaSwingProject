package com.example;

import java.sql.*;

public class Jdbc {
    private Connection conn = null;
    private PreparedStatement insertPstm = null;
    private PreparedStatement selectPstm = null;
    private PreparedStatement updatePstm = null;
    private ResultSet rs = null;
    private String[] rank = new String[3];
    public int count = 0;
    private String url = "jdbc:mysql://user-info.cy5xlgs0kg5p.ap-northeast-2.rds.amazonaws.com:3306/user";
    
    // 생성자 : db와 연결하는 커넥션 객체(conn) 생성
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
    
    // 회원가입 : 사용자의 입력 값(name)을 db의 name 컬럼에 저장
    public void join(String name){
        try {
            String sql = "insert into user(name) values (?)";
            insertPstm = conn.prepareStatement(sql);
            insertPstm.setString(1, name);
            insertPstm.executeUpdate();
            System.out.println("가입 성공!");
            count=2;
    
        } catch (SQLException e) {
            System.out.println("중복된 이름입니다!");
            System.err.println(e);
            return;
        } 
        finally {
            try {
                if (rs != null) 
                    rs.close();

                if (insertPstm != null) 
                    insertPstm.close();
                
                if (selectPstm != null) 
                    selectPstm.close();
                
                if (conn != null) 
                    conn.close();
            } 
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 로그인 : 입력한 이름(name)이 db에 있는지 확인
    public void login(String name){
        try {
            conn = DriverManager.getConnection(url,"myUser","myUser");
            String sql = "select name from user where name=(?)";
            selectPstm = conn.prepareStatement(sql);
            selectPstm.setString(1, name);
            rs = selectPstm.executeQuery();
            rs.next();
            if(rs.getString("name").equals(name)){
                System.out.println("로그인 성공!");
                int count=1;
            }
            else System.out.println("로그인 실패!");
        } catch (SQLException e) {
            System.out.println("로그인 쿼리 실행 실패!");
            System.err.println(e);
        } 
    }

    // 게임 점수 저장
    public void saveGameScore(String gameName, int gameScore, String userName){
        try{
            String sql = "update user set "+gameName+"=(?) where name=(?)";
            updatePstm = conn.prepareStatement(sql);
            updatePstm.setInt(1, gameScore);
            updatePstm.setString(2, userName);
            updatePstm.executeUpdate();
            System.out.println("점수 기록 성공!");
        } 
        catch (SQLException e) {
            System.out.println("점수 기록 실패!");
            System.err.println(e);
        }
    }

    // 게임 랭킹 조회 : 결과는 String[]으로 리턴 -> JLabel에 setText()로 순위 기록
    public String[] gameRank(String gameName){
        try{
            String sql = "select name,"+gameName+" from user order by "+gameName+" desc";
            updatePstm = conn.prepareStatement(sql);
            rs = updatePstm.executeQuery();
            for(int i=0;i<3;i++){
                if(!rs.next()) break;
                rank[i] = rs.getString("name")+" - "+rs.getInt(gameName);
            }
            System.out.println("점수 조회 성공!");
        } 
        catch (SQLException e) {
            System.out.println("점수 조회 실패!");
            System.err.println(e);
        }
        return rank;
    }

    // 로그아웃 버튼 누르면 모든 객체 close
    public void logout(){
        try {
            if (rs != null) 
                rs.close();
            
            if (insertPstm != null) 
                insertPstm.close();
            
            if (selectPstm != null) 
                selectPstm.close();

            if (updatePstm != null) 
                updatePstm.close();
            
            if (conn != null) 
                conn.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        count=3;
    }
}
