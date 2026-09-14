// 8:10~
import java.util.*;
import java.io.*;

public class Main {
    static int N;

    static int[][] map;
    static int[][] infos;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        infos = new int[N*N+1][4];

        for(int n = 0;n<N*N;n++){
            st = new StringTokenizer(br.readLine());
            int n0 = Integer.parseInt(st.nextToken());
            int n1 = Integer.parseInt(st.nextToken());
            int n2 = Integer.parseInt(st.nextToken());
            int n3 = Integer.parseInt(st.nextToken());
            int n4 = Integer.parseInt(st.nextToken());

            infos[n0] = new int[]{n1,n2,n3,n4};

            int maxLike = -1;
            int maxEmpty = -1;
            int y = 0;
            int x = 0;

            for(int i = 0;i<N;i++){
                for(int j = 0;j<N;j++){
                    if(map[i][j] > 0 ) continue;

                    int[] res = getLikeAndEmpty(i,j, n0);
                    int like = res[0];
                    int empty = res[1];

                    if(like > maxLike){
                        maxLike = like;
                        maxEmpty = empty;
                        y = i;
                        x = j;
                    }else if(like == maxLike && empty > maxEmpty){
                        maxLike = like;
                        maxEmpty = empty;
                        y = i;
                        x = j;
                    }
                }
            }

            map[y][x] = n0;
        }

        // 집계
        int totalScore = 0;
        int[] scores = new int[]{0,1,10,100,1000};
        for(int i =0;i<N;i++){
            for(int j =0;j<N;j++){
                int[] res = getLikeAndEmpty(i,j,map[i][j]);
                totalScore += scores[res[0]];
            }
        }
        System.out.println(totalScore);
                    
    }

    public static int[] getLikeAndEmpty(int y, int x, int num){
        int like = 0;
        int empty = 0;

        for(int d = 0;d<dirY.length;d++){
            int nextY = y + dirY[d];
            int nextX = x + dirX[d];

            if(!inRange(nextY, nextX)) continue;

            if(map[nextY][nextX] == 0) {
                empty++;
                continue;
            }

            if(isLike(num, map[nextY][nextX])) {
                like++;
            }
        }

        return new int[]{like,empty};
    }

    public static boolean isLike(int num, int friend){
        int[] likes = infos[num];
        for(int l : likes){
            if( l == friend){
                return true;
            }
        }
        return false;
    }

    public static boolean inRange(int y, int x){
        return y >=0 && y < N && x>=0 && x <N;
    }

    public static int[] dirY = {-1,0,0,1}; // 상,좌,우,하
    public static int[] dirX = {0,-1,1,0};
}

/*
N: 격자 크기
List<int[]> candidates;
int[][] map;

한명 들어갈때마다:
좋아하는 친구 + 비어있는칸 탐색: N*N * 4 * 4

-> N*N * (N*N * 4*4)

400 * 400 * 16
=160000*16
=2560000

(a,b)->{
    int[] resA = ...;
    int likeCountA = resA[0];
    int emptyCountA = resA[1];

    int[] resB = ...;
    int likeCountB = resB[0];
    int emptyCountB = resB[1];

    if(likeCountA != likeCountB) return // 내림차순
    if(emtyCountA != emptyCountB) return // 내림차순
    if() // 행 내림차순
    return // 열 내림차순

}

candidates.
*/