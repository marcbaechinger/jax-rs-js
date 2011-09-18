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
public class ServiceModelBuilder {
    private final ServiceMethodModelBuilder methodBuilder;

    public ServiceModelBuilder(ServiceMethodModelBuilder methodBuilder) {
        this.methodBuilder = methodBuilder;
    }  

    public ServiceModel buildServiceModel(Class clazz, ServiceModel serviceModel) {
        
        serviceModel.setName(clazz.getSimpleName());
        
        if (clazz.isAnnotationPresent(Path.class)) {
            serviceModel.setPath(((Path)clazz.getAnnotation(Path.class)).value());
        }
        
        for (Method m: clazz.getMethods()) {
            if (m.isAnnotationPresent(GET.class)  ||
                m.isAnnotationPresent(POST.class) ||
                m.isAnnotationPresent(PUT.class)  ||
                m.isAnnotationPresent(DELETE.class) ) {
    
                serviceModel.addMethod(methodBuilder.buildMethodModel(m, serviceModel));
            }
        }
        return serviceModel;
    }

    
}
