package ch.mbae.jaxjs.engine;

import ch.mbae.jaxjs.model.ServiceModel;
import ch.mbae.jaxjs.model.ServiceModelBuilder;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Engine to generate javascript code out of a <code>ServiceModel</code>.
 * 
 * @author marcbaechinger
 */
public class Engine {
    public static final String SERVICES_JS_TMPL = 
            "ch/mbae/jaxjs/engine/services.js.tmpl"; // on the classpath
    
    private String contextPath;
    private String servletPath;
    private ServiceModelBuilder builder;

    public Engine(ServiceModelBuilder builder) {
        this.builder = builder;
        
        Velocity.setProperty("resource.loader", "class");
        Velocity.setProperty("class.resource.loader.class", 
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init();
    }
    
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    public void setJAXRSServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String generateJavaScriptServices(String[] serviceClasses) 
            throws ClassNotFoundException {
        
        VelocityContext ctx = new VelocityContext();
        // add service models
        ctx.put("services", createServiceModels(serviceClasses));
        
        ctx.put("contextPath", contextPath);
        ctx.put("servletPath", servletPath);
        
        return generate(ctx);
    }

    private String generate(VelocityContext ctx) {
        StringWriter writer = new StringWriter();
        Velocity.mergeTemplate(SERVICES_JS_TMPL, "UTF-8", ctx, writer);
        return writer.toString();
    }


    private List<ServiceModel> createServiceModels(Class[] serviceClasses) {
        
        List<ServiceModel> services = new ArrayList<ServiceModel>();
        for (Class clazz : serviceClasses) {
            services.add(builder.buildServiceModel(clazz, new ServiceModel()));
        }
        return services;
    }
    
    private List<ServiceModel> createServiceModels(String[] serviceClasses) 
            throws ClassNotFoundException {
        
        Class[] classes = new Class[serviceClasses.length];
        for (int i = 0; i < serviceClasses.length; i++) {
            classes[i] = Class.forName(serviceClasses[i]);
        }
        
        return this.createServiceModels(classes);
    }
}
