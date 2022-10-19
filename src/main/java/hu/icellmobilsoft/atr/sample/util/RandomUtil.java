package hu.icellmobilsoft.atr.sample.util;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Random;

import javax.enterprise.inject.Vetoed;

import org.apache.commons.lang3.StringUtils;

/**
 * The type Random util.
 */
@Vetoed
public class RandomUtil {

    /**
     * The constant DATE_2013_01_01.
     */
    public static long DATE_2013_01_01 = 1356998400000L;
    /**
     * The constant MAX_NUM_SYS.
     */
    public static final int MAX_NUM_SYS = 62;
    /**
     * The constant LOWERCASE.
     */
    public static final char[] LOWERCASE;
    /**
     * The constant UPPERCASE.
     */
    public static final char[] UPPERCASE = new char[26];
    /**
     * The constant ALL_LETTER.
     */
    public static final char[] ALL_LETTER;
    /**
     * The constant ALL_LETTER_STRING.
     */
    public static final String ALL_LETTER_STRING;
    /**
     * The constant generatedIndex.
     */
    public static int generatedIndex = 0;
    /**
     * The constant PID.
     */
    public static final int PID;
    /**
     * The constant PID62.
     */
    protected static final String PID62;
    /**
     * The constant PID36.
     */
    protected static final String PID36;
    private static final Random RANDOM;

    /**
     * Instantiates a new Random util.
     */
    public RandomUtil() {
    }

    /**
     * Generate id string.
     *
     * @return the string
     */
    public static String generateId() {
        int xInd = getNextIndex();
        Date xDate = new Date();
        xDate.setTime(xDate.getTime() - DATE_2013_01_01);
        String xRes = convertToRadix(xDate.getTime(), 36L);
        xRes = paddL(xRes, 8, '0');
        StringBuilder builder = new StringBuilder();
        builder.append(xRes);
        Long nano = System.nanoTime();
        String xNano = convertToRadix(nano, 36L);
        builder.append(xNano.substring(xNano.length() - 4, xNano.length()));
        builder.append(paddL(convertToRadix((long) RANDOM.nextInt(1296), 36L), 2, '0'));
        builder.append(paddL(convertToRadix((long) xInd, 36L), 2, '0'));
        return builder.toString();
    }

    /**
     * Gets next index.
     *
     * @return the next index
     */
    protected static synchronized int getNextIndex() {
        ++generatedIndex;
        if (generatedIndex > 1296) {
            generatedIndex = 0;
        }

        return generatedIndex;
    }

    /**
     * Padd l string.
     *
     * @param str    the str
     * @param length the length
     * @param padd   the padd
     * @return the string
     */
    protected static String paddL(String str, int length, char padd) {
        return StringUtils.leftPad(str, length, padd);
    }

    /**
     * Convert to radix string.
     *
     * @param inNum the in num
     * @param radix the radix
     * @return the string
     */
    protected static String convertToRadix(long inNum, long radix) {
        if (radix == 0L) {
            return null;
        } else {
            long num = inNum;
            String result = "";

            long numDivRadix;
            do {
                numDivRadix = num / radix;
                long dig = (num % radix + radix) % radix;
                char var10000 = ALL_LETTER[(int) dig];
                result = "" + var10000 + result;
                num = numDivRadix;
            } while (numDivRadix != 0L);

            return result;
        }
    }

    static {
        int i;
        for (i = 65; i < 91; ++i) {
            UPPERCASE[i - 65] = (char) i;
        }

        LOWERCASE = new char[26];

        for (i = 97; i < 123; ++i) {
            LOWERCASE[i - 97] = (char) i;
        }

        ALL_LETTER = new char[62];

        for (i = 48; i < 58; ++i) {
            ALL_LETTER[i - 48] = (char) i;
        }

        for (i = 10; i < 36; ++i) {
            ALL_LETTER[i] = UPPERCASE[i - 10];
        }

        for (i = 36; i < 62; ++i) {
            ALL_LETTER[i] = LOWERCASE[i - 10 - 26];
        }

        ALL_LETTER_STRING = new String(ALL_LETTER);
        PID = Integer.valueOf(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
        PID62 = paddL(convertToRadix((long) PID, 62L), 3, '0').substring(0, 3);
        PID36 = paddL(convertToRadix((long) PID, 36L), 3, '0').substring(0, 3);
        RANDOM = new Random();
    }

}
