import java.util.*;

class Solution {
    public int solution(int[][] scores) {
        List<Integer> li = new ArrayList<>();
        for(int i = 0;i < scores.length; i++){
            li.add(i);
        }
        
        Collections.sort(li, (a,b)->{
            if(scores[a][0] != scores[b][0]) return scores[b][0] - scores[a][0];
            
            return scores[a][1] - scores[b][1];
        });
        
        HashSet<Integer> removed = new HashSet<>();
        
        int prev = scores[li.get(0)][0];
        int max = scores[li.get(0)][1];
        for(int i = 1;i< li.size();i++){
            int num = li.get(i);
            int[] score = scores[num];
            
            if(max > score[1]){
                removed.add(num);
            }
            
            max = Math.max(max, score[1]);
        }
        
        if(removed.contains(0)) return -1;
        
        Collections.sort(li, (a,b)->{
            return (scores[b][0] + scores[b][1]) - (scores[a][0] + scores[a][1]);
        });
        
        int maxSum = scores[li.get(0)][0] + scores[li.get(0)][1];
        int rank = 1;
        int count = 1;
        for(int i = 0;i< li.size();i++){
            int curNum = li.get(i);
            int curSum = scores[curNum][0] + scores[curNum][1];
            if(removed.contains(curNum)) {
                continue;
            }
            
            if(maxSum != curSum){
                rank = count;
                maxSum = curSum;
            }
            
            count++;
            if(curNum == 0)
                break;
        }
        
        return rank;
    }
}

/*
0 1 순으로 내림차순 정렬
1 맥스값보다 작으면 제외
맥스값 갱신

4 4
4 3
3 6
3 5
2 5

4 3
4 4
3 5
3 6

3 3
3 3
2 1
1 2

3 2
3 2
2 2
2 1
1 4

3 3
3 2
2 2
2 1
1 0


2 1
2 2
1 4
3 2
3 2


*/