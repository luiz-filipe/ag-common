package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The class to which this annotation is applied is considered to be thread-safe at runtime, but it contains methods
 * that are not thread safe. These classes are implemented like that because there is a big performance penalty to be
 * paid in case synchronisation were to be used.
 *
 * <p>An example of class that uses this strategy is the <i>BasicNode</i> class.</p>
 *
 * @see org.ag.common.env.BasicNode
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PseudoThreadSafe {
}
