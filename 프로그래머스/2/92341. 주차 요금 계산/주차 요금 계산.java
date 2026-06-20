import java.util.*;

class Solution {
    public final int LAST_TIME = 23*60 + 59;
    
    public int[] solution(int[] fees, String[] records) {
        HashMap<String, Integer> inTimes = new HashMap<>();
        HashMap<String, Integer> totalTimes = new HashMap<>();
        TreeMap<String, Integer> totalPrices = new TreeMap<>();
        
        for(int i = 0;i< records.length;i++){
            String[] record = records[i].split(" ");
            int timeWithMin = calMin(record[0]);
            //System.out.println(timeWithMin);
            String carNum = record[1];
            String inOut = record[2];
            
            if(inOut.equals("IN")){
                inTimes.put(carNum, timeWithMin);
            }else{
                int inTime = inTimes.remove(carNum);
                // 기록 저장
                int useTime = timeWithMin - inTime;
                totalTimes.put(carNum, totalTimes.getOrDefault(carNum, 0) + useTime );
            }
        }
        
        // 남은차 출차 처리
        for(String carNum: inTimes.keySet()){
            int inTime = inTimes.get(carNum);
            // 기록 저장
            int useTime = LAST_TIME - inTime;
            totalTimes.put(carNum, totalTimes.getOrDefault(carNum, 0) + useTime );
        }
        
        //System.out.println(totalTimes);
        
        // 요금 계산
        for(String carNum: totalTimes.keySet()){
            int price = calPrice(totalTimes.get(carNum), fees);
            totalPrices.put(carNum, price);
        }
        //System.out.println(totalPrices);
        
        int[] answer = new int[totalPrices.size()];
        int i = 0;
        for(String carNum: totalPrices.keySet()){
            answer[i++] = totalPrices.get(carNum);
        }
        
        return answer;
    }
    
    public int calMin(String s){
        String[] splitedTime = s.split(":");
        return Integer.parseInt(splitedTime[0]) * 60 + Integer.parseInt(splitedTime[1]);
    }
    
    public int calPrice(int time, int[] fees){
        if(time <= fees[0]) return fees[1];
        return fees[1] + (int)Math.ceil((double)(time - fees[0])/fees[2]) * fees[3]; 
    }
}