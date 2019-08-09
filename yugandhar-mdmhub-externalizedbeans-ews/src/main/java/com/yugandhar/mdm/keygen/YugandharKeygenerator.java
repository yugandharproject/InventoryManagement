package com.yugandhar.mdm.keygen;

import java.util.Calendar;
import java.util.Random;

public class YugandharKeygenerator extends AbstractYugandharKeygenerator {
	
	public String generateKey(){
	      Calendar calendar = Calendar.getInstance();
	      //Returns current time in millis
	      Long timeMilli2 = new Long(calendar.getTimeInMillis());	      
	      // create instance of Random class 
	       Random rand = new Random(); 
	       Integer rand_int1 = new Integer(rand.nextInt(100000)); 
	       //concatnate to create unique key
	     return (timeMilli2.toString() + rand_int1.toString());
		
		}
	
}
