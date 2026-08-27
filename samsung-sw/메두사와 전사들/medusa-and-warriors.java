import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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

    static int N; // 마을 크기
    static int M; // 전사 수

    static int[][] map;
    static Loc medusa;
    static Loc park;
    static Loc[] warriors;

    static int[] dirY = {-1, 1, 0, 0};//상,하,좌,우
    static int[] dirX = {0, 0, -1, 1};

    static int[] wDirY = {0, 0, -1, 1};//좌,우,상,하
    static int[] wDirX = {-1, 1, 0, 0};

    static int[][] smallDir = {
            {-1, -1},
            {+1, -1},
            {-1, -1},
            {-1, +1}
    };

    static int[][] bigDir = {
            {-1, +1},
            {+1, +1},
            {+1, -1},
            {+1, +1}
    };

    static int totalMoveCount;
    static int rockCount;
    static int attackCount;
    static int[][] medusaSightMap;

    public static void main(String[] args) throws Exception {
        init();
        // 로직 시작
        while (true) {
            totalMoveCount = 0;
            rockCount = 0;
            attackCount = 0;

            // 1. 메두사 이동
            if (!moveMedusa()) {
                System.out.println("-1");
                return;
            }

            // 2. 메두사의 시선
            medusaSightMap = getMedusaSight();

            // 3. 전사 이동
            moveWarriors();

            if (medusa.y == park.y && medusa.x == park.x) {
                break;
            }
            System.out.println(totalMoveCount + " " + rockCount + " " + attackCount);
        }
        System.out.println("0");
    }

    public static void moveWarriors() {
        for (int i = 0; i < warriors.length; i++) {
            Loc w = warriors[i];
            if (w == null) continue;

            if (medusaSightMap[w.y][w.x] == 1) { // 이동 불가: 돌로 변한 전사
                rockCount++;
                continue;
            }

            // 첫 번째 이동
            moveWarrior(w, dirY, dirX);
            if (w.y == medusa.y && w.x == medusa.x) { // 결투
                warriors[i] = null;
                attackCount++;
                continue;
            }

            // 두 번째 이동
            moveWarrior(w, wDirY, wDirX);
            if (w.y == medusa.y && w.x == medusa.x) {
                warriors[i] = null;
                attackCount++;
                continue;
            }
        }
    }

    public static void moveWarrior(Loc warrior, int[] dirY, int[] dirX) {
        int[][] distMap = BFS(medusa.y, medusa.x, warrior.y, warrior.x, dirY, dirX);

        // 이동
        if (distMap == null) {
            return;
        }

        int curDist = distMap[warrior.y][warrior.x];
        int[] nextLoc = null;

        for (int d = 0; d < dirY.length; d++) {
            int nextY = warrior.y + dirY[d];
            int nextX = warrior.x + dirX[d];

            if (!inRange(nextY, nextX)) continue;
            if (distMap[nextY][nextX] >= curDist) continue;
            if (medusaSightMap[nextY][nextX] == 1) continue;

            curDist = distMap[nextY][nextX];
            nextLoc = new int[]{nextY, nextX};
        }

        if (nextLoc == null) return;

        warrior.y = nextLoc[0];
        warrior.x = nextLoc[1];

        totalMoveCount++;
    }

    public static int[][] getMedusaSight() {
        int[][] maxSightMap = new int[N][N];
        int maxLockCount = -1;

        for (int d = 0; d < dirY.length; d++) {
            int[][] sightMap = new int[N][N];
            int[] sDir = smallDir[d];
            int[] bDir = bigDir[d];
            int lockCount = 0;

            //small
            List<Loc> searched = small(medusa.y, medusa.x, d, sDir, sightMap, 1);
            for (Loc l : searched) {
                if (sightMap[l.y][l.x] == 1) {
                    small(l.y, l.x, d, sDir, sightMap, 0);
                    straight(l.y, l.x, d, sightMap, 0);
                    lockCount++;
                }
            }

            // straight
            searched = straight(medusa.y, medusa.x, d, sightMap, 1);
            for (Loc l : searched) {
                if (sightMap[l.y][l.x] == 1) {
                    straight(l.y, l.x, d, sightMap, 0);
                    lockCount++;
                }
            }

            // big
            searched = big(medusa.y, medusa.x, d, bDir, sightMap, 1);
            for (Loc l : searched) {
                if (sightMap[l.y][l.x] == 1) {
                    big(l.y, l.x, d, bDir, sightMap, 0);
                    straight(l.y, l.x, d, sightMap, 0);
                    lockCount++;
                }
            }

            if (lockCount > maxLockCount) {
                maxLockCount = lockCount;
                maxSightMap = sightMap;
            }
        }

        return maxSightMap;
    }

    public static List<Loc> straight(int curY, int curX, int d, int[][] sightMap, int value) {
        List<Loc> searchedWarriors = new ArrayList<>();
        int cY = curY + dirY[d];
        int cX = curX + dirX[d];

        while (inRange(cY, cX)) {
            sightMap[cY][cX] = value;

            List<Loc> searched = searchWarrior(cY, cX);
            if (searched.size() > 0) {
                for (Loc l : searched) {
                    searchedWarriors.add(l);
                }
            }

            cY += dirY[d];
            cX += dirX[d];
        }
        return searchedWarriors;
    }

    public static List<Loc> big(int curY, int curX, int d, int[] bDir, int[][] sightMap, int value) {
        List<Loc> searchedWarriors = new ArrayList<>();
        int cY = curY + dirY[d];
        int cX = curX + dirX[d];

        int eY = curY + bDir[0];
        int eX = curX + bDir[1];

        while (inRange(cY, cX)) {
            if (d == 0 || d == 1) {
                for (int x = cX + 1; x <= eX; x++) {
                    if (!inRange(eY, x)) continue;
                    sightMap[eY][x] = value;

                    List<Loc> searched = searchWarrior(eY, x);
                    if (searched.size() > 0) {
                        for (Loc l : searched) {
                            searchedWarriors.add(l);
                        }
                    }

                }
            } else {
                for (int y = cY + 1; y <= eY; y++) {
                    if (!inRange(y, eX)) continue;
                    sightMap[y][eX] = value;

                    List<Loc> searched = searchWarrior(y, eX);
                    if (searched.size() > 0) {
                        for (Loc l : searched) {
                            searchedWarriors.add(l);
                        }
                    }
                }
            }

            cY += dirY[d];
            cX += dirX[d];

            eY += bDir[0];
            eX += bDir[1];
        }

        return searchedWarriors;
    }

    public static List<Loc> small(int curY, int curX, int d, int[] sDir, int[][] sightMap, int value) {
        List<Loc> searchedWarriors = new ArrayList<>();
        int cY = curY + dirY[d];
        int cX = curX + dirX[d];

        int sY = curY + sDir[0];
        int sX = curX + sDir[1];

        while (inRange(cY, cX)) {
            if (d == 0 || d == 1) {
                for (int x = sX; x < cX; x++) {
                    if (!inRange(sY, x)) continue;
                    sightMap[sY][x] = value;

                    List<Loc> searched = searchWarrior(sY, x);
                    if (searched.size() > 0) {
                        for (Loc l : searched) {
                            searchedWarriors.add(l);
                        }
                    }
                }
            } else {
                for (int y = sY; y < cY; y++) {
                    if (!inRange(y, sX)) continue;
                    sightMap[y][sX] = value;

                    List<Loc> searched = searchWarrior(y, sX);
                    if (searched.size() > 0) {
                        for (Loc l : searched) {
                            searchedWarriors.add(l);
                        }
                    }
                }
            }

            cY += dirY[d];
            cX += dirX[d];

            sY += sDir[0];
            sX += sDir[1];
        }

        return searchedWarriors;
    }

    public static List<Loc> searchWarrior(int y, int x) {
        List<Loc> searched = new ArrayList<>();
        for (Loc w : warriors) {
            if (w == null) continue;

            if (w.y == y && w.x == x) {
                searched.add(w);
            }
        }
        return searched;
    }

    public static boolean moveMedusa() {
        int[][] distMap = BFS(park.y, park.x, medusa.y, medusa.x, dirY, dirX);

        // 이동
        if (distMap == null) {
            return false;
        }

        int curDist = distMap[medusa.y][medusa.x];
        int[] nextLoc = new int[]{medusa.y,medusa.x};

        for (int d = 0; d < dirY.length; d++) {
            int nextY = medusa.y + dirY[d];
            int nextX = medusa.x + dirX[d];

            if (!inRange(nextY, nextX)) continue;
            if (map[nextY][nextX] == 1) continue;

            if (distMap[nextY][nextX] >= curDist) continue;

            curDist = distMap[nextY][nextX];
            nextLoc = new int[]{nextY, nextX};
        }

        medusa.y = nextLoc[0];
        medusa.x = nextLoc[1];

        for (int i = 0; i < warriors.length; i++) {
            Loc w = warriors[i];
            if (w == null) continue;

            if (w.y == medusa.y && w.x == medusa.x) {
                warriors[i] = null;
            }
        }
        return true;
    }

    public static int[][] BFS(int sY, int sX, int targetY, int targetX, int[] dirY, int[] dirX) {
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        q.offer(new int[]{sY, sX});
        visited[sY][sX] = true;
        dist[sY][sX] = 0;

        boolean onGoal = false;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (targetY == medusa.y && targetX == medusa.x) {// 메두사의 경우
                    if (map[nextY][nextX] == 1)
                        continue;
                }

                q.offer(new int[]{nextY, nextX});
                visited[nextY][nextX] = true;
                dist[nextY][nextX] = dist[poll[0]][poll[1]] + 1;

                if (nextY == targetY && nextX == targetX) {
                    onGoal = true;
                    break;
                }
            }
            if (onGoal) break;
        }

        if (!onGoal) {
            return null;
        }

        return dist;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void printMap(int[][] map) {
        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[i][j] = map[i][j];
            }
        }
        tmp[medusa.y][medusa.x] = 2;
        tmp[park.y][park.x] = 3;

        for (Loc w : warriors) {
            if (w == null) continue;

            tmp[w.y][w.x] = 4;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(tmp[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printSightMap(int[][] map) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
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

        map = new int[N][N];
        warriors = new Loc[M];

        st = new StringTokenizer(br.readLine());
        medusa = new Loc(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        park = new Loc(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            warriors[i] = new Loc(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

    }
}


/*
N: 맵 크기 ( 0: 도로, 1: 도로가 아닌 곳)
Sr, Sc: 메두사 집 좌표
Er, Ec: 공원 좌표

r,c : 전사 위치

1. 메두사 이동
- 이동 칸 전사있으면 전사 공격
- 우선순위: 상,하,좌,우
- 경로 없을 수 있음

2. 메두사의 시선

- 우선순위:  상,하,좌,우
상: (-1,-1) ~ (-1, +1)
(y-1,x-1) (y-1,x) (y-1, x+1)
          (y,x)


하: (+1, -1) ~ (+1, +1)
                      (y,x)
           (y+1,x-1) (y+1,x)  (y+1, x+1)
(y+2, x-2) (y+2,x-1) (y+2,x)  (y+2, x+1) (y+2, x+2)

좌:(-1,-1) ~ (+1,-1)
(y-1,x-1)
(y,x-1)   (y,x)
(y+1,x-1)

우: (-1, +1) ~ (+1, +1)

       (y-1,x+1)
(y,x)  (y, x+1)
       (y,+1, x+1)



3. 전사들의 이동
- 최대 두칸 이동
- 한칸에 두 명 이상 가능
    #첫 번쨰 이동
        - 우선순위: 상,하,좌,우
        - 시야 안으로 이동 불가
    #두 번째 이동
        - 우선순위: 좌,우,상,하
        - 시야 안으로 이동 불가

4. 전사의 공격
- 메두사와 같은 같에 도달하면 사라짐

최단거리 계산: 맨해튼 거리
*/
