import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[] dp = new int[N + 1];
        int[] parents = new int[N + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[N] = 0;

        for (int i = N; i >= 0; i--) {
            if ((i % 3) == 0) {
                if (i / 3 > 0 && dp[i / 3] > dp[i] + 1) {
                    dp[i / 3] = dp[i] + 1;
                    parents[i / 3] = i;
                }
            }
            if ((i % 2) == 0) {
                if (i / 2 > 0 && dp[i / 2] > dp[i] + 1) {
                    dp[i / 2] = dp[i] + 1;
                    parents[i / 2] = i;
                }
            }
            if (i - 1 > 0 && dp[i - 1] > dp[i] + 1) {
                dp[i - 1] = dp[i] + 1;
                parents[i - 1] = i;
            }
        }

        System.out.println(dp[1]);
        List<Integer> res = new LinkedList<>();
        int next = 1;
        while (true) {
            if (next == N) {
                res.add(next);
                break;
            }
            res.add(next);
            next = parents[next];
        }

        Collections.sort(res, Collections.reverseOrder());
        for (Integer re : res) {
            System.out.print(re + " ");
        }
    }
}