import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 1:50
// 초기 출구 방향 => 0:북 1:동 2:남 3:서
public class Main {
    static int R;
    static int C;
    static int K;
    static int[][] map;

    static final int[] exitDirY = {-1, 0, 1, 0}; // 북, 동, 남, 서
    static final int[] exitDirX = {0, 1, 0, -1};

    static final int[] dirY = {-1, 0, 1, 0}; // 북, 동, 남, 서
    static final int[] dirX = {0, 1, 0, -1};

    static final int[] dirRotWestY = {-1, 0, 1, 1, 2}; //좌1상1, 좌2, 좌1하1, 좌2하1, 좌1하2
    static final int[] dirRotWestX = {-1, -2, -1, -2, -1};

    static final int ROT_WEST_DIR = 1;
    static final int ROT_EAST_DIR = -1;

    static int ROW_SIZE;
    static int COL_SIZE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        ROW_SIZE = R + 3;
        COL_SIZE = C;
        map = new int[ROW_SIZE][COL_SIZE];

        int answer = 0;

        for (int k = 0; k < K; k++) { // K 수정@@@
            st = new StringTokenizer(br.readLine());
            int centerY = 1;
            int centerX = Integer.parseInt(st.nextToken()) - 1;
            int exitDir = Integer.parseInt(st.nextToken());

            //if(k != 2) continue;

            int num = k + 1;
            //골렘 진입
            enter(centerY, centerX, exitDir, num);
//            printMap();

            // 골렘 이동 시작
            while (true) {
                if (checkSouth(centerY, centerX)) {
                    int[] nextCenter = moveSouth(centerY, centerX);
                    centerY = nextCenter[0];
                    centerX = nextCenter[1];

//                    printMap();
                    continue;
                }

                if (checkRot(centerY, centerX, ROT_WEST_DIR)) {
                    int[] nextCenter = moveRot(centerY, centerX, ROT_WEST_DIR);

                    centerY = nextCenter[0];
                    centerX = nextCenter[1];

//                    System.out.println("서쪽 회전@@");
//                    printMap();
                    continue;
                }

                if (checkRot(centerY, centerX, ROT_EAST_DIR)) {
                    int[] nextCenter = moveRot(centerY, centerX, ROT_EAST_DIR);

                    centerY = nextCenter[0];
                    centerX = nextCenter[1];

//                    System.out.println("동쪽 회전@@");
//                    printMap();
                    continue;
                }

                break;
            }

            // 넘치는 경우
            if (centerY == 1 || centerY == 2 || centerY == 3) {
                map = new int[ROW_SIZE][COL_SIZE]; // 맵 초기화
                continue;
            }

            // 정령 이동
            int curMaxY = BFS(centerY, centerX) - 2;
            answer += curMaxY;
//            System.out.println(curMaxY + " " + answer);
        }
        System.out.println(answer);
    }

    public static int BFS(int y, int x) {
        Queue<int[]> q = new LinkedList<>();
        int maxY = 0;
        boolean[][] checkMap = new boolean[ROW_SIZE][COL_SIZE];
        q.offer(new int[]{y, x});
        checkMap[y][x] = true;
        maxY = Math.max(maxY, y);

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            int curNum = map[poll[0]][poll[1]];

            for (int i = 0; i < dirY.length; i++) {
                int nextY = poll[0] + dirY[i];
                int nextX = poll[1] + dirX[i];

                if (!inBound(nextY, nextX) || checkMap[nextY][nextX] || map[nextY][nextX] == 0) continue;

                if (curNum < 0) { // 출구 인 경우
                    q.offer(new int[]{nextY, nextX});
                    checkMap[nextY][nextX] = true;
                    maxY = Math.max(maxY, nextY);
                    continue;
                }

                if (curNum > 0 && Math.abs(map[nextY][nextX]) == curNum) { // 출구가 아니면 골렘 내부만
                    q.offer(new int[]{nextY, nextX});
                    checkMap[nextY][nextX] = true;
                    maxY = Math.max(maxY, nextY);
                }
            }
        }

        return maxY;
    }

    public static int[] moveRot(int y, int x, int rotDir) {
        // 현재 골렘 상태 저장
        int[] prevPosition = new int[4];
        int curNum = map[y][x];
        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            prevPosition[dir] = map[nextY][nextX];
        }

        // 현재 자리 정리
        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            map[nextY][nextX] = 0;
        }
        map[y][x] = 0;

        // 출구 회전
        int[] rotatedPosition = new int[4];
        for (int i = 0; i < rotatedPosition.length; i++) {
            rotatedPosition[(rotatedPosition.length + i - rotDir) % rotatedPosition.length] = prevPosition[i];
        }

        // 이동
        int nextCenterY = y + 1;
        int nextCenterX = x - rotDir;
        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = nextCenterY + dirY[dir];
            int nextX = nextCenterX + dirX[dir];

            map[nextY][nextX] = rotatedPosition[dir];
        }
        map[nextCenterY][nextCenterX] = curNum;


        return new int[]{nextCenterY, nextCenterX};
    }

    public static boolean checkRot(int y, int x, int rotDir) {
        for (int dir = 0; dir < dirRotWestY.length; dir++) {
            int nextY = y + dirRotWestY[dir];
            int nextX = x + dirRotWestX[dir] * rotDir;

            if (!inBound(nextY, nextX)) return false;
            if (map[nextY][nextX] != 0) return false;
        }

        return true;
    }

    public static int[] moveSouth(int y, int x) {
        // 현재 골렘 상태 저장
        int[] prevPosition = new int[4];
        int curNum = map[y][x];

        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            prevPosition[dir] = map[nextY][nextX];
        }


        // 현재 자리 정리
        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            map[nextY][nextX] = 0;
        }
        map[y][x] = 0;

        // 이동
        int nextCenterY = y + 1;
        int nextCenterX = x;
        for (int dir = 0; dir < dirY.length; dir++) {
            int nextY = nextCenterY + dirY[dir];
            int nextX = nextCenterX + dirX[dir];

            map[nextY][nextX] = prevPosition[dir];
        }
        map[nextCenterY][nextCenterX] = curNum;

        return new int[]{nextCenterY, nextCenterX};
    }

    public static boolean checkSouth(int y, int x) {
        for (int dir = 0; dir < dirY.length; dir++) {
            if (dir == 0) continue; // 북 제외

            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            if (!inBound(nextY + 1, nextX)) return false;
            if (map[nextY + 1][nextX] != 0) return false;
        }
        return true;
    }

    public static boolean inBound(int y, int x) {
        return y >= 0 && y < ROW_SIZE && x >= 0 && x < COL_SIZE;
    }

    public static void enter(int startY, int startX, int exitDir, int num) {
        map[startY][startX] = num;
        for (int d = 0; d < exitDirY.length; d++) {
            int nextY = startY + exitDirY[d];
            int nextX = startX + exitDirX[d];

            if (d == exitDir) {
                map[nextY][nextX] = -num;
            } else {
                map[nextY][nextX] = num;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i < map.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
0
1
2
--- 시작선
3
4
5
6
7
...

5 3 2
2 2
2 2

*/