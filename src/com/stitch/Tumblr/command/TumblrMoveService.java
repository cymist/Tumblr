package com.stitch.Tumblr.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TumblrMoveService implements CommandService {

	@Override
	public String service(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		// ---------- return(응답 : response) 값 설정 ---------- [ 1 ]
		return "http://www.tumblr.com";
	}
}
