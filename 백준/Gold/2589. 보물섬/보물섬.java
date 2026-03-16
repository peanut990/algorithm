import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static int L;
    static int M;
    static char[][] map;
    static boolean[][] visited;
    static int[] dirY = {-1, 1, 0, 0};
    static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        L = sc.nextInt();
        M = sc.nextInt();
        sc.nextLine();

        map = new char[L][M];
        visited = new boolean[L][M];
        for (int i = 0; i < L; i++) {
            String line = sc.nextLine();

            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        int maxDis = 0;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 'L') {
                    int dis = BFS(i, j);
                    maxDis = Math.max(maxDis, dis);
                    visited = new boolean[L][M];
                }
            }
        }
        System.out.println(maxDis);
    }

    public static int BFS(int y, int x) {
        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[]{y, x});
        visited[y][x] = true;

        int dis = -1;
        while (!q.isEmpty()) {
            dis++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for (int j = 0; j < 4; j++) {
                    int nextY = cur[0] + dirY[j];
                    int nextX = cur[1] + dirX[j];

                    if (nextY >= 0 && nextY < L && nextX >= 0 && nextX < M && !visited[nextY][nextX]) {
                        if (map[nextY][nextX] == 'L') {
                            q.offer(new int[]{nextY, nextX});
                            visited[nextY][nextX] = true;
                        }
                    }
                }
            }

        }

        return dis;
    }
}
