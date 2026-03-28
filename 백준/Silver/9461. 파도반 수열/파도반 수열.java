import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        int N = 101;
        long[] dp = new long[N];
        dp[1] = 1;
        dp[2] = 1;
        dp[3] = 1;
        dp[4] = 2;
        dp[5] = 2;
        dp[6] = 3;
        dp[7] = 4;

        for (int i = 8; i < N; i++) {
            dp[i] = dp[i - 1] + dp[i - 5];
        }

        for (int t = 0; t < T; t++) {
            System.out.println(dp[sc.nextInt()]);
        }
    }
}