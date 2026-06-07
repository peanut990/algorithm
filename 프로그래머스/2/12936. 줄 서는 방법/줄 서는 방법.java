class Solution {
    
    public int[] solution(int n, long k) {
        int[] answer = new int[n];
        boolean[] checked = new boolean[n+1];
        int numIdx = 0;
        long fac = 1;
        int N = n -1;
        
        for(int i = 1;i<= N;i++){
            fac *= i;
        }
        
        k--;
        for(int idx= 0;idx< answer.length-1;idx++){
            numIdx = (int)(k/fac +1); 
            k %= fac; 
            
            int numCount = 0;
            for(int i = 1;i< checked.length;i++){
                if(!checked[i]) numCount++;
                if(numCount == numIdx){
                    answer[idx] = i;
                    checked[i] = true;
                    break;
                }
            }
            
            fac /= N--;
        }
        
        for(int i = 1; i<checked.length;i++){
            if(!checked[i]){
                answer[answer.length -1] =  i;
            }
        }
        
        return answer;
    }
}