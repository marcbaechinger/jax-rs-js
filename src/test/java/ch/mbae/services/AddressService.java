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
@Path("/address")
public class AddressService {

    @Context
    private UriInfo context;

    
    /**
     * Sub-resource locator method for {id}
     */
    @GET
    public Address get() {
        final List<Address> addresses = new ArrayList<Address>();
        addresses.add(new Address(1L, "Marc Bächinger"));
        addresses.add(new Address(2L, "Hans Meier"));
        return new Address(2L, "Hans Meier");
    }
    
    
    @POST
    public Address create(Address address   ) {
        address.setId(1L);
        return address;
    }

    @PUT
    public Address update(Address address) {
        return address;
    }

    @DELETE
    public Address remove(Address address) {
        return address;
    }
}
