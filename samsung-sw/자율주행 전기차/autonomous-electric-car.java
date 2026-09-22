// 3: 52 ~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int C; // 현재 에너지 량

    static int[][] map;
    static int[][] customMap;

    static int[][] customLocs;
    static int[][] goalLocs;

    static int[] distToGoal;

    static int carY;
    static int carX;

    static int[] dirY = {-1, 0, 1, 0};// 상, 우, 하, 좌
    static int[] dirX = {0, 1, 0, -1};

    static int pickUpCount = 0;

    public static void main(String[] args) throws Exception {
        init();

        //0. 각 승객 -> 목적지
        for (int i = 1; i < distToGoal.length; i++) {
            distToGoal[i] = getDistToGoal(i);
        }

        // 로직 시작
        while (pickUpCount < M) {
            // 1. 승객 픽업
            int[] res = getCustomNumAndDist();
            int customNum = res[0];
            int dist = res[1];

            // 연료 부족 -> 종료
            if (customNum == -1 || dist == -1 || C < dist) {
                System.out.println("-1");
                return;
            }

            int[] customLoc = customLocs[customNum];
            carY = customLoc[0];
            carX = customLoc[1];

            customMap[customLoc[0]][customLoc[1]] = 0;
            C -= dist;

            pickUpCount++;

            // 2. 승객 운송
            // 이동 불가
            if (distToGoal[customNum] == -1 || C < distToGoal[customNum]) {
                System.out.println("-1");
                return;
            }

            // 이동 가능
            int[] goalLoc = goalLocs[customNum];
            carY = goalLoc[0];
            carX = goalLoc[1];

            C -= distToGoal[customNum];
            // 연료 주입
            C += (2 * distToGoal[customNum]);
        }

        System.out.println(C);
    }

    public static int[] getCustomNumAndDist() {
        List<int[]> candidates = new ArrayList<>();

        Queue<int[]> q = new ArrayDeque<>();
        int[][] dist = new int[N][N];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        int[] sLoc = new int[]{carY, carX};

        if (customMap[sLoc[0]][sLoc[1]] > 0) {
            return new int[]{customMap[sLoc[0]][sLoc[1]], 0};
        }

        q.offer(sLoc);
        dist[sLoc[0]][sLoc[1]] = 0;

        boolean found = false;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();

                for (int d = 0; d < dirY.length; d++) {
                    int nextY = poll[0] + dirY[d];
                    int nextX = poll[1] + dirX[d];

                    // 범위 안, 미방문, 벽 X
                    if (!inRange(nextY, nextX) || dist[nextY][nextX] >= 0 || map[nextY][nextX] == 1) continue;

                    q.offer(new int[]{nextY, nextX});
                    dist[nextY][nextX] = dist[poll[0]][poll[1]] + 1;
                    if (customMap[nextY][nextX] > 0) {
                        candidates.add(new int[]{nextY, nextX});
                        found = true;
                    }
                }
            }
            if (found) break;
        }

        if (candidates.size() == 0) {
            return new int[]{-1, -1};
        }

        Collections.sort(candidates, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });

        int[] customLoc = candidates.get(0);

        return new int[]{customMap[customLoc[0]][customLoc[1]], dist[customLoc[0]][customLoc[1]]};
    }

    public static int getDistToGoal(int num) {
        Queue<int[]> q = new ArrayDeque<>();
        int[][] dist = new int[N][N];
        for (int[] row : dist) {
            Arrays.fill(row, -1);
        }

        int[] sLoc = customLocs[num];

        q.offer(sLoc);
        dist[sLoc[0]][sLoc[1]] = 0;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                // 범위 안, 미방문, 벽 X
                if (!inRange(nextY, nextX) || dist[nextY][nextX] >= 0 || map[nextY][nextX] == 1) continue;

                q.offer(new int[]{nextY, nextX});
                dist[nextY][nextX] = dist[poll[0]][poll[1]] + 1;
            }
        }

        int[] gLoc = goalLocs[num];
        return dist[gLoc[0]][gLoc[1]];
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        customMap = new int[N][N];

        customLocs = new int[M + 1][2];
        goalLocs = new int[M + 1][2];

        distToGoal = new int[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        carY = Integer.parseInt(st.nextToken()) - 1;
        carX = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int customY = Integer.parseInt(st.nextToken()) - 1;
            int customX = Integer.parseInt(st.nextToken()) - 1;
            int goalY = Integer.parseInt(st.nextToken()) - 1;
            int goalX = Integer.parseInt(st.nextToken()) - 1;

            customLocs[i] = new int[]{customY, customX};
            goalLocs[i] = new int[]{goalY, goalX};

            customMap[customY][customX] = i;
        }

    }
}

/*
N: 격자 크기
M: 승객 수
C: 초기 배터리 충전량

- curEnergy: 현재 배터리양
- curY: 전기차 위치
- curX

map = new int[N][N];
customMap = new int[N][N];

distToGoal = new int[M+1]

0. 각 승객 -> 목적지
- BFS
    -> 최단거리(비용)

1. 승객 픽업
- BFS
    -> 최단거리(비용)
    -> 승객 번호 num
// 이동가능 체크
if(curEnergy >= 최단거리) // 이동
    좌표 이동
else // 이동 불가( 로직 종료 )

2. 승객 운송
if(curEnergy >= distToGoal[num]) // 이동 가능
    좌표 이동, 에너지 충전
else // 이동 불가 (로직 종료)

*/