package ch.mbae.notes.services;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 
 * @author marcbaechinger
 */
public class ServiceException extends WebApplicationException {

    public ServiceException(String message) {
        super(Response.status(400)
             .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}
