class Solution
{
    public int solution(String s)
    {
        int max = 1;
        for(int i = 0;i< s.length();i++){
            int count = 1;
            int left = i-1;
            int right = i +1;
            while(left >= 0 && right < s.length()){
                if(s.charAt(left) != s.charAt(right)){
                    break;
                }
                left--;
                right++;
                count += 2;
            }
            max = Math.max(max, count);
        }
        
        for(int i = 0;i< s.length();i++){
            int left = i;
            int right = i+1;
            int count = 0;
            
            while(left >= 0 && right < s.length()){
                if(s.charAt(left) != s.charAt(right)){
                    break;
                }
                left--;
                right++;
                count += 2;
            }
            max = Math.max(max, count);
        }
        
        return max;
        
    }
}