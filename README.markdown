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
    title: "a<b>öäü</b>dd note 99",
    note: "a note added"
  },
  function(note) {
    // do something
  }
);</code></pre>


Build and install
==================================
jax-rs-jsx
