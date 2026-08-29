import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 3:48 ~
public class Main {
    public static class Group {
        int num;
        List<int[]> creatures = new ArrayList<>();

        Group(int num) {
            this.num = num;
        }

        public int[] getMinLoc() {
            int minY = N;
            int minX = N;

            for (int[] loc : creatures) {
                minY = Math.min(minY, loc[0]);
                minX = Math.min(minX, loc[1]);
            }
            return new int[]{minY, minX};
        }
    }

    static int N; // 맵 사이즈
    static int Q; // 미생물 수
    static int[][] map;
    static int result = 0;

    // 상하좌우
    public static int[] dirY = {-1, 1, 0, 0};
    public static int[] dirX = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        map = new int[N][N];

        // 로직 시작
        for (int q = 0; q < Q; q++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            // 1. 미생물 투입
            inputCreature(y1, x1, y2, x2, q + 1);
            // 1-1. 그룹 형성 후 리스트에 추가
            List<Group> groupList = makeGroupList();

            // 2. 배양 용기 이동
            map = moveGroups(groupList);

            // 3. 실험 결과 기록
            result = 0;
            calResult(groupList);

            System.out.println(result);
        }
    }

    public static void calResult(List<Group> groupList) {
        Group[] groups = new Group[Q + 1];
        for (Group g : groupList) {
            groups[g.num] = g;
        }

        for (Group g : groupList) {
            HashSet<Integer> set = new HashSet<>();
            for (int[] loc : g.creatures) {
                for (int d = 0; d < dirY.length; d++) {
                    int nextY = loc[0] + dirY[d];
                    int nextX = loc[1] + dirX[d];

                    if (!inRange(nextY, nextX) || map[nextY][nextX] == 0 || map[nextY][nextX] == g.num) continue;

                    if (map[nextY][nextX] > g.num) {
                        set.add(map[nextY][nextX]);
                    }
                }
            }

            for (int n : set) {
                result += g.creatures.size() * groups[n].creatures.size();
            }
        }
    }

    public static int[][] moveGroups(List<Group> groupList) {
        int[][] newMap = new int[N][N];

        // 역순 정렬
        Collections.sort(groupList, (a, b) -> {
            if (a.creatures.size() != b.creatures.size()) return a.creatures.size() - b.creatures.size();
            return b.num - a.num;
        });

        // 뒤에서 부터 탐색하면서 옮기기
        for (int i = groupList.size() - 1; i >= 0; i--) {
            Group g = groupList.get(i);

            int[] moveStartLoc = getMoveStartLoc(g, newMap);
            if (moveStartLoc == null) {
                // 옮기기 불가 -> 그룹 제거
                groupList.remove(i);
            } else {
                moveToNewMap(g, moveStartLoc, newMap);
            }
        }

        return newMap;
    }

    public static void moveToNewMap(Group g, int[] moveStartLoc, int[][] newMap) {
        int[] minLoc = g.getMinLoc();

        for (int[] loc : g.creatures) {
            int moveY = loc[0] - minLoc[0] + moveStartLoc[0];
            int moveX = loc[1] - minLoc[1] + moveStartLoc[1];

            // 좌표 이동
            loc[0] = moveY;
            loc[1] = moveX;

            // 옮기기
            newMap[loc[0]][loc[1]] = g.num;
        }
    }

    public static int[] getMoveStartLoc(Group g, int[][] newMap) {
        int[] minLoc = g.getMinLoc();

        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                boolean found = true;

                for (int[] loc : g.creatures) {
                    int moveY = loc[0] - minLoc[0] + y;
                    int moveX = loc[1] - minLoc[1] + x;

                    if (!inRange(moveY, moveX) || newMap[moveY][moveX] > 0) {
                        found = false;
                        break;
                    }
                }

                if (found) {
                    return new int[]{y, x};
                }
            }
        }

        return null;
    }

    public static List<Group> makeGroupList() {
        Group[] groups = new Group[Q + 1];
        Arrays.fill(groups, null);

        boolean[][] visited = new boolean[N][N];
        boolean[] checked = new boolean[Q + 1];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (visited[i][j]) continue;
                if (map[i][j] == 0) continue;

                int curNum = map[i][j];
                Group g = makeGroup(i, j, visited); // BFS

                if (checked[curNum]) { // 그룹 리스트에서 제거
                    groups[curNum] = null;
                } else { // 그룹 리스트에 추가
                    groups[curNum] = g;
                    checked[curNum] = true;
                }
            }
        }

        List<Group> groupList = new ArrayList<>();
        for (Group g : groups) {
            if (g == null) continue;
            groupList.add(g);
        }

        return groupList;
    }

    public static Group makeGroup(int y, int x, boolean[][] visited) {
        int groupNum = map[y][x];
        Group g = new Group(groupNum);

        Queue<int[]> q = new ArrayDeque<>();

        g.creatures.add(new int[]{y, x});
        q.offer(new int[]{y, x});
        visited[y][x] = true;

        while (!q.isEmpty()) {
            int[] poll = q.poll();

            for (int d = 0; d < dirY.length; d++) {
                int nextY = poll[0] + dirY[d];
                int nextX = poll[1] + dirX[d];

                // 범위안, 미방문, 조건: 같은 번호
                if (!inRange(nextY, nextX) || visited[nextY][nextX]) continue;

                if (map[nextY][nextX] == groupNum) {
                    g.creatures.add(new int[]{nextY, nextX});
                    q.offer(new int[]{nextY, nextX});
                    visited[nextY][nextX] = true;
                }
            }
        }
        return g;
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void printMap(int[][] map) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void inputCreature(int r1, int c1, int r2, int c2, int num) {
        for (int y = r1; y < r2; y++) {
            for (int x = c1; x < c2; x++) {
                map[y][x] = num;
            }
        }
    }
}

/*
Group{
 int num;
 List<int[]> locs;

}

1. 미생물 투입
- 맵에 입력
    1-1. 그룹 형성 후 리스트에 추가
    // 2번 이상 발견
    if(checked[num]) -> 그룹 리스트에 있으면 제거 (이미 제거되서 없을 수도 있음)
    List<Group> groupList;
    new Group
    checked[num] = true; //발견된 그룹 체크
    
    
2. 배양 용기 이동
- groupList 역순 정렬 (크기 오름차순, num 내림차순)
- 뒤에서 부터 탐색하면서 옮기기
- for( x-> N)
    for( y->N)
        새 용기에 투입가능한지 체크
        -> 가능: 투입후 return; (함수 종료)
        -> 불가능: break; (다음 x좌표로)
  여기까지오면 이동 불가 -> groupList에서 삭제

3. 실험 결과 기록
- for( Group g : groupList)
    - group loc 돌면서 Set에 번호 추가

8 2
2 2 5 6
2 3 5 6
*/