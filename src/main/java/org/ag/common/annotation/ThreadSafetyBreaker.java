package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the method breaks thread-safety. It is to be used with
 * PseudoThreadSafe annotation. This is useful to document what methods of the
 * class cause it to not be thread-safe.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ThreadSafetyBreaker {
}
