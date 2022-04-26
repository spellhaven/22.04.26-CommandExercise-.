package com.gyojincompany.ex;

import java.sql.*;

public class MemberDao {

	static String driverName = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/webdb";
	static String user = "root";
	static String password = "12345";
	
	public int insertMember(MemberDto dto) {
		
		String m_id = dto.getId();
		String m_pw = dto.getPw();
		String m_name = dto.getName();
		String m_email = dto.getEmail();
		String m_address = dto.getAddress();
		
		String sql = "INSERT INTO web_members(id, pw, name, email, address) VALUES('" + m_id + "','" + m_pw + "','" + m_name + "','" + m_email + "','" + m_address + "')";
		
		int dbFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;//sql 실행 객체
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로딩
			conn = DriverManager.getConnection(url, user, password);//DB 연동			
			pstmt = conn.prepareStatement(sql);
			
			dbFlag = pstmt.executeUpdate();//sql실행->실행 성공시 1 반환
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
		}
		
		return dbFlag;
	}
	
	public int confirmId(String id) { //가입여부를 확인
		
		int dbFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;//sql 실행 객체
		ResultSet rs = null;
		
		String sql = "SELECT id from web_members where id = ?";
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로딩
			conn = DriverManager.getConnection(url, user, password);//DB 연동			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//조건이 참이면 이미 가입된 아이디
				dbFlag = 1;				
			} else {
				dbFlag = 0;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
		}		
		
		return dbFlag;//가입이 되어 있으면 1이 반환, 아니면 0이 반환
	}
	
	public int userCheck(String id, String pw) {
		
		int dbFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;//sql 실행 객체
		ResultSet rs = null;
		
		String sql = "SELECT pw from web_members where id = ?";
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로딩
			conn = DriverManager.getConnection(url, user, password);//DB 연동			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//조건이 참이면 DB에 입력된 아이디가 존재함
				String dbPw = rs.getString("pw");
				if(dbPw.equals(pw)) {
					dbFlag = 1;//로그인 성공
				} else {
					dbFlag = 2;//비번이 틀림
				}
							
			} else { //조건이 거짓이면 DB에 입력된 아이디 존재하지 않음
				dbFlag = 0;//회원자체가 아님(db에 아이디 존재하지 않음)
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
		}		
		
		
		return dbFlag;
		//1이 반환되면 로그인성공, 0이 반환되면 회원아님(id 없음), 2가 반환되면 비번만 틀림
	}
	
	public MemberDto getMemberInfo(String id) {
		//System.out.println(id);
		
		MemberDto dto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;//sql 실행 객체
		ResultSet rs = null;
		
		String sql = "SELECT * from web_members where id = ?";
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로딩
			conn = DriverManager.getConnection(url, user, password);//DB 연동			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//조건이 참이면 DB에 입력된 아이디가 존재함
				
				dto = new MemberDto();
				//DB에 저장되어 있던 해당 아이디의 데이터 가져오기
				String dbId = rs.getString("id");
				String dbPw = rs.getString("pw");
				String dbName = rs.getString("name");
				String dbEmail = rs.getString("email");
				String dbAddr = rs.getString("address");
				
				dto.setId(dbId);
				dto.setPw(dbPw);
				dto.setName(dbName);
				dto.setEmail(dbEmail);
				dto.setAddress(dbAddr);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
		}	
		
		return dto;
	}
	
	public int modifyMemberInfo(MemberDto dto) {
		int dbFlag = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;//sql 실행 객체		
		
		String sql = "UPDATE web_members SET pw=?, email=?, address=? WHERE id = ?";
		
		try {
			Class.forName(driverName);//jdbc 드라이버 로딩
			conn = DriverManager.getConnection(url, user, password);//DB 연동			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getEmail());
			pstmt.setString(3, dto.getAddress());
			pstmt.setString(4, dto.getId());
			
			dbFlag = pstmt.executeUpdate();//수정 성공이면 1이 반환, 실패는 다른 값 반환
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}			
			
		}	
			
		return dbFlag;		
	}
}
