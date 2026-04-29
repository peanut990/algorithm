import java.util.*;

class Solution {
    class Music{
        int num;
        int playCount;
        
        Music(int num, int playCount){
            this.num = num;
            this.playCount = playCount;
        }
    }
    public int[] solution(String[] genres, int[] plays) {
        HashMap<String, Integer> musicCounts = new HashMap<>();
        HashMap<String, List<Music>> musicLists = new HashMap<>();
        
        for(int i = 0;i<genres.length;i++){
            String genre = genres[i];
            int playCount = plays[i];
            
            if(!musicCounts.containsKey(genre)){
                musicCounts.put(genre, playCount);
                
                List<Music> musicList = new LinkedList<>();
                musicList.add(new Music(i,playCount));
                musicLists.put(genre, musicList);
                continue;
            }
            
            musicCounts.put(genre, musicCounts.get(genre) + playCount);
            musicLists.get(genre).add(new Music(i,playCount));
        }
        
        List<Map.Entry<String, Integer>> sorted = new LinkedList<>(musicCounts.entrySet());
        Collections.sort(sorted, (a,b)->{
            return b.getValue() - a.getValue();
        });
        
        for(List<Music> list : musicLists.values()){
            Collections.sort(list, (a,b)->{
                if(a.playCount != b.playCount) return b.playCount - a.playCount;
                return a.num - b.num;
            });
        }
        
        List<Integer> newList = new LinkedList<>();
        for(Map.Entry<String, Integer> entry : sorted){
            String genre = entry.getKey();
            
            List<Music> curList = musicLists.get(genre);
            int count = 0;
            while(count < 2 && count < curList.size()){
                newList.add(curList.get(count).num);
                count++;
            }
        }
        
        int[] answer = new int[newList.size()];
        for(int i = 0;i< newList.size();i++){
            answer[i] = newList.get(i);
        }
        return answer;
    }
}