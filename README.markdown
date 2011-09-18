What is jax-rs-js?
=================================
jax-rs-js aims to help accessing JAX-RS annotated services from within a browser application. Complete coverage of JAX-RS features is not a goal of jax-rs-js. Instead some common and straightforward patterns are covered. See <code>ch.mbae.notes.services.NotesService</code> in the test source directory to see whats currently covered.   

jax-rs-js is shipped with a Servlet to be included in a web application and to serve JavaScript files for JAX-RS services:

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
    // do something with service response in callback function
  }
);</code></pre>


Build and install
==================================
Build with maven
----------------------------------
<pre><code>mvn clean install</code></pre>

Include dependency in your pom.xml

<pre><code>&lt;dependency&gt;
  &lt;groupId&gt;ch.mbae&lt;/groupId&gt;
  &lt;artifactId&gt;jax-rs-js&lt;/artifactId&gt;
  &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

or copy <code>jax-rs-js-1.0-SNAPSHOT.jar</code> to your environment.

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
        &lt;param-value&gt;<strong>/resources</strong>&lt;/param-value&gt;
    &lt;/init-param&gt;
    &lt;init-param&gt;
        &lt;param-name&gt;library.default&lt;/param-name&gt;
        &lt;param-value&gt;
            <strong>ch.mbae.notes.services.NotesService</strong>
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

&lt;script&gt;
$(function() {
  jaxjs.services.NotesService.getAll(function(notes) {
     // render notes...
  });
);
&lt;/script&gt;

</code></pre>

