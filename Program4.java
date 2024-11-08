import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Program4 {
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}

    private static Result program4(int n, int W, int[] heights, int[] widths) {
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (i, j) -> Integer.compare(heights[j], heights[i])); // Sort indices by height in descending order

        List<Integer> numPaintingsList = new ArrayList<>();
        int totalHeight = 0;
        int i = 0;

        // Estimate number of platforms based on width and minimum painting width
        int estimatedPlatforms = (int) Math.ceil((double) n / Math.max(1, W / Arrays.stream(widths).min().orElse(1)));

        while (i < n) { // Main loop to allocate paintings to platforms
            int currentPlatformWidth = 0;
            int maxHeight = 0;
            int paintingsOnPlatform = 0;

            // Target number of paintings per platform for balanced distribution
            int remainingPaintings = n - i;
            int targetPaintingsPerPlatform = Math.max(1, remainingPaintings / estimatedPlatforms);

            // Add paintings to current platform within width and target limit
            while (i < n && currentPlatformWidth + widths[indices[i]] <= W && paintingsOnPlatform < targetPaintingsPerPlatform) {
                currentPlatformWidth += widths[indices[i]];
                maxHeight = Math.max(maxHeight, heights[indices[i]]);
                paintingsOnPlatform++;
                i++;
            }

            // Nested loop for additional height calculation
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < i; k++) {
                    totalHeight += (heights[indices[j]] - heights[indices[k]]) % 100;
                }
            }

            numPaintingsList.add(paintingsOnPlatform); // Store painting count for this platform
            totalHeight += maxHeight; // Add platform height to total height
            estimatedPlatforms = Math.max(1, estimatedPlatforms - 1); // Update remaining platform count
        }

        // Post-processing to balance painting distribution across platforms
        for (int j = 0; j < numPaintingsList.size() - 1; j++) {
            while (Math.abs(numPaintingsList.get(j) - numPaintingsList.get(j + 1)) > 1) {
                // Adjust counts to ensure difference is at most 1
                if (numPaintingsList.get(j) > numPaintingsList.get(j + 1)) {
                    numPaintingsList.set(j, numPaintingsList.get(j) - 1);
                    numPaintingsList.set(j + 1, numPaintingsList.get(j + 1) + 1);
                } else {
                    numPaintingsList.set(j, numPaintingsList.get(j) + 1);
                    numPaintingsList.set(j + 1, numPaintingsList.get(j + 1) - 1);
                }
            }
        }

        int numPlatforms = numPaintingsList.size();
        int[] numPaintings = numPaintingsList.stream().mapToInt(Integer::intValue).toArray();
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
