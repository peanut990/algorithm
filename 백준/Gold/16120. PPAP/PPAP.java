import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        Stack<Character> stack = new Stack();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                if (stack.size() < 2 || stack.peek() == 'A' || i + 1 >= s.length() || s.charAt(i + 1) == 'A') {
                    System.out.println("NP");
                    return;
                }

                stack.pop();
                stack.pop();
                stack.push('P');
                i++;
            } else {
                stack.push(s.charAt(i));
            }
        }
        if (stack.size() > 1) {
            System.out.println("NP");
        } else {
            System.out.println("PPAP");
        }
    }
}
