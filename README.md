# Overview
This project aims to provide a convenient way to generate JSON that follows the triky PATCH format specified by <a href='https://tools.ietf.org/html/rfc6902'>RFC6902</a>. 
It supports <a href='https://github.com/FasterXML/jackson-annotations'>Jackson annotated</a> domain objects. User can also use the included annotation to specify JSON configuration. 

# Usage
## Use Cases
### Use Case 1 Compare the current and future state of the object and consturct a JSON based on the difference. 
Just use the following one line of code:
```
String result = ObjectToPatchJson.parseByComparingObjectsToJson(object1, object2);
```
object1 is current state of the object, object2 is future state of the object. It will genereate the patch JSON based on the difference. 

### Use Case 2 You know what values certain fields of an object you want to update, but not the current state of the object.
In this case, you want to include in the JSON the field/value pairs you want to touch, but leave others untouched. 
You will use this method:
```
String result = ObjectToPatchJson.parseFreshObjectToJson(object);
```
Note that for all the fields exist in the object, op (operation) will be "add", unless annotated otherwise. 

## Examples
Assume that you have a Jackson annotated domain object:
```
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Person {
	private String email;
	private String givenName;
	private String surname;
	@JsonIgnore
	private String personType;

	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	public class EnterpriseRole {
		private String program;
		private String affiliation;
		private String exit;
		private String status;

		public String getProgram() {
			return program;
		}

		public void setProgram(String program) {
			this.program = program;
		}

		public String getAffiliation() {
			return affiliation;
		}

		public void setAffiliation(String affiliation) {
			this.affiliation = affiliation;
		}

		public String getExit() {
			return exit;
		}

		public void setExit(String exit) {
			this.exit = exit;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
```
### Use Case 1 Comparation example
You have the current state of the object as person1, and the future state of the object you want it to be as person2:
```
    Person person1 = new Person();
		person1.setEmail("john.doe@example.org");
		person1.setGivenName("John");
		Person.EnterpriseRole enterpriserole = person1.new EnterpriseRole();
		enterpriserole.setProgram("aprogram");
		enterpriserole.setAffiliation("anaffiliation");
		person1.setEnterpriserole(enterpriserole);
		
		Person person2 = new Person();
		person2.setEmail("john.doe@example.org");
		person2.setSurname("Doe");;
		Person.EnterpriseRole enterpriserole2 = person2.new EnterpriseRole();
		enterpriserole2.setProgram("anotherprogram");
		enterpriserole2.setAffiliation("anaffiliation");
		person2.setEnterpriserole(enterpriserole2);
```
Your expected patch format JSON will be:
```
[
   {
      "path":"/enterpriserole/program",
      "op":"replace",
      "value":"anotherprogram"
   },
   {
      "op":"remove",
      "path":"/givenName"
   },
   {
      "path":"/surname",
      "op":"add",
      "value":"Doe"
   }
]
```
To make the conversion, simply use the following one line of code:
```
ObjectToPatchJson.parseByComparingObjectsToJson(person1, person2);
```

### Use Case 2 include only fields to touch example
If you have person1 mentioned above, the expected JSON string will be: 
```
[
   {
      "op":"add",
      "path":"/email",
      "value":"john.doe@example.org"
   },
   {
      "op":"add",
      "path":"/enterpriserole/affiliation",
      "value":"anaffiliation"
   },
   {
      "op":"add",
      "path":"/enterpriserole/program",
      "value":"aprogram"
   },
   {
      "op":"add",
      "path":"/givenName",
      "value":"John"
   }
]
```
you will just need the following one line for conversion:
```
String result = ObjectToPatchJson.parseFreshObjectToJson(person1);
```

## Jackson Annotations
### @JsonIgnore
Fields annotated with @JsonIgnore are ignored. 
```
@JsonIgnore
private String personType;
```

### @JsonProperty
Path will follow value specified by JsonProperty if presented, instead of the field name. 
```
@JsonProperty(value=abettername)
private String aname;
```

## Patch Annotations
### @PatchIgnore
Similar to @JsonIgnore, use it when you don't want the normal JSON converted from your Jackson annotation domain object to exclude the field, but want that in the PATCH JSON. 
```
@PatchIgnore
private String ldapGuid;
```

### @PatchIncludeNull
If the field annotated with @PatchIncludeNull has null value, instead of ignoring it, the JSON will include it with op as remove. 
```
{op='remove', path='/enterpriserole/exit'}
```
