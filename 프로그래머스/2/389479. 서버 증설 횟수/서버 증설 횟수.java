import java.util.*;

class Solution {
    class Server{
        int time;
        Server(int time){
            this.time = time;
        }
    }
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        PriorityQueue<Server> pq = new PriorityQueue<>((a,b)->{
            return a.time - b.time;
        });
        
        for(int player : players){
            for(Server s : pq){
                s.time--;
            }

            while(!pq.isEmpty() && pq.peek().time == 0){
                pq.poll();
            }

            int needCount = player/m;
            int curCount = pq.size();
            int addCount = needCount - curCount;
            if(addCount < 0) addCount = 0;
            
            for(int i = 0;i< addCount;i++){
                pq.offer(new Server(k));
            }
            
            answer += addCount;
            // System.out.println(player + " " + pq.size() + " " +addCount);
        }
       
        
        return answer;
    }
}

/*
필요 서버 수: player/m
증설 횟수: 필요 서버 수 - 현재 증설된 서버 수
매턴: 서버 유지 시간 - 1, 0이면 서버 제거


*/