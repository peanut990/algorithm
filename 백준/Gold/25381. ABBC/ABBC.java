import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] input = sc.nextLine().toCharArray();
        int result = 0;

        // B, C 제거 O(n)
        int bp = 0, cp = 1;
        while (cp < input.length) {
            if (input[cp] != 'C') {
                cp++;
                continue;
            }

            if (bp + 1 < cp && input[bp] != 'B') {
                bp++;
                continue;
            }

            if (input[bp] == 'B' && input[cp] == 'C') {
                input[bp++] = 'X';
                input[cp++] = 'X';
                result++;
                continue;
            }

            cp++;
        }

        // A,B 제거 O(n)
        int ap = 0;
        bp = 1;
        while (bp < input.length) {
            if (input[bp] != 'B') {
                bp++;
                continue;
            }

            if (ap + 1 < bp && input[ap] != 'A') {
                ap++;
                continue;
            }

            if (input[ap] == 'A' && input[bp] == 'B') {
                input[ap++] = 'X';
                input[bp++] = 'X';
                result++;
                continue;
            }

            bp++;
        }

        System.out.println(result);

    }
}
