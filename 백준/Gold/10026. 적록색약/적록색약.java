import java.util.Scanner;

public class Main {
    static int N;
    static char[][] ary_1; // 일반인
    static char[][] ary_2; // 적록색약
    static boolean[][] visited;
    static int[] dirY = {1, -1, 0, 0};
    static int[] dirX = {0, 0, -1, 1};
    static int count_1 = 0;
    static int count_2 = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        sc.nextLine();

        ary_1 = new char[N][N];
        ary_2 = new char[N][N];
        visited = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            String input = sc.nextLine();
            for (int j = 0; j < input.length(); j++) {
                char cur = input.charAt(j);
                ary_1[i][j] = cur;
                if (cur == 'R') {
                    ary_2[i][j] = 'G';
                } else {
                    ary_2[i][j] = cur;
                }

            }
        }

        //일반인
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (visited[y][x]) {
                    continue;
                }

                count_1++;
                DFS(y, x, ary_1);
            }
        }

        visited = new boolean[N][N];
        //적록색약
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (visited[y][x]) {
                    continue;
                }

                count_2++;
                DFS(y, x, ary_2);
            }
        }

        System.out.println(count_1);
        System.out.println(count_2);
    }

    public static void DFS(int y, int x, char[][] ary) {
        char cur = ary[y][x];
        visited[y][x] = true;

        for (int i = 0; i < 4; i++) {
            int nextY = y + dirY[i];
            int nextX = x + dirX[i];

            if (nextY >= 0 && nextY < N && nextX >= 0 && nextX < N && !visited[nextY][nextX]) {
                if (ary[nextY][nextX] == cur) {
                    visited[nextY][nextX] = true;
                    DFS(nextY, nextX, ary);
                }
            }
        }
    }
}
