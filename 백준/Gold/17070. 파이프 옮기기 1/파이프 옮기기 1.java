import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;
    static int[][][] countMap;

    static int[] dirY = {0, 1, 1}; // 우, 우하단, 하단
    static int[] dirX = {1, 1, 0};

    static int[][] dir = {{0, 1}, {0, 1, 2}, {1, 2}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        countMap = new int[N][N][3];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(DFS(0, 1, 0));
    }

    public static int DFS(int y, int x, int curDir) {
        if (y == N - 1 && x == N - 1) {
            return 1;
        }

        boolean flag = true;
        for (int i = 0; i < dir[curDir].length; i++) {
            int nextDir = dir[curDir][i];
            if (countMap[y][x][nextDir] == 0) {
                flag = false;
            }
        }

        if (flag) {
            int tmp = 0;
            for (int i = 0; i < dir[curDir].length; i++) {
                int nextDir = dir[curDir][i];
                tmp += countMap[y][x][nextDir];
            }
            return tmp;
        }


        for (int i = 0; i < dir[curDir].length; i++) {
            int nextDir = dir[curDir][i];
            int nextY = y + dirY[nextDir];
            int nextX = x + dirX[nextDir];

            if (!inBound(nextY, nextX)) continue;
            if (map[nextY][nextX] == 1) continue;

            // 1인경우 우,하 체크
            if (nextDir == 1) {
                if (map[y][x + 1] == 1 || map[y + 1][x] == 1) continue;
            }

            countMap[y][x][nextDir] = DFS(nextY, nextX, nextDir);
//            printMap(countMap);
        }

        int tmp = 0;
        for (int i = 0; i < dir[curDir].length; i++) {
            int nextDir = dir[curDir][i];
            tmp += countMap[y][x][nextDir];
        }
        return tmp;
    }


    public static boolean inBound(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}
