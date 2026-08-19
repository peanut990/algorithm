//10:44

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static class Player {
        int num;
        int y;
        int x;
        int dir;
        int value;

        int gun = 0;

        Player(int num, int y, int x, int dir, int value) {
            this.num = num;
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.value = value;
        }
    }

    public static int N; // 격자 크기
    public static int M; // 플에이어 수
    public static int K; // 라운드 수

    public static int[] dirY = {-1, 0, 1, 0}; // 상,우,하,좌
    public static int[] dirX = {0, 1, 0, -1};

    public static List<Integer>[][] map;
    public static int[][] playerMap;
    public static Player[] players;

    public static int[] scores;

    public static void main(String[] args) throws Exception {
        init();

        // 로직 시작
        for (int k = 0; k < K; k++) {
            // 플에이어 이동
            movePlayers();
        }

        for (int i = 1; i < scores.length; i++) {
            System.out.print(scores[i] + " ");
        }

    }

    public static void movePlayers() {
        for (int i = 1; i < players.length; i++) {
            Player p = players[i];

            move(p);
        }
    }

    public static void move(Player p) {
        int nextY = p.y + dirY[p.dir];
        int nextX = p.x + dirX[p.dir];

        if (!inRange(nextY, nextX)) { // 격자 밖 -> 방향 전환
            p.dir = (p.dir + 2) % dirY.length;
            nextY = p.y + dirY[p.dir];
            nextX = p.x + dirX[p.dir];
        }

        // 싸움
        if (isPlayer(nextY, nextX)) {
            Player op = players[playerMap[nextY][nextX]];
            doMove(p, nextY, nextX);
            doFight(p, op);

        } else if (isGun(nextY, nextX)) {
            //  총 로직, 이동
            choiceGun(p, nextY, nextX);
            doMove(p, nextY, nextX);
        } else {
            // 이동
            doMove(p, nextY, nextX);
        }

    }

    public static void doFight(Player p, Player op) {
        Player[] fightResult = getWinnerAndLoser(p, op);
        Player winner = fightResult[0];
        Player loser = fightResult[1];

        // 점수 반영
        scores[winner.num] += Math.abs((winner.value + winner.gun) - (loser.value + loser.gun));

        // 진 플에이어 
        moveLoser(loser);

        // 이긴 플레이어
        moveWinner(winner);
    }

    public static void moveWinner(Player p) {
        doMove(p, p.y, p.x);
        if(isGun(p.y,p.x)){
            choiceGun(p, p.y, p.x);
        }
    }

    public static void moveLoser(Player p) {
        // 총 내려놓기
        if (p.gun > 0) {
            map[p.y][p.x].add(p.gun);
            p.gun = 0;
        }

        int nextY = p.y + dirY[p.dir];
        int nextX = p.x + dirX[p.dir];

        while (!inRange(nextY, nextX) || isPlayer(nextY, nextX)) {
            p.dir = (p.dir + 1) % dirY.length;
            nextY = p.y + dirY[p.dir];
            nextX = p.x + dirX[p.dir];
        }

        if (isGun(nextY, nextX)) {
            //  총 로직, 이동
            choiceGun(p, nextY, nextX);
            doMove(p, nextY, nextX);
        } else {
            // 이동
            doMove(p, nextY, nextX);
        }
    }

    public static Player[] getWinnerAndLoser(Player p, Player op) {
        Player winner = null;
        Player loser = null;

        int pAttack = p.value + p.gun;
        int opAttack = op.value + op.gun;

        if (pAttack > opAttack) {
            winner = p;
            loser = op;
        } else if (pAttack < opAttack) {
            winner = op;
            loser = p;
        } else if (p.value > op.value) {
            winner = p;
            loser = op;
        } else {
            winner = op;
            loser = p;
        }

        return new Player[]{winner, loser};
    }

    public static void doMove(Player p, int nextY, int nextX) {
        playerMap[p.y][p.x] = 0;

        p.y = nextY;
        p.x = nextX;

        playerMap[p.y][p.x] = p.num;
    }

    public static void choiceGun(Player p, int y, int x) {
        List<Integer> gunList = map[y][x];

        if (p.gun > 0) { // 총 소지
            gunList.add(p.gun);
            p.gun = 0;
        }

        Collections.sort(gunList, (a, b) -> {
            return a - b; // 오름 차순 정렬
        });

        // 가장 쌘 총 선택
        p.gun = gunList.remove(gunList.size() - 1);
    }

    public static boolean isGun(int y, int x) {
        return map[y][x].size() > 0 ? true : false;
    }

    public static boolean isPlayer(int y, int x) {
        return playerMap[y][x] > 0 ? true : false;
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

        map = new List[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        playerMap = new int[N][N];
        players = new Player[M + 1];
        scores = new int[M + 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int gun = Integer.parseInt(st.nextToken());
                if (gun == 0) continue;

                map[i][j].add(gun);
            }
        }

        for (int num = 1; num <= M; num++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());

            playerMap[y][x] = num;
            players[num] = new Player(num, y, x, d, s);
        }
    }
}
