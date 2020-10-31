package com.chcraft.util.jsonstringconverter;

import java.util.List;

public interface JSONStringConverter {
	String toFileString(String packagePath, String jsonString, String className, boolean makeGetterAndSetter);
	
	String toClassString(String jsonString, String className, boolean makeGetterAndSetter);
	
	List<String> getKeys(String jsonString);
}
