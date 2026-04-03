import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	public static class Fish {
		int s;
		int d;
		int z;

		Fish(int s, int d, int z) {
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}

	public static int[] dirY = { -1, 1, 0, 0 }; // 위,아래,오른쪽,왼쪽
	public static int[] dirX = { 0, 0, 1, -1 };

	public static int R;
	public static int C;

	public static Fish[][] map;

	public static int result = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		int M = sc.nextInt();
		sc.nextLine();

		map = new Fish[R][C];

		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(sc.nextLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());

			map[r][c] = new Fish(s, d, z);
		}

		// 시작
		for (int i = 0; i < C; i++) {
			// 낚시
			doFishing(i);

			// 이동
			map = move();

		}

		System.out.println(result);

	}

	public static void doFishing(int curX) {
		for (int y = 0; y < R; y++) {
			// 물고기발견
			if (map[y][curX] != null) {
				result += map[y][curX].z;
				map[y][curX] = null;
				break;
			}
		}
	}

	public static Fish[][] move() {
		Fish[][] nextMap = new Fish[R][C];
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] == null) {
					continue;
				}
				Fish curFish = map[i][j];
				int[] dest = calLoc(i, j, curFish);
				int nextY = dest[0];
				int nextX = dest[1];

				Fish nextFish = nextMap[nextY][nextX];

				if (nextFish == null) {
					nextMap[nextY][nextX] = curFish; // 확인필요
				} else { // 크기 비교
					if (nextFish.z < curFish.z) {
						nextMap[nextY][nextX] = curFish;
					}
				}
			}
		}
		return nextMap;
	}

	public static int[] calLoc(int y, int x, Fish fish) {
		int curS = fish.s;
		int curD = fish.d;

		if (curD == 0 || curD == 1) { // 상,하
			if (R > 1) {
				curS %= 2 * (R - 1);
			} else {
				curS = 0;
			}
		} else {
			if (C > 1) {
				curS %= 2 * (C - 1);
			} else {
				curS = 0;
			}
		}

		for (int s = 0; s < curS; s++) {
			int nextY = y + dirY[curD];
			int nextX = x + dirX[curD];

			if (nextY < 0 || nextY >= R || nextX < 0 || nextX >= C) {
				if (curD == 0) {
					curD = 1;
				} else if (curD == 1) {
					curD = 0;
				} else if (curD == 2) {
					curD = 3;
				} else if (curD == 3) {
					curD = 2;
				}
				nextY = y + dirY[curD];
				nextX = x + dirX[curD];
			}

			y = nextY;
			x = nextX;
			fish.d = curD;
		}

		return new int[] {y,x};
	}

}
