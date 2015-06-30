package org.tfa.jsonpatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tfa.jsonpatch.testobjects.ADomainObject;

public class ObjectToPatchJsonTests {
	private ADomainObject ado;
	private ADomainObject ado2;
	private ADomainObject ado3;
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
		List<String> alist = new ArrayList<String>();
		alist.add("abc");
		alist.add("def");
		ado.setAlist(alist);
		
		ado2 = new ADomainObject();
		ado2.setLdapGuid("123-456-7890");
		ado2.setGivenName("Hell");
		ado2.setSurname("World");
		ado2.setDispositionStep("ALUM");
		ADomainObject.EnterpriseRole enterpriseRole2 = ado.new EnterpriseRole();
		enterpriseRole2.setAffiliation("NONE");
		ado2.setEnterpriserole(enterpriseRole2);
		
		ado3 = new ADomainObject();
		ado3.setLdapGuid("123-456-7890");
		ado3.setGivenName("Hello");
		ado3.setSurname("World");
		ado3.setDispositionStep("ALUM");
	}
	
	@Test
	public void testParseFreshObjectToJson() throws JsonGenerationException, JsonMappingException, IOException{
		String result = ObjectToPatchJson.parseFreshObjectToJson(ado);
		System.out.println(result);
		Assert.assertThat(result, CoreMatchers.containsString("{\"op\":\"add\",\"path\":\"/alist\",\"value\":[\"abc\",\"def\"]}"));
	}
	
	@Test
	public void testParseByComparingObjectsToJson() throws JsonGenerationException, JsonMappingException, IOException{
		String result = ObjectToPatchJson.parseByComparingObjectsToJson(ado, ado2);
		System.out.println(result);
		Assert.assertThat(result, CoreMatchers.containsString("{\"op\":\"remove\",\"path\":\"/alist\"}"));
	}
}
