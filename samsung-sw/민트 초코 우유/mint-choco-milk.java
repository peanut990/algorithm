// 5:00 ~ 6:42 10:32~
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static int N;
    public static int T;

    public static int[][] map;
    public static int[][] type;

    public static int[] dirY = {-1, 1, 0, 0};//상,하,좌,우
    public static int[] dirX = {0, 0, -1, 1};

    public static final int MINT_CHOCO_MILK = 0;
    public static final int MINT_CHOCO = 1;
    public static final int MINT_MILK = 2;
    public static final int CHOCO_MILK = 3;
    public static final int MILK = 4;
    public static final int CHOCO = 5;
    public static final int MINT = 6;


    public static void main(String[] args) throws Exception {
        init();

        //로직 시작
        for (int t = 0; t < T; t++) {
            // 1. 아침 시간
            morningTime();

            // 2. 점심 시간
            List<int[]> preList = lunchTime();

            // 3. 저녁 시간
            nightTime(preList);

            // 집계
            int[] score = new int[7];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curType = type[i][j];
                    score[curType] += map[i][j];
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int s : score) {
                sb.append(s + " ");
            }
            System.out.println(sb);
        }
    }

    public static void nightTime(List<int[]> preList) {
        /*
        - 리스트 분리
                type
            0)  4~6 :List<int[]> oneFood
            1)  1~3 :            twoFood
            2)  0   :            threeFood
         */
        List<List<int[]>> groups = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            groups.add(new ArrayList<>());
        }

        for (int[] p : preList) {
            int preType = type[p[0]][p[1]];
            if (preType == 0) {
                groups.get(2).add(p);
            } else if (preType <= 3) {
                groups.get(1).add(p);
            } else {
                groups.get(0).add(p);
            }
        }

        // 각 그룹 정렬
        for (int i = 0; i < 3; i++) {
            List<int[]> group = groups.get(i);
            Collections.sort(group, (a, b) -> {
                int aB = map[a[0]][a[1]];
                int bB = map[b[0]][b[1]];

                if (aB != bB) return bB - aB; // 내림차순

                if (a[0] != b[0]) return a[0] - b[0]; // 오름차순

                return a[1] - b[1];
            });
        }

        // 전파 시작
        boolean[][] visited = new boolean[N][N]; // 전파 대상 체크
        for (int i = 0; i < 3; i++) {
            List<int[]> preGroup = groups.get(i);
            propa(preGroup, visited);

        }
    }

    public static void propa(List<int[]> preGroup, boolean[][] visited) {
        for (int[] pre : preGroup) {
            //대표자 전파당했는지 체크
            if (visited[pre[0]][pre[1]]) continue;

            // 전파 시작
            int x = map[pre[0]][pre[1]] - 1;
            int dir = map[pre[0]][pre[1]] % 4;

            map[pre[0]][pre[1]] = 1;
            int nextY = pre[0] + dirY[dir];
            int nextX = pre[1] + dirX[dir];

            while (inRange(nextY, nextX)) {
                if (x == 0) break;
                if (type[nextY][nextX] != type[pre[0]][pre[1]]) { // 타입 다른 경우만 전파
                    int y = map[nextY][nextX];

                    if (x > y) { // 강한 전파
                        type[nextY][nextX] = type[pre[0]][pre[1]];
                        x -= y + 1;
                        map[nextY][nextX] += 1;
                    } else { // 약한 전파
                        int nextType = weakPropa(type[pre[0]][pre[1]], type[nextY][nextX]);

                        type[nextY][nextX] = nextType;
                        map[nextY][nextX] += x;
                        x = 0;
                    }

                    //전파 대상자 visited 체크
                    visited[nextY][nextX] = true;
                }


                nextY += dirY[dir];
                nextX += dirX[dir];
            }
        }
    }

    public static int weakPropa(int pivot, int target) {
        switch (pivot) {
            case MINT_CHOCO_MILK:
                return MINT_CHOCO_MILK;
            case MINT_CHOCO:
                if (target == MINT_CHOCO_MILK || target == MINT_MILK || target == CHOCO_MILK) return MINT_CHOCO_MILK;
                else if (target == MILK) return MINT_CHOCO_MILK;
                else return MINT_CHOCO;
            case MINT_MILK:
                if (target == MINT_CHOCO_MILK || target == MINT_CHOCO || target == CHOCO_MILK) return MINT_CHOCO_MILK;
                else if (target == CHOCO) return MINT_CHOCO_MILK;
                else return MINT_MILK;
            case CHOCO_MILK:
                if (target == MINT_CHOCO_MILK || target == MINT_CHOCO || target == MINT_MILK) return MINT_CHOCO_MILK;
                else if (target == MINT) return MINT_CHOCO_MILK;
                else return CHOCO_MILK;
            case MILK:
                if (target == MINT_CHOCO_MILK || target == MINT_CHOCO) return MINT_CHOCO_MILK;
                else if (target == CHOCO_MILK || target == CHOCO) return CHOCO_MILK;
                else return MINT_MILK;
            case CHOCO:
                if (target == MINT_CHOCO_MILK || target == MINT_MILK) return MINT_CHOCO_MILK;
                else if (target == MINT_CHOCO || target == MINT) return MINT_CHOCO;
                else return CHOCO_MILK;

            case MINT:
                if (target == MINT_CHOCO_MILK || target == CHOCO_MILK) return MINT_CHOCO_MILK;
                if (target == MINT_CHOCO || target == CHOCO) return MINT_CHOCO;
                else return MINT_MILK;
        }
        return -1;
    }

    public static List<int[]> lunchTime() {
        List<int[]> preList = new ArrayList<>();
        boolean[][] visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 그룹 탐색
                if (visited[i][j]) continue;
                List<int[]> group = getGroup(i, j, visited);

                // 대표자 선정
                Collections.sort(group, (a, b) -> {
                    int aB = map[a[0]][a[1]];
                    int bB = map[b[0]][b[1]];

                    if (aB != bB) return bB - aB; // 내림차순

                    if (a[0] != b[0]) return a[0] - b[0]; // 오름차순

                    return a[1] - b[1];
                });

                // 신앙심 넘기기
                int[] pre = group.get(0);
                for (int g = 1; g < group.size(); g++) {
                    int[] gLoc = group.get(g);
                    map[gLoc[0]][gLoc[1]]--;
                    map[pre[0]][pre[1]]++;
                }

                // preList에 대표자 추가
                preList.add(pre);
            }
        }

        return preList;
    }

    public static List<int[]> getGroup(int y, int x, boolean[][] visited) {
        List<int[]> group = new ArrayList<>();
        Queue<int[]> q = new ArrayDeque<>();
        int pivotType = type[y][x];

        q.offer(new int[]{y, x});
        group.add(new int[]{y, x});
        visited[y][x] = true;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                // 범위 안,방문 x, 조건(같은타입)
                if (!inRange(nextY, nextX)) continue;
                if (visited[nextY][nextX]) continue;
                if (type[nextY][nextX] != pivotType) continue;

                q.offer(new int[]{nextY, nextX});
                group.add(new int[]{nextY, nextX});
                visited[nextY][nextX] = true;
            }
        }
        return group;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void morningTime() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] += 1;
            }
        }
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        type = new int[N][N];

        for (int i = 0; i < N; i++) {
            String l = br.readLine();
            for (int j = 0; j < N; j++) {
                char c = l.charAt(j);
                int curType = -1;
                if (c == 'T') {
                    curType = 6;
                } else if (c == 'C') {
                    curType = 5;
                } else {
                    curType = 4;
                }
                type[i][j] = curType;
            }
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
boolean[][][] types = new boolean[N][N][3];
0      1        2
T: 민트, C: 초코, M: 우유

or

0         1       2       3      4    5    6
민트초코우유, 민트초코, 민트우유, 초코우유, 우유, 초코, 민트

List<int[]> preList;

1. 아침 시간

2. 점심 시간
- BFS -> 그룹 리스트 생성 -> 대표자 선정,신앙심 넘기기 -> preList에 대표자 추가 

3. 저녁시간
- visited[N][N] -> 전파 대상 체크
- preList -> 리스트 분리
    4~6 :List<int[]> oneFood
    1~3: twoFood
    0: threeFood
- 각 그룹 정렬
- 전파 시작
    - 대표자 전파당했는지 체크 => if(visited[N][N] ) continue;
    - 전파 대상자 visited 체크


#저녁시간: 각 그룹 대표자가 신앙 전파
전파 방향: 0:위, 1:아래, 2:왼쪽, 3: 오른쪽

- 강한전파 ( x>y)
    - 전파 대상은 전파자의 사상과 같아짐
    - 간절함(x)가 0되면 종료
- 약한 전파 ( x<= y)
    - 기존 음식 + 전파자의 음식이 합쳐진 음식 신봉
    - 전파자의 간절함(x) 즉시 0되고 전파 종료.
    - 대상의 신앙심 x 만큼 증가

- 전파 당한 대상은 방어상태가 됨-> 당일에 전파 불가 (전파 대상이 그룹의 대표자인 경우)
    - 추가로 전파를 받을수는 있음
*/