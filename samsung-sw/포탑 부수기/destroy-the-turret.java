// 3:26

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static int N; // 행
    public static int M; // 열
    public static int K; // 턴 수

    public static int[] dirY = {0, 1, 0, -1};// 우,하,좌,상
    public static int[] dirX = {1, 0, -1, 0};

    public static int[] reverseDirY = {-1, 0, 1, 0}; // 상,좌,하,우
    public static int[] reverseDirX = {0, -1, 0, 1};

    public static int[] bombDirY = {-1, -1, -1, 0, 0, 0, 1, 1, 1}; //좌상, 상, 우상, 좌, 가운데, 우, 좌하, 하, 우하
    public static int[] bombDirX = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

    public static int[][] powerMap;
    public static int[][] recentAttackMap;

    public static int curTurn;

    public static void main(String[] args) throws Exception {
        // 초기화
        init();

        // 로직 시작
        for (int k = 1; k <= K; k++) { // k 값 수정 필요##
            curTurn = k;
            // 부서지지 않은 포탑 1개면 종료
            int unBrokenCount = getUnBrokenCount();
            if (unBrokenCount == 1) break;

            // 1.공격자 선정
            int[] attacker = choiceAttacker();

            // 2. 공격자의 공격
            List<int[]> relatedAttack = attackStrongest(attacker);

            // 3. 포탑 부서짐
            updateBrokenTurret(relatedAttack);

            // 4. 포탑 정비
            upgradeTurret(relatedAttack);

        }

        int maxPower = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                maxPower = Math.max(maxPower, powerMap[i][j]);
            }
        }
        System.out.println(maxPower);
    }

    public static void upgradeTurret(List<int[]> relatedAttack) {
        boolean[][] related = new boolean[N][M];
        for (int[] r : relatedAttack) {
            related[r[0]][r[1]] = true;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (powerMap[i][j] <= 0) continue;
                if (related[i][j]) continue;

                powerMap[i][j] += 1;
            }
        }
    }

    public static int getUnBrokenCount() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (powerMap[i][j] > 0) count++;
            }
        }
        return count;
    }

    public static void updateBrokenTurret(List<int[]> relatedAttack) {
        for (int[] related : relatedAttack) {
            if (powerMap[related[0]][related[1]] < 0) powerMap[related[0]][related[1]] = 0;
        }
    }

    public static List<int[]> attackStrongest(int[] attacker) {
        List<int[]> relatedAttack = new ArrayList<>();
        int[] strongest = searchStrongest(attacker);

        //레이저 공격 체크
        int[][][] razerMap = getRazerMap(attacker, strongest);
        if (razerMap != null) {
            doRazerAttack(attacker, strongest, razerMap, relatedAttack);
        } else {
            // 포탄 공격
            doBombAttack(attacker, strongest, relatedAttack);
        }

        // 공격 턴 저장
        recentAttackMap[attacker[0]][attacker[1]] = curTurn;

        return relatedAttack;
    }

    public static void doBombAttack(int[] attacker, int[] strongest, List<int[]> relatedAttack) {
        int attackPower = powerMap[attacker[0]][attacker[1]];

        int targetY = strongest[0];
        int targetX = strongest[1];

        relatedAttack.add(attacker);

        for (int d = 0; d < bombDirY.length; d++) {
            int nextY = (N + targetY + bombDirY[d]) % N;
            int nextX = (M + targetX + bombDirX[d]) % M;


            if (nextY == attacker[0] && nextX == attacker[1]) { // 공격자 대미지 무시
                continue;
            }

            if (nextY == targetY && nextX == targetX) {
                powerMap[nextY][nextX] -= attackPower;
            } else {
                powerMap[nextY][nextX] -= attackPower / 2;
            }

            relatedAttack.add(new int[]{nextY, nextX});
        }

    }

    public static void doRazerAttack(int[] attacker, int[] strongest, int[][][] razerMap, List<int[]> relatedAttack) {
        int attackPower = powerMap[attacker[0]][attacker[1]];

        int curY = strongest[0];
        int curX = strongest[1];

        // 공격 대상 공격
        powerMap[strongest[0]][strongest[1]] -= attackPower;

        // strongest-> attacker 공격 최단 경로 탐색
        relatedAttack.add(strongest);

        // 경로 공격
        while (true) {
            int parentY = razerMap[curY][curX][0];
            int parentX = razerMap[curY][curX][1];

            if (parentY == attacker[0] && parentX == attacker[1]) {
                relatedAttack.add(attacker);
                return;
            }

            powerMap[parentY][parentX] -= attackPower / 2;
            relatedAttack.add(new int[]{parentY, parentX});

            curY = parentY;
            curX = parentX;

        }
    }

    public static int[][][] getRazerMap(int[] attacker, int[] strongest) {
        int[][][] razerMap = new int[N][M][2];
        Queue<int[]> q = new LinkedList<>();
        boolean[][] checked = new boolean[N][M];

        q.offer(attacker);
        checked[attacker[0]][attacker[1]] = true;

        while (!q.isEmpty()) {
            int size = q.size();

            for (int s = 0; s < size; s++) {
                int[] poll = q.poll();

                for (int d = 0; d < dirY.length; d++) {
                    int nextY = (N + poll[0] + dirY[d]) % N;
                    int nextX = (M + poll[1] + dirX[d]) % M;

                    if (powerMap[nextY][nextX] == 0) continue; // 부서진 포탑
                    if (checked[nextY][nextX]) continue;

                    q.offer(new int[]{nextY, nextX});
                    checked[nextY][nextX] = true;
                    razerMap[nextY][nextX] = new int[]{poll[0], poll[1]};

                    if (nextY == strongest[0] && nextX == strongest[1]) {
                        return razerMap;
                    }
                }
            }
        }

        return null;
    }

    public static int[] searchStrongest(int[] attacker) {
        List<int[]> candidateStrongest = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if ((i == attacker[0] && j == attacker[1])) continue; // 공격자 후보에서 제외

                if (powerMap[i][j] > 0) {
                    candidateStrongest.add(new int[]{i, j});
                }
            }
        }

        Collections.sort(candidateStrongest, (a, b) -> {
            int powerA = powerMap[a[0]][a[1]];
            int powerB = powerMap[b[0]][b[1]];
            if (powerA != powerB) return powerB - powerA; // 공격력 내림 차순

            int recentAttackA = recentAttackMap[a[0]][a[1]];
            int recentAttackB = recentAttackMap[b[0]][b[1]];

            if (recentAttackA != recentAttackB) return recentAttackA - recentAttackB; // 최근 공격 오름 차순

            if (a[0] + a[1] != b[0] + b[1]) return (a[0] + a[1]) - (b[0] + b[1]); // 행+열 오름차순

            return a[1] - b[1];// 열 오름 차순
        });

        return candidateStrongest.get(0);
    }

    public static int[] choiceAttacker() {
        // 가장 약한 포탑 탐색
        int[] attacker = searchAttacker();

        // 가장 약한 포탑 공격력 증가
        powerMap[attacker[0]][attacker[1]] += N + M;

        return attacker;
    }


    public static int[] searchAttacker() {
        List<int[]> candidateAttacks = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (powerMap[i][j] > 0) {
                    candidateAttacks.add(new int[]{i, j});
                }
            }
        }

        Collections.sort(candidateAttacks, (a, b) -> {
            int powerA = powerMap[a[0]][a[1]];
            int powerB = powerMap[b[0]][b[1]];
            if (powerA != powerB) return powerA - powerB; // 공격력 오름 차순

            int recentAttackA = recentAttackMap[a[0]][a[1]];
            int recentAttackB = recentAttackMap[b[0]][b[1]];

            if (recentAttackA != recentAttackB) return recentAttackB - recentAttackA; // 최근 공격 내림 차순

            if (a[0] + a[1] != b[0] + b[1]) return (b[0] + b[1]) - (a[0] + a[1]); // 행+열 내림차순

            return b[1] - a[1];// 열 내림 차순
        });
        
        return candidateAttacks.get(0);
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%4d ", map[i][j]);
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
        K = Integer.parseInt(st.nextToken());

        powerMap = new int[N][M];
        recentAttackMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                powerMap[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}
