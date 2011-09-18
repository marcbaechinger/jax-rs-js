/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.container;

import java.util.Hashtable;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import junit.framework.Assert;
import org.easymock.EasyMockSupport;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class JaxRsJsServletTest {
    
    
    private static final EasyMockSupport MOCKS = new EasyMockSupport();
    private static final ServletConfig SERVLET_CONFIG_MOCK = MOCKS.createMock(ServletConfig.class);
    private static final ServletContext SERVLET_CONTEXT_MOCK = MOCKS.createMock(ServletContext.class);

    private JaxRsJsServlet servlet;
            
    @Test
    public void init() throws ServletException {
        
        final String paramName = "library.default";
        final String service1 = "ch.mbae.Service", service2 = "ch.mbae.AnotherService";
        final String paramValue = service1 + ", " + service2;
        final String servletPath = "/resource";
        
        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put(paramName, paramValue);
        
        MOCKS.resetAll();
        expect(SERVLET_CONFIG_MOCK.getInitParameterNames()).andReturn(params.keys());
        expect(SERVLET_CONFIG_MOCK.getInitParameter(paramName)).andReturn(paramValue);
        expect(SERVLET_CONFIG_MOCK.getInitParameter("jaxrs.servlet.path")).andReturn(servletPath);
        expect(SERVLET_CONFIG_MOCK.getInitParameter("config.minification")).andReturn("false");
        expect(SERVLET_CONFIG_MOCK.getServletContext()).andReturn(SERVLET_CONTEXT_MOCK);
        expect(SERVLET_CONTEXT_MOCK.getContextPath()).andReturn("");
        MOCKS.replayAll();
        servlet.init(SERVLET_CONFIG_MOCK);
        MOCKS.verifyAll();
        
        Assert.assertEquals(service1, servlet.getLibraryClasses().get(paramName)[0]);
        Assert.assertEquals(service2, servlet.getLibraryClasses().get(paramName)[1]);
    }
    
    @Before
    public void initTest() {
        servlet = new JaxRsJsServlet();
    }     
}
