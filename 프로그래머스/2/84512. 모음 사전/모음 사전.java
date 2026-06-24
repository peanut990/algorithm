class Solution {
    public String[] sc = new String[]{"A","E","I","O","U"};
    public int count = 0;
    public boolean found = false;
    public int solution(String word) {
        DFS("", word, 0);
        return count;
    }
    
    public void DFS(String s, String word, int lv){
        if(lv > 5) return;
        
        if(s.equals(word)) {
            found = true;
            return;
        }
        
        count++;
        
        for(int i = 0;i< sc.length;i++){
            DFS(s + sc[i], word, lv + 1);
            if(found) return;
        }    
    }
}