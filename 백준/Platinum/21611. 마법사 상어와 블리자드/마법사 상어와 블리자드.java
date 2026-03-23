import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;

    static int[] dirY_Magic = {-1, 1, 0, 0}; //상,하,좌,우
    static int[] dirX_Magic = {0, 0, -1, 1};

    static int[] dirY_Move = {0, 1, 0, -1}; // 왼, 하, 우, 상
    static int[] dirX_Move = {-1, 0, 1, 0};

    static int initY;
    static int initX;

    static int[] explodedCount = new int[4];


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        int M = sc.nextInt();
        sc.nextLine();
        map = new int[N][N];
        initY = N / 2;
        initX = N / 2;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(sc.nextLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int m = 0; m < M; m++) {
            //시작
            int d = sc.nextInt() - 1;
            int s = sc.nextInt();

            //마법 시전
            doMagic(d, s);
            List<Integer> balls = checkBalls();
            setBalls(balls);

            //폭발
            balls = explode(balls);

            //구슬 변화
            changeBalls(balls);
        }

        // 결과 계산
        int result = 0;
        for (int i = 1; i < explodedCount.length; i++) {
            result += i * explodedCount[i];
        }
        System.out.println(result);
    }

    public static void doMagic(int d, int s) {
        for (int i = 1; i <= s; i++) {
            int ny = initY + dirY_Magic[d] * i;
            int nx = initX + dirX_Magic[d] * i;

            map[ny][nx] = 0;
        }
    }

    public static List<Integer> checkBalls() {
        //1,1,2,2,3,3,4,4 ...
        int curDist = 1;
        int dir = 0;
        int curY = initY;
        int curX = initX;

        List<Integer> balls = new ArrayList<>();
        while (true) {
            for (int step = 0; step < 2; step++) {
                for (int dist = 0; dist < curDist; dist++) {
                    int ny = curY + dirY_Move[dir];
                    int nx = curX + dirX_Move[dir];
                    if (ny == 0 && nx == 0) {
                        balls.add(map[ny][nx]);
                        return balls;
                    }

                    if (map[ny][nx] > 0) {
                        balls.add(map[ny][nx]);
                    }
                    curY = ny;
                    curX = nx;
                }
                //방향 전환
                dir = (dir + 1) % 4;
            }
            curDist++;
        }
    }

    public static void setBalls(List<Integer> balls) {
        //1,1,2,2,3,3,4,4 ...
        int curDist = 1;
        int dir = 0;
        int curY = initY;
        int curX = initX;
        int ballCount = 0;
        while (true) {
            for (int step = 0; step < 2; step++) {
                for (int dist = 0; dist < curDist; dist++) {
                    int ny = curY + dirY_Move[dir];
                    int nx = curX + dirX_Move[dir];
                    if (ny == 0 && nx == 0) {
                        if (ballCount < balls.size()) {
                            map[ny][nx] = balls.get(ballCount);
                        } else {
                            map[ny][nx] = 0;
                        }
                        return;
                    }

                    if (ballCount < balls.size()) {
                        map[ny][nx] = balls.get(ballCount++);
                    } else {
                        map[ny][nx] = 0;
                    }
                    curY = ny;
                    curX = nx;
                }
                //방향 전환
                dir = (dir + 1) % 4;
            }
            curDist++;
        }
    }

    public static List<Integer> explode(List<Integer> balls) {
        boolean found = true;

        while (found) {
            found = false;
            int lt = 0;
            int rt = 0;
            int count = 0;

            while (rt < balls.size()) {
                if (balls.get(lt) == balls.get(rt)) {
                    count++;
                    rt++;
                } else {
                    if (count >= 4) {
                        found = true;

                        //폭발
                        while (lt < rt) {
                            explodedCount[balls.get(lt)]++;

                            balls.set(lt, 0);
                            lt++;
                        }
                        count = 0;
                    } else {
                        lt = rt;
                        count = 0;
                    }
                }
            }

            //마지막
            if (count >= 4) {
                //폭발
                while (lt < rt) {
                    explodedCount[balls.get(lt)]++;

                    balls.set(lt, 0);
                    lt++;
                }
            }

            List<Integer> newBalls = new ArrayList();
            for (int i = 0; i < balls.size(); i++) {
                if (balls.get(i) > 0) {
                    newBalls.add(balls.get(i));
                }
            }

            setBalls(newBalls);
            balls = newBalls;
        }

        return balls;
    }

    public static void changeBalls(List<Integer> balls) {
        int lt = 0;
        int rt = 0;
        int count = 0;
        int ballNum = 0;
        List<Integer> newBalls = new ArrayList<>();
        while (rt < balls.size()) {
            if (balls.get(lt) == balls.get(rt)) {
                count++;
                ballNum = balls.get(rt);
                rt++;
            } else {
                newBalls.add(count);
                newBalls.add(ballNum);

                lt = rt;
                count = 0;
            }
        }
        // 마지막
        newBalls.add(count);
        newBalls.add(ballNum);

        setBalls(newBalls);
    }

}
