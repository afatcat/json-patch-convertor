# Overview
This project aims to provide a convenient way to generate JSON that follows the triky PATCH format specified by <a href='https://tools.ietf.org/html/rfc6902'>RFC6902</a>. 
It supports <a href='https://github.com/FasterXML/jackson-annotations'>Jackson annotated</a> domain objects. User can also use the included annotation to specify JSON configuration. 

# Usage
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
