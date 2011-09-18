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
<pre><code>mvn clean install</code></pre>

<pre><code>
      &lt;dependency&gt;
            &lt;groupId&gt;ch.mbae&lt;/groupId&gt;
            &lt;artifactId&gt;jax-rs-js&lt;/artifactId&gt;
            &lt;version&gt;1.0-SNAPSHOT&lt;/version&gt;
        &lt;/dependency&gt;
</code></pre>
