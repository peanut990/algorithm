import java.util.*;

class Solution {
    public int solution(int n, int[] stations, int w) {
        int size = 2*w + 1;
        int answer = 0;
        
        int subSize = stations[0] - 1;
        if( subSize > w) {
            subSize -= w;
            answer += calc(subSize,size);
        }
        
        for(int i = 0;i<stations.length -1;i++){
            int next = i + 1;
            if( next >= stations.length) break;
            
            subSize = stations[next] - stations[i] - 1;
            if(subSize > 2*w) {
                subSize -= 2*w;
                answer += calc(subSize,size);
            }
        }
        
        subSize = n - stations[stations.length-1];
        if( subSize > w) {
            subSize -= w;
            answer += calc(subSize,size);
        }
        
        return answer;
    }
    
    public int calc(int subSize, int size){
        if(subSize % size > 0){
            return subSize/size + 1;
        }
        
        return subSize/size;
    }
  
}