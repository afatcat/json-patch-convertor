package org.tfa.jsonpatch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ObjectToPatchJson {
	/**
	 * Convert object into patch format JSON string. 
	 * All fields are considered new (op='add') unless annotated otherwise.
	 * Null value will be ignored unless annotated otherwise.
	 * 
	 * @param object input object
	 * @return patch format JSON string converted from input object
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String parseFreshObjectToJson(Object object) throws JsonGenerationException, JsonMappingException, IOException{
		List<Map<String, Object>> list = ObjectToPatchMap.parseFreshObject(object);
		String result = new ObjectMapper().writeValueAsString(list);
		return result;
	}
	
	/**
	 * Compare new object from the old and convert the difference into patch formatted JSON string. 
	 * Fields exist in new object but not the old gets op='add';
	 * Fields exist in both new object and old one gets op='replace';
	 * Fields exist in old object but not the new gets op='remove'.
	 * Null value will be ignored unless annotated otherwise.
	 * 
	 * @param object1 old object
	 * @param object2 new object
	 * @return patch format JSON string containing the difference between the two objects
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static String parseByComparingObjectsToJson(Object object1, Object object2) throws JsonGenerationException, JsonMappingException, IOException{
		List<Map<String, Object>> list = ObjectToPatchMap.parseByComparingObjects(object1, object2);
		String result = new ObjectMapper().writeValueAsString(list);
		return result;
	}
}
