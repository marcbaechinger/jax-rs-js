package ch.mbae.jaxjs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author marcbaechinger
 */
public class ServiceMethodModel {
    private String name;
    private Cardinality returnType;
    private Cardinality jsonParamCardinality;
    private String jsonParameter;
    private Class jsonParameterType;
    private List<String> pathParameters = new ArrayList<String>();
    
    private HttpVerb httpVerb;
    private String path;
    private List<PathVariable> vars;


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

    public Cardinality getJsonParamCardinality() {
        return jsonParamCardinality;
    }

    public void setJsonParamCardinality(Cardinality jsonParamateCardinality) {
        this.jsonParamCardinality = jsonParamateCardinality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cardinality getReturnType() {
        return returnType;
    }

    public void setReturnType(Cardinality returnType) {
        this.returnType = returnType;
    }
    
    public String getPayloadPropertyName() {
        return this.jsonParameterType != null &&
               Cardinality.Collection.equals(jsonParamCardinality) ? 
               derivePropertyNameFromType(jsonParameterType) : null;
    }
    
    
    private String derivePropertyNameFromType(Class type) {
        if (type == null) {
            return null;
        }
        String payloadPropertyName = type.getSimpleName().substring(0, 1).toLowerCase();
        payloadPropertyName += type.getSimpleName().substring(1);
        return payloadPropertyName;
    }

    public void setPathVariables(List<PathVariable> vars) {
        this.vars = vars;
    }
}
