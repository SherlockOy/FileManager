package com.java.big4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Utils {
public static Connection getConn(){
		//建立连接到mysql数据库
		Connection conn = null;
		try {			
			Class.forName("com.mysql.jdbc.Driver");//获取驱动程序的名称			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");//建立连接	地址端口，用户名，密码		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

public static void closeConn(ResultSet rs,PreparedStatement pstmt, Connection conn){

	//释放连接
	try {
		
		if(rs!=null) rs.close();
		
		if(pstmt!=null) pstmt.close();
		
		if(conn!=null && conn.isClosed()==false) conn.close();
		
	} catch (Exception e) {			
		e.printStackTrace();
	}		
}

public static void welcome(){
	System.out.println("Please choose the funtion you want to use:");
	System.out.println("(1) stands for 'ListType'");
	System.out.println("(2) stands for 'ListFile'");
	System.out.println("(3) stands for 'CleanFile'");
	System.out.println("(4) stands for 'RenFile'");
	System.out.println("(5) stands for 'Search File By Name'");
	System.out.println("(6) stands for 'Search File By Content'\n");
	System.out.println("now enter your choice, end with 'return' key...");
}

}
