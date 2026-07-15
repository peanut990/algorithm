import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        Arrays.sort(targets, Comparator.comparingInt(target->target[0]));
        
        int answer = 1;
        int minEnd = targets[0][1];

        for (int i = 1; i < targets.length; i++) {
            int start = targets[i][0];
            int end = targets[i][1];

            if (start < minEnd) {
                minEnd = Math.min(minEnd, end);
            } else {
                answer++;
                minEnd = end;
            }

        }
        return answer;
    }
}
