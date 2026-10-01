import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int K;

    static final int N = 10;
    static int[][] map = new int[N][N];

    static int[] dirY = {1, 0}; // 하, 우
    static int[] dirX = {0, 1};

    static final int YELLOW = 0;
    static final int RED = 1;

    static final int COLOR_SIZE = 4;

    static int score = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        K = Integer.parseInt(st.nextToken());
        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            // 로직 시작
            // 1. 내리기
            List<int[]> blocks = getCurBlocks(t, y, x);

            downToYello(blocks);
            downToRed(blocks);

            // 2. 줄 체크
            // 노란색 체크
            checkYello();
            // 빨간색 체크
            checkRed();

            // 3. 연한 구역 체크
            checkLightYello();
            checkLightRed();
        }

        System.out.println(score);
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) count++;
            }
        }
        System.out.println(count);
    }

    /*
     - 연한 노란색 체크
        - minY, maxY, -> lenY
        - if(lenY > 0)
            밑에칸 lenY 만큼 행 지우기
        - 노랑칸 내리기

     */
    public static void checkLightYello() {
        boolean onFour = false;
        boolean onFive = false;

        for (int x = 0; x < 4; x++) {
            if (map[5][x] == 1) {
                onFive = true;
                break;
            }
        }

        for (int x = 0; x < 4; x++) {
            if (map[4][x] == 1) {
                onFour = true;
                break;
            }
        }

        int len = 0;
        if (onFive) {
            len++;
            if (onFour) {
                len++;
            }
        }

        // 밑칸 지우기
        for (int i = 0; i < len; i++) {
            for (int x = 0; x < 4; x++) {
                map[N - 1][x] = 0;
            }

            // 내리기
            afterBombInYellow(N - 1);
        }
    }

    public static void checkLightRed() {
        boolean onFour = false;
        boolean onFive = false;

        for (int y = 0; y < 4; y++) {
            if (map[y][5] == 1) {
                onFive = true;
                break;
            }
        }

        for (int y = 0; y < 4; y++) {
            if (map[y][4] == 1) {
                onFour = true;
                break;
            }
        }

        int len = 0;
        if (onFive) {
            len++;
            if (onFour) {
                len++;
            }
        }

        // 밑칸 지우기
        for (int i = 0; i < len; i++) {
            for (int y = 0; y < 4; y++) {
                map[y][N - 1] = 0;
            }

            // 내리기
            afterBombInRed(N - 1);
        }
    }


    public static void checkYello() {
        while (true) {
            boolean found = false;
            for (int y = N - 1; y >= N - COLOR_SIZE; y--) {
                boolean canBomb = true;
                for (int x = 0; x < COLOR_SIZE; x++) {
                    if (map[y][x] == 0) {
                        canBomb = false;
                        break;
                    }
                }

                if (canBomb) { // 꽉찬 행
                    score++;
                    // 행 지우기
                    for (int x = 0; x < COLOR_SIZE; x++) {
                        map[y][x] = 0;
                    }
                    found = true;
                    afterBombInYellow(y);
                    break;
                }
            }

            if (!found) {
                return;
            }
        }
    }

    public static void afterBombInYellow(int bombY) {
        // 노랑칸 내리기
        for (int y = bombY - 1; y >= 4; y--) {
            for (int x = 0; x < 4; x++) {
                if (map[y][x] == 1) {
                    int nextY = y + 1;
                    int nextX = x;
                    if (inRange(nextY, nextX) && map[nextY][nextX] == 0) {
                        map[y][x] = 0;
                        map[nextY][nextX] = 1;
                    }
                }
            }
        }
    }

    public static void checkRed() {
        while (true) {
            boolean found = false;
            for (int x = N - 1; x >= N - COLOR_SIZE; x--) {
                boolean canBomb = true;
                for (int y = 0; y < COLOR_SIZE; y++) {
                    if (map[y][x] == 0) {
                        canBomb = false;
                        break;
                    }
                }

                if (canBomb) { // 꽉찬 행
                    score++;
                    // 행 지우기
                    for (int y = 0; y < COLOR_SIZE; y++) {
                        map[y][x] = 0;
                    }
                    found = true;
                    afterBombInRed(x);
                    break;
                }
            }

            if (!found) {
                return;
            }
        }
    }

    public static void afterBombInRed(int bombX) {
        // 빨간칸 내리기
        for (int x = bombX - 1; x >= 4; x--) {
            for (int y = 0; y < 4; y++) {
                if (map[y][x] == 1) {
                    int nextY = y;
                    int nextX = x + 1;
                    if (inRange(nextY, nextX) && map[nextY][nextX] == 0) {
                        map[y][x] = 0;
                        map[nextY][nextX] = 1;
                    }
                }
            }
        }
    }

    public static int getRedMinLen(List<int[]> right) {
        int minLen = N - 1;
        for (int[] loc : right) {
            int[] nextLoc = getNextLoc(loc[0], loc[1], RED);
            int len = nextLoc[1] - loc[1];
            minLen = Math.min(minLen, len);
        }

        return minLen;
    }

    public static List<int[]> getRight(List<int[]> blocks) {
        int[][] tmp = new int[N][N];
        for (int[] l : blocks) {
            tmp[l[0]][l[1]] = 1;
        }

        List<int[]> right = new ArrayList<>();

        for (int y = 0; y < 4; y++) {
            for (int x = N - 1; x >= 0; x--) {
                if (tmp[y][x] == 1) {
                    right.add(new int[]{y, x});
                    break;
                }
            }
        }

        return right;
    }

    public static void downToRed(List<int[]> blocks) {
        List<int[]> right = getRight(blocks);
        int minLen = getRedMinLen(right);

        Collections.sort(blocks, (a, b) -> {
            return b[1] - a[1];
        });

        for (int[] loc : blocks) {
            map[loc[0]][loc[1]] = 0;
            map[loc[0]][loc[1] + minLen] = 1;
        }
    }

    public static int getYellowMinLen(List<int[]> bottom) {
        int minLen = N - 1;

        for (int[] loc : bottom) {
            int[] nextLoc = getNextLoc(loc[0], loc[1], YELLOW);
            int len = nextLoc[0] - loc[0];
            minLen = Math.min(minLen, len);
        }

        return minLen;
    }

    public static List<int[]> getBottom(List<int[]> blocks) {
        int[][] tmp = new int[N][N];
        for (int[] l : blocks) {
            tmp[l[0]][l[1]] = 1;
        }

        List<int[]> bottom = new ArrayList<>();

        for (int x = 0; x < 4; x++) {
            for (int y = N - 1; y >= 0; y--) {
                if (tmp[y][x] == 1) {
                    bottom.add(new int[]{y, x});
                    break;
                }
            }
        }

        return bottom;
    }


    public static void downToYello(List<int[]> blocks) {
        List<int[]> bottom = getBottom(blocks);
        int minLen = getYellowMinLen(bottom);

        Collections.sort(blocks, (a, b) -> {
            return b[0] - a[0];
        });

        for (int[] loc : blocks) {
            map[loc[0]][loc[1]] = 0;
            map[loc[0] + minLen][loc[1]] = 1;
        }
    }

    public static int[] getNextLoc(int y, int x, int dir) {
        int nextY = y + dirY[dir];
        int nextX = x + dirX[dir];

        while (true) {
            if (!inRange(nextY, nextX) || map[nextY][nextX] == 1) break;

            y = nextY;
            x = nextX;
            nextY += dirY[dir];
            nextX += dirX[dir];
        }

        return new int[]{y, x};
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void printMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static List<int[]> getCurBlocks(int type, int y, int x) {
        List<int[]> locs = new ArrayList<>();

        locs.add(new int[]{y, x});

        if (type == 2) {
            locs.add(new int[]{y, x + 1});
        } else if (type == 3) {
            locs.add(new int[]{y + 1, x});
        }

        return locs;
    }
}