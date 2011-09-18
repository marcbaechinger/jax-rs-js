package ch.mbae.jaxjs.model;

import ch.mbae.jaxjs.annotations.Type;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
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
    
    public ServiceMethodModel buildMethodModel(Method m, ServiceModel model) {
        
        final ServiceMethodModel serviceMethodModel = new ServiceMethodModel();
        
        // set the name of the service
        serviceMethodModel.setName(m.getName());
        // set the HttpVerb
        this.setHttpVerb(m, serviceMethodModel);
        // set path to service
        if (m.isAnnotationPresent(Path.class)) {
           serviceMethodModel.setPath(((Path)m.getAnnotation(Path.class)).value());
        }
        
        // object or collection ?
        if (isJSONArray(m.getReturnType())) {
            serviceMethodModel.setReturnType(ReturnType.Collection);
        } else {
            serviceMethodModel.setReturnType(ReturnType.Object);    
        }     
        
        buildPathParameterModel(m, model, serviceMethodModel);
        // check wheter a json parameter is available
        if (m.getParameterTypes().length > serviceMethodModel.getPathParameters().size()) {
            serviceMethodModel.setJsonParameter("data");
            buildJsonParameterTypeModel(m, serviceMethodModel);
        }
        
        return serviceMethodModel;
    }
    
    
    private void buildPathParameterModel(Method m, final ServiceModel model, final ServiceMethodModel serviceMethodDescription) {
        // check for @PathParam annotation (jax-rs)
        final Annotation[][] parameterAnnotations = m.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof PathParam) {
                    serviceMethodDescription.addPathParameter(((PathParam)annotation).value());
                }
            }
        }
    }
    

    private void buildJsonParameterTypeModel(Method m, final ServiceMethodModel serviceMethodModel) {
        Class type = null;
        if (m.getReturnType().isArray()) {
            type = m.getReturnType().getComponentType();
        } else if (isJSONArray(m.getReturnType())){
            // check for @Type annotation (helper annotation)
            final Annotation[][] parameterAnnotations = m.getParameterAnnotations();
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Type) {
                        type = ((Type)annotation).value();
                    }
                }
            }
        } else {
            type = m.getReturnType();
        }
        serviceMethodModel.setJsonParameterType(type);
    }

    /**
     * checks if a given type is represented as a JSON array
     * 
     * @param clazz
     * @return 
     */
    private boolean isJSONArray(Class clazz) {
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
