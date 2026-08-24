import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static int N; // 미지의 공간 크기
    public static int M; // 시간의 벽 크기
    public static int F; // 시간 이상 현상 개수

    public static final int MACHINE = 2;
    public static final int GOAL = 4;

    public static final int PASS = 10;
    public static final int ONE_CONVERTER = 11;
    public static final int TWO_CONVERTER = 12;
    public static final int THREE_CONVERTER = 13;
    public static final int FOUR_CONVERTER = 14;

    public static final int STRANGE = 20;

    public static int TIME_WALL_SIZE;

    public static int[][] unknownMap; // N
    public static int[][] timeWallMap; // 9

    public static int[][] map; // N + 9 // 최종 맵

    public static int[][] stranges; // 0:y, 1:x, 2: dir, 3:v
    public static int[] machineLoc;
    public static int[] goalLoc;

    public static int[] dirY = {0, 0, 1, -1};// 동,서,남,북
    public static int[] dirX = {1, -1, 0, 0};

    public static final int EAST = 0;
    public static final int WEST = 1;
    public static final int SOUTH = 2;
    public static final int NORTH = 3;

    public static int minDist = -1;

    public static void main(String[] args) throws Exception {
        init();
        // 로직 시작
        for (int k = 1; k <= map.length * map.length; k++) {
            //1. 이상현상 확산
            spreadStranges(k);

            //2. 타임머신 이동
            int curDist = getMoveMachine();

            minDist = curDist;
        }
        System.out.println(minDist);
    }

    public static int getMoveMachine() {
        boolean[][] checked = new boolean[map.length][map.length];
        Queue<int[]> q = new LinkedList<>();
        q.offer(machineLoc);
        int lv = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();

                for (int d = 0; d < dirY.length; d++) {
                    int nextY = poll[0] + dirY[d];
                    int nextX = poll[1] + dirX[d];

                    if (!inRange(nextY, nextX)) continue;
                    if (checked[nextY][nextX]) continue;
                    if (map[nextY][nextX] == 1) continue;
                    if (isStrange(map[nextY][nextX]) && lv + 1 >= (map[nextY][nextX] - STRANGE)) continue;


                    if (isPass(map[nextY][nextX]) || isConverter(map[nextY][nextX])) {
                        int[] passedLoc = doPass(nextY, nextX, d);
                        nextY = passedLoc[0];
                        nextX = passedLoc[1];

                        if (!inRange(nextY, nextX)) continue;
                        if (checked[nextY][nextX]) continue;
                        if (map[nextY][nextX] == 1) continue;
                        if (isStrange(map[nextY][nextX]) && lv + 1 >= (map[nextY][nextX] - STRANGE)) continue;
                    }

                    if (map[nextY][nextX] == GOAL) {
                        // 경로 입력
                        return lv + 1;
                    }

                    // 0
                    checked[nextY][nextX] = true;
                    q.offer(new int[]{nextY, nextX});
                }
            }
            lv++;
        }

        return -1;
    }

    public static int[] doPass(int y, int x, int dir) {
        while (isPass(map[y][x]) || isConverter(map[y][x])) {

            if (isConverter(map[y][x])) {
                dir = changeDir(map[y][x], dir);
            }

            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];


            y = nextY;
            x = nextX;
        }

        return new int[]{y, x};
    }

    /*
        * : 방향 바꾸고 통과
        - 방향 전환
                (1사: 동->남, 북->서) => 11
                (2사: 서->남, 북->동) => 12
                (3사: 남->동, 서->북) => 13
                (4사: 동->북, 남->서) => 14

         // 0:동,1:서,2:남,3:북
     */
    public static int changeDir(int converterType, int dir) {
        int changedDir = dir;

        if (converterType == ONE_CONVERTER) {
            if (dir == EAST) changedDir = SOUTH;
            else if (dir == NORTH) changedDir = WEST;

        } else if (converterType == TWO_CONVERTER) {
            if (dir == WEST) changedDir = SOUTH;
            else if (dir == NORTH) changedDir = EAST;

        } else if (converterType == THREE_CONVERTER) {
            if (dir == SOUTH) changedDir = EAST;
            else if (dir == WEST) changedDir = NORTH;

        } else {
            if (dir == EAST) changedDir = NORTH;
            else if (dir == SOUTH) changedDir = WEST;
        }

        return changedDir;
    }

    public static boolean isPass(int value) {
        return value == PASS;
    }

    public static boolean isConverter(int value) {
        return value == ONE_CONVERTER || value == TWO_CONVERTER || value == THREE_CONVERTER || value == FOUR_CONVERTER;
    }

    public static void spreadStranges(int k) {
        for (int i = 0; i < stranges.length; i++) {
            int[] strange = stranges[i];
            int dir = strange[2];

            // v 체크
            if (k % strange[3] != 0) continue;

            int nextY = strange[0] + dirY[dir];
            int nextX = strange[1] + dirX[dir];

            if (!inRange(nextY, nextX) || isStrange(map[nextY][nextX])) {
                continue;
            }

            if (isPass(map[nextY][nextX]) || isConverter(map[nextY][nextX])) {
                int[] passedLoc = doPass(nextY, nextX, dir);
                nextY = passedLoc[0];
                nextX = passedLoc[1];

                if (!inRange(nextY, nextX)) continue;
                if (map[nextY][nextX] != 0) continue;
            }

            if (map[nextY][nextX] != 0) continue;

            // 확산
            map[nextY][nextX] = k + STRANGE;
            strange[0] = nextY;
            strange[1] = nextX;
        }
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < map.length && x >= 0 && x < map.length;
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                System.out.printf("%2d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        F = Integer.parseInt(st.nextToken());

        TIME_WALL_SIZE = M * 3;

        //unknownMap
        unknownMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                unknownMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // timeWallMap
        timeWallMap = new int[TIME_WALL_SIZE][TIME_WALL_SIZE];
        for (int i = 0; i < TIME_WALL_SIZE; i++) {
            for (int j = 0; j < TIME_WALL_SIZE; j++) {
                timeWallMap[i][j] = PASS;
            }
        }

        for (int d = 0; d < 5; d++) { //동:270,서:90,남:0,북: 180, 윗면:0
            int[][] timeWall = new int[M][M];
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    timeWall[i][j] = Integer.parseInt(st.nextToken());
                    if (timeWall[i][j] == MACHINE) {
                        machineLoc = new int[]{i, j};
                    }
                }
            }

            timeWall = rotateRight90(timeWall, rotateCount[d]);

            startLocInTimeWallMap = new int[][]{
                    {M, 2 * M}, {M, 0}, {2 * M, M}, {0, M}, {M, M}
            };

            int[] startLoc = startLocInTimeWallMap[d];

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    timeWallMap[startLoc[0] + i][startLoc[1] + j] = timeWall[i][j];
                }
            }

            // 방향 컨버터 입력
            //1사 분면
            for (int i = 0; i < M; i++) {
                timeWallMap[i][TIME_WALL_SIZE - 1 - i] = ONE_CONVERTER;
            }

            //2사 분면
            for (int i = 0; i < M; i++) {
                timeWallMap[i][i] = TWO_CONVERTER;
            }

            //3사 분면
            for (int i = 0; i < M; i++) {
                timeWallMap[TIME_WALL_SIZE - 1 - i][i] = THREE_CONVERTER;
            }

            //4사 분면
            for (int i = 0; i < M; i++) {
                timeWallMap[TIME_WALL_SIZE - 1 - i][TIME_WALL_SIZE - 1 - i] = FOUR_CONVERTER;
            }
        }

        // 이상현상
        stranges = new int[F][4];
        for (int i = 0; i < F; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            stranges[i] = new int[]{y, x, d, v};
        }

        //map
        map = new int[N + TIME_WALL_SIZE - M][N + TIME_WALL_SIZE - M];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = PASS;
            }
        }

        // 시간벽 시작 좌표
        int[] timeWallStartLoc = searchTimeWallStartLoc();
        int timeWallStartY = timeWallStartLoc[0];
        int timeWallStartX = timeWallStartLoc[1];

        //printMap(unknownMap);
        int timeWallEndY = timeWallStartY + M - 1;
        int timeWallEndX = timeWallStartX + M - 1;

        //System.out.println(timeWallStartY + " " + timeWallStartX);
        // 시간 벽 -> map 옮기기
        for (int y = 0; y < timeWallMap.length; y++) {
            for (int x = 0; x < timeWallMap.length; x++) {
                map[y + timeWallStartY][x + timeWallStartX] = timeWallMap[y][x];

                if (isMachine(timeWallMap[y][x])) {
                    machineLoc = new int[]{y + timeWallStartY, x + timeWallStartX};
                }
            }
        }

        // 미지의 벽 -> map 옮기기
        for (int y = 0; y < unknownMap.length; y++) {
            for (int x = 0; x < unknownMap.length; x++) {
                int[] mapLoc = changeMapLoc(y, x, timeWallStartY, timeWallStartX, timeWallEndY, timeWallEndX);

                if (mapLoc == null) continue;
                int mapY = mapLoc[0];
                int mapX = mapLoc[1];

                map[mapY][mapX] = unknownMap[y][x];

                if (unknownMap[y][x] == GOAL) {
                    goalLoc = new int[]{mapY, mapX};
                }
            }
        }

        // 이상현상 -> map
        for (int[] s : stranges) {
            int[] mapLoc = changeMapLoc(s[0], s[1], timeWallStartY, timeWallStartX, timeWallEndY, timeWallEndX);

            s[0] = mapLoc[0];
            s[1] = mapLoc[1];

            map[s[0]][s[1]] = STRANGE;
        }
    }

    public static int[] changeMapLoc(int y, int x, int timeWallStartY, int timeWallStartX, int timeWallEndY, int timeWallEndX) {
        int mapY = 0;
        int mapX = 0;
        if (smallThanTimeWall(y, timeWallStartY)) {
            // 시간벽 상단
            if (smallThanTimeWall(x, timeWallStartX)) {
                mapY = y;
                mapX = x;
            } else if (inTimeWall(x, timeWallStartX, timeWallEndX)) {
                mapY = y;
                mapX = x + M;
            } else if (biggerThanTimeWall(x, timeWallEndX)) {
                mapY = y;
                mapX = x + 2 * M;
            }
        } else if (inTimeWall(y, timeWallStartY, timeWallEndY)) {
            // 시간벽 중간
            if (smallThanTimeWall(x, timeWallStartX)) {
                mapY = y + M;
                mapX = x;
            } else if (inTimeWall(x, timeWallStartX, timeWallEndX)) {
                return null;
            } else if (biggerThanTimeWall(x, timeWallEndX)) {
                mapY = y + M;
                mapX = x + 2 * M;
            }
        } else { // 시간벽 하단
            if (smallThanTimeWall(x, timeWallStartX)) {
                mapY = y + 2 * M;
                mapX = x;
            } else if (inTimeWall(x, timeWallStartX, timeWallEndX)) {
                mapY = y + 2 * M;
                mapX = x + M;
            } else if (biggerThanTimeWall(x, timeWallEndX)) {
                mapY = y + 2 * M;
                mapX = x + 2 * M;
            }
        }
        return new int[]{mapY, mapX};
    }

    public static boolean isStrange(int value) {
        return value >= STRANGE;
    }

    public static boolean isMachine(int value) {
        return value == MACHINE;
    }

    public static int[] searchTimeWallStartLoc() {
        int timeWallStartY = 0;
        int timeWallStartX = 0;
        for (int i = 0; i < unknownMap.length; i++) {
            for (int j = 0; j < unknownMap.length; j++) {
                if (unknownMap[i][j] == 3) {
                    timeWallStartY = i;
                    timeWallStartX = j;

                    return new int[]{timeWallStartY, timeWallStartX};
                }
            }
        }
        return new int[]{timeWallStartY, timeWallStartX};
    }

    public static boolean smallThanTimeWall(int v, int timeWallStartV) {
        return v >= 0 && v < timeWallStartV;
    }

    public static boolean inTimeWall(int v, int timeWallStartV, int timeWallEndV) {
        return v >= timeWallStartV && v <= timeWallEndV;
    }

    public static boolean biggerThanTimeWall(int v, int timeWallEndV) {
        return v > timeWallEndV && v < N;
    }


    public static int[] rotateCount = new int[]{3, 1, 0, 2, 0}; // 동,서,남,북,윗면
    public static int[][] startLocInTimeWallMap;

    public static int[][] rotateRight90(int[][] map, int count) {
        for (int c = 0; c < count; c++) {
            int[][] tmp = new int[map.length][map.length];

            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map.length; x++) {
                    tmp[x][map.length - 1 - y] = map[y][x];
                }
            }
            map = tmp;
        }

        return map;
    }
}


/*
#map
- 0:공간, 1: 장애물, 2: 타임머신

# 미지의 공간
- 3: 시간의 벽
- 4: 탈출구

이상현상 확산 방향: 동,서,남,북

    북
서   위  동
    남


(y,x)
                    (0,3) (0,4) (0,5)
                    (1,3) (1,4) (1,5)
                    (2,3) (2,4) (2,5)

(3,0) (3,1) (3,2)   (3,3) (3,4) (3,5)  (3,6) (3,7) (3,8)  (   )  (   ) (   )
(4,0) (4,1) (4,2)   (4,3) (4,4) (4,5)  (4,6) (4,7) (4,8)  (   )  (   ) (   )
(5,0) (5,1) (5,2)   (5,3) (5,4) (5,5)  (5,6) (5,7) (5,8)  (   )  (   ) (   )

                    (6,3) (6,4) (6,5)    *     -     -      -     -     -
                    (7,3) (7,4) (7,5)    -     *     -      -     -     -
                    (8,3) (8,4) (8,5)    -     -     *      -     -     -

                    (   )  (   ) (   )   -     -     -      (   )  (   ) (   )
                    (   )  (   ) (   )   -     -     -      (   )  (   ) (   )
                    (   )  (   ) (   )   -     -     -      (   )  (   ) (   )


1. 이상현상 확산
2. 타임머신 이동
- : 그냥 통과 => 10
* : 방향 바꾸고 통과
    - 방향 전환
        (1사: 동->남, 북->서) => 11
        (2사: 서->남, 북->동) => 12
        (3사: 남->동, 서->북) => 13
        (4사: 동->북, 남->서) => 14


*/


