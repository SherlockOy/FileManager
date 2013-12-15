package com.java.big4;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class FunctionListFile {

	public void functionListFile() throws SQLException {

		System.out.println("Enter the directory, please...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();
		// 提示输入待查询内容
		System.out
				.println("Enter the name of file, if there is no paticular rules, press 'return' key...");
		Scanner fileName = new Scanner(System.in);
		String filename = fileName.nextLine();
		System.out.println("Searching...\n");
		// 连接数据库
		Connection conn = Utils.getConn();
		Statement statement = conn.createStatement();
		String sqlstatement = null;

		/*
		 * 输入路径，输入文件名，判断文件名是否为空，若为空列出数据库中对应路径的所有文件名，将数据库返回的结果存入ArrayList对象a中，
		 * 将目标目录下的文件Flie.List（）存入ArrayList对象b中，从b中逐一取出元素与a中所有元素对比，若存在该文件名，则输出文件名
		 * ，若不存在，则对b中下一个元素与a中所有元素对比。直到执行结束。输出“All results are listed.”
		 */

		if (filename.equals("")) {
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
			for (int fileIndex_fs = 0; fileIndex_fs < fn_fs.size(); fileIndex_fs++) {
				for (int fileIndex_db = 0; fileIndex_db < fn_db.size(); fileIndex_db++) {
					String a = fn_db.get(fileIndex_db);
					String b = fn_fs.get(fileIndex_fs);
					if (a.equals(b))
						System.out.println(fn_fs.get(fileIndex_fs));
				}
			}
			System.out.println("All results are listed.");

		} else {

			/*
			 * 输入路径，输入文件名，判断文件名是否为空，若不为空则将输入的文件名作为查询字段插入查询语句。
			 * 在文件系统中查找该文件，若不存在，输出“文件不存在”，若存在将文件名存入String对象中，之后用
			 * 先前的string值对数据库进行查找，看是否存在该文件的记录，若不存在，输出提示“本地文件找到符合
			 * 搜索要求的文件，但数据库中没有该文件的信息”，若存在，则直接输出本地该文件的信息。
			 */

	/*		File path = new File(directory);
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
	*/
			// 若指定了文件名，而该文件名在数据库中有记录，则输出该文件的详细信息
			sqlstatement = "SELECT * FROM test.FileList WHERE FileDir = '"
					+ directory + "';";
			ResultSet resultSet = statement.executeQuery(sqlstatement);

			while (resultSet.next()) {
				String name = resultSet.getString("FileName");
				String filedir2 = resultSet.getString("FileDir");
				String size = resultSet.getString("FileSize");

				System.out.println(name + "\t" + filedir2 + "\t" + size + "\n");
			}
		}

	}

}
