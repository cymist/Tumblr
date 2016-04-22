package com.stitch.Tumblr.dbinterface;

import java.sql.Connection;

public interface DBConnector {
	
	public Connection getConnection() throws Exception;		// ----- DB 연결 메서드 -----
	public void getClose() throws Exception;				// ----- DB 연결 종료 메서드 -----

}
