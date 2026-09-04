import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

//3:12~
public class Main {
    public static class Turtle {
        int num;
        int y;
        int x;
        int goalTurn = -1;
        boolean isRock = false;
        boolean isGoal = false;

        Turtle(int num, int y, int x) {
            this.num = num;
            this.y = y;
            this.x = x;
        }
    }

    public static class Vol {
        int y;
        int x;
        int P;
        int curPressure = 0;
        boolean bombInCurTurn = false;

        Vol(int y, int x, int P) {
            this.y = y;
            this.x = x;
            this.P = P;
        }
    }


    public static int N; // 맵 크기
    public static int M; // 바다거북 수
    public static int K; // 해저 화산 수

    public static int[][] map;
    public static int[][] turtleMap;

    public static List<Turtle> turtleList;
    public static List<Vol> volList;

    public static int[][] heatingMap;

    public static int goalY;
    public static int goalX;

    public static int[] dirY = {0, 1, 0, -1};// 우,하,좌,상
    public static int[] dirX = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {
        init();

        // 로직 시작
        for (int turn = 1; turn <= 100; turn++) { // 100 수정 ##
            //1. 바다 거북 이동
            for (Turtle t : turtleList) {
                if (t.isRock || t.isGoal) continue;

                moveTurtle(t, turn);
            }

            // 2. 화산 압력 증가
            for (Vol v : volList) {
                v.curPressure += 10;
            }

            //3. 화산 분출 및 연쇄 반응
            bombVol();
        }

        // 결과 출력
        for (Turtle t : turtleList) {
            System.out.println(t.goalTurn);
        }
    }

    public static void bombVol() {
        heatingMap = new int[N][N];
        // 1. 열기 전파
        for (Vol v : volList) {
            if (!v.bombInCurTurn && v.curPressure >= v.P) {
                bomb(v);
            }
        }

        // 2. 연쇄 반응
        boolean doBomb = true;
        while (doBomb) {
            doBomb = false;
            for (Vol v : volList) {
                if (!v.bombInCurTurn && v.curPressure + heatingMap[v.y][v.x] >= v.P) {
                    bomb(v);
                    doBomb = true;
                }
            }
        }

        // 3. 화석화
        for (Turtle t : turtleList) {
            if (heatingMap[t.y][t.x] >= 20) {
                t.isRock = true;
            }
        }

        // 4. 환경 초기화
        for (Vol v : volList) {
            if (v.bombInCurTurn) {
                v.bombInCurTurn = false;
                v.curPressure = 0;
            }
        }
    }

    public static void bomb(Vol v) {
        v.bombInCurTurn = true;

        heatingMap[v.y][v.x] += v.P;

        for (int d = 0; d < dirY.length; d++) {
            int nextY = v.y + dirY[d];
            int nextX = v.x + dirX[d];
            int curHeat = v.P / 2;

            while (inRange(nextY, nextX)) {
                if (map[nextY][nextX] == 1 || curHeat == 0) break;

                heatingMap[nextY][nextX] += curHeat;

                nextY += dirY[d];
                nextX += dirX[d];
                curHeat /= 2;
            }
        }
    }

    public static void moveTurtle(Turtle t, int turn) {
        int[][] distMap = new int[N][N];
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        q.offer(new int[]{goalY, goalX});
        visited[goalY][goalX] = true;
        distMap[goalY][goalX] = 1;

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                // 범위안, 미방문, 조건: 산호초x, 거북이x
                if (!inRange(nextY, nextX) || visited[nextY][nextX]) continue;
                if (map[nextY][nextX] == 1 || turtleMap[nextY][nextX] > 0) continue;

                visited[nextY][nextX] = true;
                q.offer(new int[]{nextY, nextX});
                distMap[nextY][nextX] = distMap[poll[0]][poll[1]] + 1;
            }
        }

        int minDist = Integer.MAX_VALUE;
        int minDir = -1;

        for (int d = 0; d < dirY.length; d++) {
            int nextY = t.y + dirY[d];
            int nextX = t.x + dirX[d];
            if (!inRange(nextY, nextX)) continue;
            if (map[nextY][nextX] == 1 || turtleMap[nextY][nextX] > 0) continue;
            if (distMap[nextY][nextX] == 0) continue;

            if (distMap[nextY][nextX] < minDist) {
                minDist = distMap[nextY][nextX];
                minDir = d;
            }
        }

        // 좌표 반영
        if (minDir == -1) return;
        
        turtleMap[t.y][t.x] = 0;

        t.y += dirY[minDir];
        t.x += dirX[minDir];

        if (t.y == goalY && t.x == goalX) {
            t.goalTurn = turn;
            t.isGoal = true;
            return;
        }

        turtleMap[t.y][t.x] = t.num;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        turtleMap = new int[N][N];
        goalY = N - 1;
        goalX = N - 1;

        turtleList = new ArrayList<>();
        volList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 거북
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            turtleList.add(new Turtle(i + 1, y, x));
        }

        // 화산
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int P = Integer.parseInt(st.nextToken());

            volList.add(new Vol(y, x, P));
        }


        // 거북이 맵
        for (Turtle t : turtleList) {
            turtleMap[t.y][t.x] = t.num;
        }
    }
}
