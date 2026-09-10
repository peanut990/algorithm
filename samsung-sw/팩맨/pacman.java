 // 4:44~


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Monster {
        int dir;

        Monster(int dir) {
            this.dir = dir;
        }
    }

    static class Egg {
        int dir;

        Egg(int dir) {
            this.dir = dir;
        }
    }

    static class Dead {
        int remainTurn;

        Dead() {
            this.remainTurn = 2;
        }
    }

    static int pacY;
    static int pacX;

    static List<Monster>[][] monsterMap;
    static List<Egg>[][] eggMap;
    static List<Dead>[][] deadMap;

    static final int N = 4; // 사이즈
    static int M; // 몬스터 수
    static int T; // 턴수

    public static void main(String[] args) throws Exception {
        init();

        // 로직 시작
        for (int t = 0; t < T; t++) {
            // 1. 몬스터 복제 시도
            copyMonsters();

            // 2. 몬스터 이동
            monsterMap = moveMonsters();

            // 3. 팩맨 이동
            movePacMan();

            // 4. 몬스터 시체 소멸
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    List<Dead> curDead = deadMap[i][j];
                    for (int l = curDead.size() - 1; l >= 0; l--) {
                        Dead d = curDead.get(l);
                        if (d.remainTurn == 0) {
                            curDead.remove(l);
                        }
                    }
                }
            }

            // 5. 몬스터 복제 완성
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    List<Egg> curEgg = eggMap[i][j];
                    for (int l = curEgg.size() - 1; l >= 0; l--) {
                        Egg e = curEgg.get(l);

                        curEgg.remove(l);
                        monsterMap[i][j].add(new Monster(e.dir));
                    }
                }
            }

            // 6. 시체.remainTurn -1
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    List<Dead> curDead = deadMap[i][j];
                    for (int l = curDead.size() - 1; l >= 0; l--) {
                        Dead d = curDead.get(l);
                        d.remainTurn--;
                    }
                }
            }

        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result += monsterMap[i][j].size();
            }
        }
        System.out.println(result);
    }

    public static void movePacMan() {
        maxEatCount = -1;
        // 최대 경로(maxMoveRoot) 설정
        DFS(0);

        // 최대 경로로 이동
        for (int[] loc : maxMoveRoot) {
            int nextY = loc[0];
            int nextX = loc[1];
            int size = monsterMap[nextY][nextX].size();

            pacY = nextY;
            pacX = nextX;

            monsterMap[nextY][nextX] = new ArrayList<>();

            for (int s = 0; s < size; s++) {
                deadMap[nextY][nextX].add(new Dead());
            }
        }

    }

    public static int[] pacDirY = {-1, 0, 1, 0};// 상,좌,하,우
    public static int[] pacDirX = {0, -1, 0, 1};

    public static int[] rootDir = new int[3];

    public static int maxEatCount = 0;
    public static List<int[]> maxMoveRoot;

    public static void DFS(int lv) {
        if (lv == 3) {
            int[][] tmpSizeMap = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tmpSizeMap[i][j] = monsterMap[i][j].size();
                }
            }

            // 경로 완성
            int nextY = pacY;
            int nextX = pacX;

            List<int[]> moveRoot = new ArrayList<>();
            int eatCount = 0;

            for (int d : rootDir) {
                nextY += pacDirY[d];
                nextX += pacDirX[d];

                if (!inRange(nextY, nextX)) break;

                eatCount += tmpSizeMap[nextY][nextX];
                tmpSizeMap[nextY][nextX] = 0;

                moveRoot.add(new int[]{nextY, nextX});
            }

            if (moveRoot.size() == 3) {
                if (eatCount > maxEatCount) {
                    maxEatCount = eatCount;
                    maxMoveRoot = moveRoot;
                }
            }

            return;
        }

        for (int d = 0; d < pacDirY.length; d++) {
            rootDir[lv] = d;
            DFS(lv + 1);
        }
    }

    public static int[] dirY = {-1, -1, 0, 1, 1, 1, 0, -1};//상, 좌상, 좌, 좌하, 하, 우하, 우, 우상
    public static int[] dirX = {0, -1, -1, -1, 0, 1, 1, 1};

    public static List<Monster>[][] moveMonsters() {
        List<Monster>[][] nextMonsterMap = new ArrayList[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nextMonsterMap[i][j] = new ArrayList<>();
            }
        }

        // 이동
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (Monster m : monsterMap[i][j]) {
                    int[] nextLoc = getNextLoc(i, j, m);

                    nextMonsterMap[nextLoc[0]][nextLoc[1]].add(new Monster(nextLoc[2]));
                }
            }
        }

        return nextMonsterMap;
    }

    public static int[] getNextLoc(int y, int x, Monster m) {
        for (int i = 0; i < dirY.length; i++) { // 반시계
            int dir = (m.dir + i) % dirY.length;
            int nextY = y + dirY[dir];
            int nextX = x + dirX[dir];

            // (격자안,시체x, 팩맨x)
            if (!inRange(nextY, nextX) || deadMap[nextY][nextX].size() > 0 || isPacMan(nextY, nextX)) continue;

            return new int[]{nextY, nextX, dir};
        }

        // 이동 불가, 제자리
        return new int[]{y, x, m.dir};
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static boolean isPacMan(int y, int x) {
        return y == pacY && x == pacX;
    }

    public static void copyMonsters() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (Monster m : monsterMap[i][j]) {
                    eggMap[i][j].add(new Egg(m.dir));
                }
            }
        }
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        pacY = Integer.parseInt(st.nextToken()) - 1;
        pacX = Integer.parseInt(st.nextToken()) - 1;

        monsterMap = new ArrayList[N][N];
        eggMap = new ArrayList[N][N];
        deadMap = new ArrayList[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                monsterMap[i][j] = new ArrayList<>();
                eggMap[i][j] = new ArrayList<>();
                deadMap[i][j] = new ArrayList<>();
            }
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            monsterMap[r][c].add(new Monster(d));
        }
    }
}


/*
class Monster{
    int dir;
}

class Egg{
    int remainTurn;
    int dir;
}

class Dead{
    int remainTurn;
}

int pacY;
int pacX;

List<Monster>[][] monsterMap;
List<Egg>[][] eggMap;
List<Dead>[][] deadMap;

// 1. 몬스터 복제 시도
for monsterMap
    -> eggMap.add(new Egg)


// 2. 몬스터 이동
List<Monster>[][] nextMonsterMap;

for monsterMap
    -> if(격자안,시체x, 팩맨x)
        -> 이동
    -> 이동 불가면 제자리
    => nextMonsterMap[i][j].add(new monster());

// 3. 팩맨 이동
- DFS()
     -> 중복순열 -> List<int[]>  최대 먹이 경로 반환
- 최대 먹이경로로 이동
    -> 죽음 처리 (monsterMap[i][j] 비우기,  deadMap[i][j].add())

// 4. 몬스터 시체 소멸
for deadMap
    -> if(deadMap[i][j].get(i).remainTurn == 0) 제거

// 5. 몬스터 복제 완성
for eggMap
    -> if(egg.remainTurn == 0) eggMap에서 제거, monsterMap.add()

// 6. 시체.remainTurn -1
*/