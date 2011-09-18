/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.services;

import ch.mbae.jaxjs.annotations.Type;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author marcbaechinger
 */
@Path("/contact")
public class ContactsResource {

    @Context
    private UriInfo context;

    
    /**
     * Sub-resource locator method for {id}
     */
    @GET
    @Produces("application/json")
    @Type(Contact.class)
    @Path("/{id}")
    public List<Contact> getContacts(@PathParam("id") String id) {
        final List<Contact> contacts = new ArrayList<Contact>();
        contacts.add(new Contact(1L, "Marc Bächinger"));
        contacts.add(new Contact(2L, "Hans Meier"));
        return contacts;
    }
    
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Contact create(Contact contact) {
        contact.setId(1L);
        return contact;
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Contact update(Contact contact) {
        return contact;
    }

    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    public Contact remove(Contact contact) {
        return contact;
    }
}
