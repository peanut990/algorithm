class Solution {
    public static boolean[][] checked;
    public int[] solution(int[][] arr) {
        int[] answer = new int[2];
        checked = new boolean[arr.length][arr.length];
        
        for(int len = arr.length; len >= 1; len /= 2 ){
            for(int y = 0; y < arr.length; y += len){
                for(int x= 0;x<arr.length;x += len){
                    // System.out.printf("y: %d, x: %d, len: %d \n", y,x,len);
                    if(checked[y][x]) continue;
                    
                    if(find(y,x,len,arr)){
                        int type = arr[y][x];
                        answer[type]++;
                        check(y,x,len,arr);
                    }
                }
            }
        }
        
        return answer;
    }
    
    public boolean find(int y, int x, int len, int[][] arr){
        int pivot = arr[y][x];
        for(int i = y;i<y+len;i++){
            for(int j = x;j<x+len;j++){
                if(arr[i][j] != pivot) return false;
            }
        }
        return true;
    }
    
    public void check(int y, int x, int len, int[][] arr){
        for(int i = y;i<y+len;i++){
            for(int j = x;j<x+len;j++){
                checked[i][j] = true;
            }
        }
    }
    
    
}

/*
10 * 1024* 1024

*/