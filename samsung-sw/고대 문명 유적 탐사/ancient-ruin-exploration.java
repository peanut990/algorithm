import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int MAP_SIZE = 5;
    static int K; // 탐사 횟수
    static int M; // 변면에 적힌 유물 조각 개수
    static int[][] map = new int[MAP_SIZE][MAP_SIZE];
    static int[] printedNums;

    static int searchedCount;

    static int[] dirY = {-1, 1, 0, 0}; // 상하좌우
    static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        printedNums = new int[M];

        for (int i = 0; i < MAP_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            printedNums[i] = Integer.parseInt(st.nextToken());
        }

//        printMap(map);
        int printedNumsIdx = 0;
        for (int k = 0; k < K; k++) {  // ### K 값 수정
            int answer = 0;

            // 1. 탐사 진행
            int maxCount = 0;

            int[][] maxMap = copyMap(map);
            for (int d = 0; d < 3; d++) { // 90, 180, 270
                for (int cx = 1; cx <= 3; cx++) {
                    for (int cy = 1; cy <= 3; cy++) {
                        int[][] rotatedMap = copyMap(map);
                        rotate(cy, cx, rotatedMap, d);

//                        System.out.printf("cy: %d, cx: %d, d:%d \n", cy, cx, d);
//                        printMap(rotatedMap);

                        int[][] searchedMap = search(rotatedMap);
                        if (searchedCount > maxCount) {
                            maxCount = searchedCount;
                            maxMap = searchedMap;
                        }
//                        System.out.printf("searchedCount: %d\n", searchedCount);

                    }
                }
            }

            // 탐사 종료, 결과 반영
            if (maxCount == 0) {
                break;
            }

            map = maxMap;
            answer += maxCount;

//            System.out.println("탐사 종료 ) maxCount: " + maxCount);
//            printMap(map);

            while (true) {
                //2. 조각 생성
                printedNumsIdx = insertNums(map, printedNumsIdx);
//                System.out.println("조각 생성 후 ");
//                printMap(map);

                //3. 유물 연쇄 획득
                map = search(map);
//                System.out.println("유물 연쇄 획득 후 ");
//                printMap(map);

                if (searchedCount <= 0) {
                    break;
                }

                answer += searchedCount;
            }

            System.out.print(answer + " ");
        }
    }

    public static int insertNums(int[][] map, int printedNumsIdx) {
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = MAP_SIZE - 1; y >= 0; y--) {
                if (map[y][x] == 0) {
                    map[y][x] = printedNums[printedNumsIdx];
                    printedNumsIdx = (printedNumsIdx + 1) % printedNums.length;
                }
            }
        }
        return printedNumsIdx;
    }

    public static int[][] search(int[][] rotatedMap) {
        searchedCount = 0; // static
        int[][] tmpMap = copyMap(rotatedMap);
        boolean[][] checkedMap = new boolean[MAP_SIZE][MAP_SIZE];

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (checkedMap[i][j] || tmpMap[i][j] == 0) continue;

                // 개수 탐색
                int count = bfs(i, j, tmpMap, checkedMap);
                if (count >= 3) {
                    searchedCount += count;
                    bfsForZero(i, j, tmpMap);
                }
                //System.out.printf("startY: %d, startX: %d, count: %d \n",i,j,count);
            }
        }

        return tmpMap;
    }

    public static void bfsForZero(int y, int x, int[][] map) {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] checkedMap = new boolean[MAP_SIZE][MAP_SIZE];

        int pivotNum = map[y][x];

        q.offer(new int[]{y, x});
        checkedMap[y][x] = true;
        map[y][x] = 0;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX) || checkedMap[nextY][nextX]) continue;
                if (map[nextY][nextX] == pivotNum) {
                    q.offer(new int[]{nextY, nextX});
                    checkedMap[nextY][nextX] = true;
                    map[nextY][nextX] = 0;
                }
            }
        }

    }

    public static int bfs(int y, int x, int[][] map, boolean[][] checkedMap) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y, x});
        checkedMap[y][x] = true;

        int pivotNum = map[y][x];
        int count = 1;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX) || checkedMap[nextY][nextX]) continue;
                if (map[nextY][nextX] == pivotNum) {
                    q.offer(new int[]{nextY, nextX});
                    checkedMap[nextY][nextX] = true;
                    count++;
                }
            }
        }

        return count;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < MAP_SIZE && x >= 0 && x < MAP_SIZE;
    }

    public static int[][] copyMap(int[][] map) {
        int[][] copiedMap = new int[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                copiedMap[i][j] = map[i][j];
            }
        }

        return copiedMap;
    }

    public static void rotate(int cy, int cx, int[][] map, int d) {
        int[] tmp = new int[8];
        int idx = 0;
        final int size = 2;

        for (int i = 0; i < size; i++) {
            tmp[idx++] = map[cy - 1][cx - 1 + i];
        }
        for (int i = 0; i < size; i++) {
            tmp[idx++] = map[cy - 1 + i][cx + 1];
        }
        for (int i = 0; i < size; i++) {
            tmp[idx++] = map[cy + 1][cx + 1 - i];
        }
        for (int i = 0; i < size; i++) {
            tmp[idx++] = map[cy + 1 - i][cx - 1];
        }

        // 회전 인덱스 설정
        int stIdx = 6 - (2 * d);
        for (int i = 0; i < size; i++) {
            map[cy - 1][cx - 1 + i] = tmp[stIdx];
            stIdx = (stIdx + 1) % 8;
        }
        for (int i = 0; i < size; i++) {
            map[cy - 1 + i][cx + 1] = tmp[stIdx];
            stIdx = (stIdx + 1) % 8;
        }
        for (int i = 0; i < size; i++) {
            map[cy + 1][cx + 1 - i] = tmp[stIdx];
            stIdx = (stIdx + 1) % 8;
        }
        for (int i = 0; i < size; i++) {
            map[cy + 1 - i][cx - 1] = tmp[stIdx];
            stIdx = (stIdx + 1) % 8;
        }
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
1.탐사 진행
for i: 1 ~3
 for j: 1~3
    tmp 맵
    90, 180, 270도 회전
        유물 1차 획득
        최대 맵 tmp 에 저장
    map = tmp

while:
2. 조각 생성
3. 유물 연쇄 획득


*/