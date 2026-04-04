import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static class Fish{
		int y;
		int x;
		int num;
		int dir;
		
		Fish(int y,int x,int num, int dir){
			this.y = y;
			this.x = x;
			this.num = num;
			this.dir = dir;
		}
	}
	
	public static class Shark{
		int y;
		int x;
		int dir;
		
		Shark(int y,int x, int dir){
			this.y = y;
			this.x = x;
			this.dir = dir;
		}
	}
	
	public static int SIZE = 4;
	public static List<Fish> fishes;
	public static Fish[][] map;
	
	public static int result =0;
	
	public static int[] dirY = {-1,-1,0,1,1,1,0,-1}; //시계방향 	
	public static int[] dirX = {0,-1,-1,-1,0,1,1,1};

	
	public static void main(String[] agrs) {
		Scanner sc = new Scanner(System.in);
		
		fishes = new LinkedList<>();
		map = new Fish[SIZE][SIZE];
		
		for(int i =0;i<SIZE;i++) {
			for(int j = 0;j<SIZE;j++) {
				int a = sc.nextInt();
				int b = sc.nextInt() -1;
				
				Fish f = new Fish(i,j,a,b); 
				
				fishes.add(f);
				map[i][j] = f;
			}
		}
		
		Collections.sort(fishes, (a,b)->{
			return a.num - b.num;
		});
		

		Shark shark = new Shark(0,0,0);

		//1. 먹기 
		int sum = eatFish(0,0,shark, map,fishes);
		
		//2. 물고기 이동
		//3. 상어 이동 
		move(shark, sum, map,fishes);
		
		System.out.println(result);
		
	}

	public static int eatFish(int y, int x, Shark shark, Fish[][] map, List<Fish> fishes) {
		Fish eated = map[y][x];
		
		fishes.remove(eated);
		map[y][x] = null;
		
		//상어 방향  반영
		shark.dir = eated.dir;
		
		return eated.num;
	}
	
	public static void moveFish(Shark shark, Fish[][] map,List<Fish> fishes) {

		for (Fish f : fishes) {
			int curY = f.y;
			int curX = f.x;
			
			for(int i = 0;i<8;i++) {
				int curDir = f.dir;
				int nextY = curY + dirY[curDir];
				int nextX = curX + dirX[curDir];
				
				if( nextY >= 0 && nextY < SIZE && nextX >=0 && nextX < SIZE ) {
					if(nextY == shark.y && nextX == shark.x) {
						f.dir = (f.dir +1)%8;
						continue;
					}
					
					if(map[nextY][nextX] == null) {
						// 좌표 갱신
						map[curY][curX].y = nextY;
						map[curY][curX].x = nextX;

						// 위치 교환 
						map[nextY][nextX] = map[curY][curX];
						map[curY][curX]= null;
						break;
					}
					
					// 좌표 갱신
					map[nextY][nextX].y = curY;
					map[nextY][nextX].x = curX;
					map[curY][curX].y = nextY;
					map[curY][curX].x = nextX;
					
					// 위치 교환 
					Fish tmp = map[nextY][nextX];
					map[nextY][nextX] = map[curY][curX];
					map[curY][curX]= tmp;
					break;
				}

				f.dir = (f.dir +1)%8;
			}
			

			//printMap(map);
			
		}
		
	}
	
	public static void move(Shark shark, int sum, Fish[][] map, List<Fish> fishes) {
		moveFish(shark, map,fishes);

		//printMap(map);
		
		result = Math.max(result, sum);
		int curDir = shark.dir;
		int nextY = shark.y + dirY[curDir];
		int nextX = shark.x + dirX[curDir];
		
		while( nextY >= 0 && nextY < SIZE && nextX >=0 && nextX < SIZE ) {
			if(map[nextY][nextX] == null) {

				nextY += dirY[curDir];
				nextX += dirX[curDir];
				continue;
			}

			// 얕은 복사
			Fish[][] nextMap = new Fish[SIZE][SIZE];
			List<Fish> nextFishes = new LinkedList<>();
			for(int i =0;i<SIZE;i++) {
				for(int j = 0;j<SIZE;j++) {
					Fish old = map[i][j];
					if(old == null) continue;
					
					Fish newFish = new Fish(old.y,old.x,old.num,old.dir);
					nextMap[i][j] = newFish;
					nextFishes.add(newFish);
				}
			}
			Collections.sort(nextFishes, (a,b)->{
				return a.num - b.num;
			});
			
			// 상어 반영 
			Shark newShark = new Shark(nextY,nextX,0);
			int eatedNum = eatFish(nextY,nextX,newShark,nextMap,nextFishes);
			
			move(newShark, sum + eatedNum, nextMap, nextFishes);
			
			nextY += dirY[curDir];
			nextX += dirX[curDir];
		}
	}
	
	public static void printMap(Fish[][] map) {
		for(int i =0;i<SIZE;i++) {
			for(int j = 0;j<SIZE;j++) {
				if(map[i][j] == null) {
					System.out.print(0 + " ");
					continue;
				}
				System.out.print(map[i][j].num + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
