import java.util.*;

class Program5A {
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}

    /**
     * Solution to program 5A
     * @param n number of paintings
     * @param w width of the platform
     * @param heights array of heights of the paintings
     * @param widths array of widths of the paintings
     * @return Result object containing the number of platforms, total height of the paintings, and the number of paintings on each platform
     */

    // Helper class to store intermediate results
    private static class ResultData {
        int totalHeight;
        int numPlatforms;
        List<Integer> platforms; // Stores number of paintings on each platform

        ResultData(int totalHeight, int numPlatforms, List<Integer> platforms) {
            this.totalHeight = totalHeight;
            this.numPlatforms = numPlatforms;
            this.platforms = platforms;
        }
    }

    private static Result program5A(int n, int w, int[] heights, int[] widths) {
        Map<Integer, ResultData> memo = new HashMap<>();
        ResultData finalResult = recursiveSolve(n, w, heights, widths, memo);

        // Extract the number of platforms and total height
        int numPlatforms = finalResult.numPlatforms;
        int totalHeight = finalResult.totalHeight;

        // Extract the number of paintings on each platform
        int[] numPaintings = new int[numPlatforms];
        int index = 0;
        for (int i = finalResult.platforms.size() - 1; i >= 0; i--) {
            numPaintings[index++] = finalResult.platforms.get(i);
        }

        return new Result(numPlatforms, totalHeight, numPaintings);
    }

    // Recursive function with memoization
    private static ResultData recursiveSolve(int i, int W, int[] heights, int[] widths, Map<Integer, ResultData> memo) {
        // Base case: no paintings
        if (i == 0) {
            return new ResultData(0, 0, new ArrayList<>());
        }

        // Check memoization
        if (memo.containsKey(i)) {
            return memo.get(i);
        }

        int minHeight = Integer.MAX_VALUE;
        int minPlatforms = Integer.MAX_VALUE;
        List<Integer> bestPlatforms = null;

        int totalWidth = 0;
        int maxHeight = 0;

        // Consider all possible platforms ending at painting i
        for (int j = i; j >= 1; j--) {
            totalWidth += widths[j - 1];
            if (totalWidth > W) {
                break;
            }
            maxHeight = Math.max(maxHeight, heights[j - 1]);

            // Recursive call for the first j - 1 paintings
            ResultData prevResult = recursiveSolve(j - 1, W, heights, widths, memo);
            int currentHeight = prevResult.totalHeight + maxHeight;
            int currentPlatforms = prevResult.numPlatforms + 1;

            // Update minimum total height
            if (currentHeight < minHeight ||
                (currentHeight == minHeight && currentPlatforms < minPlatforms)) {
                minHeight = currentHeight;
                minPlatforms = currentPlatforms;
                // Create a new list of platforms
                bestPlatforms = new ArrayList<>(prevResult.platforms);
                bestPlatforms.add(i - j + 1); // Number of paintings on the new platform
            }
        }

        // Memoize and return result
        ResultData result = new ResultData(minHeight, minPlatforms, bestPlatforms);
        memo.put(i, result);
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int W = sc.nextInt();
        int[] heights = new int[n];
        int[] widths = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            widths[i] = sc.nextInt();
        }
        sc.close();
        Result result = program5A(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for (int i = 0; i < result.numPaintings.length; i++) {
            System.out.println(result.numPaintings[i]);
        }
    }
}
