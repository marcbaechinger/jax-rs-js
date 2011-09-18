package ch.mbae.notes.services;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author marcbaechinger
 */
@XmlRootElement
public class Note {

    private Long id;
    private String title;
    private String note;

    public Note() {
    }

    public Note(Long id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }
    
    

    /**
     * Get the value of note
     *
     * @return the value of note
     */
    public String getNote() {
        return note;
    }

    /**
     * Set the value of note
     *
     * @param note new value of note
     */
    public void setNote(String note) {
        this.note = note;
    }

    
    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        this.title = title;
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
