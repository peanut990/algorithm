import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] ary = new int[N];

        for (int i = 0; i < N; i++) {
            ary[i] = sc.nextInt();
        }

        int[] dp1 = new int[N];
        int[] dp2 = new int[N];

        for (int i = 0; i < N; i++) {
            dp1[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (ary[j] < ary[i]) {
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }
            }
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                if (ary[i] > ary[j]) {
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }
        }

        int max = 0;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, dp1[i] + dp2[i]);
        }
        System.out.println(max);
    }
}
