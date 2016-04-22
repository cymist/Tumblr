$(document).ready(function(){
	$('#manLabel').click(function() {
		$('#manLabel').css('border','1px solid red');
		$('#manLabel').css('color','red');
		$('#womanLabel').css('border','1px solid black');
		$('#womanLabel').css('color','black');
	});
	
	$('#womanLabel').click(function() {
		$('#womanLabel').css('border','1px solid red');
		$('#womanLabel').css('color','red');
		$('#manLabel').css('border','1px solid black');
		$('#manLabel').css('color','black');
	});	
	
	$('#regitstButton').click(function() {
		
		var queryString = {
			m_id : $('#m_id').val(),
			m_password :  $('#m_password').val(),
			m_name : $('#m_name').val(),
//			year:  $('#year').val(),
//			month : $('#month').val(),
//			day :  $('#day').val(),
			m_gender :  $("input[name=m_gender]:checked").val()	
		};
		
		var url = '/Tumblr/insertMemberPro.do';
		
		$.ajax({
		   type: "POST",
		   url: url,
		   data: queryString,
		   success: function(data){
			   location.href = "/Tumblr/mainDisplayForm.do";
		   }
		});
	});
});