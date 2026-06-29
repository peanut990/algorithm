class Solution {
    public long[] solution(long[] numbers) {
        long[] answer = new long[numbers.length];
        
        for(int i = 0;i< numbers.length;i++){
            answer[i] = F(numbers[i]);
        }

        return answer;
    }
    
    public long F(long l){
        if(l % 2 == 0 ){
            return l + 1;
        }else{
            String s = '0' + Long.toBinaryString(l);
            int zeroIdx = 0;
            for(int i = s.length() - 1 ; i >= 0; i--){
                if(s.charAt(i) == '0') {
                    zeroIdx = i;
                    break;
                }
            }
            
            StringBuilder sb = new StringBuilder(s);
            sb.setCharAt(zeroIdx, '1');
            sb.setCharAt(zeroIdx + 1, '0');
            
            return Long.parseLong(sb.toString(),2);
        }
    }
}

/*
1011 : 11
1100 : 12
1101 : 13

1001 : 9
1010 : 10

*/