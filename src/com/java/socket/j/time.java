package com.java.socket.j;

import java.text.SimpleDateFormat;
import java.util.Date;

public class time {
	public String regDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm]");
		
		return sdf.format(d);
	}
}
