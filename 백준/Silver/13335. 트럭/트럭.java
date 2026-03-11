import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int w = sc.nextInt();
        int L = sc.nextInt();

        Queue<Integer> bridge = new LinkedList<>();
        for (int i = 0; i < w; i++) {
            bridge.add(0);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            q.add(sc.nextInt());
        }

        int curWeight = 0;
        int outCount = 0;
        int time = 0;

        while (outCount < n) {
            int out = bridge.poll();
            curWeight -= out;
            if (out > 0) {
                outCount++;
            }
            
            if (!q.isEmpty() && curWeight + q.peek() <= L) {
                int in = q.poll();
                curWeight += in;
                bridge.add(in);
            } else {
                bridge.add(0);
            }
            time++;
        }
        
        System.out.println(time);
    }
}
