import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        sc.nextLine();
        char[] input = sc.nextLine().toCharArray();
        int lt = 0, rt = 0;
        int result = 0;

        while (rt < M) {
            if (input[rt] == 'I') {
                rt++;
                int cur = 0;
                while (rt < M && cur < N) {
                    if (input[rt] != 'O') break;

                    rt++;

                    if (rt >= M || input[rt] != 'I') break;

                    rt++;
                    cur++;

                    if (cur == N) {
                        result++;

                        lt += 2;
                        cur--;
                    }
                }
            } else {
                lt++;
                rt++;
            }
        }
        System.out.println(result);
    }
}
