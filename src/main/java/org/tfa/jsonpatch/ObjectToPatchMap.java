package org.tfa.jsonpatch;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.tfa.jsonpatch.annotations.*;

public class ObjectToPatchMap {
private final static transient Logger logger = Logger.getLogger(ObjectToPatchMap.class);
	
	public static List<Map<String, Object>> parseFreshObject(Object object){
		List<Map<String, Object>> list = parseFreshObject(object, null);
		
		logger.debug("list generated from annotated object: "+list);
		return list;
	}
	
	private static List<Map<String, Object>> parseFreshObject(Object object, String prefix){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Class<? extends Object> clazz=object.getClass();
		logger.trace("class: "+clazz);
		try {
			for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors()){
				logger.trace("propertyDescriptor:"+propertyDescriptor);
				Method readMethod = propertyDescriptor.getReadMethod();
				String fieldName = propertyDescriptor.getName();
				logger.trace("field name:"+fieldName);
				Field field = clazz.getDeclaredField(fieldName);
				
				//skip fields annotated to ignore
				Annotation[] annotations = field.getDeclaredAnnotations();
				boolean skip = false;
				boolean includeNull = false;
				for(Annotation annotation:annotations){
					logger.trace("annotation:"+annotation);
					if(annotation instanceof JsonIgnore){
						if(((JsonIgnore) annotation).value()){
							skip = true;
							break;
						}
					}else if(annotation instanceof PatchIgnore){
						if(((PatchIgnore) annotation).value()){
							skip = true;
							break;
						}
					}else if(annotation instanceof PatchIncludeNull){
						if(((PatchIncludeNull) annotation).value()){
							includeNull = true;
						}
					}else if(annotation instanceof JsonProperty){
						fieldName = ((JsonProperty) annotation).value();
					}
				}
				logger.trace("skip field "+skip);
				
				if(!skip){
					Object value = readMethod.invoke(object, (Object[]) null);
					logger.trace("value:"+value);
					logger.trace("prefix: "+prefix);
					
					if(value!=null || includeNull){//null values are ignored by default
						if(prefix==null){
							prefix="";//null string will print out as "null"
						}
						if(includeNull && value==null){//explicit null (PatchIncludeNull) will be removed
							String path = prefix+"/"+fieldName;
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("op", "remove");
							map.put("path", path);
							list.add(map);
						}else if(propertyDescriptor.getPropertyType().equals(String.class)){
							String path = prefix+"/"+fieldName;
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("op", "add");
							map.put("path", path);
							map.put("value", (String) value);
							list.add(map);
						}else if(propertyDescriptor.getPropertyType().equals(List.class)){
							//TODO: test this
							logger.debug("property is of type list "+propertyDescriptor);
							String path = prefix+"/"+fieldName;
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("op", "add");
							map.put("path", path);
							map.put("value", value);
							list.add(map);
						}else{
							logger.trace("property not string or list"+propertyDescriptor);
							String newprefix=prefix+"/"+propertyDescriptor.getName();
							list.addAll(parseFreshObject(value, newprefix));
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
