import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] ary = new int[N + 1];

        sc.nextLine();

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        for (int i = 1; i <= N; i++) {
            ary[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = i; j <= N; j++) {
                dp[j] = Math.max(dp[j - i] + ary[i], dp[j]);
            }
        }

        System.out.println(dp[N]);
    }
}