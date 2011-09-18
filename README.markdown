What is jax-rs-js?
=================================
jax-rs-js aim to help accessing JAX-RS annotated services from within a browser application. jax-rs-js is shipped with a Servlet to be included in a web application and to serve JavaScript files for JAX-RS services:

<pre><code>@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Note addNote(Note note) {
    return note;
}</code></pre>

is called in JavaScript like this:

<pre><code>jaxjs.services.NotesService.addNote(
  {
    title: "Sample note",
    note: "push changes on github"
  },
  function(note) {
    // do something
  }
);</code></pre>


Build and install
==================================
Build with maven
----------------------------------
<pre><code>mvn clean install</code></pre>

Include dependency in your pom.xml

<pre><code>
&lt;dependency&gt;
  &lt;groupId&gt;ch.mbae&lt;/groupId&gt;
  &lt;artifactId&gt;jax-rs-js&lt;/artifactId&gt;
  &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

or copy jax-rs-js-1.0-SNAPSHOT.jar to your environment.

Servlet configuration in  web.xml
---------------------------------
A servlet generates and serves a JavaScript file for classes defined with init params:

<pre><code>
&lt;servlet&gt;
    &lt;servlet-name&gt;JaxRsJsServlet&lt;/servlet-name&gt;
    &lt;servlet-class&gt;ch.mbae.jaxjs.container.JaxRsJsServlet&lt;/servlet-class&gt;
    &lt;!-- required: path to the JSX-RS servlet --&gt;
    &lt;init-param&gt;
        &lt;param-name&gt;jaxrs.servlet.path&lt;/param-name&gt;
        &lt;param-value&gt;/resources&lt;/param-value&gt;
    &lt;/init-param&gt;
    &lt;init-param&gt;
        &lt;param-name&gt;library.default&lt;/param-name&gt;
        &lt;param-value&gt;
            ch.mbae.notes.services.NotesService
        &lt;/param-value&gt;
    &lt;/init-param&gt;
&lt;/servlet&gt;
&lt;servlet-mapping&gt;
    &lt;servlet-name&gt;JaxRsJsServlet&lt;/servlet-name&gt;
    &lt;url-pattern&gt;/resources-js/*&lt;/url-pattern&gt;
&lt;/servlet-mapping&gt;
</code></pre>

Include JavaScript in the HTML page
---------------------------------
<pre><code>&lt;script src="&lt;%= request.getContextPath() %&gt;/resources-js/"&gt; &lt;/script&gt;
</code></pre>

