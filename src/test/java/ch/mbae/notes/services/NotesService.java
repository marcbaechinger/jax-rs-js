package ch.mbae.notes.services;

import ch.mbae.jaxjs.annotations.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *  
 * @author marcbaechinger
 */
@Path("/note")
public class NotesService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAll() {
        List<Note> rs = new ArrayList<Note>();
        rs.add(new Note(1L, "Test 1", "Test 1 ----"));
        rs.add(new Note(2L, "Test 2", "Test 2 ----"));
        return rs;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/testArrayReturnValue")
    public Note[] getAllAsArray() {
        Note[] notes = new Note[]{
            new Note(1L, "Test 1", "Test 1 ----"),
            new Note(2L, "Test 2", "Test 2 ----")
        };
        return notes;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note get(@PathParam("id") String id) {
        return new Note(Long.valueOf(id), "Test " + id, "Test " + id + " ----");
    }

    @GET
    @Path("/{id}/comments/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getComments(@PathParam("id") String id, @PathParam("commentId") String commentId) {
        System.out.println("id: " + id);
        System.out.println("commentId: " + commentId);
        List<Note> rs = new ArrayList<Note>();
        rs.add(new Note(1L, "Test 1", "Test 1 ----"));
        rs.add(new Note(2L, "Test 2", "Test 2 ----"));
        return rs;
    }

    @GET
    @Path("/{id}/references/{ref}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getReferences(@PathParam("id") String id, @PathParam("ref") Long refId) throws ServiceException {
        if (true) {
            throw new ServiceException("Elephant panic! Run!!");
        }
        List<Note> rs = new ArrayList<Note>();
        rs.add(new Note(1L, "Test 1", "Test 1 ----"));
        rs.add(new Note(2L, "Test 2", "Test 2 ----"));
        return rs;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Note addNote(Note note) {
        note.setId(109L);
        return note;
    }

    @POST
    @Path("/batch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Note> addNotes(@Type(Note.class) List<Note> notes) {
        Long id = Long.valueOf(1L);
        for (Note note : notes) {
            note.setId(id++);
        }
        return notes;
    }
    
    @POST
    @Path("/batchArray")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Note[] addNotesAsArray(Note[] notes) {
        Long id = Long.valueOf(1L);
        for (Note note : notes) {
            note.setId(id++);
        }
        return notes;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Note updateNote(@PathParam("id") String id, Note note) {
        return note;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note removeNote(@PathParam("id") String id) {
        return new Note(Long.valueOf(id), "Test " + id, "Test " + id + " ----");
    }
    
    @PUT
    @Path("/user/{username:[a-z]}/comment/{commentId:[a-zA-Z][_a-zA-Z]$}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("username") String username, @PathParam("commentId") String commentId) {
        return new User();
    }
}