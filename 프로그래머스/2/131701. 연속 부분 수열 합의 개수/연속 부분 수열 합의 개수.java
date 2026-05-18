import java.util.*;

class Solution {
    public int solution(int[] elements) {
        HashSet<Integer> set = new HashSet<>();
        for(int start = 0;start < elements.length;start++){
            int sum = 0;
            for(int i = 0;i< elements.length;i++){
                int end = ( start + i) % elements.length;
                sum += elements[end];
                
                set.add(sum);
            }
        }
        return set.size();
    }
}
