import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int[][] stairs = new int[N][3];

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 3; i++) {
                stairs[n][i] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] maxDp = new int[N][3];
        int[][] minDp = new int[N][3];

        for (int i = 0; i < 3; i++) {
            maxDp[0][i] = stairs[0][i];
            minDp[0][i] = stairs[0][i];
        }

        int max = Math.max(maxDp[0][0], Math.max(maxDp[0][1], maxDp[0][2]));
        int min = Math.min(minDp[0][0], Math.min(minDp[0][1], minDp[0][2]));

        for (int s = 1; s < N; s++) {
            maxDp[s][0] = Math.max(maxDp[s - 1][0], maxDp[s - 1][1]) + stairs[s][0];
            maxDp[s][1] = Math.max(maxDp[s - 1][0], Math.max(maxDp[s - 1][1], maxDp[s - 1][2])) + stairs[s][1];
            maxDp[s][2] = Math.max(maxDp[s - 1][1], maxDp[s - 1][2]) + stairs[s][2];

            max = Math.max(maxDp[s][0], Math.max(maxDp[s][1], maxDp[s][2]));

            minDp[s][0] = Math.min(minDp[s - 1][0], minDp[s - 1][1]) + stairs[s][0];
            minDp[s][1] = Math.min(minDp[s - 1][0], Math.min(minDp[s - 1][1], minDp[s - 1][2])) + stairs[s][1];
            minDp[s][2] = Math.min(minDp[s - 1][1], minDp[s - 1][2]) + stairs[s][2];

            min = Math.min(minDp[s][0], Math.min(minDp[s][1], minDp[s][2]));
        }


        System.out.print(max + " " + min);
    }
}
