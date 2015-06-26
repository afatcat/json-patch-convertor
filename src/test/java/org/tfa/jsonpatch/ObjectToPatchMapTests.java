package org.tfa.jsonpatch;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.tfa.jsonpatch.testobjects.ADomainObject;

public class ObjectToPatchMapTests {
	private ADomainObject ado;
	@Before
	public void setup(){
		ado = new ADomainObject();
		ado.setLdapGuid("123-456-7890");
		ado.setGivenName("Hello");
		ado.setSurname("World");
		ado.setDispositionStep("ALUM");
		ADomainObject.EnterpriseRole enterpriseRole = ado.new EnterpriseRole();
		enterpriseRole.setAffiliation("REGULAR");
		ado.setEnterpriserole(enterpriseRole);
	}
	
	@Test
	public void testParseInnerClass(){
		List<Map<String, Object>> list = ObjectToPatchMap.parseFreshObject(ado);
		System.out.println(list);
		assertEquals(5, list.size());
		assertTrue(listOfMapContains(list, "path", "/enterpriserole/affiliation"));
		assertTrue(listContainsMap(list, "/enterpriserole/affiliation", "REGULAR"));
	}
	
	@Test
	public void testJsonIgnore(){
		List<Map<String, Object>> list = ObjectToPatchMap.parseFreshObject(ado);
		assertFalse(listOfMapContains(list, "path", "/dispositionStep"));
	}
	
	private boolean listContainsMap(List<Map<String, Object>> list, String path, Object value){
		if(list == null || path == null){
			return false;
		}
		boolean found = false;
		for(Map<String, Object> map:list){
			if(path.equals(map.get("path"))){
				Object actualValue = map.get("value");
				if(value == null){
					if(actualValue == null){
						return true;
					}else{
						return false;
					}
				}else{
					return value.equals(actualValue);
				}
			}
		}
		return found;
	}
	
	private boolean listOfMapContains(List<Map<String, Object>> list, String key, Object expectedValue){
		boolean found = false;
		for(Map<String, Object> map:list){
			Object value = map.get(key);
			if(value.equals(expectedValue)){
				found = true;
				break;
			}
		}
		return found;
	}
}
