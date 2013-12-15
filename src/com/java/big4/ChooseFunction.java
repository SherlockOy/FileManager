package com.java.big4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ChooseFunction {

	public static void main(String[] args) {

		while (true) {
			// TODO Auto-generated method stub

			// 提示语，提示用户进行跳转
			Utils.welcome();

			// 接收键盘输入的指令，运用scanner类
			Scanner sc = new Scanner(System.in);
			int functionNum = sc.nextInt();

			// 分支语句，方便用户选择功能
			switch (functionNum) {
			case 1:
				System.out.println("ListType, 显示所有文件类型\n");
				System.out.println("Enter the directory, please...");
				Scanner dir = new Scanner(System.in);
				String directory = dir.nextLine();
				// 提示输入待查询内容
				
				ListType.inputJudge(directory);
				
				break;
			case 2:
				//ListFile, 显示所有文件
				System.out.println("ListFile, 按要求显示文件\n");
				FunctionListFile listFile = new FunctionListFile();
				try {
					listFile.functionListFile();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("CleanFile, 清理文件\n");
				FunctionCleanFile cleanFile = new FunctionCleanFile();
				try {
					cleanFile.cleanfile();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				System.out.println("RenFile, 验证文件\n");
				FunctionRenFile renFile = new FunctionRenFile();
				try {
					renFile.renFile();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("RenFile, 按文件名搜索文件\n");
				FunctionSearchByName searchByName = new FunctionSearchByName();
				searchByName.functionSearchByName();
				break;
			case 6:
				System.out.println("RenFile, 按内容搜索文件\n");
				FunctionSearchByContent searchByContent = new FunctionSearchByContent();
				searchByContent.functionSearchByContent();
				break;
			default:
				System.out
						.println("Error: Unknow command, please retry.");
				break;
			}

		}
	}

}
