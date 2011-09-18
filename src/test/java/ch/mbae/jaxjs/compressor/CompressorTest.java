/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.compressor;

import junit.framework.Assert;
import java.io.IOException;
import net.jawr.web.minification.JSMin.JSMinException;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class CompressorTest {
    private Minificator compressor;
        
    
    @Before
    public void setUp() {
        compressor = new Minificator();
    }

    @Test
    public void compress() throws IOException, JSMinException {
        String script = "/* commment */var      a  =     \"text  \"     ;          // comment";
        Assert.assertEquals("\nvar a=\"text  \";", compressor.compress(script));
    }
}
