import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        int[] ary = new int[T + 1];

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            int tree = Integer.parseInt(st.nextToken());
            ary[t] = tree;
        }
        int[][] dp = new int[W + 1][T + 1];

        for (int i = 1; i <= T; i++) {
            if (ary[i] == 1) {
                dp[0][i] = dp[0][i - 1] + 1;
            } else {
                dp[0][i] = dp[0][i - 1];
            }
        }

        int max = dp[0][T];
        for (int w = 1; w <= W; w++) {
            int curTree = 0;
            if (w % 2 == 0) {
                curTree = 1;
            } else {
                curTree = 2;
            }

            for (int t = w; t <= T; t++) {
                // 현재 나무랑 바꾼 나무랑 같은 경우
                if (ary[t] == curTree) {
                    dp[w][t] = Math.max(dp[w - 1][t - 1] + 1, dp[w][t - 1] + 1);
                } else {
                    dp[w][t] = Math.max(dp[w - 1][t - 1], dp[w][t - 1]);
                }
            }
            max = Math.max(dp[w][T], max);
        }
        System.out.println(max);

    }
}
