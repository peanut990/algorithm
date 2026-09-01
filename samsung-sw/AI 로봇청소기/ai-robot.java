// 2: 54~ 3:06 3:10~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static class Loc {
        int y;
        int x;

        Loc(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static int N; // 맵 크기
    public static int K; // 청소기 개수
    public static int L; // 테스트 횟수

    public static int[][] map;

    public static int[][] robotMap;
    public static Loc[] robots;

    public static int[] dirY = {0, 1, 0, -1};// 우 하 좌 상
    public static int[] dirX = {1, 0, -1, 0};

    public static void main(String[] args) throws Exception {
        init();

        // 로직 시작
        for (int l = 0; l < L; l++) {
            // 1. 청소기 이동
            for (int num = 1; num < robots.length; num++) {
                moveRobot(num);
            }

            // 2. 청소
            for (int i = 1; i < robots.length; i++) {
                Loc robot = robots[i];
                cleaning(robot);
            }

            // 3. 먼지 축적
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] > 0) map[i][j] += 5;
                }
            }

            // 4. 먼지 확산
            map = spreadDust();

            // 결과출력
            int result = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] > 0) result += map[i][j];
                }
            }
            System.out.println(result);

        }
    }

    public static int[][] spreadDust() {
        int[][] newMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) { // 빈 칸인 경우
                    int sum = 0;
                    for (int d = 0; d < dirY.length; d++) {
                        int nextY = i + dirY[d];
                        int nextX = j + dirX[d];

                        // 범위안, 조건( != -1 )
                        if (!inRange(nextY, nextX)) continue;
                        if (map[nextY][nextX] == -1) continue;

                        sum += map[nextY][nextX];
                    }
                    newMap[i][j] = sum / 10;
                } else {
                    newMap[i][j] = map[i][j];
                }
            }
        }

        return newMap;
    }

    public static void cleaning(Loc r) {
        int cleaningDir = getCleaningDir(r);

        // 청소
        map[r.y][r.x] -= map[r.y][r.x] > 20 ? 20 : map[r.y][r.x];

        for (int nD = -1; nD <= 1; nD++) {
            int nextD = (dirY.length + cleaningDir + nD) % dirY.length;
            int nextY = r.y + dirY[nextD];
            int nextX = r.x + dirX[nextD];

            // 범위안, 조건( != -1 )
            if (!inRange(nextY, nextX)) continue;
            if (map[nextY][nextX] == -1) continue;

            map[nextY][nextX] -= map[nextY][nextX] > 20 ? 20 : map[nextY][nextX];
        }
    }

    public static int getCleaningDir(Loc r) {
        int maxSum = 0;
        int maxDir = 0;

        for (int d = 0; d < dirY.length; d++) {
            int sum = map[r.y][r.x] > 20 ? 20 : map[r.y][r.x];

            for (int nD = -1; nD <= 1; nD++) {
                int nextD = (dirY.length + d + nD) % dirY.length;
                int nextY = r.y + dirY[nextD];
                int nextX = r.x + dirX[nextD];

                // 범위안, 조건( != -1 )
                if (!inRange(nextY, nextX)) continue;
                if (map[nextY][nextX] == -1) continue;

                int cleanedDust = map[nextY][nextX] > 20 ? 20 : map[nextY][nextX];
                sum += cleanedDust;
            }

            if (sum > maxSum) {
                maxSum = sum;
                maxDir = d;
            }
        }
        return maxDir;
    }

    public static void moveRobot(int num) {
        Loc robot = robots[num];

        int[] nextLoc = getNextLoc(robot, num);

        robotMap[robot.y][robot.x] = 0;

        // 이동
        robot.y = nextLoc[0];
        robot.x = nextLoc[1];

        robotMap[robot.y][robot.x] = num;
    }

    public static int[] getNextLoc(Loc robot, int num) {
        List<int[]> dustList = getDustList(robot);

        // 먼지 리스트 정렬
        Collections.sort(dustList, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });

        if (dustList.size() > 0) {
            return dustList.get(0);
        }

        return new int[]{robot.y, robot.x};
    }


    public static List<int[]> getDustList(Loc robot) {
        List<int[]> dustList = new ArrayList<>();
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        boolean foundDust = false;

        if (map[robot.y][robot.x] > 0) { // 제자리
            dustList.add(new int[]{robot.y, robot.x});
            return dustList;
        }

        q.offer(new int[]{robot.y, robot.x});
        visited[robot.y][robot.x] = true;


        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();

                for (int d = 0; d < dirY.length; d++) {
                    int nextY = poll[0] + dirY[d];
                    int nextX = poll[1] + dirX[d];

                    // 범위안, 미방문, 조건( != -1 , 로봇 x)
                    if (!inRange(nextY, nextX) || visited[nextY][nextX]) continue;
                    if (map[nextY][nextX] == -1 || robotMap[nextY][nextX] > 0) continue;

                    q.offer(new int[]{nextY, nextX});
                    visited[nextY][nextX] = true;

                    if (map[nextY][nextX] > 0) {
                        foundDust = true;
                        dustList.add(new int[]{nextY, nextX});
                    }
                }
            }
            if (foundDust) {
                break;
            }
        }

        return dustList;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static int[][] getRobotMap() {
        int[][] newRobotMap = new int[N][N];

        for (int num = 1; num < robots.length; num++) {
            Loc robot = robots[num];
            newRobotMap[robot.y][robot.x] = num;
        }
        return newRobotMap;
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        robotMap = new int[N][N];
        robots = new Loc[K + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= K; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;

            robots[i] = new Loc(y, x);
        }

        robotMap = getRobotMap();
    }
}
