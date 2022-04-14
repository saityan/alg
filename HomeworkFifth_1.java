public class HomeworkFifth_1 {
    public static void main(String[] args) {
        System.out.println(exponentiate(25, 4));
    }

    public static long exponentiate (int m, int n) throws ArithmeticException {
        if (n == 0) return 1;
        else if (n == 1) return m;
        else if (n < 0) {
            throw new ArithmeticException("Only non-negative exponents are allowed. Given: " + n);
        }
        try {
            return Math.multiplyExact(m, exponentiate(m, n - 1));
        }
        catch (Exception e) {
            throw new ArithmeticException("long overflow");
        }
    }
}
