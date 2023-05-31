package flik;

/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
    }
    // for Integer, if the value is between -128 and 127,
    // it will use the cached pool and this is true only when auto-boxing
    /**
    public static boolean isSameNumber(int a, int b) {
        return a == b;
    }
     */
}
