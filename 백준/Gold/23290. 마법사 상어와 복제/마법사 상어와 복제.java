import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int M;
    static int S;
    static List<Fish>[][] map;
    static int[] dirY_move = {0, -1, -1, -1, 0, 1, 1, 1}; // 시계방향
    static int[] dirX_move = {-1, -1, 0, 1, 1, 1, 0, -1};

    static int[] dirY_shark = {-1, 0, 1, 0}; //상, 좌, 하, 우
    static int[] dirX_shark = {0, -1, 0, 1};

    static int sharkY;
    static int sharkX;

    static int[] sharkMoveIdx;
    static int max;
    static int[][] curMove;
    static int[][] moveResult;
    static int[][] firstMove;
    static boolean[][] ch;
    static boolean isFirst;
    static boolean isFound;

    static int[][] fishSmell = new int[4][4];

    public static class Fish {
        int y;
        int x;
        int d;

        Fish(int fy, int fx, int d) {
            this.y = fy;
            this.x = fx;
            this.d = d;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        M = sc.nextInt();
        S = sc.nextInt();
        sc.nextLine();

        map = new LinkedList[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                map[i][j] = new LinkedList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int fy = Integer.parseInt(st.nextToken()) - 1;
            int fx = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            map[fy][fx].add(new Fish(fy, fx, d));
        }

        StringTokenizer st = new StringTokenizer(sc.nextLine());
        sharkY = Integer.parseInt(st.nextToken()) - 1;
        sharkX = Integer.parseInt(st.nextToken()) - 1;

        // S만큼 반복
        for (int s = 0; s < S; s++) {
            //시작
            // 물고기 이동
            List<Fish>[][] nextMap = new LinkedList[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    nextMap[i][j] = new LinkedList<>();
                }
            }

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    for (Fish fish : map[y][x]) {
                        // 방향 탐색
                        boolean found = false;
                        for (int dir = 0; dir < 8; dir++) {
                            int curDir = (8 + fish.d - dir) % 8; // 반 시계
                            int nextY = y + dirY_move[curDir];
                            int nextX = x + dirX_move[curDir];

                            if (nextY >= 0 && nextY < 4 && nextX >= 0 && nextX < 4) {
                                // ## 냄새 체크 추가  ##
                                if (fishSmell[nextY][nextX] > 0) continue;
                                if (nextY == sharkY && nextX == sharkX) continue;

                                found = true;
                                nextMap[nextY][nextX].add(new Fish(nextY, nextX, curDir));
                                break;
                            }
                        }
                        if (!found) {
                            nextMap[y][x].add(new Fish(y, x, fish.d));
                        }
                    }
                }
            }

            // 냄새 제거
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (fishSmell[i][j] > 0) {
                        fishSmell[i][j]--;
                    }
                }
            }


            //상어 이동 경로 탐색
            max = 0;
            sharkMoveIdx = new int[3];
            curMove = new int[3][2];
            moveResult = new int[3][2];
            firstMove = new int[3][2];
            isFirst = true;
            isFound = false;

            DFS(0, nextMap);

            // 상어 이동
            if (isFound) {
                // 이동 경로 물고기 제거
                for (int[] ints : moveResult) {
                    int removeY = ints[0];
                    int removeX = ints[1];

                    if (nextMap[removeY][removeX].size() > 0) {
                        fishSmell[removeY][removeX] = 2;
                    }
                    nextMap[removeY][removeX] = new LinkedList<>();
                }

                sharkY = moveResult[2][0];
                sharkX = moveResult[2][1];
            } else {
                sharkY = firstMove[2][0];
                sharkX = firstMove[2][1];
            }

            //복제
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (Fish fish : nextMap[i][j]) {
                        map[i][j].add(fish);
                    }
                }
            }
        }
        int result = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result += map[i][j].size();
            }
        }

        System.out.println(result);

    }


    public static void DFS(int lv, List<Fish>[][] map) {
        if (lv == 3) {
            ch = new boolean[4][4];
            int nextY = sharkY;
            int nextX = sharkX;

            // 이동
            int count = 0;
            for (int i = 0; i < sharkMoveIdx.length; i++) {
                nextY += dirY_shark[sharkMoveIdx[i]];
                nextX += dirX_shark[sharkMoveIdx[i]];

                if (nextY >= 0 && nextY < 4 && nextX >= 0 && nextX < 4) {
                    if (!ch[nextY][nextX]) {
                        count += map[nextY][nextX].size();
                        ch[nextY][nextX] = true;
                    }
                    curMove[i] = new int[]{nextY, nextX};
                } else {
                    return;
                }
            }

            if (isFirst) {
                for (int i = 0; i < sharkMoveIdx.length; i++) {
                    firstMove[i] = new int[]{curMove[i][0], curMove[i][1]};
                }
                isFirst = false;
            }

            // 최대값 저장
            if (count > max) {
                //System.out.println("갱신!");
                isFound = true;
                max = count;
                for (int i = 0; i < sharkMoveIdx.length; i++) {
                    moveResult[i] = new int[]{curMove[i][0], curMove[i][1]};
                }
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            sharkMoveIdx[lv] = i;
            DFS(lv + 1, map);
        }
    }
}