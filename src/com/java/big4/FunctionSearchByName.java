package com.java.big4;

import java.io.File;
import java.util.Scanner;

public class FunctionSearchByName {

	File path;
	String name;
	File temp;
	
	public void functionSearchByName() {
		System.out.println("Search File By Name, search by file name.");
		System.out.println("Enter the direction, please...");
		Scanner dir = new Scanner(System.in);
		String directory = dir.nextLine();
		// 提示输入文件名
		System.out.println("Enter the filename, please...");
		Scanner fn = new Scanner(System.in);
		String filename = fn.nextLine();
		System.out.println("searching by name...\n");
		// 分支主体部分功能
		path = new File(directory);//带路径的文件对象
		name = filename;//文件名
		temp = new File("temp.search");
		temp.delete();
		
		FileNameSearcher fns = new FileNameSearcher();
		//FileNameSeacher fns = new FileNameSeacher();
		fns.setName(filename);
		fns.setPath(path);
		fns.setTemp(temp);
		fns.search();
		//OutPrint.printResult();
	}
	
}

