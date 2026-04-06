import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	public static class Shark {
		int num;
		int curDir;
		int[][] dirMap;
		int y;
		int x;

		Shark(int num, int curDir, int y, int x) {
			this.num = num;
			this.curDir = curDir;
			this.y = y;
			this.x = x;

			dirMap = new int[4][4];
		}

	}

	public static class Smell {
		int sharkNum;
		int timeCount;
		int y;
		int x;

		Smell(int sharkNum, int k, int y, int x) {
			this.sharkNum = sharkNum;
			this.timeCount = k;
			this.y = y;
			this.x = x;
		}
	}

	public static int N;
	public static int M;
	public static int k;
	public static Shark[][] sharkMap;
	public static List<Shark> sharkList;
	public static Smell[][] smellMap;
	public static List<Smell> smellList;

	public static int[] dirY = { -1, 1, 0, 0 }; // 상,하,좌,우
	public static int[] dirX = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		StringTokenizer st = new StringTokenizer(sc.nextLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		sharkMap = new Shark[N][N];
		sharkList = new LinkedList<>();
		smellMap = new Smell[N][N];
		smellList = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(sc.nextLine());
			for (int j = 0; j < N; j++) {
				int sharkNum = Integer.parseInt(st.nextToken());
				if (sharkNum == 0)
					continue;
				Shark s = new Shark(sharkNum, 0, i, j);
				sharkMap[i][j] = s;
				sharkList.add(s);
			}
		}

		// 현재방향 입력
		st = new StringTokenizer(sc.nextLine());
		Collections.sort(sharkList, (a, b) -> {
			return a.num - b.num;
		});
		for (int i = 0; i < M; i++) {
			sharkList.get(i).curDir = Integer.parseInt(st.nextToken()) - 1;
		}

		// 방향 맵 입력
		for (int i = 0; i < M; i++) {
			Shark s = sharkList.get(i);
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(sc.nextLine());
				for (int d = 0; d < 4; d++) {
					s.dirMap[j][d] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		}
		// printSharkList();

		// 처음 냄새 남기기
		doSmell();

//
//		printSharkMap();
//		printSmellMap();

		int time = 0;
		while (true) {
			if (sharkList.size() == 1) {
				System.out.println(time);
				return;
			}

			Shark[][] nextMap = new Shark[N][N];
			// 1. 방향 탐색 및 이동, 먹기
			sharkMove(nextMap);

			// 2. 냄새 제거
			reduceSmell();

			// 3.냄새 남기기
			doSmell();

			// 상어맵 동기화
			sharkMap = nextMap;

			//System.out.println(time);
//			printSharkMap();
//			printSmellMap();
//			printSharkList();
//			printSmellList();

			time++;

			if (time > 1000) {
				System.out.println(-1);
				return;
			}
		}

	}

	public static void reduceSmell() {
		for (int i = 0; i < smellList.size(); i++) {
			Smell smell = smellList.get(i);
			smell.timeCount -= 1;
			if (smell.timeCount == 0) {
				// 맵 반영
				smellMap[smell.y][smell.x] = null;
			}
		}

		List<Smell> nextSmellList = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (smellMap[i][j] != null && smellMap[i][j].timeCount > 0) {
					nextSmellList.add(smellMap[i][j]);
				}
			}
		}

		smellList = nextSmellList;
	}

	public static void sharkMove(Shark[][] nextMap) {
		for (Shark shark : sharkList) {
			// 방향 탐색
			int[] nextLoc = searchDir(shark);
			// 이동 및 제거
			doMove(shark, nextLoc, nextMap);
		}

		// 리스트 반영
		List<Shark> nextSharkList = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (nextMap[i][j] != null) {
					nextSharkList.add(nextMap[i][j]);
				}
			}
		}
		sharkList = nextSharkList;

	}

	public static void doMove(Shark s, int[] nextLoc, Shark[][] nextMap) {
		// 상어 탐지 및 제거
		int nextY = nextLoc[0];
		int nextX = nextLoc[1];
		Shark old = nextMap[nextY][nextX];

		// 좌표 반영
		s.y = nextY;
		s.x = nextX;

		if (old == null) {
			nextMap[nextY][nextX] = s;
		} else { // 제거
			if (old.num > s.num) {
				nextMap[nextY][nextX] = s;
			}
			// 자기 위치 인 경우 그대로 두기
		}
	}

	public static int[] searchDir(Shark s) {
		int curY = s.y;
		int curX = s.x;

		// 냄새 없는 칸 탐색
		for (int i = 0; i < 4; i++) {
			int dir = s.dirMap[s.curDir][i];
			int nextY = curY + dirY[dir];
			int nextX = curX + dirX[dir];

			if (isInMap(nextY, nextX)) {
				// 냄새 없는 칸 발견 (빈칸)
				if (smellMap[nextY][nextX] == null) {
					// 방향 반영
					s.curDir = dir;
					return new int[] { nextY, nextX };
				}
			}
		}

		// 없으면 자기 냄새 탐색
		for (int i = 0; i < 4; i++) {
			int dir = s.dirMap[s.curDir][i];
			int nextY = curY + dirY[dir];
			int nextX = curX + dirX[dir];

			if (isInMap(nextY, nextX)) {
				// 자기 냄새 발견
				if (smellMap[nextY][nextX] != null && smellMap[nextY][nextX].sharkNum == s.num) {
					// 방향 반영
					s.curDir = dir;
					return new int[] { nextY, nextX };
				}
			}
		}

		return new int[] { curY, curX };
	}

	public static boolean isInMap(int nextY, int nextX) {
		return nextY >= 0 && nextY < N && nextX >= 0 && nextX < N;
	}

	public static void doSmell() {
		for (Shark s : sharkList) {
			Smell smell = new Smell(s.num, k, s.y, s.x);
			smellList.add(smell);
			Smell old = smellMap[s.y][s.x];
			if (old != null) {
				smellList.remove(old);
			}
			smellMap[s.y][s.x] = smell;
		}
	}


	public static void printSmellMap() {
		System.out.println("## Smell Map ##");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (smellMap[i][j] == null) {
					System.out.print("[n:0,t:0] ");
					continue;
				}
				System.out.printf("[n:%d,t:%d] ", smellMap[i][j].sharkNum, smellMap[i][j].timeCount);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printSharkMap() {
		System.out.println("## Shark Map ##");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (sharkMap[i][j] == null) {
					System.out.print("[n:0,d:0] ");
					continue;
				}
				System.out.printf("[n:%d,d:%d] ", sharkMap[i][j].num, sharkMap[i][j].curDir);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printSharkList() {
		for (Shark s : sharkList) {
			System.out.printf("num: %d, curDir: %d, y: %d, x: %d", s.num, s.curDir, s.y, s.x);
			System.out.println();
			int[][] dirMap = s.dirMap;
			for (int i = 0; i < dirMap.length; i++) {
				for (int j = 0; j < dirMap[i].length; j++) {
					System.out.print(dirMap[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public static void printSmellList() {
		for (Smell s : smellList) {
			System.out.printf("num: %d, curCount: %d, y: %d, x: %d", s.sharkNum, s.timeCount, s.y, s.x);
			System.out.println();
		}
	}

}
