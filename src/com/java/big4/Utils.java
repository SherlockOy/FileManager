package com.java.big4;

/*
 * 工具类，提供静态方法
 * */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.mysql.jdbc.StringUtils;

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
	//静态方法调用，用以输出初始提示语句
	System.out.println("==============================================================");
	System.out.println("Please choose the funtion you want to use:");
	System.out.println("(1) stands for 'ListType'");
	System.out.println("(2) stands for 'ListFile'");
	System.out.println("(3) stands for 'CleanFile'");
	System.out.println("(4) stands for 'RenFile'");
	System.out.println("(5) stands for 'ScanFile'");
	System.out.println("(6) stands for 'Search File By Content");
	System.out.println("(7) stands for 'Quit'");
	System.out.println("Now pick your choice, end with 'enter' key...");
	
}

public static void backtochoose(){
	//按回车键继续，提高用户体验
	System.out.println("Press enter to continue...");
	Scanner back = new Scanner(System.in);
	String goback = back.nextLine();
	while(!goback.equals("")){
		break;
	}
}

public static boolean wildcardMatch(String pattern, String str) {
    int patternLength = pattern.length();
    int strLength = str.length();
    int strIndex = 0;
    char ch;
    for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
        ch = pattern.charAt(patternIndex);
        if (ch == '*') {
            //通配符星号*表示可以匹配任意多个字符
            while (strIndex < strLength) {
                if (wildcardMatch(pattern.substring(patternIndex + 1),
                        str.substring(strIndex))) {
                    return true;
                }
                strIndex++;
            }
        } else if (ch == '?') {
            //通配符问号?表示匹配任意一个字符
            strIndex++;
            if (strIndex > strLength) {
                //表示str中已经没有字符匹配?了。
                return false;
            }
        } else {
            if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
                return false;
            }
            strIndex++;
        }
    }
    return (strIndex == strLength);
}
}
