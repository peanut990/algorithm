import java.util.Scanner;

public class Main {
    final static int MOD_NUM = 1_000_000_000;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] dp = new int[N + 1];

        if (K == 1) {
            System.out.println(1);
            return;
        }

        // k == 2
        for (int i = 0; i <= N; i++) {
            dp[i] = i + 1;
        }

        for (int k = 3; k <= K; k++) {
            for (int c = 1; c <= N; c++) {
                dp[c] = (dp[c - 1] + dp[c]) % MOD_NUM;
            }
        }

        System.out.println(dp[N]);

    }
}