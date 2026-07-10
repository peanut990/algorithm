import java.util.*;

class Solution {
    public int solution(int[] picks, String[] minerals) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
            if(a[0] != b[0]) return b[0] - a[0];
            if(a[1] != b[1]) return b[1] - a[1];
            return b[2] - a[2];
        });
        
        int[][] powers = {{1,1,1},{5,1,1,},{25,5,1}};
        
        int pickCount = 0;
        for(int p : picks){
            pickCount += p;
        }
       
        for(int i = 0;i< minerals.length;i+= 5){
            if(pq.size() >= pickCount) break;
            
            int[] mineralsCount = new int[3];
            for(int j = i;j< i+5;j++){
                if(j >= minerals.length) break;
                String mineral = minerals[j];
                if(mineral.equals("diamond")){
                    mineralsCount[0]++;
                }else if(mineral.equals("iron")){
                    mineralsCount[1]++;
                }else{
                    mineralsCount[2]++;
                }
            }
            pq.offer(mineralsCount);
        }
        
        int answer = 0;
        while(!pq.isEmpty()){
            int[] p = pq.poll();
            
            for(int i = 0;i<picks.length;i++){
                if(picks[i] > 0){
                    for(int j = 0;j<3;j++){
                        answer += p[j] * powers[i][j];
                    }
                    
                    picks[i]--;
                    break;
                }
            }
        }
        
        return answer;
    }
}


/*

*/