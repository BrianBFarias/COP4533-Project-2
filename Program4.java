import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Program4 {
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}

    private static Result program4(int n, int W, int[] heights, int[] widths) {
        // minimal total height for the first i paintings
        int[] dp = new int[n + 1];
        dp[0] = 0; 

        // stores the index where the last platform starts
        int[] prev = new int[n + 1];

        // Initialize dp array with MAX values
        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        // process grouping of each painting
        for (int i = 1; i <= n; i++) {
            // looking back from i painting
            for (int j = i - 1; j >= 0; j--) {
                int totalWidth = 0;
                int maxHeight = 0;

                // Compute totalWidth and maxHeight for paintings from j to i - 1
                for (int k = j; k < i; k++) {
                    totalWidth += widths[k];
                    maxHeight = Math.max(maxHeight, heights[k]);
                }

                if (totalWidth > W) {
                    break;
                }

                if (dp[j] + maxHeight < dp[i]) {
                    dp[i] = dp[j] + maxHeight;
                    prev[i] = j;
                }
            }
        }

        // Reconstruct the arrangement of platforms
        List<Integer> numPaintingsList = new ArrayList<>();
        int index = n;
        while (index > 0) {
            int start = prev[index];
            numPaintingsList.add(index - start);
            index = start;
        }

        // List reversed
        Collections.reverse(numPaintingsList);

        int numPlatforms = numPaintingsList.size();
        int[] numPaintings = numPaintingsList.stream().mapToInt(Integer::intValue).toArray();
        int totalHeight = dp[n];

        return new Result(numPlatforms, totalHeight, numPaintings);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W = sc.nextInt();
        int[] heights = new int[n];
        int[] widths = new int[n];

        for(int i=0; i<n; i++){
            heights[i] = sc.nextInt();
        }
        for(int i=0; i<n; i++){
            widths[i] = sc.nextInt();
        }
        sc.close();

        Result result = program4(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);

        for(int i=0; i<result.numPaintings.length; i++){
            System.out.println(result.numPaintings[i]);
        }
    }
}
