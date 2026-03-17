import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int R;
    static int C;
    static int T;
    static int[][] map;
    static int[][] nextMap;

    static int[] dirY = {-1, 0, 1, 0}; //시계방향
    static int[] dirX = {0, 1, 0, -1};
    static int[] top;
    static int[] bottom;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        T = sc.nextInt();
        map = new int[R][C];
        nextMap = new int[R][C];
        sc.nextLine();

        boolean findTop = false;
        for (int i = 0; i < R; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                nextMap[i][j] = map[i][j];
                if (map[i][j] == -1) {
                    if (!findTop) {
                        top = new int[]{i, j};
                        findTop = true;
                    } else {
                        bottom = new int[]{i, j};
                    }
                }

            }
        }
        
        for (int t = 0; t < T; t++) {
            //확산
            for (int y = 0; y < R; y++) {
                for (int x = 0; x < C; x++) {
                    if (map[y][x] <= 0) continue;

                    int dif = map[y][x] / 5;
                    for (int k = 0; k < 4; k++) {
                        int nextY = y + dirY[k];
                        int nextX = x + dirX[k];

                        if (nextY >= 0 && nextY < R && nextX >= 0 && nextX < C && map[nextY][nextX] != -1) {
                            nextMap[y][x] -= dif;
                            nextMap[nextY][nextX] += dif;
                        }
                    }
                }
            }
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    map[i][j] = nextMap[i][j];
                }
            }
            
            // 순환
            //top
            int dir = 0;
            int curY = top[0] - 1; // 청소기 윗칸 부터 시작
            int curX = top[1];
            while (true) {
                int nextY = curY + dirY[dir];
                int nextX = curX + dirX[dir];

                if (dir == 3 && nextX == 0) {
                    map[curY][curX] = 0;
                    break;
                }
                if (nextY >= 0 && nextY <= top[0] && nextX >= 0 && nextX < C) {
                    map[curY][curX] = map[nextY][nextX];
                    curY = nextY;
                    curX = nextX;
                } else {
                    dir++;
                }
            }

            // 순환
            //bottom
            dir = 0;
            curY = bottom[0] + 1; // 청소기 아래칸 부터 시작
            curX = bottom[1];
            while (true) {
                int nextY = curY - dirY[dir];
                int nextX = curX + dirX[dir];

                if (dir == 3 && nextX == 0) {
                    map[curY][curX] = 0;
                    break;
                }
                if (nextY >= bottom[0] && nextY < R && nextX >= 0 && nextX < C) {
                    map[curY][curX] = map[nextY][nextX];
                    curY = nextY;
                    curX = nextX;
                } else {
                    dir++;
                }
            }

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    nextMap[i][j] = map[i][j];
                }
            }
        }

        int count = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    count += map[i][j];
                }
            }
        }

        System.out.println(count);
    }

}
