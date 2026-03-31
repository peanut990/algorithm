import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] T = new int[N + 2];
        int[] P = new int[N + 2];
        int[] dp = new int[N + 2];

        for (int i = 1; i <= N; i++) {
            T[i] = sc.nextInt();
            P[i] = sc.nextInt();
        }

        int max = 0;

        for (int i = N; i >= 1; i--) {
            if (i + T[i] > N + 1) {
                dp[i] = Math.max(dp[i], dp[i + 1]);
                continue;
            }

            // 하는 경우
            dp[i] = Math.max(dp[i], dp[i + T[i]] + P[i]);

            // 안하는 경우
            dp[i] = Math.max(dp[i], dp[i + 1]);

            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }
}
