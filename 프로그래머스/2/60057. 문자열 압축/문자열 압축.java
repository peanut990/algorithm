class Solution {
    public int solution(String s) {
        int min = s.length();
        
        for(int len = 1;len<= s.length()/2;len++){
            int idx = 0;
            int answer = 0;
            
            while(idx < s.length() && idx + len <= s.length()){
                String curS = s.substring(idx, idx+len);
                int count = 1;
                idx += len;

                while(idx < s.length() && idx + len <= s.length() && s.substring(idx, idx+len).equals(curS)){
                    count++;
                    idx += len;
                }

                if(count>1){
                    answer += len + String.valueOf(count).length();
                }else{
                    answer += len;
                }
            }

            if(idx < s.length()){
                answer += s.length() - idx;
            }
            
            
            min = Math.min(min, answer);
        }
        
        return min;
    }
}

/*
2a2ba3c

*/