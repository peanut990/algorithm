import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] ary = new int[N];
        int[] dp = new int[N];

        for (int i = 0; i < N; i++) {
            ary[i] = sc.nextInt();
        }

        int max = ary[0];
        dp[0] = ary[0];
        if (N > 1) {
            dp[1] = ary[0] + ary[1];
        }

        if (N > 2) {
            dp[2] = Math.max(ary[0] + ary[2], ary[1] + ary[2]);
        }

        for (int i = 1; i < N; i++) {
            for (int j = i - 2; j >= 0; j--) {
                dp[i] = Math.max(dp[j] + ary[i], dp[i]);
            }

            for (int j = i - 3; j >= 0; j--) {
                dp[i] = Math.max(dp[j] + ary[i - 1] + ary[i], dp[i]);
            }

            dp[i] = Math.max(dp[i], dp[i - 1]);
            max = Math.max(dp[i], max);
        }

        System.out.println(max);
    }
}