package ch.mbae.services;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author marcbaechinger
 */
@XmlRootElement
public class Contact {

    private Long id;
    private String name;

    public Contact() {
        
    }
    public Contact(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Long id) {
        this.id = id;
    }

}
