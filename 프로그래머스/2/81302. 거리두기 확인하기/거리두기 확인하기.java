class Solution {
    public static int[] dirY = {0,1, 1,-1,-1,1 ,0,2};
    public static int[] dirX = {1,0, 1,1,-1,-1, 2,0};
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];

        for(int i = 0;i<places.length;i++){
            String[] place = places[i];

            answer[i] = searchFail(place) ? 0:1;
        }
        return answer;
    }
    
    public boolean searchFail(String[] place){
        for(int i = 0;i<place.length;i++){
            for(int j = 0;j<place[i].length();j++){
                char c = place[i].charAt(j);
                
                if(c != 'P') continue;
                
                for(int dir = 0;dir<dirY.length;dir++){
                    int nextY = i + dirY[dir];
                    int nextX = j + dirX[dir];
                    
                    if(nextY < 0 || nextY >= place.length || nextX < 0 || nextX >= place[i].length()) continue;
                    
                    if(place[nextY].charAt(nextX) != 'P') continue;
                    
                    // 우1, 하1
                    if( dir == 0 || dir == 1){
                        return true;
                    }else if(dir == 2){ // 우하
                        if(!(place[nextY-1].charAt(nextX) == 'X' && place[nextY].charAt(nextX-1) == 'X') ){
                          return true;  
                        } 
                    }else if(dir == 3){ // 우상
                        if(!(place[nextY].charAt(nextX-1) == 'X' && place[nextY+1].charAt(nextX) == 'X') ){
                          return true;  
                        } 
                    }else if(dir == 4){ // 좌상
                        if(!(place[nextY].charAt(nextX+1) == 'X' && place[nextY+1].charAt(nextX) == 'X') ){
                          return true;  
                        } 
                    }else if(dir == 5){ // 좌하
                        if(!(place[nextY-1].charAt(nextX) == 'X' && place[nextY].charAt(nextX+1) == 'X') ){
                          return true;  
                        } 
                    }else if(dir == 6){// 우2
                        if(place[nextY].charAt(nextX-1) != 'X'){
                            return true;
                        }
                    }else{// 하2
                        if(place[nextY-1].charAt(nextX) != 'X'){
                            return true;
                        }
                    }
                }  
            }
        }
        
        return false;
    }
}

/*


*/