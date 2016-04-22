package com.stitch.Tumblr.command;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stitch.Tumblr.member.bean.MemberDAOImple;

public class LoginMemberProService implements CommandService {

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		// ---------- 인코딩 ---------- [ 1 ]
		request.setCharacterEncoding("utf-8");
		
		// ---------- 회원가입 정보 셋팅 ---------- [ 2 ]
		String m_id = request.getParameter("m_id");
		String m_password = request.getParameter("m_password");

		// ---------- 사용자가 입력한 id, pw를 가지고 인증 체크 후 값 반환 ---------- [ 3 ]
		MemberDAOImple memberDAOImple = MemberDAOImple.getInstance();
		int check = memberDAOImple.userCheck(m_id, m_password);
		
		request.setAttribute("m_id", m_id);
		request.setAttribute("check", new Integer(check));
		return "/member/loginMemberPro.jsp";
	}

}
