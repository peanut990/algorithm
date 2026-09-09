// 5:23 ~ 6:45 7:17~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N; // 맵 크기
    static int M; // 시행 횟수

    static int[][][] cube = {
            {},
            { // 1
                    {0, 5, 0},
                    {3, 1, 4},
                    {0, 2, 0}
            },
            { // 2
                    {0, 6, 0},
                    {4, 2, 3},
                    {0, 1, 0}
            },
            {// 3
                    {0, 5, 0},
                    {6, 3, 1},
                    {0, 2, 0}
            },
            {//4
                    {0, 5, 0},
                    {1, 4, 6},
                    {0, 2, 0}
            },
            {//5
                    {0, 1, 0},
                    {4, 5, 3},
                    {0, 6, 0}
            },
            { // 6
                    {0, 5, 0},
                    {4, 6, 3},
                    {0, 2, 0}
            }
    };

    static int[][] map;


    static int cubeY = 0;
    static int cubeX = 0;

    static int prevNum = -1;
    static int cubeNum = 6;
    static int cubeDir = 1; // 초기방향: 오른쪽

    static int moveDir = 1;

    static int[] dirY = {-1, 0, 1, 0}; //상,우,하,좌 :시계방향
    static int[] dirX = {0, 1, 0, -1};

    static final int CY = 1;
    static final int CX = 1;

    static int result = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 로직시작
        for (int m = 0; m < M; m++) {
            // 1. 주사위 굴리기
            rotCube();

            // 2. 점수 계산
            int score = calScore();
            result += score;

            // 3. 방향 설정
            setNextDir();
        }
        System.out.println(result);

    }

    public static void setNextDir() {
        int[][] curCubeMap = cube[cubeNum];

        // 큐브 방향 설정
        for (int d = 0; d < dirY.length; d++) {
            int nextY = CY + dirY[d];
            int nextX = CX + dirX[d];

            if (curCubeMap[nextY][nextX] == prevNum) {
                cubeDir = (d + 2) % dirY.length; // 반대 방향
                break;
            }
        }

        // 방향 회전
        if (map[cubeY][cubeX] == cubeNum) return;
        else if (map[cubeY][cubeX] < cubeNum) { // 시계방향 90도 회전
            moveDir = (moveDir + 1) % dirY.length;
            cubeDir = (cubeDir + 1) % dirY.length;
        } else {
            moveDir = (dirY.length + moveDir - 1) % dirY.length;
            cubeDir = (dirY.length + cubeDir - 1) % dirY.length;
        }
    }

    public static int calScore() {
        int score = 0;
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];

        q.offer(new int[]{cubeY, cubeX});
        visited[cubeY][cubeX] = true;
        score += map[cubeY][cubeX];

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX) || visited[nextY][nextX]) continue;

                if (map[nextY][nextX] == map[cubeY][cubeX]) {
                    q.offer(new int[]{nextY, nextX});
                    visited[nextY][nextX] = true;
                    score += map[nextY][nextX];
                }
            }
        }

        return score;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void rotCube() {
        int[][] curCubeMap = cube[cubeNum];

        int nextY = cubeY + dirY[moveDir];
        int nextX = cubeX + dirX[moveDir];

        if (!inRange(nextY, nextX)) { // 맵 벗어난 경우
            moveDir = (moveDir + 2) % dirY.length;
            cubeDir = (cubeDir + 2) % dirY.length;
        }

        prevNum = cubeNum;
        cubeNum = curCubeMap[CY + dirY[cubeDir]][CX + dirX[cubeDir]];
        cubeY += dirY[moveDir];
        cubeX += dirX[moveDir];
    }
}

/*
4 3
1 2 4 4
4 2 2 2
6 2 6 6
5 3 3 1


 */