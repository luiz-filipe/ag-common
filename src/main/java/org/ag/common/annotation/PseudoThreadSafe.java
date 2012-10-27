package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class to which this annotation is applied is considered to be thread-safe
 * at runtime, but it contains methods that are not thread safe. For example,
 * for a performance optimisation point of view, a method that is likely to be
 * called all the time would gain from not having to synchronise as long as its
 * users know the risks they are taking.
 * 
 * Example of ussage: BasicNode
 * 
 * @see BasicNode
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PseudoThreadSafe {
}
