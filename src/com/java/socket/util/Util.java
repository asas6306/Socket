package com.java.socket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public String getRegDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm]");
		
		return sdf.format(d);
	}
}
