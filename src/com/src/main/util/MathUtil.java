package com.src.main.util;

public class MathUtil {
	
	public static long ClickCooldown = 1;

	public static double get3DDistance(double x1, double y1, double z1, double x2, double y2, double z2){
		
		double changeX = (getHigherDouble(x1, x2) - getLowerDouble(x1, x2));
		double changeY = (getHigherDouble(y1, y2) - getLowerDouble(y1, y2));
		double changeZ = (getHigherDouble(z1, z2) - getLowerDouble(z1, z2));
		
		double dis = Math.sqrt((Math.pow(changeX, 2)) + (Math.pow(changeY, 2)) + (Math.pow(changeZ, 2)));
		
		return dis;
			
	}
	
	public static double get2DDistance(double x1, double z1, double x2, double z2){
		
		double changeX = (getHigherDouble(x1, x2) - getLowerDouble(x1, x2));
		double changeZ = (getHigherDouble(z1, z2) - getLowerDouble(z1, z2));
		
		double dis = Math.sqrt((Math.pow(changeX, 2)) + (Math.pow(changeZ, 2)));
		
		return dis;
			
	}
	
	public static void setCooldown(long i){
		ClickCooldown = i;
	}
	

	public static double getHigherDouble(double d1, double d2) {

		if (d1 > d2) {
			return d1;
		} else {
			return d2;
		}

	}

	public static double getLowerDouble(double d1, double d2) {

		if (d1 > d2) {
			return d2;
		} else {
			return d1;
		}

	}
	
}
