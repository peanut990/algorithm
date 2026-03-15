import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] map;
    static boolean[][] visited;
    static int totalCount = 0;
    static int afterCount = 0;

    static int[] dirY = {-1, 1, 0, 0};
    static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        map = new int[N][M];
        visited = new boolean[N][M];
        totalCount = 0;

        sc.nextLine();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < M; j++) {
                int height = Integer.parseInt(st.nextToken());
                if (height > 0) {
                    totalCount++;
                }
                map[i][j] = height;
            }
        }

        int result = 0;

        afterCount = totalCount;

        while (true) {
            visited = new boolean[N][M];
            boolean isFound = false;

            for (int i = 1; i < N - 1; i++) {
                for (int j = 1; j < M - 1; j++) {
                    if (visited[i][j] || map[i][j] == 0) {
                        continue;
                    }
                    isFound = true;
                    int curCount = BFS(i, j);

                    if (curCount != totalCount) {
                        System.out.println(result);
                        return;
                    }
                    totalCount = afterCount;

                    result++;
                }
            }

            if (!isFound) {
                System.out.println(0);
                return;
            }
        }
    }

    public static int BFS(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        int curCount = 0;

        visited[y][x] = true;
        q.offer(new int[]{y, x});
        curCount++;

        while (!q.isEmpty()) {
            for (int i = 0; i < q.size(); i++) {
                int[] curLoc = q.poll();
                int curY = curLoc[0];
                int curX = curLoc[1];

                int zeroCount = 0;

                for (int j = 0; j < 4; j++) {
                    int nextY = curY + dirY[j];
                    int nextX = curX + dirX[j];

                    if (!visited[nextY][nextX] && map[nextY][nextX] > 0) {
                        visited[nextY][nextX] = true;
                        q.offer(new int[]{nextY, nextX});
                        curCount++;
                    } else {
                        if (!visited[nextY][nextX]) {
                            zeroCount++;
                        }
                    }
                }

                if (map[curY][curX] - zeroCount < 0) {
                    map[curY][curX] = 0;
                } else {
                    map[curY][curX] -= zeroCount;
                }

                if (map[curY][curX] == 0) {
                    afterCount--;
                }

            }
        }
        return curCount;
    }
}
