import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int absN = Math.abs(N);
        int[] dp = new int[absN + 2];
        dp[0] = 0;
        dp[1] = 1;

        if (N == 0) {
            System.out.println(0);
            System.out.println(0);
            return;
        }

        if (N > 0) {
            for (int i = 2; i <= absN; i++) {
                dp[i] = (dp[i - 1] + dp[i - 2]) % 1_000_000_000;
            }
        } else if (N < 0) {
            for (int i = 2; i <= absN; i++) {
                dp[i] = (dp[i - 2] - dp[i - 1]) % 1_000_000_000;
            }
        }

        if (dp[absN] > 0) {
            System.out.println(1);
        } else {
            System.out.println(-1);
        }
        System.out.println(Math.abs(dp[absN]));
    }
}