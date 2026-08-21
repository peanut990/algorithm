// 3:41~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static class Player {
        int y;
        int x;
        int dir;
        boolean isAlive = true;

        Player(int y, int x, int dir) {
            this.y = y;
            this.x = x;
            this.dir = dir;
        }

        public void changeOpDir() {
            this.dir = (this.dir + 2) % 4;
        }
    }

    public static int N; // 격자 크기
    public static int M; // 도망자 수
    public static int H; // 나무 수
    public static int K; // 라운드 수

    public static int[][] treeMap;
    public static List<Player> runners;
    public static Player catcher;

    public static int[] dirY = {-1, 0, 1, 0};// 상,우,하,좌
    public static int[] dirX = {0, 1, 0, -1};

    public static int score = 0;

    public static void main(String[] args) throws Exception {
        init();

        //로직 시작
        for (int k = 1; k <= K; k++) {
            // 도망자 이동
            moveRunners();

            // 술레 이동
            moveCatcher();

            // 도망자 잡기
            int catchedCount = doCatch();
            score += k * catchedCount;
        }

        System.out.println(score);
    }

    public static int doCatch() {
        List<int[]> visibleLocs = getVisibleLocs();

        int removedCount = 0;
        for (int[] visible : visibleLocs) {
            removedCount += removeRunner(visible);
        }

        return removedCount;
    }

    public static int removeRunner(int[] visible) {
        int removedCount = 0;
        for (int i = runners.size() - 1; i >= 0; i--) {
            Player p = runners.get(i);
            if (p.y == visible[0] && p.x == visible[1] && !isTree(p.y, p.x)) {
                runners.remove(i);
                removedCount++;
            }
        }
        return removedCount;
    }

    public static List<int[]> getVisibleLocs() {
        List<int[]> visibleLocs = new ArrayList<>();
        int curY = catcher.y;
        int curX = catcher.x;

        for (int i = 0; i < 3; i++) {
            if (!inRange(curY, curX)) return visibleLocs;
            visibleLocs.add(new int[]{curY, curX});

            curY += dirY[catcher.dir];
            curX += dirX[catcher.dir];
        }

        return visibleLocs;
    }

    public static boolean isTree(int y, int x) {
        return treeMap[y][x] == 1;
    }


    //술레 이동)
    // 중앙 -> 0,0 : 1 1 2 2 3 3 4 4 5
    // 0,0 -> 중앙 : 4 4 4 3 3 2 2 1 1
    public static int moveSize = 1;
    public static int curMove = 0; // moveSize 되면 방향 변경
    public static int curMoveSizeTurn = 0; // 2되면 moveSize증가 및 0으로 초기화
    public static int dirChangeValue = 1; // 1: 센터 -> 0,0 // -1: 0,0 -> 센터
    public static int moveSizeChangeValue = 1;

    public static void moveCatcher() {
        catcher.y += dirY[catcher.dir];
        catcher.x += dirX[catcher.dir];

        if (isEnd(catcher.y, catcher.x)) {
            setUpMove(N - 1, -1, -1);
            return;
        }

        if (isCenter(catcher.y, catcher.x)) {
            setUpMove(1, 1, 0);
            return;
        }

        curMove++;
        if (curMove == moveSize) { // 이동방향이 들어지는 지점
            catcher.dir = (4 + catcher.dir + dirChangeValue) % 4;

            curMove = 0;
            curMoveSizeTurn++;

            if (curMoveSizeTurn == 2) {
                moveSize += moveSizeChangeValue;
                curMoveSizeTurn = 0;
            }
        }
    }

    public static void setUpMove(int size, int changeValue, int moveSizeTurn) {
        catcher.changeOpDir();
        moveSize = size;
        curMove = 0;
        curMoveSizeTurn = moveSizeTurn;
        dirChangeValue = changeValue;
        moveSizeChangeValue = changeValue;
    }

    public static boolean isEnd(int y, int x) { // 0,0
        return y == 0 && x == 0;
    }

    public static boolean isCenter(int y, int x) { //정중앙 체크
        return y == N / 2 && x == N / 2;
    }

    public static void moveRunners() {
        for (int i = 0; i < runners.size(); i++) {
            Player p = runners.get(i);

            int dist = getDist(p.y, p.x, catcher.y, catcher.x);
            if (dist > 3) continue;

            int nextY = p.y + dirY[p.dir];
            int nextX = p.x + dirX[p.dir];

            if (!inRange(nextY, nextX)) {
                p.changeOpDir();
                nextY = p.y + dirY[p.dir];
                nextX = p.x + dirX[p.dir];
            }

            if (!isCatcher(nextY, nextX)) {
                p.y = nextY;
                p.x = nextX;
            }
        }
    }

    public static boolean isCatcher(int y, int x) {
        return y == catcher.y && x == catcher.x;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static int getDist(int y1, int x1, int y2, int x2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // init catcher
        catcher = new Player(N / 2, N / 2, 0); // 위쪽

        // init runner
        runners = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken());

            runners.add(new Player(y, x, dir));
        }

        // init treeMap
        treeMap = new int[N][N];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            treeMap[y][x] = 1;
        }
    }
}

/*
도망자 상태
- 방향
- 위치
- 생존

# 도망자 리스트로 관리
- 이동 m
- 도망자 잡기: 3*m

# 도망자 맵에 관리
- 방향, 생존 따로 필요
- 이동 n*n
- 도망자 잡기 : 3
- 겹치는 경우 따로 관리
5 3 1 1
2 4 1
1 4 2
4 2 1
2 4

*/