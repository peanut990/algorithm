//3:10~

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static class Group {
        int num;
        int size;
        int value;

        Group(int num, int size, int value) {
            this.num = num;
            this.size = size;
            this.value = value;
        }
    }

    public static int N;
    public static int[][] map;
    public static int totalScore = 0;

    public static void main(String[] args) throws Exception {
        init();
        //로직 시작
        for (int k = 0; k < 4; k++) {
            List<Group> groupList = new ArrayList<>();
            int[][] groupNumMap = new int[N][N];
            // 1.그룹 탐색
            initGroups(groupList, groupNumMap);

            // 2. 그룹 쌍 조화로움 구하기
            calGroupsArtistry(groupList, groupNumMap);

            // 3. 회전
            rotate();
        }
        System.out.println(totalScore);
    }

    public static void rotate() {
        int[][] rotatedMap = new int[N][N];
        // 십자가 회전 //(y,x) -> (N-1 - x,y)
        int cY = N / 2;
        int cX = N / 2;
        for (int x = 0; x < N; x++) {
            rotatedMap[N - 1 - x][cY] = map[cY][x];
        }
        for (int y = 0; y < N; y++) {
            rotatedMap[N - 1 - cX][y] = map[y][cX];
        }

        // 정사각형 회전 (y,x) -> (x, size-1 - y)
        int size = N / 2;
        int[][] sLoc = {
                {0, size + 1}, // 1사
                {0, 0}, // 2사
                {size + 1, 0}, //3사
                {size + 1, size + 1},
        };

        for (int d = 0; d < 4; d++) {
            int sY = sLoc[d][0];
            int sX = sLoc[d][1];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    rotatedMap[sY + j][sX + size - 1 - i] = map[sY + i][sX + j];
                }
            }
        }
        map = rotatedMap;
    }

    public static void calGroupsArtistry(List<Group> groupList, int[][] groupNumMap) {
        boolean[] checked = new boolean[groupList.size()];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (checked[groupNumMap[i][j]]) continue;

                checked[groupNumMap[i][j]] = true;
                int[] adjGroupCount = getAdjGroupCount(i, j, groupList.size(), groupNumMap);

                calScore(groupList.get(groupNumMap[i][j]), groupList, adjGroupCount);
            }
        }
    }

    public static void calScore(Group curGroup, List<Group> groupList, int[] adjGroupCount) {
        for (int adjGroupNum = curGroup.num + 1; adjGroupNum < adjGroupCount.length; adjGroupNum++) {
            if (adjGroupCount[adjGroupNum] == 0) continue;
            Group adjGroup = groupList.get(adjGroupNum);
            int curPairScore = (curGroup.size + adjGroup.size) * curGroup.value * adjGroup.value * adjGroupCount[adjGroupNum];
            totalScore += curPairScore;
        }

    }

    public static int[] getAdjGroupCount(int y, int x, int groupCount, int[][] groupNumMap) {
        int[] adjGroupCount = new int[groupCount];

        Queue<int[]> q = new LinkedList<>();
        boolean[][] checked = new boolean[N][N];

        q.offer(new int[]{y, x});
        checked[y][x] = true;
        int curGroupNum = groupNumMap[y][x];

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (checked[nextY][nextX]) continue;

                if (groupNumMap[nextY][nextX] > curGroupNum) {
                    adjGroupCount[groupNumMap[nextY][nextX]]++;
                    continue;
                }

                if (groupNumMap[nextY][nextX] == curGroupNum) {
                    checked[nextY][nextX] = true;
                    q.offer(new int[]{nextY, nextX});
                }
            }
        }
        return adjGroupCount;
    }

    public static void initGroups(List<Group> groupList, int[][] groupNumMap) {
        boolean[][] checked = new boolean[N][N];
        int groupNum = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (checked[i][j]) continue;
                int value = map[i][j];
                int size = searchGroup(i, j, groupNum, checked, groupNumMap);
                groupList.add(new Group(groupNum, size, value));

                groupNum++;
            }
        }
    }

    static int[] dirY = {-1, 1, 0, 0};// 상,하,좌,우
    static int[] dirX = {0, 0, -1, 1};

    public static int searchGroup(int y, int x, int groupNum, boolean[][] checked, int[][] groupNumMap) {
        int value = map[y][x];
        int size = 0;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{y, x});
        checked[y][x] = true;
        groupNumMap[y][x] = groupNum;
        size++;

        while (!q.isEmpty()) {
            int[] poll = q.poll();
            for (int d = 0; d < 4; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                if (!inRange(nextY, nextX)) continue;
                if (checked[nextY][nextX]) continue;
                if (value != map[nextY][nextX]) continue;

                q.offer(new int[]{nextY, nextX});
                checked[nextY][nextX] = true;
                groupNumMap[nextY][nextX] = groupNum;
                size++;
            }
        }
        return size;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }
}

/*
Group{
 int num;
 int size;
 int value;
}
# 그룹: 0 ~ M

# 1. 그룹 탐색
    -> List<Group>
    => 그룹 numMap

# 2. 그룹 쌍 조화로움 구하기
- 현재 그룹 num 보다 크고 M 이하 번호만 탐색
    for(i:0 ~ M)
        // BFS
        curGroupNum = i;
        groupCount = int[M+1]
        -> 맞다아 있는 변 계산
        nextGroupNum = numMap[nextY][nextX]
        if( nextGroupNum > curGroupNum && nextGroupNum  != curGroupNum)
            groupCount[nextGourNum]++;

        -> 조화로움 계산
        for(j:i+1 ~ M)
            if(groupCount[j] > 0)
            ...

# 3. 회전
   0     1     2     3     4
0 (0,0) (0,1) (0,2) (0,3) (0,4)
1 (1,0) (1,1) (1,2) (1,3) (1,4)
2 (2,0) (2,1) (2,2) (2,3) (2,4)
3 (3,0) (3,1) (3,2) (3,3) (3,4)
4 (4,0) (4,1) (4,2) (4,3) (4,4)

- 십자가 회전
(y,x) -> (N-1 - x,y)

- 정사각형 4개 회전
(y,x) -> (x, size-1 - y)
size = N/2
1사분면:
sy = 0
sx = size + 1

2사분면:
sy = 0
sx = 0

3사:
sy: size + 1
sx: 0

4사:
sy: size+1
sx: size+1

*/