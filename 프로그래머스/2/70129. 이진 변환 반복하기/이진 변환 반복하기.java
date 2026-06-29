class Solution {
    public int[] solution(String s) {
        int zeroCount = 0;
        int count = 0;
        while(!s.equals("1")){
            int oneCount = 0;    
            for(int i = 0; i<s.length();i++){
                if(s.charAt(i) == '1'){
                    oneCount++;
                }else{
                    zeroCount++;
                }
            }
            
            s = Integer.toBinaryString(oneCount);
            count++;
        }
        
        return new int[]{count, zeroCount};
    }
}