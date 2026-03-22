import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int[] dirY = {0, 1, 0, -1}; // 우, 하, 좌, 상
    static int[] dirX = {1, 0, -1, 0};
    static int N;
    static int Q;
    static int size;
    static int[][] map;
    static int[][] meltedMap;
    static boolean[][] visited;
    static int maxCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Q = sc.nextInt();
        sc.nextLine();

        size = (int) Math.pow(2, N);
        map = new int[size][size];
        meltedMap = new int[size][size];

        visited = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < size; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int q = 0; q < Q; q++) {
            // 시작
            int L = sc.nextInt();
            int len = (int) Math.pow(2, L);

            if (L > 0) {
                for (int y = 0; y < size; y += len) {
                    for (int x = 0; x < size; x += len) {
                        rotate(y, x, len);
                    }
                }
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    meltedMap[i][j] = map[i][j];
                }
            }

            //녹이기
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (map[y][x] <= 0) {
                        continue;
                    }
                    int count = 0;
                    for (int dir = 0; dir < 4; dir++) {
                        int ny = y + dirY[dir];
                        int nx = x + dirX[dir];

                        // 근처 얼음 카운트
                        if (ny >= 0 && ny < size && nx >= 0 && nx < size && map[ny][nx] > 0) {
                            count++;
                        }
                    }

                    if (count < 3) {
                        meltedMap[y][x] = map[y][x] - 1;
                    }
                }
            }

            // 동기화
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    map[y][x] = meltedMap[y][x];
                }
            }

        }


        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += map[i][j];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int iceCount = 0;
                if (!visited[i][j] && map[i][j] > 0) {
                    iceCount = BFS(i, j);
                }
                maxCount = Math.max(maxCount, iceCount);
            }
        }
        System.out.println(result);
        System.out.println(maxCount);
    }

    public static void rotate(int startY, int startX, int len) {
        int[][] temp = new int[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                temp[j][len - 1 - i] = map[startY + i][startX + j];
            }
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                map[startY + i][startX + j] = temp[i][j];
            }
        }
    }

    public static int BFS(int startY, int startX) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{startY, startX});
        visited[startY][startX] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int[] ice = q.poll();

                for (int dir = 0; dir < 4; dir++) {
                    int ny = ice[0] + dirY[dir];
                    int nx = ice[1] + dirX[dir];

                    if (ny >= 0 && ny < size && nx >= 0 && nx < size && !visited[ny][nx] && map[ny][nx] > 0) {
                        q.offer(new int[]{ny, nx});
                        visited[ny][nx] = true;
                        count++;
                    }
                }
            }
        }

        return count;
    }

}
