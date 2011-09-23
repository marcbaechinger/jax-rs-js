package ch.mbae.jaxjs.model;

import ch.mbae.jaxjs.annotations.Type;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 
 * @author marcbaechinger
 */
public class ServiceMethodModelBuilder {
    private final PathVariableParser parser;

    public ServiceMethodModelBuilder(PathVariableParser parser) {
        this.parser = parser;
    }
    
    
    
    public ServiceMethodModel buildMethodModel(Method m, ServiceModel model) {
        
        final ServiceMethodModel serviceMethodModel = new ServiceMethodModel();
        
        // set the name of the service
        serviceMethodModel.setName(m.getName());
        // set the HttpVerb
        this.setHttpVerb(m, serviceMethodModel);
        
        buildPath(m, serviceMethodModel);
        
        // object or collection ?
        if (isJSONArray(m.getReturnType())) {
            serviceMethodModel.setReturnType(Cardinality.Collection);
        } else {
            serviceMethodModel.setReturnType(Cardinality.Object);    
        }     
        
        buildPathParameterModel(m, model, serviceMethodModel);
        // check wheter a json parameter is available
        if (m.getParameterTypes().length > serviceMethodModel.getPathParameters().size()) {
            serviceMethodModel.setJsonParameter("data");
            buildJsonParameterTypeModel(m, serviceMethodModel);
        }
        
        return serviceMethodModel;
    }

    private void buildPath(Method m, final ServiceMethodModel serviceMethodModel) {
        System.out.println("------------------");
        // set path to service
        if (m.isAnnotationPresent(Path.class)) {
            String path = m.getAnnotation(Path.class).value();
            
            List<PathVariable> vars = new ArrayList<PathVariable>();
            System.out.println("expr: " + path);
            path = parser.parse(path, vars);
            
            serviceMethodModel.setPath(path);
            System.out.println("path: " + path);
            serviceMethodModel.setPathVariables(vars);
        }
    }
    
    
    private void buildPathParameterModel(Method m, final ServiceModel model, final ServiceMethodModel serviceMethodModel) {
        // check for @PathParam annotation (jax-rs)
        final Annotation[][] parameterAnnotations = m.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof PathParam) {
                    final String pathParamName = ((PathParam)annotation).value();
                    serviceMethodModel.addPathParameter(pathParamName);
                }
            }
        }
    }
    

    private void buildJsonParameterTypeModel(Method m, final ServiceMethodModel serviceMethodModel) {
        Class type = null;
        Class paramType = m.getParameterTypes()[m.getParameterTypes().length - 1];;
        if (paramType.isArray()) {
            type = paramType.getComponentType();
            serviceMethodModel.setJsonParamCardinality(Cardinality.Collection);
        } else if (isJSONArray(paramType)){
            // check for @Type annotation (helper annotation)
            final Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Type) {
                        type = ((Type)annotation).value();
                    }
                }
            }
            serviceMethodModel.setJsonParamCardinality(Cardinality.Collection);
        } else {
            type = paramType;
            serviceMethodModel.setJsonParamCardinality(Cardinality.Object);
        }
        serviceMethodModel.setJsonParameterType(type);
            
    }

    /**
     * checks if a given type is represented as a JSON array
     * 
     * @param clazz
     * @return 
     */
    public static boolean isJSONArray(Class clazz) {
        boolean isCollection = clazz.isArray();
        if (!isCollection) {
            for (Class inf : clazz.getInterfaces()) {
                if (inf.equals(Collection.class)) {
                    isCollection = true;
                    break;
                }
            }
        }
        return isCollection;
    }

    private void setHttpVerb(Method m, ServiceMethodModel serviceMethodModel) {
        if (m.isAnnotationPresent(GET.class)) {
            serviceMethodModel.setHttpVerb(HttpVerb.GET);
        } else if (m.isAnnotationPresent(POST.class)) {
            serviceMethodModel.setHttpVerb(HttpVerb.POST);
        } else if (m.isAnnotationPresent(PUT.class)) {
            serviceMethodModel.setHttpVerb(HttpVerb.PUT);
        } else if (m.isAnnotationPresent(DELETE.class)) {
            serviceMethodModel.setHttpVerb(HttpVerb.DELETE);
        } 
    }

    
}
