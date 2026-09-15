 // 4:30~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N; //크기
    static int Q; // 시행 횟수

    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = (int) Math.pow(2, Integer.parseInt(st.nextToken()));
        Q = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 로직 시작
        st = new StringTokenizer(br.readLine());
        for (int q = 0; q < Q; q++) {
            int level = Integer.parseInt(st.nextToken());

            int size = (int) Math.pow(2, level);
            int[][] nextMap = new int[N][N];

            // 1. 회전
            if (level > 0) {
                for (int i = 0; i < N; i += size) {
                    for (int j = 0; j < N; j += size) {
                        rot(i, j, size, nextMap);
                    }
                }


                map = nextMap;
            }

            // 2. 녹이기
            map = melting();
        }


        // 2. 집계
        getMaxSize();

        System.out.println(totalCount);
        System.out.println(maxSize);
    }

    public static int[][] melting() {
        int[][] meltedMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) continue;

                int iceCount = 0;
                for (int d = 0; d < dirY.length; d++) {
                    int nextY = i + dirY[d];
                    int nextX = j + dirX[d];

                    if (!inRange(nextY, nextX) || map[nextY][nextX] == 0) continue;

                    iceCount++;
                }

                if (iceCount >= 3) { // 안 녹음
                    meltedMap[i][j] = map[i][j];
                } else {
                    meltedMap[i][j] = map[i][j] - 1;
                }

            }
        }
        return meltedMap;
    }

    public static int totalCount = 0;
    public static int maxSize = 0;

    public static void getMaxSize() {
        boolean[][] visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j] || map[i][j] == 0) continue;

                int size = BFS(i, j, visited);
                maxSize = Math.max(maxSize, size);
            }
        }
    }

    public static int[] dirY = {-1, 1, 0, 0}; // 상,하,좌,우
    public static int[] dirX = {0, 0, -1, 1};

    public static int BFS(int sY, int sX, boolean[][] visited) {
        int size = 0;
        Queue<int[]> q = new LinkedList<>();

        q.offer(new int[]{sY, sX});
        visited[sY][sX] = true;
        size++;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            totalCount += map[poll[0]][poll[1]];

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX) || visited[nextY][nextX] || map[nextY][nextX] == 0) continue;

                q.offer(new int[]{nextY, nextX});
                visited[nextY][nextX] = true;
                size++;
            }

        }

        return size;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    static void rot(int y, int x, int size, int[][] nextMap) {
        int[] dirY = {0, size / 2, 0, -size / 2};
        int[] dirX = {size / 2, 0, -size / 2, 0};

        int sY = y;
        int sX = x;
        for (int dir = 0; dir < 4; dir++) {
            int nextSY = sY + dirY[dir];
            int nextSX = sX + dirX[dir];

            // 옮기기
            for (int i = 0; i < size / 2; i++) {
                for (int j = 0; j < size / 2; j++) {
                    nextMap[nextSY + i][nextSX + j] = map[sY + i][sX + j];
                }
            }

            sY = nextSY;
            sX = nextSX;

        }
    }
}
