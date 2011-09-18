package ch.mbae.jaxjs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author marcbaechinger
 */
public class ServiceModel {

    private String name;
    private List<ServiceMethodModel> methods = new ArrayList<ServiceMethodModel>();
    private String path;

    public void addMethod(ServiceMethodModel serviceMethodDescription) {
        if (serviceMethodDescription != null) {
            this.methods.add(serviceMethodDescription);
        }
    }

    public List<ServiceMethodModel> getMethods() {
        return this.methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
