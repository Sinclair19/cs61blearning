package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> Buggy = new BuggyAList<>();
        int[] nums = new int[]{3,4,5};
        for (int i : nums) {
            correct.addLast(i);
            Buggy.addLast(i);
        }
        for (int i : nums) {
            int correctlast = correct.removeLast();
            int Buggylast = Buggy.removeLast();
            assertEquals(correct.size(), Buggy.size());
            assertEquals(correctlast, Buggylast);
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int Bsize = B.size();
                assertEquals(Lsize, Bsize);
                //System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                if (L.size() > 0 && B.size() > 0) {
                    int Llast = L.getLast();
                    int Blast = B.getLast();
                    assertEquals(Llast, Blast);
                    //System.out.println("removelast(" + last + ")");
                }
            }else if (operationNumber == 3) {
                if (L.size() > 0 && B.size() > 0) {
                    assertEquals(L.removeLast(), B.removeLast());}
            }
        }
    }
}
