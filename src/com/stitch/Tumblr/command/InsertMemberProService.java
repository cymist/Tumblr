package com.stitch.Tumblr.command;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stitch.Tumblr.member.bean.MemberDAOImple;
import com.stitch.Tumblr.member.bean.MemberVO;

public class InsertMemberProService implements CommandService {

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		// ---------- 인코딩 ---------- [ 1 ]
		request.setCharacterEncoding("utf-8");
		
		// ---------- 회원가입 정보 셋팅 ---------- [ 2 ]
		String m_id = request.getParameter("m_id");
		String m_password = request.getParameter("m_password");
		String m_name = request.getParameter("m_name");
		String m_gender = request.getParameter("m_gender");
		
//		String year = request.getParameter("year");
//		String month = request.getParameter("month");
//		String day = request.getParameter("day");
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//		String date = year + month + day;
//		Date birth = sdf.parse(date);
		
		
		MemberVO memberVO = new MemberVO();
		
		memberVO.setM_id(m_id);
		memberVO.setM_password(m_password);
		memberVO.setM_name(m_name);
		memberVO.setM_gender(m_gender);
//		memberVO.setBirth(birth);
		
		// ---------- 회원 가입 처리 ---------- [ 3 ]
		MemberDAOImple memberDAOImple = MemberDAOImple.getInstance();
		memberDAOImple.insertMember(memberVO);
		
		return "/member/insertMemberPro.jsp";
	}

}
