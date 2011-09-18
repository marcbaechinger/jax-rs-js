package ch.mbae.jaxjs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author marcbaechinger
 */
public class ServiceMethodModel {
    private String name;
    private ReturnType returnType;
    private String jsonParameter;
    private Class jsonParameterType;
    private List<String> pathParameters = new ArrayList<String>();
    
    private HttpVerb httpVerb;
    private String path;


    public List<String> getPathParameters() {
        return pathParameters;
    }

    public void addPathParameter(String name) {
        pathParameters.add(name);
    }
    /**
     * Get the value of path
     *
     * @return the value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the value of path
     *
     * @param path new value of path
     */
    public void setPath(String path) {
        this.path = path;
    }

    
    public HttpVerb getHttpVerb() {
        return this.httpVerb;
    }
    public void setHttpVerb(HttpVerb verb) {
        this.httpVerb = verb;
    }

    public String getJsonParameter() {
        return jsonParameter;
    }

    public void setJsonParameter(String parameter) {
        this.jsonParameter = parameter;
    }

    public Class getJsonParameterType() {
        return jsonParameterType;
    }

    public void setJsonParameterType(Class jsonParameterType) {
        this.jsonParameterType = jsonParameterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ReturnType returnType) {
        this.returnType = returnType;
    }
    
    public String getPayloadPropertyName() {
        return this.jsonParameterType != null ? derivePropertyNameFromType(jsonParameterType) : null;
    }
    
    
    private String derivePropertyNameFromType(Class type) {
        if (type == null) {
            return null;
        }
        String payloadPropertyName = type.getSimpleName().substring(0, 1).toLowerCase();
        payloadPropertyName += type.getSimpleName().substring(1);
        return payloadPropertyName;
    }
}
