class Solution {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for(int i = 0;i< number.length();){
            int maxIdx = i;
            char max =  number.charAt(i);
            for(int j = i + 1;j<= k + count;j++){
                if(number.charAt(j) > max){
                    max = number.charAt(j);
                    maxIdx = j;
                }
            }
            
            sb.append(max);
            i = maxIdx + 1;
            count++;
            
            if(sb.length() == number.length() - k) break;
        }
        
        return sb.toString();
    }
}