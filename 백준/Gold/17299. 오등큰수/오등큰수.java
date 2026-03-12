import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] A = new int[N];
        int[] F = new int[1_000_001];
        int[] result = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
            F[A[i]]++;
        }

        Stack<int[]> stack = new Stack<>();

        for (int i = N - 1; i >= 0; i--) {
            int cur = F[A[i]];

            if (stack.isEmpty()) {
                result[i] = -1;
                int[] tmp = {cur, i};
                stack.push(tmp);
                continue;
            }

            if (stack.peek()[0] > cur) {
                result[i] = A[stack.peek()[1]];
                int[] tmp = {cur, i};
                stack.push(tmp);
                continue;
            }

            while (!stack.isEmpty() && stack.peek()[0] <= cur) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                result[i] = -1;
                int[] tmp = {cur, i};
                stack.push(tmp);
                continue;
            }

            result[i] = A[stack.peek()[1]];
            int[] tmp = {cur, i};
            stack.push(tmp);

        }
        
        StringBuilder sb = new StringBuilder();
        for (int i : result) {
            sb.append(i).append(' ');
        }
        System.out.print(sb);

    }
}
