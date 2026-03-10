import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int P = sc.nextInt();
        Stack<Integer>[] ary = new Stack[7];
        int result = 0;
        for (int i = 0; i < ary.length; i++) {
            ary[i] = new Stack<>();
        }

        for (int i = 0; i < N; i++) {
            int lineNum = sc.nextInt();
            int platNum = sc.nextInt();

            while (!ary[lineNum].isEmpty() && ary[lineNum].peek() > platNum) {
                ary[lineNum].pop();
                result++;
            }

            if (!ary[lineNum].isEmpty() && ary[lineNum].peek() == platNum) {
                continue;
            }

            ary[lineNum].push(platNum);
            result++;
        }
        System.out.println(result);
    }
}
