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

        dp[0] = ary[0];
        int res = dp[0];
        for (int i = 1; i < N; i++) {
            dp[i] = ary[i];
            for (int j = i - 1; j >= 0; j--) {
                if (ary[j] < ary[i]) {
                    dp[i] = Math.max(dp[j] + ary[i], dp[i]);
                }
            }
            res = Math.max(res, dp[i]);
        }
        System.out.println(res);
    }
}
