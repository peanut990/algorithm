import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] dp = new int[N];
        int[] T = new int[N];
        int[] P = new int[N];

        for (int i = 0; i < N; i++) {
            T[i] = sc.nextInt();
            P[i] = sc.nextInt();
        }

        int max = 0;

        if (T[0] <= N) {
            max = P[0];
            dp[0] = P[0];
        }

        for (int i = 1; i < N; i++) {
            if (i + T[i] > N) {
                continue;
            }
            dp[i] = P[i];

            for (int j = i - 1; j >= 0; j--) {
                if (j + T[j] > i) {
                    continue;
                }

                dp[i] = Math.max(dp[i], dp[j] + P[i]);
            }
            max = Math.max(max, dp[i]);
        }
        System.out.println(max);
    }
}