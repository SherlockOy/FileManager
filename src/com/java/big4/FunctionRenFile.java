package com.java.big4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class FunctionRenFile {

	public void renFile() throws SQLException, IOException {

		// 选择路径
		System.out.println("Enter the path...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();

		// 连接数据库，读取所有记录中的FileName字段和FileKey字段，返回结果集ResultSet
		Connection conn = Utils.getConn();
		Statement statement = conn.createStatement();
		String sqlstatement = "SELECT FileName, FileKey FROM test.FileList WHERE FileDir='"
				+ directory + "';";
		ResultSet resultSet = statement.executeQuery(sqlstatement);

		// 数据库中找到FileKey的值之后和每个文件的那个值进行对比
		while (resultSet.next()) {

			// 将目录文件下的文件对象都存入ArrayList对象files中，每次进入循环都会更新现有的files对象
			File path = new File(directory);
			String str[] = path.list();
			ArrayList<File> files = new ArrayList<File>();
			for (int i = 0; i < path.list().length; i++) {
				File f = new File(path, path.list()[i]);
				files.add(f);
			}

			String fk_db = "";
			String fn_db = "";

			fn_db = resultSet.getString("FileName");
			fk_db = resultSet.getString("FileKey");

			for (int i = 0; i < files.size(); i++) {
				// 取单个对象
				Path fp = files.get(i).toPath();
				BasicFileAttributes attrs = Files.readAttributes(fp,
						BasicFileAttributes.class);
				String fk_fs = attrs.fileKey().toString();
				// 如果数据库中的fk与文件系统中的fk相等，比较数据库中的文件名与文件系统中的文件名，若不相等，改名，相等，往下
				if (fk_db.equals(fk_fs)) {
					String fn_fs = files.get(i).getName();
					if (!(fn_fs.equals(fn_db))) {
						System.out
								.println("According to the data in Database, change '"
										+ fn_fs + "' to '" + fn_db + "'.");
						File newFile = new File(directory + fn_db);
						files.get(i).renameTo(newFile);
						// files.get(i) = newFile;
					}
					break;
				}
			}
		}
	}
}
