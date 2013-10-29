package org.ag.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Some methods should be made public because they need to be accessed by other parts of the framework but there are
 * some cases that these methods should be executed by the framework only. Users should not call these methods because
 * they may break thread-safety for example.
 *
 * <p>This annotation documents such methods, to make sure they are not used when they shouldn't be used.</p>
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FrameworkExclusive {
}
