package com.java.big4;
import java.io.File;
import java.util.Date;
public class FileContentSearcher {
	File path,temp;
	String name;
	public FileContentSearcher(File path,String name,File temp) 
	{
		this.path=path;
		this.name=name;
		this.temp=temp;
	}
	public void search()
	{
		String[] str=path.list();
		for(int i=0;i<str.length;i++)
		{
			File f=new File(path,str[i]);
			if(f.isFile())
			{
				FileContentCompare fnc=new FileContentCompare(f.toString(),name.toString());
				if(fnc.result())
				{
					System.out.println("搜索成功"+"\n"+"文件存储于: "+f.toString());
					Date date=new Date(f.lastModified());
					OutPrint nodedata=new OutPrint(f.getName().toString(),f.toString(),f.length(),date);
					nodedata.printAll();
				}
			}
			if(f.isDirectory())
			{    
				FileContentSearcher fs=new FileContentSearcher(f,name,temp); 
				fs.search();
			}
		}
	}
	
}
