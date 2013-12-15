package com.java.big4;
import java.io.File;
import java.util.Date;
public class FileNameSearcher {
	File path,temp;
	String name;
	//public FileNameSeacher(File path,String name,File temp) 
	//{
	//	this.path=path;
	//	this.name=name;
	//	this.temp=temp;
	//}
	
	
	
	void setName(String name){
		this.name = name;
	}
	void setPath(File path){
		this.path = path;
	}
	void setTemp(File temp){
		this.temp = temp;
	}
	public void search()
	{
		String[] str=path.list();
		for(int i=0;i<str.length;i++)
		{
			File f=new File(path,str[i]);
			if(f.isFile())
			{
				FileNameCompare fnc=new FileNameCompare(f.getName().toString(),name.toString());
				if(fnc.result())
				{
					System.out.println("search success"+"\n"+"Flie is under direction: "+f.toString());
					Date date=new Date(f.lastModified());
					OutPrint nodedata=new OutPrint(f.getName().toString(),f.toString(),f.length(),date);
					nodedata.printAll();
				}
			}
			if(f.isDirectory())
			{    
				//FileNameSeacher fs=new FileNameSeacher(f,name,temp); 
				FileNameSearcher fs=new FileNameSearcher(); 
				fs.setName(name);
				fs.setPath(f);
				fs.setTemp(f);
				fs.search();
			}
		}
		
	}
	public void outPrint()
	{
			System.out.println("search finished, all results are listed.");
	}
}
