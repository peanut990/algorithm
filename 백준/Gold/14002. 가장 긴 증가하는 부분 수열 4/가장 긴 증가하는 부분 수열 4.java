import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] input = new int[N];
        int[] dp = new int[N];
        int[] parents = new int[N];

        Arrays.fill(parents, -1);
        sc.nextLine();

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        for (int i = 0; i < N; i++) {
            input[i] = Integer.parseInt(st.nextToken());
        }

        int res = 1;
        int maxIdx = 0;
        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (input[j] < input[i] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    parents[i] = j;
                }
            }
            if (dp[i] > res) {
                res = dp[i];
                maxIdx = i;
            }
        }

        System.out.println(res);

        int next = maxIdx;
        List<Integer> seq = new ArrayList<>();
        while (next != -1) {
            seq.add(input[next]);
            next = parents[next];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = seq.size() - 1; i >= 0; i--) {
            sb.append(seq.get(i) + " ");
        }
        System.out.println(sb);
    }
}