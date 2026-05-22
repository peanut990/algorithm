import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        int n = numbers.length;
        Stack<Integer> stack = new Stack<>();
        int[] result = new int[numbers.length];
        
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty()
                    && stack.peek() <= numbers[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                result[i] = -1;
            } else {
                result[i] = stack.peek();
            }

            stack.push(numbers[i]);
        }
        
        return result;
    }
}