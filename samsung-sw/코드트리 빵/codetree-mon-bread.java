// 3: 47
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;
// 이동 방향: 상,좌,우,하

public class Main {
    public static class Person {
        int y;
        int x;
        boolean inGoal = false;

        Person(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static class Shop {
        int y;
        int x;

        Shop(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static int N; // 맵 크기
    public static int M; // 편의점 수

    public static int[][] baseMap; // 0: 빈칸, 1: 베이스
    public static boolean[][] availMap;
    public static Person[] people;
    public static Shop[] shops;

    public static int[] dirY = {-1, 0, 0, 1};// 이동 방향: 상,좌,우,하
    public static int[] dirX = {0, -1, 1, 0};

    public static void main(String[] args) throws Exception {
        init();

        //로직 시작
        for (int t = 1; ; t++) { 
            // 1. 사람 이동
            movePeople();

            // 2. 편의점 도착 처리
            updateInGoal();

            // 3. 베이스 캠프 진입
            if (t <= M) {
                enterBaseCamp(t);
            }

            if (checkAllGoal()) {
                System.out.println(t);
                return;
            }
        }
    }

    public static boolean checkAllGoal() {
        int inGoalCount = 0;
        for (int i = 0; i < people.length; i++) {
            Person p = people[i];

            if (p == null) continue;

            if (p.inGoal) inGoalCount++;
        }

        return inGoalCount == M ? true : false;
    }

    public static void updateInGoal() {
        for (int i = 0; i < people.length; i++) {
            Person p = people[i];
            Shop s = shops[i];
            if (p == null) continue;

            if (p.y == s.y && p.x == s.x) {
                p.inGoal = true;
                availMap[s.y][s.x] = false;
            }
        }
    }

    public static void movePeople() {
        for (int i = 0; i < people.length; i++) {
            Person p = people[i];
            Shop s = shops[i];

            if (p == null) continue;
            if (p.inGoal) continue;

            move(p, s);
        }
    }

    public static void move(Person p, Shop s) {
        int moveDir = searchMoveDir(p, s);
        if (moveDir == -1) {
            System.out.println("이동 불가????????");
            return;
        }
        p.y += dirY[moveDir];
        p.x += dirX[moveDir];
    }

    public static int searchMoveDir(Person p, Shop s) {
        Queue<int[]> q = new LinkedList<>();
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dist[i][j] = -1;
            }
        }
        q.offer(new int[]{s.y, s.x});
        dist[s.y][s.x] = 0;

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (dist[nextY][nextX] > -1) continue;
                if (!availMap[nextY][nextX]) continue;

                q.offer(new int[]{nextY, nextX});
                dist[nextY][nextX] = dist[poll[0]][poll[1]] + 1;
            }
        }

        // 최소 방향 탐색
        int minDir = -1;
        int minDist = Integer.MAX_VALUE;
        for (int d = 0; d < dirY.length; d++) {
            int nextY = p.y + dirY[d];
            int nextX = p.x + dirX[d];

            if (!inRange(nextY, nextX)) continue;
            if (dist[nextY][nextX] == -1) continue;
            if (!availMap[nextY][nextX]) continue;

            int curDist = dist[nextY][nextX];
            if (minDist > curDist) {
                minDist = curDist;
                minDir = d;
            }
        }
        return minDir;
    }


    public static void enterBaseCamp(int t) {
        Shop targetShop = shops[t];

        int[] baseCamp = searchBaseCamp(targetShop);

        // availMap 표시
        availMap[baseCamp[0]][baseCamp[1]] = false;
        // baseCampLoc으로 Person 생성
        people[t] = new Person(baseCamp[0], baseCamp[1]);
    }

    public static int[] searchBaseCamp(Shop shop) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] checked = new boolean[N][N];
        q.offer(new int[]{shop.y, shop.x});
        checked[shop.y][shop.x] = true;

        List<int[]> candidate = new ArrayList<>();

        while (!q.isEmpty()) {
            int size = q.size();
            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();
                for (int d = 0; d < dirY.length; d++) {
                    int nextY = poll[0] + dirY[d];
                    int nextX = poll[1] + dirX[d];

                    if (!inRange(nextY, nextX)) continue;
                    if (checked[nextY][nextX]) continue;
                    if (!availMap[nextY][nextX]) continue;

                    if (baseMap[nextY][nextX] == 1) { // 베이스 캠프 발견
                        candidate.add(new int[]{nextY, nextX});
                    }

                    q.offer(new int[]{nextY, nextX});
                    checked[nextY][nextX] = true;
                }
            }
            if (candidate.size() > 0) break;
        }

        Collections.sort(candidate, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return a[1] - b[1];
        });
        return candidate.get(0);
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        baseMap = new int[N][N];
        availMap = new boolean[N][N];
        people = new Person[M + 1];
        shops = new Shop[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                baseMap[i][j] = Integer.parseInt(st.nextToken());
                availMap[i][j] = true;
            }
        }

        // 편의점 입력
        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;

            shops[i] = new Shop(y, x);
        }
    }
}
