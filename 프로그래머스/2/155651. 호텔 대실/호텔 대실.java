import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        List<int[]> li = new ArrayList<>();
        for(String[] time : book_time){
            String[] sts = time[0].split(":");
            int st = Integer.parseInt(sts[0]) * 60 + Integer.parseInt(sts[1]);
            sts = time[1].split(":");
            int et = Integer.parseInt(sts[0]) * 60 + Integer.parseInt(sts[1]);
            
            li.add(new int[]{st, et});
        }
        
        Collections.sort(li, (a,b)->{
            return a[0] - b[0];
        });
        
        List<Integer> rooms = new ArrayList<>();
        
        for(int i = 0;i< li.size();i++){
            int[] curTime = li.get(i);
            int minEndTime = 2400;
            int minRoomNum = -1;
            for(int j = 0;j < rooms.size();j++){
                int curRoomEndTime = rooms.get(j);
                if(minEndTime > curRoomEndTime){
                    minEndTime = curRoomEndTime;
                    minRoomNum = j;
                }
            }
            
            if(minEndTime <= curTime[0]){
                rooms.set(minRoomNum, curTime[1] + 10);
            }else{
                rooms.add(curTime[1] + 10);
            }
            
        }
        
        return rooms.size();
    }
}

/*
시작 시간 오름차순

마지막 종료시간보다 시작 시간이 빠르면 새로운 방 필요

*/