package com.java.big4;
import java.io.*;

public class FileContentCompare {
	File file;
	String str;
	boolean result=false;
	public FileContentCompare(String _file,String _str)
	{
		file=new File(_file);
		str=_str;
	}
	@SuppressWarnings("resource")
	public boolean result() 
	{
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bf=new BufferedReader(fr);
			String read;
			do
			{
				read=bf.readLine();
				if(read!=null&&read.indexOf(str)>=0)
				{
					result=true;
					break;
				}
			}while(read!=null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
	}
}
