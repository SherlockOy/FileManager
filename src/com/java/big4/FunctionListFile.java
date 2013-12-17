package com.java.big4;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FunctionListFile {

	public void functionListFile() throws SQLException {

		System.out.println("请输入路径...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();
		// 提示输入待查询内容
		System.out
				.println("输入文件名，如果没有特别要求，请直接敲击回车...");
		Scanner fileName = new Scanner(System.in);
		String filename = fileName.nextLine();
		System.out.println("查找中...若有冲突将按照数据库内的内容进行筛选\n");
		// 连接数据库
		Connection conn = Utils.getConn();
		Statement statement = conn.createStatement();
		String sqlstatement = null;
		// 获取数据库内容，返回含有文件名的内容
		sqlstatement = "SELECT FileName FROM test.FileList WHERE FileDir = '"
				+ directory + "';";
		ResultSet resultSet = statement.executeQuery(sqlstatement);

		/*
		 * 输入路径，输入文件名，判断文件名是否为空，若为空列出数据库中对应路径的所有文件名，将数据库返回的结果存入ArrayList对象a中，
		 * 将目标目录下的文件Flie.List（）存入ArrayList对象b中，从b中逐一取出元素与a中所有元素对比，若存在该文件名，则输出文件名
		 * ，若不存在，则对b中下一个元素与a中所有元素对比。直到执行结束。输出“All results are listed.”
		 */

		if (filename.equals("")) {

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
			System.out
					.println("==============================================================");
			// 对比文件系统中的文件名和数据库中的文件名是否相同，若相同输出文件系统中的文件名，文件系统标识指向下一个文件，数据文件标识指向下一个，
			// 若不相等，查找下一个

			for (int fileIndex_fs = 0; fileIndex_fs < fn_fs.size(); fileIndex_fs++) {
				for (int fileIndex_db = 0; fileIndex_db < fn_db.size(); fileIndex_db++) {
					String a = fn_db.get(fileIndex_db);
					String b = fn_fs.get(fileIndex_fs);
					if (a.equals(b))
						System.out.println(fn_fs.get(fileIndex_fs));
				}
			}
			System.out.println("\n所有的结果均已输出...");
			System.out
					.println("==============================================================");

		} else {

			 
			// 若指定了文件名，而该文件名在数据库中有记录，则输出该文件的详细信息
			// 将数据库中的内容存入ArrayList对象fn_db
			ArrayList<String> fn_db = new ArrayList<String>();
			while (resultSet.next()) {
				String name_db = resultSet.getString("FileName");
				fn_db.add(name_db);
			}

			File path = new File(directory);
			ArrayList<File> files = new ArrayList<File>();

			//将目录下的文件存入files对象
			for (int i = 0; i < path.list().length; i++) {
				File f = new File(directory + path.list()[i]);
				files.add(f);
			}

			//调用Utils类中的通配符，对输入的命令和每个文件的文件名进行模式匹配，若匹配成功，
			//对匹配成功的文件和数据库中的记录进行比对，如果找到该记录，则输出该文件的详细信息
			String targetName = filename;
			System.out
					.println("==============================================================");
			for (int i = 0; i < files.size(); i++) {
				String name = files.get(i).getName();
				File f = files.get(i);
				//模式匹配
				boolean matchresult = Utils.wildcardMatch(targetName, name);
				if (matchresult) {
					for (int j = 0; j < fn_db.size(); j++) {
						if(name.equals(fn_db.get(j))){
						Date lastmodified = new Date(f.lastModified());
						System.out.println(name + "\t" + directory + "\t"
								+ lastmodified);
						}
					}
				}
			}
			System.out.println("\n所有的结果均已输出...");
			System.out
					.println("==============================================================");

		}

	}
}
