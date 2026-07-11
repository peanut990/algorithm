class Solution {
    public long solution(int w, int h) {
        long inavailCount = 0;
        
        for(int i = 0;i< w;i++){
            int minY = (int)Math.floor(F(i,w,h));
            int maxY = (int)Math.ceil(F(i+1,w,h));
            
            inavailCount += maxY - minY;
        }
        
        return ((long)w*h) - inavailCount;
    }
    
    public double F(int x, int w, int h){
        return ((double)(x - w) * h/w) + h;
    }
}