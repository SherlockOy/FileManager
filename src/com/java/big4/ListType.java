package com.java.big4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.DecimalFormat;

public class ListType {

	/**
	 * @param args
	 */

	// 初始键入判断
	public static void inputJudge(String path) {
		File f = new File(path);
		Scanner input = new Scanner(System.in);
		System.out.println("请输入文件类型,查找全部则输入'*'");
		String type = input.next();
		if ("*".equals(type)) {
			listType(f);
			formetSize(getDirSize(f));
		} else {
			formetSize(fileType(type, f));
		}

	}

	// 输出所有文件类型信息
	public static void listType(File f) {
		List<Object> dirs = new ArrayList<Object>();
		Map<Object, Object> fileType = new HashMap<Object, Object>();
		dirs.add(f.listFiles());
		for (int i = 0; i < dirs.size(); i++) {
			File[] files = (File[]) dirs.get(i);
			for (int j = 0; j < files.length; j++) {
				if (files[j].isDirectory()) {
					dirs.add(files[j].listFiles());
				} else {
					String fName = files[j].getName(); // 返回文件目录名
					String[] ff = fName.split("\\."); // 划分路径
					String eName = "";
					if (ff.length == 2)
						eName = ff[1]; // 取文件名
					if (fileType.containsKey(eName)) { // 是否此映射包含指定键的映射关系
						Integer size = (Integer) fileType.get(eName); // 强制转换文件后缀到size对象
						size = new Integer(size.intValue() + 1);// 传输int值
						fileType.put(eName, size); // 关联eName和size
					} else {
						Integer size = new Integer(1);
						fileType.put(eName, size);
					}
				}
			}
		}
		for (Iterator<Object> key = fileType.keySet().iterator(); key.hasNext();) {
			String eName = key.next().toString();
			Integer size = (Integer) fileType.get(eName);
			System.out.println(eName + "  " + size + "个");
		}
	}

	// 计算所有文件大小，返回size值
	public static long getDirSize(File f) {
		long size = 0;
		if (f.isDirectory()) { // 判断文件夹是否存在
			File[] files = f.listFiles();
			for (File file : files) {// foreach语句遍历
				if (file.isFile())
					size += file.length();
				else if (file.isDirectory()) { // 判断是否还有文件夹，并遍历子文件夹
					size += file.length();
					size = size + getDirSize(file);
				}
			}
		}
		return size;
	}

	// 格式换算
	public static void formetSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		System.out.println("总文件大小为:" + fileSizeString);
	}

	// 输出指定类型文件信息,以及返回文件大小size值
	public static long fileType(String type, File f) {
		List<Object> dirs = new ArrayList<Object>();
		Map<Object, Object> fileType = new HashMap<Object, Object>();
		dirs.add(f.listFiles());
		long size = 0; // 初始化“文件大小”
		for (int i = 0; i < dirs.size(); i++) {
			File[] files = (File[]) dirs.get(i);
			for (int j = 0; j < files.length; j++) {
				if (files[j].isDirectory()) {
					dirs.add(files[j].listFiles());
				} else {
					String fName = files[j].getName(); // 返回文件目录名
					String[] ff = fName.split("\\."); // 划分文件名中的“.”前后内容
					String eName = ""; // 初始化字符串“后缀名”
					if (ff.length == 2)
						eName = ff[1]; // 取文件后缀名
					if (eName.equals(type)) {
						size += files[j].length();
					}
					if (fileType.containsKey(eName)) { // 是否此映射包含指定键的映射关系
						Integer count = (Integer) fileType.get(eName);
						count = new Integer(count.intValue() + 1);// 个数count+1

						fileType.put(eName, count); // 关联eName和count;
					} else {
						Integer count = new Integer(1);
						fileType.put(eName, count);
					}
				}
			}

		}

		for (Iterator<Object> key = fileType.keySet().iterator(); key.hasNext();) {
			String eName = key.next().toString();
			Integer count = (Integer) fileType.get(eName);
			if (eName.equals(type)) {
				System.out.println(eName + "类型: " + count + "个");

			}
		}

		return size;
	}

}
