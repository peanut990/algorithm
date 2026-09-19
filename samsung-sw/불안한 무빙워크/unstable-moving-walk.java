import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int K;

    static int[] movingWalk;
    static int[] up;

    static int sIdx = 0;
    static int zeroCount = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        movingWalk = new int[2 * N];
        up = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2 * N; i++) {
            movingWalk[i] = Integer.parseInt(st.nextToken());
        }

        // 로직 시작
        int k = 0;
        for (; ; k++) {
            // 1. 무빙 워크 회전
            sIdx = (sIdx - 1 + movingWalk.length) % movingWalk.length;
            for (int i = N - 1; i >= 0; i--) {
                if (up[i] == 0) continue;
                if (i == N - 1) {
                    up[i] = 0;
                    continue;
                }
                // 이동
                up[i + 1] = 1;
                if (i + 1 == N - 1) {
                    up[i + 1] = 0;
                }

                up[i] = 0;
            }


            // 2. 사람 이동
            movePeople();

            // 3. 사람 올리기
            enterPerson();

            //4. 0 카운트
            if (zeroCount >= K) break;
        }
        System.out.println(k + 1);
    }

    public static void enterPerson() {
        if (up[0] == 1 || movingWalk[sIdx] == 0) return;

        up[0] = 1;
        movingWalk[sIdx]--;
        if (movingWalk[sIdx] == 0) zeroCount++;
    }

    public static void movePeople() {
        for (int i = N - 1; i >= 0; i--) {
            if (up[i] == 0) continue;

            if (i == N - 1) {
                up[i] = 0;
                continue;
            }

            int nextMovingIdx = (sIdx + i + 1) % movingWalk.length;

            // 다음 칸 사람, 비어있으면
            if (up[i + 1] == 1 || movingWalk[nextMovingIdx] == 0) continue;

            // 이동
            up[i + 1] = 1;
            if (i + 1 == N - 1) {
                up[i + 1] = 0;
            }

            up[i] = 0;

            movingWalk[nextMovingIdx]--;
            if (movingWalk[nextMovingIdx] == 0) zeroCount++;
        }
    }
}
