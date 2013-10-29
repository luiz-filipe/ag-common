package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documents which methods break thread-safety. Usually used in conjunction to the <i>PseudoThreadSafe</i> annotation.
 *
 * @see PseudoThreadSafe
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ThreadSafetyBreaker {
}