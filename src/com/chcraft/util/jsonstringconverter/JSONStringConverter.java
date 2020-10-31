package com.chcraft.util.jsonstringconverter;

public interface JSONStringConverter {
	/***
	 * 
	 * @param classPath
	 * package path for Java, namespace for C# and etc...
	 * @param jsonString
	 * @param className
	 * @param makeGetterAndSetter
	 * if want to generate getter and setter set this true
	 * @return File String include package or namespace.
	 */
	String generateClassFileString(String classPath, String jsonString, String className, boolean makeGetterAndSetter);
	
	String generateClassString(String jsonString, String className, boolean makeGetterAndSetter);
}
