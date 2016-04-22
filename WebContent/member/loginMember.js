$(document).ready(function() {
	$('#loginMember').click(function() {
		
		var queryString = {
				m_id : $("#m_id").val(),
				m_password : $("#m_password").val()
		}
		
		var url = "/Tumblr/loginMemberPro.do";
		
		$.ajax({
			type : "POST",
			url : url,
			data : queryString,
			success : function(data) {
				if(check == "1") {
					window.location.href = "http://www.tumblr.com";
				} else if(check == "0") {
					alert("비밀번호 틀림");
					$("#m_password").val("");
				} else {
					alert("아이디 틀림");
					$("#m_id").val("");
					$("#m_password").val("");
				}
			}
		});
		
		
	});
	
	$('#cancel').click(function() {
		location.href = "/Tumblr/mainDisplayForm.do";
	});
	
	/*$('#login').click(function() {
//		 location.href = "main_body.jsp";
	});*/
});