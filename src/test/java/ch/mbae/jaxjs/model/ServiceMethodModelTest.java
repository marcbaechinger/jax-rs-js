/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.model;

import ch.mbae.jaxjs.model.Cardinality;
import ch.mbae.jaxjs.model.ServiceMethodModel;
import ch.mbae.jaxjs.model.HttpVerb;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class ServiceMethodModelTest {
    private ServiceMethodModel methodDesc;
    
    
    @Before
    public void setUp() {
        this.methodDesc = new ServiceMethodModel();
    }

    @Test
    public void setGetHttpVerb() {
        methodDesc.setHttpVerb(HttpVerb.GET);
        Assert.assertEquals(HttpVerb.GET, methodDesc.getHttpVerb());
        methodDesc.setHttpVerb(HttpVerb.POST);
        Assert.assertEquals(HttpVerb.POST, methodDesc.getHttpVerb());
        methodDesc.setHttpVerb(HttpVerb.PUT);
        Assert.assertEquals(HttpVerb.PUT, methodDesc.getHttpVerb());
        methodDesc.setHttpVerb(HttpVerb.DELETE);
        Assert.assertEquals(HttpVerb.DELETE, methodDesc.getHttpVerb());
    }
    
    @Test
    public void setParameter() {
        final String name = "contact";
        methodDesc.setJsonParameter(name);
        Assert.assertEquals(name, methodDesc.getJsonParameter());
    }
    
    @Test
    public void setReturnType() {
        methodDesc.setReturnType(Cardinality.Collection);
        Assert.assertEquals(Cardinality.Collection, methodDesc.getReturnType());
        
        methodDesc.setReturnType(Cardinality.Object);
        Assert.assertEquals(Cardinality.Object, methodDesc.getReturnType());
    }
}
