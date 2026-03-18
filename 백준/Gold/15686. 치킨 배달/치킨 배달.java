import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] map;
    static boolean[][] visited;
    static List<int[]> chicken;
    static int[] curChicken;
    static int result = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        sc.nextLine();

        map = new int[N + 1][N + 1];
        visited = new boolean[N + 1][N + 1];
        chicken = new ArrayList<>();
        curChicken = new int[M];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 2) {
                    chicken.add(new int[]{i, j});
                    map[i][j] = 0;
                } else if (map[i][j] == 1) {
                    map[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        // m개 선택
        DFS(0, 0);

        System.out.println(result);
    }

    public static void DFS(int start, int count) {
        if (count == M) {
            int[][] copyMap = new int[N + 1][N + 1];
            for (int y = 1; y <= N; y++) {
                for (int x = 1; x <= N; x++) {
                    copyMap[y][x] += map[y][x];
                }
            }

            for (int k = 0; k < count; k++) {
                for (int y = 1; y <= N; y++) {
                    for (int x = 1; x <= N; x++) {
                        if (copyMap[y][x] == 0) continue;

                        int[] curLoc = chicken.get(curChicken[k]);
                        int dis = Math.abs(curLoc[0] - y) + Math.abs(curLoc[1] - x);
                        copyMap[y][x] = Math.min(copyMap[y][x], dis);
                    }
                }
            }

            int curSum = 0;
            for (int y = 1; y <= N; y++) {
                for (int x = 1; x <= N; x++) {
                    curSum += copyMap[y][x];
                }
            }

            result = Math.min(result, curSum);

            return;
        }

        for (int i = start; i < chicken.size(); i++) {
            curChicken[count] = i;
            DFS(i + 1, count + 1);
        }
    }

}
