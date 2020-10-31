package com.chcraft.util.test;

import org.junit.Before;
import org.junit.Test;

import com.chcraft.util.jsonstringconverter.JSONToJavaConverter;

public class JSONConverterTest {
	private String className;
	private String testcase1;
	
	@Before
	public void init() {
		className = "TestCase";
		testcase1 = "{\r\n" + 
				"\"test1\" : {},\r\n" + 
				"\"test2\" : [[[\"GyaOo\"]],[[]],[[]]],\r\n" + 
				"\"test3\" : null,\r\n" + 
				"\"test4\" : 7,\r\n" + 
				"\"test5\" : 0.0,\r\n" + 
				"\"test6\" : \"string\"\r\n" + 
				"}";
	}
}
