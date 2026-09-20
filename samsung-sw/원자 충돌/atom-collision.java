import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//3:26 ~ 5:00 6:07~
public class Main {
    static class Atom {
        int y;
        int x;
        int m;
        int v;
        int d;

        Atom(int y, int x, int m, int v, int d) {
            this.y = y;
            this.x = x;
            this.m = m;
            this.v = v;
            this.d = d;
        }
    }

    static int N;
    static int M;
    static int K;

    static List<Atom> atoms;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        atoms = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()) - 1;
            int x = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            atoms.add(new Atom(y, x, m, v, d));
        }

        // 로직 시작
        for (int k = 0; k < K; k++) {
            // 1. 원자 이동
            for (Atom a : atoms) {
                moveAtom(a);
            }

            // 2. 합성
            atoms = collision();
        }

        int res = 0;
        for(Atom a : atoms){
            res += a.m;
        }
        System.out.println(res);
    }

    
    public static List<Atom> collision() {
        List<Atom> newAtoms = new ArrayList<>();

        List<Atom>[][] map = new List[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new ArrayList<>();
            }
        }
        for (Atom a : atoms) {
            map[a.y][a.x].add(a);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                List<Atom> sameLocAtoms = map[i][j];

                if (sameLocAtoms.size() >= 2) { // 합성
                    List<Atom> divided = sumAndDivide(i, j, sameLocAtoms);

                    for (Atom a : divided) {
                        newAtoms.add(a);
                    }
                } else if (sameLocAtoms.size() == 1) {
                    newAtoms.add(sameLocAtoms.get(0));
                }
            }
        }
        return newAtoms;
    }

    public static final int DIR_VER_TYPE = 0;
    public static final int DIR_NOT_VER_TYPE = 1;

    public static List<Atom> sumAndDivide(int y, int x, List<Atom> sameLocAtoms) {
        List<Atom> diveded = new ArrayList<>();

        // 합성
        int totalM = 0;
        int totalV = 0;
        int totalCount = sameLocAtoms.size();
        int dirType = getDirType(sameLocAtoms.get(0).d);
        boolean isVerticalDir = true;

        for (Atom a : sameLocAtoms) {
            if (dirType != getDirType(a.d)) {
                isVerticalDir = false;
            }
            totalM += a.m;
            totalV += a.v;
        }

        // 나누기
        int dividedM = totalM / 5;
        if (dividedM == 0) return diveded; // 소멸

        int dividedV = totalV / totalCount;
        int[] dividedAtomDirs = getDivededAtomDirs(isVerticalDir);

        for (int i = 0; i < dividedAtomDirs.length; i++) {
            int dir = dividedAtomDirs[i];
            diveded.add(new Atom(y, x, dividedM, dividedV, dir));
        }

        return diveded;
    }

    public static int[] getDivededAtomDirs(boolean isVerticalDir) {
        if (isVerticalDir) {
            return new int[]{0, 2, 4, 6};
        }
        return new int[]{1, 3, 5, 7};
    }

    public static int getDirType(int d) {
        if (d == 0 || d == 2 || d == 4 || d == 6) {
            return DIR_VER_TYPE;
        } else {
            return DIR_NOT_VER_TYPE;
        }
    }

    public static int[] dirY = {-1, -1, 0, 1, 1, 1, 0, -1};//상, 우상,우, 우하, 하, 좌하,좌,좌상
    public static int[] dirX = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void moveAtom(Atom a) {
        int nextY = (a.y + (a.v * dirY[a.d]) % N + N) % N;
        int nextX = (a.x + (a.v * dirX[a.d]) % N + N) % N;

        a.y = nextY;
        a.x = nextX;
    }

    public static void printAtoms(List<Atom> atoms) {
        for (Atom a : atoms) {
            System.out.printf("(%d,%d), m: %d, v: %d, d: %d \n", a.y, a.x, a.m, a.v, a.d);
        }
    }
}
