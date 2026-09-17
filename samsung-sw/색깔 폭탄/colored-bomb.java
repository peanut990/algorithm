//3:13~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;

    static final int EMPTY = 9;
    static final int ROCK = -1;
    static final int RED = 0;

    static int[][] map;

    static int totalScore = 0;
    public static int[] dirY = {-1, 1, 0, 0};//상,하,좌,우
    public static int[] dirX = {0, 0, -1, 1};

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

        while (true) {
            //1. 폭탄 선택
            List<int[]> bombs = getBombs();

            if (bombs.size() == 0) break;

            totalScore += (bombs.size()) * (bombs.size());

            // 2. 폭탄 제거
            removeBomb(bombs);

            // 2-1. 중력 작용
            moveDown();

            // 3. 맵 회전
            map = rotMap();

            // 4. 중력 작용
            moveDown();
        }

        System.out.println(totalScore);
    }

    public static int[][] rotMap() {
        int[][] nextMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nextMap[N - 1 - j][i] = map[i][j];
            }
        }

        return nextMap;
    }

    /*
    0,0 0,1 0,2
    1,0 1,1 1,2
    2,0 2,1 2,2

    y,x -> N-1-x ,y
     */

    public static void moveDown() {
        for (int y = N - 1; y >= 0; y--) {
            for (int x = 0; x < N; x++) {
                if (map[y][x] >= 0 && map[y][x] <= M) {
                    int[] nextLoc = getNextLoc(y, x);
                    int num = map[y][x];
                    map[y][x] = EMPTY;
                    map[nextLoc[0]][nextLoc[1]] = num;
                }
            }
        }
    }

    public static int[] getNextLoc(int y, int x) {
        int dir = 1; // 아래쪽
        int nextY = y + dirY[dir];
        int nextX = x + dirX[dir];

        while (inRange(nextY, nextX) && map[nextY][nextX] == EMPTY) {
            y = nextY;
            x = nextX;

            nextY += dirY[dir];
            nextX += dirX[dir];
        }

        return new int[]{y, x};
    }

    public static void removeBomb(List<int[]> bombs) {
        for (int[] b : bombs) {
            map[b[0]][b[1]] = EMPTY;
        }
    }

    static class Group {
        int redCount;
        List<int[]> locs;
        int[] pivotLoc;

        Group(int r, List<int[]> locs, int[] pivotLoc) {
            this.redCount = r;
            this.locs = locs;
            this.pivotLoc = pivotLoc;
        }
    }

    public static List<int[]> getBombs() {
        boolean[][] visited = new boolean[N][N];
        List<int[]> selectedBombs = new ArrayList<>();

        List<Group> groups = new ArrayList<>();
//        int minRedCount = N * N;
//        int[] pivotLoc = new int[2];

        for (int y = N - 1; y >= 0; y--) {
            for (int x = 0; x < N; x++) {
                if (visited[y][x]) continue;
                if (map[y][x] == RED || map[y][x] == ROCK || map[y][x] == EMPTY) continue; // 빨간색 외 폭탄인 경우

                List<int[]> bombs = BFS(y, x, visited);
                if (bombs.size() < 2) continue;

                int[] pivotLoc = new int[]{0, Integer.MAX_VALUE};
                int redCount = 0;
                
                for (int[] b : bombs) {
                    int color = map[b[0]][b[1]];
                    if (color == RED) {
                        redCount++;
                        continue;
                    }

                    if (b[0] > pivotLoc[0]) {
                        pivotLoc = b;
                    } else if (b[0] == pivotLoc[0] && b[1] < pivotLoc[1]) {
                        pivotLoc = b;
                    }
                }

                groups.add(new Group(redCount, bombs, pivotLoc));
            }
        }

        if (groups.size() == 0) {
            return selectedBombs;
        }

        Collections.sort(groups, (a, b) -> {
            if (a.locs.size() != b.locs.size()) return b.locs.size() - a.locs.size();
            if (a.redCount != b.redCount) return a.redCount - b.redCount;
            if (a.pivotLoc[0] != b.pivotLoc[0]) return b.pivotLoc[0] - a.pivotLoc[0];
            return a.pivotLoc[1] - b.pivotLoc[1];
        });

        selectedBombs = groups.get(0).locs;

        return selectedBombs;
    }

    public static List<int[]> BFS(int y, int x, boolean[][] visited) {
        List<int[]> bombs = new ArrayList<>();
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] curVisited = new boolean[N][N];
        int pivotColor = map[y][x];

        q.offer(new int[]{y, x});
        visited[y][x] = true;
        curVisited[y][x] = true;
        bombs.add(new int[]{y, x});

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                // 범위안, 미방문, 돌x, 빈칸 x
                if (!inRange(nextY, nextX) || visited[nextY][nextX] || curVisited[nextY][nextX] || map[nextY][nextX] == ROCK || map[nextY][nextX] == EMPTY)
                    continue;

                // 다른 색깔이면서 빨간색이 아니면
                if (map[nextY][nextX] != pivotColor && map[nextY][nextX] != RED) continue;

                q.offer(new int[]{nextY, nextX});

                if (map[nextY][nextX] != RED) {
                    visited[nextY][nextX] = true;
                }
                curVisited[nextY][nextX] = true;
                bombs.add(new int[]{nextY, nextX});
            }
        }
        return bombs;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }


    public static void printMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%2d ", map[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
-1: 검은 돌
0: 빨간색 폭탄
1~m: 빨간색x 서로다른색의 폭탄

1. 폭탄 선택
List<int[]> selectedBombs;
int minRedCount;
int[] pivotLoc;

for y N-1 -> 0
    for x 0 -> N-1
        boolean isValid;
        int redCount;
        int[] curPivotLoc; // 빨간색x
        List<int[]> bombs = BFS(y,x,isValid,redCount,curPivotLoc) // isValid, redCount

        if(isValid && redCount < minRedCount){
            if(curPivotLoc[0] < pivotLoc[0]){
            }else if(curPivotLoc[0] == pivotLoc[0] && ...){

            }

        }
2. 폭탄 제거
    2-1. 중력 작용
3. 격자 회전
4. 중력 작용

4 3
1 2 0 -1
-1 0 -1 -1
1 -1 2 2
3 3 3 2

4 3
1 2 0 -1
-1 0 -1 -1
1 -1 2 2
3 3 3 2
*/