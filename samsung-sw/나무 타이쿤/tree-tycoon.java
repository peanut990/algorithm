// 4:38~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;

    static int[][] map;
    static int[][] potionMap;

    static int[] dirY = {0, -1, -1, -1, 0, 1, 1, 1}; //우, 우상, 상, 좌상, 좌, 좌하, 하, 우하
    static int[] dirX = {1, 1, 0, -1, -1, -1, 0, 1};

    static int[] growUpDir = {1, 3, 5, 7};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        potionMap = new int[N][N];

        // map 초기화
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // potionMap 초기화
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                potionMap[N - 2 + i][j] = 1;
            }
        }

        // 로직 시작
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int dist = Integer.parseInt(st.nextToken());

            //1. 특수 영양제 이동
            potionMap = movePotions(d, dist);

            //2. 특수 영양제 투입
            insertPotions();

            // 3. 리브로수 성장
            growUp();

            //4. 특수 영양제 제거 및 새로 생성
            potionMap = potionRemoveAndCreate();

        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result += map[i][j];
            }
        }
        System.out.println(result);
    }

    public static int[][] potionRemoveAndCreate() {
        int[][] nextPotionMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] >= 2 && potionMap[i][j] != 1) {
                    map[i][j] -= 2;
                    nextPotionMap[i][j] = 1;
                }
            }
        }

        return nextPotionMap;
    }

    public static void growUp() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (potionMap[i][j] == 1) {
                    for (int d : growUpDir) {
                        int nextY = i + dirY[d];
                        int nextX = j + dirX[d];

                        // 범위 안, > 0
                        if (!inRange(nextY, nextX)) continue;

                        if (map[nextY][nextX] > 0) {
                            map[i][j]++;
                        }
                    }
                }
            }
        }
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void insertPotions() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (potionMap[i][j] == 1) {
                    map[i][j]++;
                }
            }
        }
    }

    public static int[][] movePotions(int d, int dist) {
        int[][] nextPotionMap = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (potionMap[i][j] == 1) {
                    int nextY = i + dirY[d] * dist;
                    int nextX = j + dirX[d] * dist;

                    nextY = (N + nextY) % N;
                    nextX = (N + nextX) % N;

                    nextPotionMap[nextY][nextX] = 1;
                }
            }
        }

        return nextPotionMap;
    }
}

/*
boolean[][] potionMap;


1. 특수 영양제 이동
2. 특수 영양제 투입
 2-1. 영양제 위치 리브로수 ++
3. 리브로수 성장
    - 대각선 리브로수 만큼 ++
4. 특수 영양제 제거 및 새로 생성
boolean[][] newPotionMap;


5 1
1 0 0 4 2
2 1 3 2 1
0 0 0 2 5
1 0 0 0 3
1 2 1 3 3
2 3
*/