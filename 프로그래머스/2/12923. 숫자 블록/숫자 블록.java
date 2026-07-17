class Solution {
    public int[] solution(long begin, long end) {
        int[] answer = new int[(int)end - (int)begin+1];
        for(int num = (int)begin;num<=(int)end;num++){
            //System.out.println(num + " " + getD(num));
            answer[num-(int)begin] = getD(num);
        }
        return answer;
    }
    
    public int getD(int n){
        if( n== 1) return 0;
        
        int d = 1;
        for(int i = 2;i <= (int)Math.sqrt(n); i++){
            if(n%i == 0){
                d = n/i;
                
                if(d > 10_000_000) {
                    d = i;
                    continue;
                }
                
                break;
            }
        }
        return d;
    }
}

/*
15
5 * 3


*/