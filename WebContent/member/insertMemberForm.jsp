<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script src="/Tumblr/js/jquery-1.12.3.js"></script>
<script src="/Tumblr/member/insertMember.js"></script>
<title>회원가입</title>
</head>
<body>
	<div id="memberInsert_body">
		<div><input id="m_id" type="email" placeholder="이메일" required></div>
		<div><input id="m_password" type="password" placeholder="비밀번호" required></div>
		<div><input id="m_passwordCheck" type="password" placeholder="비밀번호 재확인" required></div>
		<div><input id="m_name" type="text" placeholder="닉네임" required></div>
		
		<%--
		<div>
			<input class="birth" id="year" type="text" placeholder="ex.1991" maxlength="4" required>
			<select class="birth" id="month">
				<option value="0" selected disabled>월</option>
				<option value="01">1</option>
				<option value="02">2</option>
				<option value="03">3</option>
				<option value="04">4</option>
				<option value="05">5</option>
				<option value="06">6</option>
				<option value="07">7</option>
				<option value="08">8</option>
				<option value="09">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
			</select>
			<input class="birth" id="day" type="text" placeholder="일" maxlength="2" required>
		</div>
		--%>
		
		<div>
			<input id="man" name="m_gender" type="radio" value="male">
			<label id="manLabel" for="man">남성</label>
			<input id="woman" name="m_gender" type="radio" value="female">
			<label id="womanLabel" for="woman">여성</label>
		</div>
		<div><input id="regitstButton" type="button" value="가입하기"></div>
	</div>
</body>
</html>