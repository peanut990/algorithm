import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static class Fire {
        int m; //질량
        int s; //속력
        int d;// 방향

        Fire(int m, int s, int d) {
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    public static int[] dirY = {-1, -1, 0, 1, 1, 1, 0, -1}; // 시계방향
    public static int[] dirX = {0, 1, 1, 1, 0, -1, -1, -1};
    public static List<Fire>[][] map;
    public static List<Fire>[][] nextMap;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int K = sc.nextInt();
        sc.nextLine();

        map = new LinkedList[N][N];
        nextMap = new LinkedList[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new LinkedList<>();
                nextMap[i][j] = new LinkedList<>();
            }
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            int[] inputs = new int[5];
            for (int j = 0; j < 5; j++) {
                inputs[j] = Integer.parseInt(st.nextToken());
            }

            map[inputs[0] - 1][inputs[1] - 1].add(new Fire(inputs[2], inputs[3], inputs[4]));
        }


        //시작
        for (int t = 0; t < K; t++) {
            // 1
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    List<Fire> fires = map[y][x];
                    for (int k = 0; k < fires.size(); k++) {
                        Fire fire = fires.get(k);
                        // 불 이동
                        int nextY = (N + (y + dirY[fire.d] * fire.s) % N) % N;
                        int nextX = (N + (x + dirX[fire.d] * fire.s) % N) % N;

                        nextMap[nextY][nextX].add(fire);
                    }

                }
            }

            // 2
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    List<Fire> fires = nextMap[y][x];

                    if (fires.size() <= 1) continue;

                    // 불 합치기
                    int sumM = 0;
                    int sumS = 0;
                    int size = fires.size();
                    int oddCount = 0;
                    for (int k = 0; k < size; k++) {
                        Fire removed = fires.remove(0);
                        sumM += removed.m;
                        sumS += removed.s;

                        if (removed.d % 2 != 0) {
                            oddCount++;
                        }
                    }
                    // 방향 정하기
                    int dir = 1;
                    if (oddCount == 0 || oddCount == size) {
                        dir = 0;
                    }

                    // 소멸
                    if (sumM / 5 == 0) {
                        continue;
                    }

                    // 불 나누기
                    for (int i = 0; i < 4; i++) {
                        fires.add(new Fire(sumM / 5, sumS / size, dir));
                        dir += 2;
                    }
                }
            }


            // 맵 초기화
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    map[i][j] = new LinkedList<>();
                    int size = nextMap[i][j].size();
                    for (int k = 0; k < size; k++) {
                        map[i][j].add(nextMap[i][j].remove(0));
                    }
                }
            }
        }

        // 결과
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < map[i][j].size(); k++) {
                    result += map[i][j].get(k).m;
                }
            }
        }
        System.out.println(result);

    }
}
