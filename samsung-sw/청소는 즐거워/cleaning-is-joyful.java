 // 4:19~
import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[][] map;

    static int y;
    static int x;
    static int dir = 0;
    static int prevDir = -1;

    static int moveCount;
    static int len = 1;
    static int twice = 0;

    
    public static int[] dirY = {0,1,1,1,0,-1,-1,-1}; // 좌, 좌하, 하, 우하, 우, 우상, 상, 좌상
    public static int[] dirX = {-1,-1,0,1,1,1,0,-1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for(int i =0;i<N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0;j<N;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        y = N/2;
        x = N/2;

        // 로직시작
       while(y != 0 || x != 0){
            // 이동
            move();
            
            // 먼지 이동
            cleaning();
        }
        System.out.println(outDust);
    }
    /*
          6
        7 5 4
    8 a
        0 1 3
          2
        
    */
   
    public static int outDust = 0;

    public static void cleaning(){
        int[] percentages = {10, 7, 2, 1, 1,7,2,10,5};

        int totalDust = map[y][x];
        int movedDust = 0;

        map[y][x] = 0;

        //System.out.println("prevDir: " + prevDir);
        for(int i = 0; i < percentages.length;i++){
            int dir = prevDir;
            if(i == 0){
                dir = prevDir + 1;
            } else if(i == 1 || i == 2){
                dir = prevDir + 2;
            } else if(i == 3){
                dir = prevDir + 3;
            } else if(i == 4){
                dir = prevDir + 5;
            } else if( i == 5 || i == 6){
                dir = prevDir + 6;
            }else if( i == 7){
                dir = prevDir + 7;
            }

            dir = dir % dirY.length;
            //System.out.println("dir: " + dir);
            int dist = 1;
            if(i == 2 || i == 6 || i == 8){
                dist = 2;
            }

            int nextY = y + dirY[dir] * dist;
            int nextX = x + dirX[dir] * dist;

            int curDust = (totalDust * percentages[i]) / 100; 
            movedDust += curDust;

            if(!inRange(nextY,nextX)){
                outDust += curDust;
                continue;
            }

            map[nextY][nextX] += curDust;
        }

        // a 칸 
        int nextY = y + dirY[prevDir];
        int nextX = x + dirX[prevDir];

        int a = totalDust - movedDust;

        movedDust += a;

        if(!inRange(nextY,nextX)){
            outDust += a;
            return;
        }
        map[nextY][nextX] += a;
    }

    public static boolean inRange(int y, int x){
        return y >= 0 && y < N && x >=0 && x < N;
    }

    public static void move(){
        y += dirY[dir];
        x += dirX[dir];

        moveCount++;
        prevDir = dir;

        if(moveCount == len){
            moveCount = 0;
            twice++;

            dir = (dir+2)% dirY.length;

            if(twice == 2){
                twice = 0;
                len++;
            }
        }
        
    }
}

