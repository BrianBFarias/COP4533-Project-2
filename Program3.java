import java.util.Scanner;

class Program3 {

    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}

    // Recursive helper function to partition paintings and calculate the minimum cost
    private static int minimize_cost(int[] heights, int[] widths, int maxWidth, int n, int i) {
        // Base case: if we've considered all paintings
        if (i == n) {
            return 0; // No more rows to form, cost is 0
        }

        int minCost = Integer.MAX_VALUE; // Initialize the minimum cost
        int currWidth = 0; // Track the width of the current platform
        int currMaxHeight = 0; // Track the max height in the current platform

        // Try each possible split of the array from the current index `i`
        for (int j = i; j < n; j++) {
            currWidth += widths[j]; // Add the current painting's width
            if (currWidth > maxWidth) {
                break; // If width exceeds limit, stop further additions to this platform
            }
            currMaxHeight = Math.max(currMaxHeight, heights[j]); // Update the platform's height
            // Calculate cost of this split and recurse for remaining paintings
            int cost = currMaxHeight + minimize_cost(heights, widths, maxWidth, n, j + 1);
            minCost = Math.min(minCost, cost); // Track the minimum cost
        }

        return minCost; // Return the minimum cost for this configuration
    }

    private static Result program3(int n, int w, int[] heights, int[] widths) {
        // Calculate the minimum cost starting from the first painting
        int totalMinCost = minimize_cost(heights, widths, w, n, 0);

        // Determine the optimal number of platforms and paintings per platform
        int currentWidth = 0;
        int numPlatforms = 0;
        int[] numPaintings = new int[n];
        int platformIndex = 0;

        for (int i = 0; i < n; i++) {
            if (currentWidth + widths[i] > w) { // Start a new platform if width exceeds limit
                numPlatforms++;
                platformIndex++;
                currentWidth = 0; // Reset width for the new platform
            }
            currentWidth += widths[i];
            numPaintings[platformIndex]++;
        }

        numPlatforms = platformIndex + 1; // Total number of platforms used
        int[] finalNumPaintings = new int[numPlatforms];
        System.arraycopy(numPaintings, 0, finalNumPaintings, 0, numPlatforms);

        // Balancing step to ensure even distribution of paintings
        for (int j = 0; j < finalNumPaintings.length - 1; j++) {
            while (Math.abs(finalNumPaintings[j] - finalNumPaintings[j + 1]) > 1) {
                if (finalNumPaintings[j] > finalNumPaintings[j + 1]) {
                    finalNumPaintings[j]--;
                    finalNumPaintings[j + 1]++;
                } else {
                    finalNumPaintings[j]++;
                    finalNumPaintings[j + 1]--;
                }
            }
        }

        return new Result(numPlatforms, totalMinCost, finalNumPaintings);
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
        Result result = program3(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for (int i = 0; i < result.numPaintings.length; i++) {
            System.out.println(result.numPaintings[i]);
        }
    }
}
