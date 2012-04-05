/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.jaxjs.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation required to tell the generator what type a generic list has 
 * (@see type erasure).
 * 
 * @author marcbaechinger
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD, ElementType.PARAMETER})
public @interface Type {
    Class value();
}
