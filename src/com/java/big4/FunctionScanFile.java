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
import java.util.Date;
import java.util.Scanner;

public class FunctionScanFile {
	public void scanFile() throws SQLException, IOException {

		// 输入路径

		System.out.println("Enter the path...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();

		// 建立数据库连接，取回结果集
		Connection conn = Utils.getConn();
		Statement statement = conn.createStatement();

		String sqlstatement = "SELECT FileKey FROM test.FileList WHERE FileDir='"
				+ directory + "';";
		ResultSet resultSet = statement.executeQuery(sqlstatement);

		// 将结果集存入ArrayList对象filekeys中
		ArrayList<String> filekeys = new ArrayList<String>();
		for (int i = 0; resultSet.next(); i++) {
			String filekey = resultSet.getString("FileKey");
			filekeys.add(filekey);
		}

		// 扫描路径下的文件内容，文件夹跳过，文件存入ArrayList对象files中
		File path = new File(directory);
		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < path.list().length; i++) {

			File f = new File(path, path.list()[i]);
			if (f.isFile())
				files.add(f);
		}
		/*
		 * 遍历files里面的file对象的fk值，如果找到和数据库数据匹配的，不做操作，如果没找到，获取信息插入数据库
		 */

		for (int i = 0; i < files.size(); i++) {

			// 读取files路径下的子文件对象f，建立Path对象
			File f = new File(directory + files.get(i).getName());
			Path fp = files.get(i).toPath();
			BasicFileAttributes attrs = Files.readAttributes(fp,
					BasicFileAttributes.class);
			// 获取单个文件的FileKey，
			String fk_fs = attrs.fileKey().toString();

			int found = 0;
			// 取单条数据库记录，利用fk对文件进行比较
			for (int j = 0; j < filekeys.size(); j++) {

				String fk_db = filekeys.get(j);
				// 若找到，设置found为1
				if (fk_fs.equals(fk_db)) {
					found = 1;
					break;
				}
			}
			if (found != 1) {
				// 如果没找到的话执行插入操作
				String fn_fs = f.getName();
				Date lastModified = new Date(f.lastModified());
				System.out
						.println("Insert record '" + fn_fs + "' to Database.");
				String sqlinsert = "INSERT INTO `test`.`FileList` (`FileName`, `FileDir`, `FileKey`, `LastModified`) VALUES ('"
						+ fn_fs
						+ "', '"
						+ directory
						+ "', '"
						+ fk_fs
						+ "', '"
						+ lastModified + "');";
				statement.execute(sqlinsert);
			}

		}
		System.out.println("Scanning finished, all new files are recorded in the database.");
	}
}
