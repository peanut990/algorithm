import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int[] dirY = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dirX = {-1, -1, 0, 1, 1, 1, 0, -1};
    //대각 방향 = 1,3,5,7

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        sc.nextLine();

        int[][] map = new int[N][N];
        List<int[]> cloud = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //cloud init
        cloud.add(new int[]{N - 1, 0});
        cloud.add(new int[]{N - 1, 1});
        cloud.add(new int[]{N - 2, 0});
        cloud.add(new int[]{N - 2, 1});


        //시뮬레이션 시작
        for (int m = 0; m < M; m++) {
            int d = sc.nextInt();
            int s = sc.nextInt() % N;
            boolean[][] cloudMap = new boolean[N][N];

            //이동
            for (int i = 0; i < cloud.size(); i++) {
                int nextY = (N + cloud.get(i)[0] + dirY[d - 1] * s) % N;
                int nextX = (N + cloud.get(i)[1] + dirX[d - 1] * s) % N;
                cloud.set(i, new int[]{nextY, nextX});
                cloudMap[nextY][nextX] = true;

                // 비내리기 (+1)
                map[nextY][nextX] += 1;
            }

            // 물 복사
            for (int i = 0; i < cloud.size(); i++) {
                int curY = cloud.get(i)[0];
                int curX = cloud.get(i)[1];

                for (int j = 1; j < dirY.length; j += 2) {
                    int nextY = curY + dirY[j];
                    int nextX = curX + dirX[j];

                    if (nextY >= 0 && nextY < N && nextX >= 0 && nextX < N && map[nextY][nextX] > 0) {
                        map[curY][curX] += 1;
                    }
                }
            }

            //구름 생성
            List<int[]> nextCloud = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] >= 2 && !cloudMap[i][j]) {
                        map[i][j] -= 2;
                        nextCloud.add(new int[]{i, j});
                    }
                }
            }
            cloud = nextCloud;

        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result += map[i][j];
            }
        }

        System.out.println(result);
    }
}
