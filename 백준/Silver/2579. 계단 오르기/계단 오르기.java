import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] scores = new int[N + 2];
        int[] dp = new int[N + 2];

        for (int i = 0; i < N; i++) {
            scores[i] = sc.nextInt();
        }

        dp[0] = scores[0];
        dp[1] = scores[0] + scores[1];
        dp[2] = Math.max(scores[0] + scores[2], scores[1] + scores[2]);
        for (int i = 0; i < N - 2; i++) {
            int curScore = dp[i];
            dp[i + 2] = Math.max(dp[i + 2], scores[i + 2] + curScore);
            if (i + 3 >= N) {
                break;
            }
            dp[i + 3] = Math.max(dp[i + 3], scores[i + 3] + scores[i + 2] + curScore);
        }

        System.out.println(dp[N - 1]);
    }
}
