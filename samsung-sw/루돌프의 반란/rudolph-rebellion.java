import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static class Santa {
        int num;
        int[] pos = new int[2];
        int inActiveTurn = -1;
        int score = 0;

        Santa(int num, int y, int x) {
            this.num = num;
            pos[0] = y;
            pos[1] = x;
        }

        public void move(int nextY, int nextX) {
            // 좌표 이동
            map[this.pos[0]][this.pos[1]] = 0;
            this.pos = new int[]{nextY, nextX};

            if (!inRange(nextY, nextX)) {
                return;
            }

            map[this.pos[0]][this.pos[1]] = this.num;
        }
    }

    static int N; // 게임판 크기
    static int M; // 게임턴 수
    static int P; // 산타 수
    static int C; // 루돌푸 힘
    static int D; // 산타 힘

    static int[][] map;
    static Santa[] santas;
    static int[] rudolphPos = new int[2];

    static int[][] rudolphDirs = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    static int[][] santaDirs = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        santas = new Santa[P + 1];

        st = new StringTokenizer(br.readLine());
        rudolphPos[0] = Integer.parseInt(st.nextToken()) - 1;
        rudolphPos[1] = Integer.parseInt(st.nextToken()) - 1;

        for (int p = 0; p < P; p++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;

            santas[num] = new Santa(num, y, x);
        }

        // 1. 게임판 구성
        map[rudolphPos[0]][rudolphPos[1]] = -1;
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            map[s.pos[0]][s.pos[1]] = s.num;
        }

        for (int turn = 0; turn < M; turn++) { // turn 크기 수정 ####
            // 2. 루돌푸의 움직임
            moveRudolph(turn);

            // 3. 산타의 움직임
            moveSantas(turn);

            // 턴 종료
            if (getOutCount() == P) {
                break;
            }

            addScore();
        }

        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            System.out.print(s.score + " ");
        }

    }

    public static void moveSantas(int curTurn) {
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];

            if (!inRange(s.pos[0], s.pos[1])) continue;  // out
            if (s.inActiveTurn >= curTurn) continue; // 기절

            int moveDir = searchSantaMoveDir(s);

            if (moveDir == -1) continue;

            int nextY = s.pos[0] + santaDirs[moveDir][0];
            int nextX = s.pos[1] + santaDirs[moveDir][1];

            // 충돌 체크
            if (isRudolph(nextY, nextX)) {
                // 충돌 상태 결과
                s.score += D;
                s.inActiveTurn = curTurn + 1;

                // 산타 좌표 이동
                int targetY = rudolphPos[0] - santaDirs[moveDir][0] * D;
                int targetX = rudolphPos[1] - santaDirs[moveDir][1] * D;

                // 상호 작용 체크
                Stack<Santa> stack = new Stack<>();
                int canY = targetY;
                int canX = targetX;
                while (inRange(canY, canX) && isSanta(canY, canX)) {
                    int canNum = map[canY][canX];
                    if (canNum == s.num) break;
                    stack.push(santas[canNum]);

                    canY -= santaDirs[moveDir][0];
                    canX -= santaDirs[moveDir][1];
                }

                while (!stack.isEmpty()) {
                    Santa movingSanta = stack.pop();
                    int movingY = movingSanta.pos[0] - santaDirs[moveDir][0];
                    int movingX = movingSanta.pos[1] - santaDirs[moveDir][1];
                    movingSanta.move(movingY, movingX);
                }

                s.move(targetY, targetX);

                continue;
            }

            // 좌표 이동
            s.move(nextY, nextX);
        }
    }

    public static int searchSantaMoveDir(Santa santa) {
        int minDist = Integer.MAX_VALUE;
        int moveDir = -1;
        int curDist = getDist(rudolphPos[0], rudolphPos[1], santa.pos[0], santa.pos[1]);

        for (int d = 0; d < santaDirs.length; d++) {
            int nextY = santa.pos[0] + santaDirs[d][0];
            int nextX = santa.pos[1] + santaDirs[d][1];

            if (!inRange(nextY, nextX)) continue;
            if (map[nextY][nextX] > 0) continue; // 다음칸이 산타인 경우

            int nextDist = getDist(rudolphPos[0], rudolphPos[1], nextY, nextX);

            if (curDist <= nextDist) continue;

            if (minDist > nextDist) {
                minDist = nextDist;
                moveDir = d;
            }
        }

        return moveDir;
    }

    public static void moveRudolph(int curTurn) {
        Santa searched = searchSanta();

        // 방향 선택, 좌표 이동
        int moveDir = searchRudolphMoveDir(searched);

        int nextY = rudolphPos[0] + rudolphDirs[moveDir][0];
        int nextX = rudolphPos[1] + rudolphDirs[moveDir][1];

        // 충돌 체크
        if (isSanta(nextY, nextX)) {
            int num = map[nextY][nextX];
            Santa crashed = santas[num];

            // 충돌 상태 결과
            crashed.score += C;
            crashed.inActiveTurn = curTurn + 1;

            // 산타 좌표 이동
            int targetY = crashed.pos[0] + rudolphDirs[moveDir][0] * C;
            int targetX = crashed.pos[1] + rudolphDirs[moveDir][1] * C;

            // 상호 작용 체크
            Stack<Santa> stack = new Stack<>();
            int canY = targetY;
            int canX = targetX;
            while (inRange(canY, canX) && isSanta(canY, canX)) {
                int canNum = map[canY][canX];
                if (canNum == crashed.num) break;

                stack.push(santas[canNum]);

                canY += rudolphDirs[moveDir][0];
                canX += rudolphDirs[moveDir][1];
            }

            while (!stack.isEmpty()) {
                Santa movingSanta = stack.pop();
                int movingY = movingSanta.pos[0] + rudolphDirs[moveDir][0];
                int movingX = movingSanta.pos[1] + rudolphDirs[moveDir][1];
                movingSanta.move(movingY, movingX);
            }

            crashed.move(targetY, targetX);
        }

        // 루돌프 좌표 이동
        map[rudolphPos[0]][rudolphPos[1]] = 0;
        rudolphPos = new int[]{nextY, nextX};
        map[rudolphPos[0]][rudolphPos[1]] = -1;
    }

    public static int searchRudolphMoveDir(Santa santa) {
        int minDist = Integer.MAX_VALUE;
        int moveDir = 0;
        for (int d = 0; d < rudolphDirs.length; d++) {
            int nextY = rudolphPos[0] + rudolphDirs[d][0];
            int nextX = rudolphPos[1] + rudolphDirs[d][1];

            if (!inRange(nextY, nextX)) continue;

            int dist = getDist(santa.pos[0], santa.pos[1], nextY, nextX);
            if (minDist > dist) {
                minDist = dist;
                moveDir = d;
            }
        }
        return moveDir;
    }

    public static Santa searchSanta() {
        List<Santa> searchedList = new ArrayList<>();
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            if (!inRange(s.pos[0], s.pos[1])) continue; // out

            searchedList.add(s);
        }

        Collections.sort(searchedList, (a, b) -> {
            int distA = getDist(rudolphPos[0], rudolphPos[1], a.pos[0], a.pos[1]);
            int distB = getDist(rudolphPos[0], rudolphPos[1], b.pos[0], b.pos[1]);

            if (distA != distB) return distA - distB;
            if (a.pos[0] != b.pos[0]) return b.pos[0] - a.pos[0];
            return b.pos[1] - a.pos[1];
        });

        return searchedList.get(0);
    }

    public static boolean isRudolph(int y, int x) {
        return map[y][x] == -1;
    }

    public static boolean isSanta(int y, int x) {
        return map[y][x] > 0;
    }

    public static int getDist(int y1, int x1, int y2, int x2) {
        return (y1 - y2) * (y1 - y2) + (x1 - x2) * (x1 - x2);
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static int getOutCount() {
        int outCount = 0;
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            if (!inRange(s.pos[0], s.pos[1])) {
                outCount++;
            }
        }
        return outCount;
    }

    public static int addScore() {
        int outCount = 0;
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            if (!inRange(s.pos[0], s.pos[1])) {
                outCount++;
                continue;
            }

            s.score += 1;
        }
        return outCount;
    }

    public static void printSantas() {
        for (int i = 1; i < santas.length; i++) {
            Santa s = santas[i];
            System.out.printf("num: %d, inActiveTurn: %d, score: %d, y: %d, x: %d\n", s.num, s.inActiveTurn, s.score, s.pos[0], s.pos[1]);
        }
        System.out.println();
    }


    public static void printMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.printf("%2d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
