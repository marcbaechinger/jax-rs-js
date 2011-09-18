/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.model;

import ch.mbae.jaxjs.model.ServiceModel;
import ch.mbae.jaxjs.model.ServiceMethodModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class ServiceModelTest {
    private ServiceModel serviceDescription;
    
    @Before
    public void setUp() {
        this.serviceDescription = new ServiceModel();
    }
    

    @Test
    public void addMethod() {
        this.serviceDescription.addMethod(new ServiceMethodModel());
        Assert.assertTrue(this.serviceDescription.getMethods().size() == 1);
        this.serviceDescription.addMethod(new ServiceMethodModel());
        Assert.assertTrue(this.serviceDescription.getMethods().size() == 2);
    }
    
    @Test
    public void getSetName() {
        final String name = "name";
        this.serviceDescription.setName(name);
        Assert.assertEquals(name, this.serviceDescription.getName());
    }
    
    @Test
    public void getSetPath() {
        final String path = "path";
        this.serviceDescription.setPath(path);
        Assert.assertEquals(path, serviceDescription.getPath());
    }
}
