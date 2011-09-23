/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class PathVariableParserTest {
    private PathVariableParser parser;
    
    @Before
    public void init() {
        parser = new PathVariableParser();
    }

    @Test
    public void parseDefault() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        final String path = "/aservice/amethod";
        String cleanPath = parser.parse(path, vars);
        assertEquals(path, cleanPath);
        assertEquals(0, vars.size());
    }
    
    @Test
    public void parseRoot() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        String cleanPath = parser.parse("/", vars);
        assertEquals("/", cleanPath);
        assertEquals(0, vars.size());
    }
    
    @Test
    public void parseVariable() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        String cleanPath = parser.parse("/{id}", vars);
        assertEquals("/{id}", cleanPath);
        checkVars(vars, "id");
    }
    @Test
    public void parseVariableWithRegExpr() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        String cleanPath = parser.parse("/{id:[a-zA-Z]}", vars);
        assertEquals("/{id}", cleanPath);
        checkVars(vars, "id");
    }
    @Test
    public void parseMultipleVariables() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        final String pathExpr = "/{id}/comment/{commentId}/{pos}";
        String cleanPath = parser.parse(pathExpr, vars);
        assertEquals(pathExpr, cleanPath);
        checkVars(vars, "id", "commentId", "pos");
    }
    @Test
    public void parseMultipleVariablesWithRegExpr() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        final String pathExpr = "/{id:[a-zA-Z][a-zA-Z][a]}/comment/{commentId:[a-zA-Z][a-zA-Z][a]}/{pos:[a-zA-Z][a-zA-Z][a]}";
        String cleanPath = parser.parse(pathExpr, vars);
        assertEquals("/{id}/comment/{commentId}/{pos}", cleanPath);
        checkVars(vars, "id", "commentId", "pos");
        
    }
    @Test
    public void parseStripWhitespace() {
        final List<PathVariable> vars = new ArrayList<PathVariable>();
        final String pathExpr = "/{   id    :    [a-z  A-Z]   }/   comment/{commentId   :   [a-zA-Z]   }";
        String cleanPath = parser.parse(pathExpr, vars);
        assertEquals("/{id}/   comment/{commentId}", cleanPath);
        checkVars(vars, "id", "commentId");
    }
    
    
    private void checkVars(List<PathVariable> vars, String... varNames) {
        for (String name : varNames) {
            boolean found = false;
            for (PathVariable var : vars) {
                if (var.getName().equals(name)) {
                    found = true;
                }
            }
            assertTrue("path variable with name '" + name + "' not found", found);
        }
    }
}
