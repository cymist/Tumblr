package com.stitch.Tumblr.member.bean;

import java.util.Date;

public class MemberVO {

	// ---------- 변수 선언 ---------- [ 1 ]
	private String m_id;		// ----- 아이디 -----
	private String m_password;	// ----- 비밀번호 -----
	private String m_name;		// ----- 이름 -----
	private Date birth;		// ----- 생일 -----
	private String m_gender;		// ----- 성별 -----
	
	// ---------- Getters And Setters ---------- [ 2 ]
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	
	public String getM_password() {
		return m_password;
	}
	public void setM_password(String m_password) {
		this.m_password = m_password;
	}
	
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	public String getM_gender() {
		return m_gender;
	}
	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}
	
}
