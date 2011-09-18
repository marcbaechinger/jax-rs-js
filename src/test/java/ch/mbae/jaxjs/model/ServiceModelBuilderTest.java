/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.model;

import ch.mbae.services.Contact;
import ch.mbae.services.ContactsResource;
import java.lang.reflect.Method;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import static junit.framework.Assert.*;
import org.easymock.EasyMockSupport;
import static org.easymock.EasyMock.*;
import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
@Path("/path")
public class ServiceModelBuilderTest {
    
    private static final EasyMockSupport MOCKS = new EasyMockSupport();
    private static final ServiceMethodModelBuilder SERVICE_METHOD_MODEL_BUILDER = MOCKS.createMock(ServiceMethodModelBuilder.class);
    
    private ServiceModelBuilder builder ;
    private ServiceModel desc;
    
    @Before
    public void setUp() {
        this.builder = new ServiceModelBuilder(SERVICE_METHOD_MODEL_BUILDER);   
    }
    
    @Test
    public void buildServiceModel() throws NoSuchMethodException {
        
        final ServiceModel serviceModel = new ServiceModel();
        final ServiceMethodModel serviceMethodModel = new ServiceMethodModel();
        Method m1 = this.getClass().getDeclaredMethod("get", new Class[0]);
        Method m2 = this.getClass().getDeclaredMethod("create", new Class[]{Contact.class});
        
        MOCKS.resetAll();
        expect(SERVICE_METHOD_MODEL_BUILDER.buildMethodModel(m1, serviceModel)).andReturn(serviceMethodModel);
        expect(SERVICE_METHOD_MODEL_BUILDER.buildMethodModel(m2, serviceModel)).andReturn(serviceMethodModel);
        MOCKS.replayAll();
        
        final ServiceModel model = builder.buildServiceModel(this.getClass(), serviceModel);
        
        MOCKS.verifyAll();
        assertEquals(2, model.getMethods().size());
        assertEquals(serviceMethodModel, model.getMethods().get(0));
        assertEquals(this.getClass().getSimpleName(), model.getName());
        assertEquals("/path", model.getPath());
    }
    
    @GET
    public Contact get() {
        return null;
    }
    @POST
    public Contact create(Contact contact) {
        return null;
    }
}
