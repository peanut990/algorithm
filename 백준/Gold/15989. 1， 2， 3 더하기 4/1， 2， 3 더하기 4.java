import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        long[] dp = new long[10_001];

        dp[0] = 1;
        for (int num = 1; num <= 3; num++) {
            for (int j = num; j <= 10_000; j++) {
                dp[j] += dp[j - num];
            }
        }

        for (int t = 0; t < T; t++) {
            int n = sc.nextInt();
            System.out.println(dp[n]);
        }
    }
}