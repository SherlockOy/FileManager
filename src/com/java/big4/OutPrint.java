package com.java.big4;
import java.io.*;
import java.util.Date;

public class OutPrint {
	File Filename,FilePath;
	long FileSize;
	Date FileLast;
	public OutPrint(String Filename,String FilePath,long FileSize,Date FileLast)
	{
		this.Filename=new File(Filename);
		this.FilePath=new File(FilePath);
		this.FileSize=FileSize;
		this.FileLast=FileLast;
	}
	public void printAll()
	{
		System.out.println("details"+"\n"+" name\t\tsize\t   update time\t\t     direction");
		System.out.println(Filename.toString()
			+"\t   "+FileSize
			+"\t    "+FileLast.toString()
			+"  "+FilePath.toString());
		System.out.println("-------------------------------------------------------------------------------");
	}
	public static void printResult()
	{
			System.out.println("search finished, all results are listed.");
	}
}
