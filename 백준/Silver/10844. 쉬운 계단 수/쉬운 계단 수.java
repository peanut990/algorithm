import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        long[] dp = new long[10];
        Arrays.fill(dp, 1);
        dp[0] = 0;

        int div = 1_000_000_000;
        for (int i = 1; i < N; i++) {
            long[] next = new long[10];

            next[1] = (next[1] + dp[0]) % div;
            next[8] = (next[8] + dp[9]) % div;
            for (int j = 1; j < 9; j++) {
                next[j + 1] = (next[j + 1] + dp[j]) % div;
                next[j - 1] = (next[j - 1] + dp[j]) % div;
            }

            for (int j = 0; j < 10; j++) {
                dp[j] = next[j];
            }
        }

        long result = 0;
        for (long i : dp) {
            result = (result + i) % div;
        }
        System.out.println(result);
    }
}