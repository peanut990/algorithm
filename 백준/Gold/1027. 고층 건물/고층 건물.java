import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] ary = new int[N];
        sc.nextLine();
        StringTokenizer st = new StringTokenizer(sc.nextLine());
        for (int i = 0; i < N; i++) {
            ary[i] = Integer.parseInt(st.nextToken());
        }
        int max = 0;

        for (int i = 0; i < N; i++) {
            int count = 0;
            for (int j = 0; j < N; j++) {
                if (j == i) continue;
                int y1 = ary[i];
                int x1 = i;

                int y2 = ary[j];
                int x2 = j;

                int leftIdx = Math.min(x1, x2);
                int rightIdx = Math.max(x1, x2);
                boolean found = false;
                for (int x = leftIdx + 1; x < rightIdx; x++) {
                    double y = fn(x1, x2, y1, y2, x);
                    if (ary[x] >= y) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    count++;
                }
            }

            max = Math.max(count, max);
        }

        System.out.println(max);
    }

    public static double fn(int x1, int x2, int y1, int y2, int x) {
        return ((double) (y2 - y1) / (x2 - x1)) * (x - x1) + y1;
    }
}
