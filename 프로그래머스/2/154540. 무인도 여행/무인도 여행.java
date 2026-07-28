import java.util.*;

class Solution {
    public static int[] dirY = {1,-1,0,0};
    public static int[] dirX = {0,0,-1,1};
    
    public static boolean[][] checked;
    
    public int[] solution(String[] maps) {
        checked = new boolean[maps.length][maps[0].length()];
        List<Integer> li = new ArrayList<>();
        
        for(int i = 0;i<maps.length;i++){
            for(int j = 0;j<maps[0].length();j++){
                if(checked[i][j] || maps[i].charAt(j) == 'X') continue;
                li.add(BFS(i,j,maps));
            }
        }
        Collections.sort(li);
        
        if(li.size() == 0){
            return new int[]{-1};
        }
        
        int[] answer = new int[li.size()];
        for(int i =0;i<answer.length;i++){
            answer[i] = li.get(i);
        }
        return answer;
    }
    
    public int BFS(int y,int x,String[] maps){
        Queue<int[]> q = new LinkedList<>();
        int count = 0;
        q.offer(new int[]{y,x});
        checked[y][x] = true;
        count += maps[y].charAt(x) - '0';
        
        while(!q.isEmpty()){
            int[] poll = q.poll();
            for(int i = 0;i<4;i++){
                int nextY = dirY[i] + poll[0];
                int nextX = dirX[i] + poll[1];
                
                if(!inBound(nextY,nextX,maps) || checked[nextY][nextX] || maps[nextY].charAt(nextX) == 'X') continue;
                
                q.offer(new int[]{nextY,nextX});
                checked[nextY][nextX] = true;
                count += maps[nextY].charAt(nextX) - '0';
            }
        }
        return count;
    }
    
    public boolean inBound(int y,int x,String[] maps){
        return y >= 0 && y < maps.length && x >=0 && x < maps[0].length();
    }
}