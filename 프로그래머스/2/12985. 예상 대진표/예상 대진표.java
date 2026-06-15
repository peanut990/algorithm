class Solution
{
    public int solution(int n, int a, int b)
    {
        int count = 0;
        int min = Math.min(a,b);
        int max = Math.max(a,b);
        min--;
        max--;
        
        while(true){
            count++;
            if(min % 2 == 0 && max % 2 == 1 && max - min == 1){
                break;
            }
            
            min /= 2;
            max /= 2;
        }
        
        return count;
    }
}

/*
1: 1 2 3 4 5 6 7 8
2: 1   4   5   7
3: 4 7 

1: 0 1 2 3 4 5 6 7
   0   1   2   3
   0   1

2^10 * 2^10 = 1_000_000
*/