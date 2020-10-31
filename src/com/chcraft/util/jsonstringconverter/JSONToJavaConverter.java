package com.chcraft.util.jsonstringconverter;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chcraft.util.StringExtension;

public class JSONToJavaConverter implements JSONStringConverter {	
	@Override
	public String toFileString(String packagePath, String jsonString ,String className, boolean makeGetterAndSetter) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("package " + packagePath + ";\n\n");
		
		sb.append("import java.util.List;\n\n");
		
		sb.append("import org.json.JSONObject;\n\n");
		
		sb.append(toClassString(jsonString, className, makeGetterAndSetter));
		
		return sb.toString();
	}
	
	@Override
	public String toClassString(String jsonString, String className, boolean makeGetterAndSetter) {
		JSONObject jsonObject = new JSONObject(jsonString);

		// get key and value type.
		Map<String, String> keyType = new HashMap<String, String>();
		
		Iterator<String> iter = jsonObject.keys();

		// get keys and values and store in map
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = jsonObject.get(key);
			String type = getType(value, false);

			keyType.put(key, type);
		}

		// Generate File string.
		StringBuilder sb = new StringBuilder();
		sb.append("public class " + className + "{\n");

		Set<Entry<String, String>> keyTypeSet = keyType.entrySet();
		keyTypeSet.forEach(x -> {
			addVariable(sb, x.getKey(), x.getValue());
		});

		sb.append("\n");
		
		if(makeGetterAndSetter) {
			keyTypeSet.forEach(x -> {generateGetterAndSetter(sb, x.getKey(), x.getValue());});
		}
		
		sb.append("}");

		return sb.toString();
	}

	@Override
	public List<String> getKeys(String jsonString){
		JSONObject jsonObject = new JSONObject(jsonString);
		
		List<String> keys = new ArrayList<String>();
		
		for(String key : jsonObject.keySet()) {
			keys.add(key);
		}
		
		return keys;
	}
	
	/***
	 * 
	 * @param value of key
	 * @return type of value. if value is JSONObject or primitive type(int, double,
	 *         etc...), return it's type. else if value is JSONArray, return
	 *         List<element's type> if value is null, return "Object".
	 */
	private static String getType(Object value, Boolean isJSONArray) {
		if (value instanceof JSONObject) {
			return "JSONObject";
		}

		if (value instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) value;
			if (jsonArray.isEmpty()) {
				return "List<Object>";
			}
			Object tmp = jsonArray.get(0);

			return "List<" + getType(tmp, true) + ">";
		} else { //
			String tmp = value.toString();

			if(tmp.equalsIgnoreCase("null")) {
				return "Object";
			}
			
			if (tmp.equalsIgnoreCase("true") || tmp.equalsIgnoreCase("false")) {
				return isJSONArray ? "Boolean" : "boolean";
			}

			try {
				Integer.parseInt(tmp);
				return isJSONArray ? "Integer" : "int";
			} catch (NumberFormatException e) {
				try {
					Double.parseDouble(tmp);
					return isJSONArray ? "Double" : "double";
				} catch (NumberFormatException e2) {
					return "String";
				}
			}
		}
	}
	
	private static void addVariable(StringBuilder sb, String variableName, String type) {
		sb.append("\tprivate " + type + " " + variableName + ";\n");
	}
	
	private static void generateGetterAndSetter(StringBuilder sb, String variableName, String type) {
		sb.append("\tpublic " + type + " get" + StringExtension.toFirstCharUpperCase(variableName) + "() {\n");
		sb.append("\t\treturn this." + variableName + ";\n");
		sb.append("\t}\n\n");
		
		sb.append("\tpublic void set" + StringExtension.toFirstCharUpperCase(variableName) + "("+ type + " " + variableName +") {\n");
		sb.append("\t\tthis." + variableName + " = " + variableName + ";\n");
		sb.append("\t}\n\n");
	}
}
