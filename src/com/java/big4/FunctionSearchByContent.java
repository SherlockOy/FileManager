package com.java.big4;

import java.io.File;
import java.util.Scanner;

public class FunctionSearchByContent {

	File path;
	String name;
	String content;
	File temp;

	public void functionSearchByContent() {
		System.out.println("Search File By Content, search by file content.");
		System.out.println("Enter the direction, please...");
		Scanner dir = new Scanner(System.in);
		String direction = dir.nextLine();
		// 提示输入待查询内容
		System.out.println("Enter the content, please...");
		Scanner cnt = new Scanner(System.in);
		String filecontent = cnt.nextLine();
		System.out.println("searching by content...\n");
		// 分支主体部分功能
		path = new File(direction);
		content = filecontent;
		temp = new File("temp.search");
		temp.delete();
		FileContentSearcher fcs = new FileContentSearcher(path, content, temp);
		fcs.search();
		OutPrint.printResult();

	}
}
