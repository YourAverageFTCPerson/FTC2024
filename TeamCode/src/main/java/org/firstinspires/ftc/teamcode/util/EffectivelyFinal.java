package org.firstinspires.ftc.teamcode.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The annotated value is set somewhere else and should be considered final.
 */
@Target(ElementType.FIELD)
public @interface EffectivelyFinal {
}
