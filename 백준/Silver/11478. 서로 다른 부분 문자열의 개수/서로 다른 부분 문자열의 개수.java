import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine(); // ~1000

        HashSet<String> set = new HashSet();

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j <= s.length() - i; j++) {
                set.add(s.substring(j, j + i));
            }
        }

        System.out.println(set.size());
    }
}