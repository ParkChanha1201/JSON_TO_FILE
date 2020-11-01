package com.chcraft.util.jsonstringconverter;

import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chcraft.util.StringExtension;

public class JSONToJavaConverter implements JSONStringConverter {	
	private Map<String, String> keyTypeMap;
	
	public JSONToJavaConverter() {
		keyTypeMap = new HashMap<String, String>();
	}
	
	public JSONObject readJSONString(String jsonString) {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		Iterator<String> iter = jsonObject.keys();

		// get keys and values and store in map
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = jsonObject.get(key);
			String type = getType(value, false);

			keyTypeMap.put(key, type);
		}
		
		return jsonObject;
	}
	
	/***
	 * 
	 * @param key
	 * @param type
	 * @return if key exists return before type
	 */
	public String setKeyType(String key, String type) {
		if(!keyTypeMap.containsKey(key)) {
			return "";
		}
		String beforeType = keyTypeMap.get(key);
		
		keyTypeMap.replace(key, type);
		
		return beforeType;
	}
	
	/***
	 * 
	 * @param before
	 * @param after
	 * @return if change success return before
	 */
	public String changeKeyName(String before, String after) {
		if(!keyTypeMap.containsKey(before)) {
			return "";
		}
		
		//duplicated key
		if(keyTypeMap.containsKey(after)) {
			return "";
		}
		
		String keyType = keyTypeMap.get(before);
		keyTypeMap.remove(before);
		keyTypeMap.put(after, keyType);
		
		return before;
	}
	
	@Override
	public String generateClassFileString(String classPath, String jsonString ,String className, boolean makeGetterAndSetter) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("package " + classPath + ";\n\n");
		
		sb.append("import java.util.List;\n\n");
		
		sb.append("import org.json.JSONObject;\n\n");
		
		sb.append(generateClassString(jsonString, className, makeGetterAndSetter));
		
		return sb.toString();
	}
	
	@Override
	public String generateClassString(String jsonString, String className, boolean makeGetterAndSetter) {
		readJSONString(jsonString);

		// get key and value type.
		Map<String, String> keyType = getKeyTypeMap();
		
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
	
	/***
	 * 
	 * @param value of key
	 * @return type of value. if value is JSONObject or primitive type(int, double,
	 *         etc...), return it's type. else if value is JSONArray, return
	 *         List<element's type> if value is null, return "Object".
	 */
	private String getType(Object value, Boolean isJSONArray) {
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
	
	private void addVariable(StringBuilder sb, String variableName, String type) {
		sb.append("\tprivate " + type + " " + variableName + ";\n");
	}
	
	private void generateGetterAndSetter(StringBuilder sb, String variableName, String type) {
		sb.append("\tpublic " + type + " get" + StringExtension.toFirstCharUpperCase(variableName) + "() {\n");
		sb.append("\t\treturn this." + variableName + ";\n");
		sb.append("\t}\n\n");
		
		sb.append("\tpublic void set" + StringExtension.toFirstCharUpperCase(variableName) + "("+ type + " " + variableName +") {\n");
		sb.append("\t\tthis." + variableName + " = " + variableName + ";\n");
		sb.append("\t}\n\n");
	}
	
	public Map<String, String> getKeyTypeMap(){
		return keyTypeMap;
	}
}
