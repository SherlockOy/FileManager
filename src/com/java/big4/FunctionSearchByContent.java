package com.java.big4;

import java.io.File;
import java.util.Scanner;

public class FunctionSearchByContent {

	File path;
	String name;
	String content;
	File temp;

	public void functionSearchByContent() {
		System.out.println("根据文件内容搜索.");
		System.out.println("请输入路径...");
		Scanner dir = new Scanner(System.in);
		String direction = dir.nextLine();
		// 提示输入待查询内容
		System.out.println("请输入要查询的内容...");
		Scanner cnt = new Scanner(System.in);
		String filecontent = cnt.nextLine();
		System.out.println("正根据内容进行搜索...");
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
