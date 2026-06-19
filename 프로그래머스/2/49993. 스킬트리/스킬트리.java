import java.util.*;
class Solution {
    public int solution(String skill, String[] skill_trees) {
        HashMap<Character, Integer> map = new HashMap<>();
        int num = 0;
        for(int i =0;i<skill.length();i++){
            map.put(skill.charAt(i), num++);
        }

        int count = 0;
        for(int i = 0;i< skill_trees.length;i++){
            String s = skill_trees[i];
            int idx = 0;
            boolean found = true;
            for(int j = 0;j<s.length();j++){
                char c = s.charAt(j);
                if(!map.containsKey(c)) continue;
                if(map.get(c) == idx){
                    idx++;
                }else{
                    found = false;
                }
            }
            if(found) count++;
        }
        
        return count;
    }
}