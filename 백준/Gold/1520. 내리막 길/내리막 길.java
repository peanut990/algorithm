import java.util.Scanner;

public class Main {
    static int M;
    static int N;
    static int[][] ary;
    static int[][] road;
    static boolean[][] visited;
    static int[] dirY = {-1, 1, 0, 0};
    static int[] dirX = {0, 0, -1, 1}; // 상하좌우

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        M = sc.nextInt();
        N = sc.nextInt();

        ary = new int[M][N];
        road = new int[M][N];
        visited = new boolean[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                ary[i][j] = sc.nextInt();
            }
        }

        int answer = DFS(0, 0);
        System.out.println(answer == -1 ? 0 : answer);

    }

    public static int DFS(int y, int x) {
        if (y == M - 1 && x == N - 1) {
            return 1;
        }

        if (road[y][x] > 0) {
            return road[y][x];
        } else if (road[y][x] == -1) {
            return -1;
        }


        for (int i = 0; i < 4; i++) {
            int nextY = y + dirY[i];
            int nextX = x + dirX[i];
            int isRoad = 0;

            if (nextY >= 0 && nextY < M && nextX >= 0 && nextX < N && !visited[nextY][nextX]) {
                if (ary[y][x] <= ary[nextY][nextX]) {
                    continue;
                }

                visited[nextY][nextX] = true;
                isRoad = DFS(nextY, nextX);
                if (isRoad > 0) {
                    road[y][x] += isRoad;
                }
                visited[nextY][nextX] = false;
            }
        }

        if (road[y][x] == 0) {
            road[y][x] = -1;
        }

        return road[y][x];
    }
}
