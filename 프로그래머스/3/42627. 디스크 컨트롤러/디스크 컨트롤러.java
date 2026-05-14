import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        PriorityQueue<int[]> jobsPq = new PriorityQueue<>((a,b)->{
            return a[0] - b[0];
        });
        
        for(int i = 0;i< jobs.length;i++){
            int[] job = jobs[i];
            jobsPq.offer(new int[]{job[0],job[1],i}); // 요청시각,소요시간, 작업번호
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
            if(a[1] != b[1]) return a[1] - b[1];
            if(a[0] != b[0]) return a[0] - b[0];
            return a[2] - b[2];
        });
        
        boolean inJob = false;
        int[] curJob = null;
        int jobCount = 0;
        int time = 0;
        
        List<Integer> reTimes = new ArrayList<>();
        
        while(reTimes.size() < jobs.length ){
        //while(time < ){
            // 대기큐 넣기
            while(!jobsPq.isEmpty() && jobsPq.peek()[0] == time){
                pq.offer(jobsPq.poll());
            }
            
            // 작업 넣기
            if(!inJob){
                if(pq.isEmpty()){
                    time++;
                    continue;
                }
                
                curJob = pq.poll();
                jobCount = 0;
                inJob = true;
            }
            
            
            jobCount++;
            time++;
            
            
            if(jobCount == curJob[1]){
                inJob = false;
                reTimes.add(time-curJob[0]);
            }
            
        }
          
        int res = 0;
        for(int t : reTimes){
            res += t;
        }
        
        return res/jobs.length;
        
        //return 0;
        
    }
}