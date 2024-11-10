import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Program5B{
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}
   
    /**
    * Solution to program 5B
    * @param n number of paintings
    * @param w width of the platform
    * @param heights array of heights of the paintings
    * @param widths array of widths of the paintings
    * @return Result object containing the number of platforms, total height of the paintings and the number of paintings on each platform
    */
    private static Result program5B(int n, int w, int[] heights, int[] widths) {
        int[] dp = new int[n + 1]; // dp[i] stores the minimum height for the first i paintings
        int[] prev = new int[n + 1]; // prev[i] stores the index where the last platform starts
        
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // Base case: no height needed when no paintings are placed
        prev[0] = -1; // No previous index for the base case

        for (int i = 1; i <= n; i++) {
            int widthSum = 0;
            int maxHeight = 0;

            for (int j = i; j > 0; j--) {
                widthSum += widths[j - 1];
                if (widthSum > w) break; // If width exceeds limit, stop this grouping
                
                maxHeight = Math.max(maxHeight, heights[j - 1]);

                // Check if grouping paintings [j, i] on a new platform minimizes the total height
                if (dp[j - 1] + maxHeight < dp[i]) {
                    dp[i] = dp[j - 1] + maxHeight;
                    prev[i] = j - 1; // Store the starting index of this platform
                }
            }
        }

        // Reconstruct the number of paintings on each platform
        List<Integer> numPaintingsList = new ArrayList<>();
        int index = n;
        while (index > 0) {
            int start = prev[index];
            numPaintingsList.add(index - start);
            index = start;
        }

        // Reverse the list to get the correct order
        Collections.reverse(numPaintingsList);

        int numPlatforms = numPaintingsList.size();
        int[] numPaintings = numPaintingsList.stream().mapToInt(Integer::intValue).toArray();

        return new Result(numPlatforms, dp[n], numPaintings);
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
        Result result = program5B(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numPaintings.length; i++){
            System.out.println(result.numPaintings[i]);
        }
    }
}