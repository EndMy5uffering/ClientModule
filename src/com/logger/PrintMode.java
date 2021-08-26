package com.logger;

public class PrintMode {

	// 0 0 0 0 | 0 0 0 0 0
	
	//mode debug | event
	//type detailed | noTrace | noPrefix
	
	public static byte DEBUG = 0x1;	
	public static byte EVENT = 0x2;
	public static byte DETAILED = 0x4;
	public static byte NO_TRACE = 0x8;
	public static byte NO_PREFIX = 0x10;
	
	public static boolean check(int a, int b) {
		return (a & b) == a;
	}
	
	public static boolean checkAllAnd(int a, int... bs) {
		boolean output = true;
		
		for(int b: bs) {
			output &= ((a & b) == a);
		}
		
		return output;
	}
	
	public static boolean checkAllOr(int a, int... bs) {
		boolean output = false;
		
		for(int b: bs) {
			output |= ((a & b) == a);
		}
		
		return output;
	}
	
}
