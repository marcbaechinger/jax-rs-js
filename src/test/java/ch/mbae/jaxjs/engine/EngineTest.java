package ch.mbae.jaxjs.engine;

import ch.mbae.jaxjs.model.PathVariableParser;
import ch.mbae.jaxjs.model.ServiceMethodModelBuilder;
import ch.mbae.jaxjs.model.ServiceModelBuilder;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author marcbaechinger
 */
public class EngineTest {

    private static final String[] SERVICE_CLASSES = new String[]{
        "ch.mbae.services.AddressService",
        "ch.mbae.services.ContactsResource",
        "ch.mbae.notes.services.NotesService"
    };
    
    private Engine engine;

    @Before
    public void setUp() {
        this.engine = new Engine(new ServiceModelBuilder(new ServiceMethodModelBuilder(new PathVariableParser())));
        engine.setJAXRSServletPath("/resources");
        engine.setContextPath("");
    }

    @Test
    public void generate() throws Exception {

        String js = engine.generateJavaScriptServices(SERVICE_CLASSES);
        System.out.println(js);
        Assert.assertNotNull(js);
        Assert.assertTrue("constructor of AddressService not found", js.contains("var AddressService = function(path) {"));
        Assert.assertTrue("constructor of ContactsResource not found", js.contains("var ContactsResource = function(path) {"));
        Assert.assertTrue("constructor of NotesService not found", js.contains("var NotesService = function(path) {"));

        Assert.assertTrue("AddressService not exposed to global.jaxjs", js.contains("global.jaxjs.services.AddressService = new AddressService (\"/resources/address\");"));
        Assert.assertTrue("ContactsResource not exposed to global.jaxjs", js.contains("global.jaxjs.services.ContactsResource = new ContactsResource (\"/resources/contact\");"));
        Assert.assertTrue("NotesService not exposed to global.jaxjs", js.contains("global.jaxjs.services.NotesService = new NotesService (\"/resources/note\");"));
    }
}
