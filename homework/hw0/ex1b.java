public class ex1b {
    public static void drawTriangle(int n) {
        int x = 1;
        String a = "*";
        while (x <= n) {
            System.out.println(a.repeat(x));
            x += 1;
        }
    }
    public static void main(String[] args) {
        drawTriangle(10);
    }
}
