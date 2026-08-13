// 2:30
import java.util.*;
import java.io.*;

public class Main {
    public static class Knight{
        int num;
        int maxHp;
        int hp;
        boolean alive = true;
        List<int[] > locs = new ArrayList<>();

        Knight(int num, int r, int c, int h, int w, int hp){
            this.num = num;
            this.hp = hp;
            this.maxHp = hp;

            for(int y = r; y < r + h; y++){
                for(int x = c; x< c + w; x++){
                    locs.add(new int[]{y,x});
                }
            }
        }
    }
    static int L; // 체스판 크기
    static int N; // 기사 수
    static int Q; // 명령 수

    static int[][] map; // 0: 빈칸, 1: 함정, 2: 벽
    static int[][] knightMap;

    static Knight[] knights;
    
    static int[] dirY = {-1,0,1,0}; //상, 우, 하, 좌
    static int[] dirX = {0,1,0,-1};

    static final int EMPTY = 0;
    static final int DANGER = 1;
    static final int WALL = 2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        map = new int[L][L]; // 0-index
        knightMap = new int[L][L]; 
        knights = new Knight[N+1];
        
        for(int i = 0;i<L;i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0;j<L;j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0;i<N;i++){
            st = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            Knight knight = new Knight(i+1,r,c, h, w, k);
            knights[i+1] = knight;
            putKnight(knight);
        }

        for(int q = 0;q<Q;q++){ // q값 수정 ##
             //1. 기사 이동
            st = new StringTokenizer(br.readLine());
            Knight orderedKnight = knights[Integer.parseInt(st.nextToken())];
            int dir = Integer.parseInt(st.nextToken());

            if(!orderedKnight.alive) continue; 

            boolean[] movingKnightsNum = getMovingKnightNums(orderedKnight, dir);
            if(movingKnightsNum == null) {
                continue;
            }; // 벽 or 범위 밖이라 이동 취소

            List<Knight> movingKnights = new ArrayList<>();
            for(int i = 1;i< movingKnightsNum.length;i++){
                if(movingKnightsNum[i]){
                    movingKnights.add(knights[i]);
                }
            }

            moveKnights(movingKnights, dir);

            // 2. 대결 대미지
            applyAttack(movingKnights, orderedKnight);
        }

        int answer = 0;
        for(int i = 1;i<knights.length;i++){
            Knight k = knights[i];
            if(!k.alive) continue;

            answer += k.maxHp - k.hp;
        }

        System.out.println(answer);
    }

    public static void printState(Knight[] knights){
        for(int i = 1;i<knights.length;i++){
            Knight k = knights[i];
            System.out.print(k.hp + " ");
        }
    }

    public static void applyAttack(List<Knight> movingKnights, Knight orderedKnight){
        for(Knight k: movingKnights){
            if(k.num == orderedKnight.num) continue; // 명령 받은 기사 제외

            for(int[] loc: k.locs){
                if(map[loc[0]][loc[1]] == DANGER){
                    k.hp--;
                }
            }

            if(k.hp <= 0){
                k.alive = false;
                removeKnight(k);
            }
        }
    }

    public static void moveKnights(List<Knight> movingKnights, int dir){
        // 현재 위치 제거
        for(Knight k : movingKnights){
            removeKnight(k);
        }

        // 이동할 좌표 반영
        for(Knight k : movingKnights){
            for(int[] loc : k.locs){
                loc[0] += dirY[dir];
                loc[1] += dirX[dir];
            }
        }

        // 맵 갱신
        for(Knight k : movingKnights){
            putKnight(k);
        }
    }

    public static boolean[] getMovingKnightNums(Knight orderedKnight, int dir){
        Queue<Knight> q = new LinkedList<>();
        boolean[] checkedNums = new boolean[N+1];
        q.offer(orderedKnight);
        checkedNums[orderedKnight.num] = true;

        while(!q.isEmpty()){
            Knight poll = q.poll();

            for(int[] loc : poll.locs){
                int nextY = loc[0] + dirY[dir];
                int nextX = loc[1] + dirX[dir];

                if(!inRange(nextY, nextX)) return null;

                if(map[nextY][nextX] == WALL) return null;

                int knightNum = knightMap[nextY][nextX];
                if(knightNum == EMPTY) continue;

                if(checkedNums[knightNum]) continue; // 자기 자신 or 다른 기사인데 중복 

                q.offer(knights[knightNum]);
                checkedNums[knightNum] = true;
            }
        }

        return checkedNums;
    }

    public static boolean inRange(int y, int x){
        return y >=0 && y < L && x >=0 && x <L;
    }

    public static void putKnight(Knight k){
        for(int[] loc : k.locs){
            knightMap[loc[0]][loc[1]] = k.num;
        }
    }

    public static void removeKnight(Knight k){
        for(int[] loc : k.locs){
            knightMap[loc[0]][loc[1]] = 0;
        }
    }

    public static void printMap(int[][] map){
        for(int i = 0;i< map.length;i++){
            for(int j = 0;j<map[0].length;j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

/*
1. 기사 이동
Kinght
    List<int[]> loc 저장

-> 명령 받은 기사 살아있는지 체크

Queue<Integer> q
boolen[] checkedNum
q.offer(자신)
checkedNum(자신)

q.poll
for(int[] l : loc)
    nextLoc = l + 다음 방향
    if(벽이거나 맵 밖) -> 기사 이동 취소
    if(checkedNum[인접한 기사 번호]) 제외 (자기 자신 포함됨)

    q.offer(인접한 기사 번호)
    checkedNum[인접한 기사 번호]   


return checkedNum
*/