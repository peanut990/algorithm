import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] ary = new int[N];
        int[] dp = new int[N];
        sc.nextLine();

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        for (int i = 0; i < N; i++) {
            ary[i] = Integer.parseInt(st.nextToken());
            dp[i] = ary[i];
        }

        int max = ary[0];
        for (int i = 1; i < N; i++) {

            dp[i] = Math.max(dp[i], dp[i - 1] + ary[i]);

            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }
}
