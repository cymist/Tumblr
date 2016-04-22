package com.stitch.Tumblr.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandService {
	
	// ---------- 추상메서드 ---------- [ 1 ]
	public String service(HttpServletRequest request, HttpServletResponse response) throws Throwable;

}
