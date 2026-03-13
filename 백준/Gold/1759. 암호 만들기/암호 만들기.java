import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int L;
    static int C;
    static List<Character> input = new ArrayList<>();
    static int countMo = 0; // 모음 카운트
    static int countJa = 0; // 자음 카운트
    static char[] resultAry;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        L = sc.nextInt();
        C = sc.nextInt();
        resultAry = new char[L];

        for (int i = 0; i < C; i++) {
            input.add(sc.next().charAt(0));
        }

        Collections.sort(input);

        DFS(0, 0);
    }

    public static void DFS(int count, int start) {
        if (count == L) {
            if (countMo >= 1 && countJa >= 2) {
                System.out.println(resultAry);
            }
            return;
        }

        for (int i = start; i < input.size(); i++) {
            char cur = resultAry[count];
            char next = input.get(i);

            if (Character.isAlphabetic(cur)) {
                if (cur == 'a' || cur == 'e' || cur == 'i' || cur == 'o' || cur == 'u') {
                    countMo--;
                } else {
                    countJa--;
                }
            }

            if (next == 'a' || next == 'e' || next == 'i' || next == 'o' || next == 'u') {
                countMo++;
            } else {
                countJa++;
            }
            resultAry[count] = next;
            DFS(count + 1, i + 1);
        }
    }
}

