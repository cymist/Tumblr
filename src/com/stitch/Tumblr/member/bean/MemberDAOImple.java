package com.stitch.Tumblr.member.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.stitch.Tumblr.dbinterface.DBConnector;
import com.stitch.Tumblr.member.crypt.BCrypt;
import com.stitch.Tumblr.member.crypt.SHA256;

public class MemberDAOImple implements MemberDAO, DBConnector {

	private Connection connector = null;
	
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦	[ 싱글톤 객체 ]
	/*	LogonDAOImple 클래스의 인스턴스를 생성하지 못하지만 이 메서드를 접근함으로써 
		인스턴스를 사용 및 공유 가능하게 한다
		또한 생성자를 통해 이 클래스의 인스턴스를 생성하지 못하도록 private 접근 한정자를 설정해준다				*/
	
	// ---------- LogonDAOImple 전역 객체 생성 ---------- [ 1 ]
	private static MemberDAOImple MemberDAOImple_INSTANCE = new MemberDAOImple();
	
	// ---------- LogonDAOImple 객체를 리턴하는 메서드 ---------- [ 2 ]
	public static MemberDAOImple getInstance() {
		return MemberDAOImple_INSTANCE;
	}

	// ---------- 생성자로 인스턴스를 생성하지 못하게 한다 ---------- [ 3 ]
	private MemberDAOImple() {}
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 싱글톤 객체 ]
	
	
	
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 커넥션 풀 설정 ]
	// ---------- 커넥션 풀에서 커넥션 객체를 얻어내는 메서드 ---------- [ 1 ]
	@Override
	public Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jdbc/stitch");	// ----- database 이름 명시 -----
//		connector = ds.getConnection();
		
		return ds.getConnection();	// ----- Connection 객체를 반환한다 -----
	}
	
	// ---------- DB 연결 종료 메서드 ----------[ 2 ]
	@Override
	public void getClose() throws Exception {
		if(connector != null){
			try {
				connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 커넥션 풀 설정 ]
	
	
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 회원가입 처리 ]
	// ---------- 회원 가입 처리(registerPro.jsp)에서 사용하는 새 레코드 추가 메서드 ----------
	@Override
	public void insertMember(MemberVO memberVO) {
		
		// ---------- DB 연결 및 사용 객체 선언 ---------- [ 1 ]
		PreparedStatement pstmt = null;	// ----- 쿼리문 관리 및 셋팅, 실행 객체 -----
		
		// ---------- 비밀번호 암호화 객체 추출 ---------- [ 2 ]
		SHA256 sha = SHA256.getInsatnce();
		
		try{
			// ----------  DB 접속 ---------- [ 3 ]
			connector = this.getConnection();	// ----- ConnectionPool에 생성되어 있는 connection 객체를 사용 -----
			
			// ---------- 비밀번호 암호화 ---------- [ 4 ]
			/* << 순서 >>
			 * 1. 평문(String 타입)의 비밀번호를 암호화 하기 위해서 변수에 저장
			 * 2. String 타입을 바이트 타입으로 변환 후 SHA256 Class의 getSha256() 메서드를 이용하여 파싱(암호화)
			 * 3. SHA256 암호화 기법으로 암호화된 password를 BCrypt Class의 gensalt() 메서드를 이용하여 파싱(암호화)
			 */
			String orgPass = memberVO.getM_password();
			String shaPass = sha.getSha256(orgPass.getBytes());
			String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
			
			// ---------- 쿼리문 작성 ---------- [ 5 ]
			String sql = "insert into member(m_id, m_password, m_name, m_gender) values(?,?,?,?)";
			
			// ---------- 쿼리문 관리 및 셋팅 ---------- [ 6 ]
			pstmt = connector.prepareStatement(sql);
			pstmt.setString(1, memberVO.getM_id());	// ----- 암호화 시킨 비밀번호를 저장한다 -----
			pstmt.setString(2, bcPass);
			pstmt.setString(3, memberVO.getM_name());
			pstmt.setString(4, memberVO.getM_gender());
			
			// ---------- 쿼리문 실행 ---------- [ 7 ]
			pstmt.executeUpdate();	// ----- Return이 없기 때문에 executeUpdate() 메서드 사용 -----
			
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if(pstmt != null){		// ----- PreparedStatement 사용 종료 -----
				try{
					pstmt.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}
			
			if(connector != null){		// ----- DB Connection 종료 -----
				try {
					connector.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 회원가입 처리 ]
	
	
	
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 유저 유효성 체크 ]
	/*
	 * (non-Javadoc)
	 * @see com.gno.BoardOnMember.member.MemberDAO#userCheck(java.lang.String, java.lang.String)
	 * 
	 * 	loginForm.jsp를 처리하는 loginPro.jsp 페이지의 사용자 인증 처리 및 
	 * 	회원 정보 수정 / 탈퇴를 처리하는 memberCheck.jsp에서 사용하는 메서드
	 */
	@Override
	public int userCheck(String m_id, String m_password) {

		// ---------- DB 연결 및 사용 객체 선언 ---------- [ 1 ]
		PreparedStatement pstmt = null;			// ----- 쿼리문 관리 및 셋팅, 실행 객체 -----
		ResultSet rs = null;					// ----- DB로부터 넘어온 데이터를 추출할 객체 -----
		int x = -1;								// ----- 리턴 값 설정을 위한 변수 -----
		
		// ---------- 비밀번호 암호화 객체 추출 ---------- [ 2 ]
		SHA256 sha = SHA256.getInsatnce();
		
		try{
			// ---------- DB 접속 ---------- [ 3 ]
			connector = this.getConnection();	// ----- ConnectionPool에 생성되어 있는 Connection 객체를 사용 -----
			
			// ---------- 비밀번호 암호화 ---------- [ 4 ]
			/* << 순서 >>
			 * 1. 평문(String 타입)의 비밀번호를 암호화 하기 위해서 변수에 저장
			 * 2. String 타입을 바이트 타입으로 변환 후 SHA256 Class의 getSha256() 메서드를 이용하여 파싱(암호화)
			 */
			String orgPass = m_password;
			String shaPass = sha.getSha256(orgPass.getBytes());
			
			// ---------- 쿼리문 작성 ---------- [ 5 ]
			String sql = "SELECT m_password FROM member WHERE id = ?";
			
			// ---------- 쿼리문 관리 및 셋팅 ---------- [ 6 ]
			pstmt = connector.prepareStatement(sql);
			pstmt.setString(1, m_id);				// ----- 쿼리문의 ?값 셋팅 -----
			
			// ---------- 쿼리문 실행 ---------- [ 7 ]
			rs = pstmt.executeQuery();				// ----- 쿼리문을 실행하고 리턴 값을 받는다 -----
			
			// ---------- DB로부터 넘어온 데이터 추출 및 셋팅 ---------- [ 8 ]
			if(rs.next()) {							// ----- 쿼리를 실행한 결과 DB에 해당 아이디가 있으면 수행 -----
				String dbpassword = rs.getString("m_password");	// ----- DB에서 넘어온 데이터 중 m_password를 추출 후 저장 -----
				if(BCrypt.checkpw(shaPass, dbpassword)) {		// ----- sha256에 의해 암호화 된 비밀번호와 DB에서 넘어온 비밀번호를 비교 -----
					x = 1;		// ----- 아이디와 비밀번호 일치(즉, 인증 성공) -----
				} else {
					x = 0;		// ----- 아이디는 일치하지만 비밀번호가 틀림(인증 실패) -----
				}
			} else {		// ----- 쿼리를 실행한 결과 DB에 해당 아이디가 없으면 수행 -----
				x = -1;			// ----- 아이디 없음 -----
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null){		// ----- ResultSet 사용 종료 -----
				try{
					rs.close();
				} catch(SQLException e){
					
				}
			}
			
			if(pstmt != null){		// ----- PreparedStatement 사용 종료 -----
				try{
					pstmt.close();
				} catch(SQLException e){
					
				}
			}
			
			if(connector != null){		// ----- DB Connection 종료 -----
				try{
					connector.close();
				} catch(SQLException e){
					
				}
			}
		}
		
		
		return x;	// ----- x =- 1, 0, -1 중의 하나의 값이 리턴된다 -----
	}
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 유저 유효성 체크 ]

	@Override
	public int confirmId(String m_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberVO getMember(String m_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO getMember(String m_id, String m_password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String m_id, String m_password) {
		// TODO Auto-generated method stub
		return 0;
	}




}
