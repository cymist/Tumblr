<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script src="/Tumblr/js/jquery-1.12.3.js"></script>
<script src="/Tumblr/member/loginMember.js"></script>

<jsp:include page="loginMemberHeader.jsp" flush="false" />

<div id="login_body">
		<h1>tumblr</h1>
		<div><input id="m_id" type="email" required placeholder="이메일"></div>
		<div><input id="m_password" type="password" required placeholder="비밀번호"></div>
		<div><input id="loginMember" type="button" value="로그인"></div>
		<div><input id="cancel" type="button" value="취소"></div>
</div>

<jsp:include page="mainFooter.jsp" flush="false" />
