package com.java.big4;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class FunctionCleanFile {
	File path;
	File temp;
	String name;

	public void setPath(File path) {
		this.path = path;
	}

	public void setTemp(File temp) {
		this.temp = temp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void cleanfile() throws SQLException {

		System.out.println("Enter the directory you want to clean, please...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();
		// 提示输入待查询内容

		System.out.println("Searching...\n");
		// 连接数据库
		Connection conn = Utils.getConn();
		Statement statement = conn.createStatement();
		String sqlstatement = null;

		sqlstatement = "SELECT FileName FROM test.FileList WHERE FileDir = '"
				+ directory + "';";
		ResultSet resultSet = statement.executeQuery(sqlstatement);

		ArrayList<String> fn_db = new ArrayList<String>();
		ArrayList<String> fn_fs = new ArrayList<String>();

		// 获取数据库中的结果集，存入ArrayList对象fn_db中
		while (resultSet.next()) {
			String name_db = resultSet.getString("FileName");
			fn_db.add(name_db);
		}

		// 获取文件系统中的结果集，存入ArrayList对象fn_fs中
		File path = new File(directory);
		File temp = new File("temp.search");
		temp.delete();
		String str[] = path.list();

		for (int i = 0; i < str.length; i++) {
			File f = new File(directory, str[i]);
			if (f.isDirectory())
				;
			else if (f.isFile())
				fn_fs.add(str[i]);

		}

		// 对比文件系统中的文件名和数据库中的文件名是否相同，若相同输出文件系统中的文件名，文件系统标识指向下一个文件，数据文件标识指向下一个，
		// 若不相等，查找下一个

		ArrayList<String> unmatchFile = new ArrayList<String>();

		for (int fileIndex_fs = 0; fileIndex_fs < fn_fs.size(); fileIndex_fs++) {
			int found = 0;

			for (int fileIndex_db = 0; fileIndex_db < fn_db.size(); fileIndex_db++) {

				String a = fn_db.get(fileIndex_db);
				String b = fn_fs.get(fileIndex_fs);
				if (b.equals(a)) {
					found = 1;
					break;
				}
			}
			if (found == 0)
				unmatchFile.add(fn_fs.get(fileIndex_fs));

		}
		
		for (int i = 0;i < unmatchFile.size();i++){
			
			File f = new File(directory+unmatchFile.get(i));
			System.out.println(f.getName());
			f.delete();
			
		}
		
		
		System.out.println("All results are listed.");

	}
}
