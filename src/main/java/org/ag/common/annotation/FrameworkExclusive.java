package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tells users that the annotated method must not be used in their code, that is
 * it is of exclusive use of the framework itself.
 * 
 * If you call any method that has this annotation in your code, you are doing
 * something wrong.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FrameworkExclusive {
}
