// 4:11~
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static class Delivery {
        int num;
        int minCol;
        int minRow;
        int width;
        int height;

        Delivery(int k, int h, int w, int c) {
            num = k;
            height = h;
            width = w;
            minCol = c;
            minRow = 0;
        }
    }

    public static int N; // 맵 크기
    public static int M; // 택배 개수
    public static int[][] map;
    public static List<Delivery> deliveryList;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        deliveryList = new ArrayList<>();

        // 1. 택배 투입
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken()) - 1;

            Delivery d = new Delivery(k, h, w, c);
            deliveryList.add(d);

            // 내리기
            moveDown(d);
        }

        while (!deliveryList.isEmpty()) {
            // 2. 택배 하차(좌측)
            int removedNum = moveLeftOrRight(LEFT);
            System.out.println(removedNum);
            moveDownWholeDelivery();

            if(deliveryList.isEmpty()) break;

            // 3. 택배 하차(우측)
            removedNum = moveLeftOrRight(RIGHT);
            System.out.println(removedNum);
            moveDownWholeDelivery();
        }

    }

    public static void moveDownWholeDelivery() {
        // 택배 정렬
        Collections.sort(deliveryList, (a, b) -> {
            return (b.minRow + b.height - 1) - (a.minRow + a.height - 1);
        });

        for (Delivery d : deliveryList) {
            moveDown(d);
        }
    }

    public static int moveLeftOrRight(int dir) {
        // 택배 정렬
        Collections.sort(deliveryList, (a, b) -> {
            return a.num - b.num;
        });

        for (int i = 0; i < deliveryList.size(); i++) {
            Delivery d = deliveryList.get(i);

            int moveX = dir == LEFT ? getLeftMoveX(d) : getRightMoveX(d);
            int targetX = dir == LEFT ? 0 : N - 1;
            if (moveX == targetX) { // 빼기 가능
                // 택베 제거
                // 맵에서 제거, 리스트에서 제거
                removeDelivery(d, i);
                return d.num;
            }
        }
        return -1;
    }

    public static void removeDelivery(Delivery d, int idx) {
        // 맵에서 제거
        for (int y = d.minRow; y < d.minRow + d.height; y++) {
            for (int x = d.minCol; x < d.minCol + d.width; x++) {
                map[y][x] = 0;
            }
        }

        // 리스트에서 제거
        deliveryList.remove(idx);
    }

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public static int getRightMoveX(Delivery d) {
        int nextX = d.minCol + d.width;
        while (true) {
            for (int y = d.minRow; y < d.minRow + d.height; y++) {
                if (!inRange(y, nextX) || map[y][nextX] > 0) // 발견
                    return nextX - 1;
            }
            nextX += 1;
        }
    }

    public static int getLeftMoveX(Delivery d) {
        int nextX = d.minCol - 1;
        while (true) {
            for (int y = d.minRow; y < d.minRow + d.height; y++) {
                if (!inRange(y, nextX) || map[y][nextX] > 0) // 발견
                    return nextX + 1;
            }
            nextX -= 1;
        }
    }

    public static void moveDown(Delivery d) {
        int moveY = getMoveY(d);

        move(d, moveY, d.minCol);
    }

    public static int getMoveY(Delivery d) {
        int nextY = d.minRow + d.height;

        while (true) {
            for (int x = d.minCol; x < d.minCol + d.width; x++) // - 넓이: minCol + w
                if (!inRange(nextY, x) || map[nextY][x] > 0) // 발견
                    return nextY - d.height;
            nextY++;
        }
    }

    public static boolean inRange(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    public static void move(Delivery d, int nextY, int nextX) {
        // 현재 상태 지우기
        for (int y = d.minRow; y < d.minRow + d.height; y++) {
            for (int x = d.minCol; x < d.minCol + d.width; x++) {
                map[y][x] = 0;
            }
        }
        // d 상태 반영
        d.minRow = nextY;
        d.minCol = nextX;

        // map에 변경된 상태 반영
        for (int y = d.minRow; y < d.minRow + d.height; y++) {
            for (int x = d.minCol; x < d.minCol + d.width; x++) {
                map[y][x] = d.num;
            }
        }
    }
}
