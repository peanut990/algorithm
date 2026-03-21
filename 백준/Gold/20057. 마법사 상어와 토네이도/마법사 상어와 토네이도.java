import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int[] dirY = {0, 1, 0, -1}; // 왼, 아래, 우, 위
    static int[] dirX = {-1, 0, 1, 0};
    static int N;
    static int[][] map;
    static int result = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        sc.nextLine();
        map = new int[N][N];
        int dist = 1;
        int dir = 0;
        int addCount = 0;
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작
        int y = N / 2;
        int x = N / 2;
        int moveCount = 0;

        // 현재 방향 다음 칸 모래 날리기
        while (true) {
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            int curSand = map[nextY][nextX];

            //모래 날리기
            // 5%
            int value5p = curSand * 5 / 100;
            int nextY_0 = nextY + 2 * dirY[dir];
            int nextX_0 = nextX + 2 * dirX[dir];
            isExclude(nextY_0, nextX_0, value5p);

            // 아래 7%
            int value7p = curSand * 7 / 100;
            int nextY_1 = nextY + dirY[(dir + 1) % 4];
            int nextX_1 = nextX + dirX[(dir + 1) % 4];
            isExclude(nextY_1, nextX_1, value7p);
            // 위 7%
            int nextY_2 = nextY + dirY[(4 + dir - 1) % 4];
            int nextX_2 = nextX + dirX[(4 + dir - 1) % 4];
            isExclude(nextY_2, nextX_2, value7p);

            // 아래 10%
            int value10p = curSand * 10 / 100;
            int nextY_3 = nextY_1 + dirY[dir];
            int nextX_3 = nextX_1 + dirX[dir];
            isExclude(nextY_3, nextX_3, value10p);
            // 위 10%
            int nextY_4 = nextY_2 + dirY[dir];
            int nextX_4 = nextX_2 + dirX[dir];
            isExclude(nextY_4, nextX_4, value10p);

            // 아래 1%
            int value1p = curSand * 1 / 100;
            int nextY_5 = nextY_1 - dirY[dir];
            int nextX_5 = nextX_1 - dirX[dir];
            isExclude(nextY_5, nextX_5, value1p);
            // 위 1%
            int nextY_6 = nextY_2 - dirY[dir];
            int nextX_6 = nextX_2 - dirX[dir];
            isExclude(nextY_6, nextX_6, value1p);
            //아래 2%
            int value2p = curSand * 2 / 100;
            int nextY_7 = nextY_1 + dirY[(dir + 1) % 4];
            int nextX_7 = nextX_1 + dirX[(dir + 1) % 4];
            isExclude(nextY_7, nextX_7, value2p);
            // 위 2%
            int nextY_8 = nextY_2 + dirY[(4 + dir - 1) % 4];
            int nextX_8 = nextX_2 + dirX[(4 + dir - 1) % 4];
            isExclude(nextY_8, nextX_8, value2p);
            // 알파
            int valueAlp = curSand - value5p - 2 * (value7p + value10p + value1p + value2p);
            int nextY_9 = nextY + dirY[dir];
            int nextX_9 = nextX + dirX[dir];
            isExclude(nextY_9, nextX_9, valueAlp);

            map[nextY][nextX] = 0;

            // 이동
            y = nextY;
            x = nextX;
            moveCount++;
            if (y == 0 && x == 0) {
                break;
            }

            // 거리 체크
            // - 다왔으면 방향 변경
            if (moveCount == dist) {
                dir = (dir + 1) % 4;
                dist += addCount % 2;
                addCount++;
                moveCount = 0;
            }
            // - 덜 왔으면 방향 유지
        }
        System.out.println(result);
    }

    public static void isExclude(int y, int x, int value) {
        if (y >= 0 && y < N && x >= 0 && x < N) {
            map[y][x] += value;
        } else {
            result += value;
        }
    }
}
