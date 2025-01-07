package org.firstinspires.ftc.teamcode.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Treat as if final if the enclosing method isn't for initialization.
 */
@Target(ElementType.FIELD)
public @interface EffectivelyFinal {
    String initializerMethodName() default "";
}
