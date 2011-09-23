package ch.mbae.jaxjs.container;

import ch.mbae.jaxjs.compressor.Minificator;
import ch.mbae.jaxjs.engine.Engine;
import ch.mbae.jaxjs.model.PathVariableParser;
import ch.mbae.jaxjs.model.ServiceMethodModelBuilder;
import ch.mbae.jaxjs.model.ServiceModelBuilder;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.jawr.web.minification.JSMin.JSMinException;

/**
 * Servlet to serve JavaScript files to access JAX-RS resources. Services are grouped in libraries which
 * can be registered by servlet init parameters. At least a default library must be defined:
 *      
 * <pre><code>&lt;init-param&gt;
 *      &lt;param-name&gt;<strong>library.default</strong>&lt;/param-name&gt;
 *      &lt;param-value&gt;
 *          ch.mbae.products.MyService
 *      &lt;/param-value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *      &lt;param-name&gt;<strong>library.productcatalog</strong>&lt;/param-name&gt;
 *      &lt;param-value&gt;
 *          ch.mbae.products.CatalogService   ,
 *          ch.mbae.checkout.CheckoutService
 *      &lt;/param-value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *      &lt;param-name&gt;<strong>jaxrs.servlet.path</strong>&lt;/param-name&gt;
 *      &lt;param-value&gt;/resources&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * &lt;init-param&gt;
 *      &lt;param-name&gt;<strong>config.minification</strong>&lt;/param-name&gt;
 *      &lt;param-value&gt;false&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * </code></pre>
 * 
 * @author marcbaechinger
 */
public class JaxRsJsServlet extends HttpServlet {
    
    private static final String COMPRESSION_INIT_PARAM_NAME = "config.minification";
    private static final String JAXRS_SERVLET_PATH_INIT_PARAM_NAME = "jaxrs.servlet.path";
    private static final String DEFAULT_JAXRS_SERVLET_PATH = "/resources";
    private static final String LIBRARY_PREFIX = "library.";
    
    private Logger logger = Logger.getLogger(JaxRsJsServlet.class.getName());
    
    private Engine engine;
    private Minificator minificator;
    private Map<String, String[]> libraryClasses = new HashMap<String, String[]>();

    private Map<String, String> libraryScripts = new HashMap<String, String>();
    
    private boolean useMinification = true;

    public JaxRsJsServlet() {
        minificator = new Minificator();
        // create template engine once
        engine = new Engine(new ServiceModelBuilder(new ServiceMethodModelBuilder(new PathVariableParser())));
        
        
    }

    public JaxRsJsServlet(Engine engine, Minificator minificator) {
        this.engine = engine;
        this.minificator = minificator;
    }
    
    

    /**
     * reads init params of the servlet:
     * 
     *  <ol>
     *      <li><strong><code>library.xyz</code></strong>: comma delimited string with classes of JAXRS services
     *      <li><strong><code>jaxrs.servlet.path</code></strong>: path to the JAX-RS-Servlet as configured in the web.xml
     *      <li><strong><code>config.minification</code></strong>: <code>false</code> to avoid minification of js code
     *  </ol>
     * 
     * At least on library with param '<code>library.default</code>' must be declared
     * 
     * @param config
     * @throws ServletException 
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        
        super.init(config);
        
        // get service libraries from init parameters
        evaluateLibraryInitParameters(config);
        
        // the path of the jax-rs servlet
        String servletPath = config.getInitParameter(JAXRS_SERVLET_PATH_INIT_PARAM_NAME);
        if (servletPath == null) {
            servletPath = DEFAULT_JAXRS_SERVLET_PATH;
            Logger.getLogger(JaxRsJsServlet.class.getName()).log(Level.WARNING, "WARNING: JAXRS servlet path not declared in servlet init param '" 
                    + JAXRS_SERVLET_PATH_INIT_PARAM_NAME + "'. Set to default '" + DEFAULT_JAXRS_SERVLET_PATH + "'");
        }
        engine.setJAXRSServletPath(servletPath);
        logger.log(Level.INFO, "jax-rs servletPath: '" + servletPath + "'");
        
        // context path of current web application
        final String contextPath = config.getServletContext().getContextPath();
        engine.setContextPath(contextPath);
        logger.log(Level.INFO, "configured contextPath: '" + contextPath + "'");
        
        // comression turned-off ?
        String compressionFlag = config.getInitParameter(COMPRESSION_INIT_PARAM_NAME);
        if ( compressionFlag != null) {
            useMinification = !compressionFlag.equals("false");
        }
        logger.log(Level.INFO, "compression: " + useMinification);
    }
    
    
    /**
     * serves javascript files for a requested library or the default library
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String libraryToken = evaluateLibraryToken(req);
            if (this.libraryClasses.containsKey(libraryToken)) {
                
                String compressedScript = getScript(libraryToken);

                resp.setContentLength(compressedScript.length());
                resp.setContentType("text/javascript");
                resp.getOutputStream().write(compressedScript.getBytes());
                resp.getOutputStream().flush();
            } else {
                sendNoSuchLibraryResponse(libraryToken, resp);
            }
        } catch (JSMinException ex) {
            logger.log(Level.SEVERE, null, ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JaxRsJsServlet.class.getName()).log(Level.SEVERE, null, ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
    

    private void evaluateLibraryInitParameters(ServletConfig config) {
        final Enumeration initParameterNames = config.getInitParameterNames();
        // scann for libraries
        while (initParameterNames.hasMoreElements()) {
            String name = (String) initParameterNames.nextElement();
            if (name.startsWith(LIBRARY_PREFIX)) {
                String[] classNames = config.getInitParameter(name).split(",");
                String[] cleanedUpClassNames = new String[classNames.length];
                for (int i = 0; i < classNames.length; i++) {
                    cleanedUpClassNames[i] = classNames[i].trim();
                }
                this.libraryClasses.put(name, cleanedUpClassNames);
            }
        }
    }
    
    /**
     * evaluates the <code>req</code> for which library is requested. The name of the library is
     * expected to be passed as extra path info:
     * 
     * [/contextPath]/servletPath[/library]
     * 
     * => http://localhost:8080/app/jaxrsjs/productcatalog
     * => requests the library 'productcatalog'
     * 
     * =>
     *   <init-param>
     *      <param-name>library.productcatalog</param-name>
     *      <param-value>
     *          ch.mbae.products.CatalogService,
     *          ch.mbae.checkout.CheckoutService
     *      </param-value>
     *   </init-param>
     * 
     * @param req
     * @return 
     */
    private String evaluateLibraryToken(HttpServletRequest req) {
        String pathInfo = "/default";
        if (req.getPathInfo() != null && req.getPathInfo().length() > 1) {
            pathInfo = req.getPathInfo();
        }

        return LIBRARY_PREFIX + pathInfo.substring(1);
    }

    /**
     * Send an error response to the client
     * 
     * @param libraryToken the library identifier of the library not found
     * @param resp the http response
     * @throws IOException if IO fails
     */
    private void sendNoSuchLibraryResponse(String libraryToken, HttpServletResponse resp) throws IOException {
        String noSuchLibraryMessage = "// no library with name '" + libraryToken.replace(LIBRARY_PREFIX, "") + "' registered. Check your init params of the JAX RS JS Servlet";
        
        noSuchLibraryMessage += "\nif (console) { console.warn(\"" + noSuchLibraryMessage + "\"); }";
        resp.setContentLength(noSuchLibraryMessage.length());
        resp.setContentType("text/javascript");
        resp.getOutputStream().write(noSuchLibraryMessage.getBytes());
        resp.getOutputStream().flush();
    }

    /**
     * gets the compressed script of a given library
     * 
     * @param libraryToken a token idnetifying the library
     * @return the compressed script for the given library
     * @throws net.jawr.web.minification.JSMin.JSMinException thrown if minification failed
     * @throws IOException thrown if minification failed
     * @throws ClassNotFoundException  thrown if a class of a service of a library could not be found
     */
    private String getScript(String libraryToken) throws JSMinException, IOException, ClassNotFoundException {
        String script = null;
        if (this.libraryScripts.containsKey(libraryToken)) {
            script = this.libraryScripts.get(libraryToken);
        } else {
            script = engine.generateJavaScriptServices(libraryClasses.get(libraryToken));
            if (useMinification) {
                script = minificator.compress(script);
            }
            this.libraryScripts.put(libraryToken, script);
        }
        return script;
    }
    // for test purposes only
    protected Map<String, String[]> getLibraryClasses() {
        return libraryClasses;
    }
}
