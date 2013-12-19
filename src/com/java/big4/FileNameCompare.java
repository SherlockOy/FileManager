package com.java.big4;

public class FileNameCompare {
	String filea = "";
	String fileb = "";
	boolean result = false;

	public FileNameCompare(String fa, String fb) {
		filea = fa;
		fileb = fb;
	}

	public boolean result() {
		if (filea.equals(fileb.toString())) {
			result = true;
		} else {
			int temp = 0;
			/*
			 * while(temp<fileb.length()&&fileb.indexOf(temp)!='*') { temp++; }
			 */
			temp = fileb.indexOf("*");
			// System.out.println("temp"+temp);
			if (temp != -1) {
				 
				String sub = fileb.substring(0, temp);
				// System.out.println("sub:"+sub);
				// System.out.println("filea.indexOf(sub):"+filea.indexOf(sub));
				if (filea.indexOf(sub) == 0) {
					result = true;
				} else {
					result = false;
				}
			}
			/*
			 * else {
			 * 
			 * }
			 */

			/*
			 * for(b=0;b<temp;b++) { if(fileb.charAt(b)==filea.charAt(a)) {
			 * result=true; } else { result=false; } }
			 */
		}
		return result;
	}
}