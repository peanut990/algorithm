import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<int[]> lines = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            lines.add(new int[]{sc.nextInt(), sc.nextInt()});
        }

        Collections.sort(lines, (a, b) -> {
            return a[0] - b[0];
        });
        int[] dp = new int[N];

        int max = 1;
        for (int i = 0; i < N; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (lines.get(j)[1] < lines.get(i)[1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }

        System.out.println(N - max);
    }
}
