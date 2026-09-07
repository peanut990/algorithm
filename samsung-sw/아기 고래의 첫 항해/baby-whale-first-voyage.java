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
    static int whaleY;
    static int whaleX;
    static int whaleDir;

    static int[][] map;
    static int[][] visitedMap;

    static int moveCount = 1;

    static int[] dirY = {-1, 1, 0, 0}; //상,하,좌,우
    static int[] dirX = {0, 0, -1, 1};

    static int[][] adjDir = {
            {0, 2, 3, 1},
            {1, 3, 2, 0},
            {2, 1, 0, 3},
            {3, 0, 1, 2}
    };

    static int[] moveFarDir = {2,1,3,0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        whaleY = Integer.parseInt(st.nextToken()) - 1;
        whaleX = Integer.parseInt(st.nextToken()) - 1;
        whaleDir = Integer.parseInt(st.nextToken()) - 1;

        map = new int[N][N];
        visitedMap = new int[N][N];
       
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visitedMap[whaleY][whaleX] = moveCount++;

        System.out.println((whaleY + 1) + " " + (whaleX + 1));
        // 로직 시작
        while (true) {
            //1. 인접 탐험
            if (moveAdj()) {
                continue;
            }

            // 2. 가장 가까운 바다로 이동
            if (moveSea()) {
                continue;
            }

            break;
        }

    }

    public static boolean moveSea() {
        Queue<int[]> q = new ArrayDeque();
        boolean[][] visited = new boolean[N][N];

        List<int[]> seaList = new ArrayList<>();
        int[][] dirMap = new int[N][N];

        q.offer(new int[]{whaleY, whaleX});
        visited[whaleY][whaleX] = true;

        boolean found = false;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();
                
                for (int d = 0; d < moveFarDir.length; d++) {
                    int dir = moveFarDir[d];

                    int nextY = poll[0] + dirY[dir];
                    int nextX = poll[1] + dirX[dir];

                    // 범위 밖, 이미 방문, 암초인 경우
                    if (!inRange(nextY, nextX) || visited[nextY][nextX] || map[nextY][nextX] == 1) continue;

                    q.offer(new int[]{nextY, nextX});
                    visited[nextY][nextX] = true;

                    if (visitedMap[nextY][nextX] == 0) { // 처음 방문
                        seaList.add(new int[]{nextY, nextX});
                        found = true;
                        dirMap[nextY][nextX] = dir;
                    }
                }
            }

            if (found) break;
        }

        // 못찾은 경우 로직 종료
        if (seaList.size() == 0) return false;

        Collections.sort(seaList, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });

        // 이동
        int[] nextLoc = seaList.get(0);

        // 이동
        whaleY = nextLoc[0];
        whaleX = nextLoc[1];
        whaleDir = dirMap[whaleY][whaleX];

        visitedMap[whaleY][whaleX] = moveCount++;

        System.out.println((whaleY + 1) + " " + (whaleX + 1));

        return true;
    }

    public static boolean moveAdj() {
        int[] nextLoc = null;
        int nextDir = -1;

        int[] curAdjDir = adjDir[whaleDir];

        for (int d = 0; d < curAdjDir.length; d++) {
            nextDir = curAdjDir[d];

            int nextY = whaleY + dirY[nextDir];
            int nextX = whaleX + dirX[nextDir];

            // 범위 밖, 이미 방문, 암초인 경우
            if (!inRange(nextY, nextX) || visitedMap[nextY][nextX] > 0 || map[nextY][nextX] == 1) continue;

            nextLoc = new int[]{nextY, nextX};
            break;
        }

        if (nextLoc == null) return false;

        // 이동
        whaleY = nextLoc[0];
        whaleX = nextLoc[1];
        whaleDir = nextDir;

        visitedMap[whaleY][whaleX] = moveCount++;

        System.out.println((whaleY + 1) + " " + (whaleX + 1));
        return true;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

}
