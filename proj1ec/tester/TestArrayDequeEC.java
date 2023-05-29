package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.algs4.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void randomTest() {
        ArrayDequeSolution<Integer> L = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> B = new StudentArrayDeque<>();

        StringBuilder str = new StringBuilder();
        str.append("\n");

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                str.append("addLast(").append(randVal).append(")");
                //assertEquals(str.toString(), L.get(L.size() - 1), B.get(B.size() - 1));
                str.append("\n");
            } else if (operationNumber == 1) {
                // addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                B.addFirst(randVal);
                str.append("addFirst(").append(randVal).append(")");
                //assertEquals(str.toString(), L.get(0), B.get(0));
                str.append("\n");
            } else if (operationNumber == 2) {
                // size
                int lSize = L.size();
                int bSize = B.size();
                str.append("size()");
                assertEquals(str.toString(), lSize, bSize);
                str.append("\n");
            } else if (operationNumber == 3) {
                // get
                if (L.size() > 0 && B.size() > 0) {
                    int num = StdRandom.uniform(0, L.size());
                    Integer lLast = L.get(num);
                    Integer bLast = B.get(num);
                    str.append("get(").append(num).append(")");
                    assertEquals(str.toString(), lLast, bLast);
                    str.append("\n");
                }
            } else if (operationNumber == 4) {
                // removeLast
                if (L.size() > 0 && B.size() > 0) {
                    Integer lLast = L.removeLast();
                    Integer bLast = B.removeLast();
                    str.append("removeLast()");
                    assertEquals(str.toString(), lLast, bLast);
                    str.append("\n");
                }
            } else if (operationNumber == 5) {
                // removeFirst
                if (L.size() > 0 && B.size() > 0) {
                    Integer lFirst = L.removeFirst();
                    Integer bFirst = B.removeFirst();
                    str.append("removeFirst()");
                    assertEquals(str.toString(), lFirst, bFirst);
                    str.append("\n");
                }
            }
        }
    }
}
