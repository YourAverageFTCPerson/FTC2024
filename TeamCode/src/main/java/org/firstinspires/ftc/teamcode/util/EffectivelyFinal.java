package org.firstinspires.ftc.teamcode.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The annotated value is static, set somewhere else and should be considered final. Should always be a wrapper type if a primitive is required to cause NPE if unset.
 */
@Target(ElementType.FIELD)
public @interface EffectivelyFinal {
}
