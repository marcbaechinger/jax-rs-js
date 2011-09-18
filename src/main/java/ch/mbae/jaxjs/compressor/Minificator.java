package ch.mbae.jaxjs.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.jawr.web.minification.JSMin;
import net.jawr.web.minification.JSMin.JSMinException;

/**
 * Compressor to compress a JavaScript file
 * 
 * @author marcbaechinger
 */
public class Minificator { 
    public String compress(String javaScript) throws IOException, JSMinException {
        ByteArrayInputStream in = new ByteArrayInputStream(javaScript.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JSMin jsmin = new JSMin(in, out);
        jsmin.jsmin();
        return new String(out.toByteArray());
    }
}
