package hu.icellmobilsoft.atr.sample.util;

import org.apache.commons.lang3.EnumUtils;

/**
 * Utilites for enums
 *
 * @author juhaszkata
 */
public class EnumUtil {

    /**
     * Enum to enum conversion
     *
     * @param source
     * @param targetClass
     * @return instance of targetClass (having same name as source) - or null, if source is null
     */
    public static <A extends Enum<A>, B extends Enum<B>> B convert(A source, Class<B> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        return EnumUtils.getEnum(targetClass, source.name());
    }
}
