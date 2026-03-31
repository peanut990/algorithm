import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] input = new int[M + 2];
        input[0] = 0;
        input[M + 1] = N + 1;
        for (int i = 1; i <= M; i++) {
            input[i] = sc.nextInt();
        }
        long[] dp = new long[41];

        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for (int i = 4; i < 41; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        int res = 1;
        for (int i = 0; i < input.length - 1; i++) {
            int gap = input[i + 1] - input[i] - 1;

            res *= dp[gap];
        }

        System.out.println(res);
    }
}
