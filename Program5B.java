import java.util.Arrays;
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
        int[] dp = new int[n + 1]; // dp[i] stores the minimum height for the first i sculptures
        int[] platformCount = new int[n + 1]; // platformCount[i] stores the number of platforms needed for dp[i]
        int[] numPaintingsOnPlatform = new int[n + 1]; // Temporary storage for number of paintings per platform
        
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // Base case: no height needed when no sculptures are placed
        platformCount[0] = 0; // Zero platforms when no sculptures are placed

        for (int i = 1; i <= n; i++) {
            int widthSum = 0;
            int maxHeight = 0;
            int paintingsOnCurrentPlatform = 0;

            for (int j = i; j > 0; j--) {
                widthSum += widths[j - 1];
                if (widthSum > w) break; // If width exceeds limit, stop this grouping
                
                maxHeight = Math.max(maxHeight, heights[j - 1]);
                paintingsOnCurrentPlatform = i - j + 1;

                // Check if grouping sculptures [j, i] on a new platform minimizes the total height
                if (dp[i] > dp[j - 1] + maxHeight) {
                    dp[i] = dp[j - 1] + maxHeight;
                    platformCount[i] = platformCount[j - 1] + 1;
                    numPaintingsOnPlatform[platformCount[i] - 1] = paintingsOnCurrentPlatform;
                }
            }
        }

        // Construct result for the number of paintings on each platform
        int[] numPaintings = Arrays.copyOf(numPaintingsOnPlatform, platformCount[n]);

        return new Result(platformCount[n], dp[n], numPaintings);
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