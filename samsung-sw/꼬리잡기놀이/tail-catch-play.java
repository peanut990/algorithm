import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static int N; // 격자 크기
    public static int M; // 팀 개수
    public static int K; // 라운드 수

    public static int[][] numInTeamMap; // 1번 부터 시작
    public static int[][] map; // 1: 머리, 2: 팀원, 3: 꼬리
    public static int[][] trackMap;

    public static int[] dirY = {-1, 0, 1, 0};// 상, 우, 하 ,좌 : 시계방향
    public static int[] dirX = {0, 1, 0, -1};

    public static int totalScore = 0;

    public static void main(String[] args) throws Exception {
        init();

        //로직 시작
        for (int k = 0; k < K; k++) {
            //1. 이동
            move();

            // 2. 공 던지기
            throwBall(k);

        }

        System.out.println(totalScore);
    }

    public static void throwBall(int k) {
        int round = k % (4 * N);

        if (round >= 0 && round < N) {
            round %= N;
            for (int c = 0; c < N; c++) {
                if (isPlayer(map[round][c])) {
                    takeBall(round, c);
                    break;
                }
            }
        } else if (round >= N && round < 2 * N) {
            round %= N;
            for (int r = N - 1; r >= 0; r--) {
                if (isPlayer(map[r][round])) {
                    takeBall(r, round);
                    break;
                }
            }
        } else if (round >= 2 * N && round < 3 * N) {
            round %= N;
            for (int c = N - 1; c >= 0; c--) {
                //map[N - 1 - round][c]
                if (isPlayer(map[N - 1 - round][c])) {
                    takeBall(N - 1 - round, c);
                    break;
                }
            }
        } else if (round >= 3 * N && round < 4 * N) {
            round %= N;
            for (int r = 0; r < N; r++) {
                //map[r][N-1-round]
                if (isPlayer(map[r][N - 1 - round])) {
                    takeBall(r, N - 1 - round);
                    break;
                }
            }
        }
    }

    public static void takeBall(int y, int x) {
        //점수 더하기
        totalScore += (int) Math.pow(numInTeamMap[y][x], 2);

        // 헤드 변환
        changeHead(y, x);
    }

    public static void changeHead(int y, int x) {
        List<int[]> team = getTeam(y, x);
        int[] head = new int[2];
        int[] tail = new int[2];

        for (int[] t : team) {
            if (map[t[0]][t[1]] == 1) {
                head = t;
            } else if (map[t[0]][t[1]] == 3) {
                tail = t;
            }
        }

        map[head[0]][head[1]] = 3;
        map[tail[0]][tail[1]] = 1;
    }


    public static boolean isPlayer(int p) {
        return p > 0 && p < 4;
    }

    public static void move() {
        int[][] nextMovedMap = new int[N][N];
        int[][] nextNumInTeamMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nextMovedMap[i][j] = trackMap[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1) {
                    int teamDir = searchTeamDir(i, j);

                    List<int[]> team = getTeamFromHead(i, j, teamDir);

                    rotate(team, nextMovedMap, nextNumInTeamMap);
                }
            }
        }

        map = nextMovedMap;
        numInTeamMap = nextNumInTeamMap;
    }

    public static void rotate(List<int[]> team, int[][] movedMap, int[][] numInTeamMap) {
        int[] prev = team.get(0);
        int moveDir = searchMoveDir(prev[0], prev[1]);
        int num = 1;

        int nextY = prev[0] + dirY[moveDir];
        int nextX = prev[1] + dirX[moveDir];

        movedMap[nextY][nextX] = map[prev[0]][prev[1]];
        numInTeamMap[nextY][nextX] = num++;

        for (int i = 1; i < team.size(); i++) {
            int[] cur = team.get(i);
            movedMap[prev[0]][prev[1]] = map[cur[0]][cur[1]];
            numInTeamMap[prev[0]][prev[1]] = num++;
            prev = cur;
        }
    }

    public static List<int[]> getTeam(int y, int x) {
        boolean[][] checked = new boolean[N][N];
        Queue<int[]> q = new LinkedList<>();
        List<int[]> team = new ArrayList<>();

        checked[y][x] = true;
        q.offer(new int[]{y, x});
        team.add(new int[]{y, x});

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (checked[nextY][nextX]) continue;
                if (map[nextY][nextX] == 0 || map[nextY][nextX] == 4) continue;

                q.offer(new int[]{nextY, nextX});
                team.add(new int[]{nextY, nextX});
                checked[nextY][nextX] = true;
            }
        }
        return team;
    }

    public static List<int[]> getTeamFromHead(int y, int x, int teamDir) {
        boolean[][] checked = new boolean[N][N];
        List<int[]> team = new ArrayList<>();
        checked[y][x] = true;
        team.add(new int[]{y, x});

        y += dirY[teamDir];
        x += dirX[teamDir];

        checked[y][x] = true;
        team.add(new int[]{y, x});

        while (map[y][x] != 3) {
            for (int d = 0; d < 4; d++) {
                int nextY = y + dirY[d];
                int nextX = x + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (map[nextY][nextX] == 0 || checked[nextY][nextX]) continue;

                if (map[nextY][nextX] == 2 || map[nextY][nextX] == 3) {
                    y = nextY;
                    x = nextX;

                    checked[y][x] = true;
                    team.add(new int[]{y, x});

                    break;
                }
            }
        }

        return team;
    }

    public static int searchMoveDir(int y, int x) {
        // 빈칸 존재
        for (int d = 0; d < 4; d++) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (map[nextY][nextX] == 4) {
                return d;
            }
        }

        // 빈칸 없으면
        for (int d = 0; d < 4; d++) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (map[nextY][nextX] == 3) {
                return d;
            }
        }

        return -1;
    }

    public static int searchTeamDir(int y, int x) {
        for (int d = 0; d < 4; d++) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (map[nextY][nextX] == 2) {
                return d;
            }
        }

        for (int d = 0; d < 4; d++) {
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if (!inRange(nextY, nextX)) continue;

            if (map[nextY][nextX] == 3) {
                return d;
            }
        }

        return -1;
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
        numInTeamMap = new int[N][N];
        trackMap = new int[N][N];
        // teamDirs = new int[M+1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > 0) {
                    trackMap[i][j] = 4;
                }
            }
        }

    }
}

