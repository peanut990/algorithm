import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	public static class Fish{
		int s;
		int d;
		int z;
		
		Fish(int s, int d, int z){
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}
	public static int R;
	public static int C;
	
	
	public static Fish[][] map;
	
	public static int result = 0;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		int M =sc.nextInt();
		sc.nextLine();
		
		map = new Fish[R][C];
		
		for(int i =0;i<M;i++) {
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			int r = Integer.parseInt(st.nextToken()) -1;
			int c = Integer.parseInt(st.nextToken()) -1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) -1;
			int z = Integer.parseInt(st.nextToken());
			
			map[r][c] = new Fish(s,d,z);
		}
		
		//printMap(map);
		//시작  
		for(int i = 0;i<C;i++) {
			//낚시   
			doFishing(i);
			
			//이동  	
			map = move();
		}
		
		System.out.println(result);
		
	}
	
	public static void doFishing(int curX) {
		for(int y = 0;y<R;y++) {
			//물고기발견 	
			if(map[y][curX] != null) {
				result += map[y][curX].z;
				map[y][curX] = null;
				break;
			}
		}
	}
	
	public static Fish[][] move() {
		Fish[][] nextMap = new Fish[R][C];
		for(int i =0;i<R;i++) {
			for(int j = 0;j<C;j++) {
				if(map[i][j] == null) {
					continue;
				}
				Fish curFish = map[i][j];
				int[] dest = calLoc(i,j, curFish);
				int nextY = dest[0];
				int nextX = dest[1];
				
				Fish nextFish = nextMap[nextY][nextX];
			
				if( nextFish == null) {
					nextMap[nextY][nextX] = curFish; //확인필요 
				}else { //크기 비교 
					if(nextFish.z < curFish.z) {
						nextMap[nextY][nextX] = curFish;
					}
				}
			}
		}
		return nextMap;
	}
	
	public static int[] calLoc(int y,int x, Fish fish) {
		int curS = fish.s;
		int curD = fish.d;
		
		
		if(curD == 0 ) {
			int avail = y - 0;
			if( avail < curS) {
				curS -= avail;
				
				int count = curS/(R-1);
				int dist = curS%(R-1);
				//방향 유지 
				if(count %2 ==1) {
					return new int[] {(R-1)-dist, x};
				}else {
					fish.d = 1;
					return new int[] {0+dist,x};
				}
			}
			return new int[] {y - curS,x};
		}else if(curD == 1) {
			int avail = R-1 - y;
			if( avail < curS) {
				curS -= avail;
				
				int count = curS/(R-1);
				int dist = curS%(R-1);
				//방향 유지 
				if(count %2 ==1) {
					return new int[] {0+dist,x};
				}else {
					fish.d = 0;
					return new int[] {(R-1)-dist, x};
				}
			}
			return new int[] {y + curS,x};
		}
		else if(curD == 2) {
			int avail = C-1 - x;
			if( avail < curS) {
				curS -= avail;
				
				int count = curS/(C-1);
				int dist = curS%(C-1);
				//방향 유지 
				if(count %2 ==1) {
					return new int[] {y,0+dist};
				}else {
					fish.d = 3;
					return new int[] {y,(C-1)-dist};
				}
			}
			return new int[] {y,x+curS};
		}
		else if(curD == 3 ) {
			int avail = x - 0;
			if( avail < curS) {
				curS -= avail;
				
				int count = curS/(C-1);
				int dist = curS%(C-1);
				//방향 유지 
				if(count %2 ==1) {
					return new int[] {y,(C-1)-dist};
				}else {
					fish.d = 2;
					return new int[] {y,0+dist};
				}
			}
			return new int[] {y,x-curS};
		}
		
		return  null;
	}
}
