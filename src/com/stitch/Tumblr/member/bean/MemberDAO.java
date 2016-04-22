package com.stitch.Tumblr.member.bean;

public interface MemberDAO {

	public void insertMember(MemberVO memberVO);	// ----- 회원 가입 처리 메서드 -----
	public int userCheck(String m_id, String m_password);	// ----- 로그인 폼 처리의 사용자 인증 처리 메서드 -----
	public int confirmId(String m_id);	// ----- 아이디 중복 확인에서 아이디의 중복 여부를 확인하는 메서드 -----
	public MemberVO getMember(String m_id);	// ----- 주어진 id에 해당하는 회원 정보를 얻어내는 메서드 -----
	public MemberVO getMember(String m_id, String m_password);	// ----- 주어진 id에 해당하는 회원 정보를 얻어내는 메서드 -----
	public int updateMember(MemberVO memberVO);	// ----- 회원 정보 수정을 처리하는 메서드 -----
	public int deleteMember(String m_id, String m_password);	// ----- 회원 정보를 삭제하는 메서드 -----
	
}
