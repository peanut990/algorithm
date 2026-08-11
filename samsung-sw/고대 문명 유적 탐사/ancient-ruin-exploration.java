import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int MAP_SIZE = 5;
    static int K; // 탐사 횟수
    static int M; // 변면에 적힌 유물 조각 개수
    static int[][] map = new int[MAP_SIZE][MAP_SIZE];
    static int[] printedNums;

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
            for (int d = 1; d <= 3; d++) { // 90, 180, 270
                for (int cx = 1; cx <= 3; cx++) {
                    for (int cy = 1; cy <= 3; cy++) {
                        int[][] rotatedMap = createRotatedMap(cy, cx, map, d);

//                        System.out.printf("cy: %d, cx: %d, d:%d \n", cy, cx, d);
//                        printMap(rotatedMap);

                        int removedCount = search(rotatedMap);
                        if (removedCount > maxCount) {
                            maxCount = removedCount;
                            maxMap = rotatedMap;
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
                int removedCount = search(map);
//                System.out.println("유물 연쇄 획득 후 ");
//                printMap(map);

                if (removedCount <= 0) {
                    break;
                }

                answer += removedCount;
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

    public static int search(int[][] rotatedMap) {
        boolean[][] checkedMap = new boolean[MAP_SIZE][MAP_SIZE];
        int removedCount = 0;

        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (checkedMap[i][j] || rotatedMap[i][j] == 0) continue;

                // 개수 탐색
                List<int[]> locList = bfs(i, j, rotatedMap, checkedMap);
                if (locList.size() >= 3) {
                    removedCount += locList.size();
                    
                    for(int[] loc : locList){
                        rotatedMap[loc[0]][loc[1]] = 0;
                    }
                }
                //System.out.printf("startY: %d, startX: %d, count: %d \n",i,j,count);
            }
        }

        return removedCount;
    }

    public static List<int[]> bfs(int y, int x, int[][] map, boolean[][] checkedMap) {
        Queue<int[]> q = new LinkedList<>();
        List<int[]> locList = new ArrayList<>();
        q.offer(new int[]{y, x});
        checkedMap[y][x] = true;

        int pivotNum = map[y][x];

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            locList.add(poll);
            
            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX) || checkedMap[nextY][nextX]) continue;
                if (map[nextY][nextX] == pivotNum) {
                    q.offer(new int[]{nextY, nextX});
                    checkedMap[nextY][nextX] = true;
                }
            }
        }

        return locList;
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

    public static int[][] createRotatedMap(int cy, int cx, int[][] map, int rotationCount) {
        int[][] result = copyMap(map);

        for (int r = 0; r < rotationCount; r++) {
            int[][] prev = copyMap(result);
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    result[cy + x][cx - y] = prev[cy + y][cx + x];
                }
            }
        }

        return result;
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

(-1, -1) (-1,0) (-1,1)
(0, -1)
(1, -1)
*/