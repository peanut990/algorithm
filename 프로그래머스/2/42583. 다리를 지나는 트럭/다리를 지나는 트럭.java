import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> q = new LinkedList<>();
        int totalWeight = 0;
        int goalCount = 0;
        int truckIdx = 0;
        int time = 0;
        for(int i = 0;i < bridge_length;i++){
            q.add(0);
        }
        
        while(goalCount < truck_weights.length){
            time++;
            // 트럭 도착
            int goalTruck = q.poll();
            totalWeight -= goalTruck;
            if(goalTruck > 0) goalCount++;
            
            // 트럭 진입
            if(truckIdx >= truck_weights.length) {
                q.offer(0);
                continue;
            }
                
            int curTruck = truck_weights[truckIdx];
            if(totalWeight + curTruck <= weight){
                q.offer(curTruck);
                totalWeight += curTruck;
                truckIdx++;
            }else{
                q.offer(0);
            }
        }
        return time;
    }
}
