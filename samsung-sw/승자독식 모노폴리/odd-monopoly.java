// 5:13 ~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int K;

    static int[][][] dirs;
    static int[] curDir;

    static int[][] map;
    static int[][][] turnMap;

    static int[] dirY = {-1, 1, 0, 0}; //상,하,좌,우
    static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        init();

        // 로직 시작
        for (int t = 1; t <= 1000; t++) {
            // 1. 독점 계약
            monoPolys();

            // 2. 이동
            map = movePlayers();

            // 3. 계약 턴수 감소
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (turnMap[i][j][1] > 0) {
                        turnMap[i][j][1]--;
                        if (turnMap[i][j][1] == 0) {
                            turnMap[i][j][0] = 0;
                        }
                    }
                }
            }

            // 1번 체크
            boolean foundOne = false;
            int pCount = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] > 0) pCount++;
                    if (map[i][j] == 1) foundOne = true;
                }
            }

            if (!foundOne) break;

            if (pCount == 1) {
                System.out.println(t);
                return;
            }
        }

        System.out.println("-1");
    }

    public static int[][] movePlayers() {
        int[][] nextMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > 0) {
                    move(i, j, nextMap);
                }
            }
        }

        return nextMap;
    }

    public static void move(int y, int x, int[][] nextMap) {
        int num = map[y][x];
        int dir = curDir[num];

        int[] nextLoc = getNextLoc(y, x, num, dir);

        // 방향 반영
        curDir[num] = nextLoc[2];

        // 좌표 이동 (
        if (nextMap[nextLoc[0]][nextLoc[1]] > 0) {
            nextMap[nextLoc[0]][nextLoc[1]] = Math.min(nextMap[nextLoc[0]][nextLoc[1]], num);
        } else {
            nextMap[nextLoc[0]][nextLoc[1]] = num;
        }
    }

    public static int[] getNextLoc(int y, int x, int num, int dir) {
        int[] dirPriority = dirs[num][dir];

        // 게약 확인
        for (int d : dirPriority) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (turnMap[nextY][nextX][1] == 0) { // 계약 없음
                return new int[]{nextY, nextX, d};
            }
        }

        // 가능한 칸 없음 -> 본인 칸으로 이동
        for (int d : dirPriority) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (turnMap[nextY][nextX][0] == num) { // 자기가 한 계약 칸
                return new int[]{nextY, nextX, d};
            }
        }

        return null;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void monoPolys() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = map[i][j];
                if (num > 0) {
                    turnMap[i][j] = new int[]{num, K};
                }
            }
        }
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dirs = new int[M + 1][4][4];
        curDir = new int[M + 1];

        map = new int[N][N];
        turnMap = new int[N][N][2];

        // 맵 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 초기 방향 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            curDir[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        // 방향 우선 순위 입력
        for (int i = 1; i <= M; i++) {
            for (int j = 0; j < 4; j++) {
                st = new StringTokenizer(br.readLine());
                for (int l = 0; l < 4; l++) {
                    dirs[i][j][l] = Integer.parseInt(st.nextToken()) - 1;
                }
            }
        }
    }
}