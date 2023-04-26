public class ex4 {
    public static int sum(int[] a) {
        int sum = 0;
        for(int i : a){
            sum += i;
        }
        return sum;
    }
    public static void windowPosSum(int[] a, int n){
        for(int i = 0; i < a.length; i += 1){
            if (a[i] < 0){
                continue;
            }
            int end = i + n + 1;
            if (end > a.length){
                end = a.length;
            }
            int[] sliced = java.util.Arrays.copyOfRange(a, i, end);
            a[i] = sum(sliced);
        }
    }
    public static void main(String[] args){
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        int[] b = {1, -1, -1, 10, 5, -1};
        int n2 = 2;
        windowPosSum(a, n);
        windowPosSum(b, n2);
        System.out.println(java.util.Arrays.toString(a));
        System.out.println(java.util.Arrays.toString(b));
    }
}
