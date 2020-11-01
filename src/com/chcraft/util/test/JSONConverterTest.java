package com.chcraft.util.test;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.chcraft.util.jsonstringconverter.JSONStringConverter;
import com.chcraft.util.jsonstringconverter.JSONToJavaConverter;

public class JSONConverterTest {
	private String className;
	private String testcase1;
	private JSONStringConverter converter = new JSONToJavaConverter();
	
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
	
	@Test
	public void generateTest() {
		converter.generateClassFileString("com.chcraft.json", testcase1, className, true);
	}
	
	@Test
	public void javaConverterTest() {
		JSONToJavaConverter javaConverter = (JSONToJavaConverter)converter;
		
		javaConverter.readJSONString(testcase1);
		
		Map<String, String> keyTypeMap = javaConverter.getKeyTypeMap();
		printMap(keyTypeMap);
		
		javaConverter.changeKeyName("test1", "test1_changed");
		printMap(keyTypeMap);

		javaConverter.setKeyType("test1_changed", "OtherClass");
		printMap(keyTypeMap);
	}
	
	private void printMap(Map<String, String>map) {
		for(Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println();
	}
}
