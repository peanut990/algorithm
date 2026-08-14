import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static int N; // 미로 크기
    public static int M; // 참가자 수
    public static int K; // 시간

    public static int[][] map;

    public static int[] dirY = {-1, 1, 0, 0};// 상,하,좌,우
    public static int[] dirX = {0, 0, -1, 1};

    public static int[] otherDirY = {-1, -1, 1, 1}; // 좌상, 우상, 좌하, 우하
    public static int[] otherDirX = {-1, 1, -1, 1};

    public static final int EXIT = 100;

    public static int moveCount = 0;
    public static int outCount = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 맵 입력
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // 참가자 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;

            map[y][x]--;
        }

        // 출구 입력
        st = new StringTokenizer(br.readLine());
        int eY = Integer.parseInt(st.nextToken()) - 1;
        int eX = Integer.parseInt(st.nextToken()) - 1;
        map[eY][eX] = EXIT;

        // 로직 시작
        for (int k = 0; k < K; k++) { // k값 수정 필요
            //1. 참자가 이동
            map = movePeople();


            if (outCount == M) break;

            //2. 미로 회전
            rotateMap();
        }

        System.out.println(moveCount);
        int[] exitPos = getExitPos(map);
        System.out.println((exitPos[0]+1) + " " + (exitPos[1]+1));

    }

    public static void rotateMap() {
        int[] exitPos = getExitPos(map);

        int[] searchedRacPosInfo = searchRacPos();

        //회전
        rotate(searchedRacPosInfo);
    }

    public static void rotate(int[] searchedRacPos) {
        int minY = searchedRacPos[0];
        int minX = searchedRacPos[1];
        int size = searchedRacPos[2];

        int[][] tmp = new int[size][size];
        int[][] rotated = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmp[i][j] = map[minY + i][minX + j];
            }
        }

        // 부분 돌리기
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rotated[j][size - 1 - i] = tmp[i][j];
            }
        }

        // 돌려진 맵 반영
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //돌려진 벽 차감
                if (isWall(rotated[i][j])) {
                    rotated[i][j]--;
                }

                map[i + minY][j + minX] = rotated[i][j];
            }
        }
    }

    public static int[] searchRacPos() {
        for (int size = 2; size <= 10; size++) {
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    if (findPeopleAndExit(y, x, size)) return new int[]{y, x, size};
                }
            }
        }
        return null;
    }

    public static boolean findPeopleAndExit(int y, int x, int size) {
        boolean foundPeople = false;
        boolean foundExit = false;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int nY = y + i;
                int nX = x + j;
                if (!inRange(nY, nX)) return false;

                if (isPeople(map[nY][nX])) foundPeople = true;
                if (isExit(map[nY][nX])) foundExit = true;
            }
        }

        return foundPeople && foundExit;
    }

    public static boolean findPeople(int minY, int minX, int maxY, int maxX) {
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (isPeople(map[y][x])) { // 사람 발견
                    return true;
                }
            }
        }
        return false;
    }

    public static int[][] movePeople() {
        int[][] movedMap = new int[N][N];
        int[] exitPos = getExitPos(map);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (isPeople(map[i][j])) { // 참자가 발견
                    // 이동 체크
                    int dir = searchMoveDir(i, j, exitPos[0], exitPos[1]);

                    // 이동 , dir = - 1 이면 제자리
                    int nextY = i;
                    int nextX = j;

                    if (dir != -1) {
                        nextY += dirY[dir];
                        nextX += dirX[dir];
                    }

                    move(map[i][j], nextY, nextX, movedMap, dir);
                } else if (isWall(map[i][j]) || isExit(map[i][j])) { // 벽 반영
                    movedMap[i][j] = map[i][j];
                }
            }
        }

        return movedMap;
    }

    public static void move(int peopleCount, int nextY, int nextX, int[][] movedMap, int dir) {
        if (dir != -1) {
            moveCount += Math.abs(peopleCount);
        }

        if (isExit(map[nextY][nextX])) {
            outCount += Math.abs(peopleCount);
            return;
        }

        // 이동 반영
        movedMap[nextY][nextX] += peopleCount;
    }


    public static int searchMoveDir(int y, int x, int exitY, int exitX) {
        int dir = -1;
        int maxDist = getDist(y, x, exitY, exitX);

        for (int d = 0; d < dirY.length; d++) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;
            if (isWall(map[nextY][nextX])) continue; // 벽이면 넘김( 사람, 빈칸 통과)

            int nextDist = getDist(nextY, nextX, exitY, exitX);
            if (nextDist >= maxDist) continue;

            maxDist = nextDist;
            dir = d;
        }

        return dir;
    }

    public static boolean isPeople(int value) {
        return value < 0;
    }

    public static boolean isWall(int value) {
        return value >= 1 && value <= 9;
    }

    public static boolean isExit(int value) {
        return value == EXIT;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static int getDist(int y1, int x1, int y2, int x2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static int[] getExitPos(int[][] map) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == EXIT) return new int[]{i, j};
            }
        }
        return null;
    }

    public static int[][] copyMap(int[][] map) {
        int[][] tmpMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmpMap[i][j] = map[i][j];
            }
        }
        return tmpMap;
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.printf("%3d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
