class Solution {
    public long solution(int r1, int r2) {
        long answer = 0;

        for(int x = 1;x<=r2;x++){
            double y2 = F(r2,x);
            double y1 = 0.0;
            if(x <= r1){
                y1 = F(r1,x);
            }
            
            answer += (int)Math.floor(y2) - (int)Math.ceil(y1) + 1;
            
        }
        return answer*4;
    }
    
    public double F(int r, int x){
        return Math.sqrt((long)r*r - (long)x*x);
    }
}

/*
x^2 +y^2 = r^2

y^2 = r^2 - x^2
*/