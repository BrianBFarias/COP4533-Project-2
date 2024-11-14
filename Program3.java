import java.util.ArrayList;
import java.util.Scanner;
class Program3 {
    
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}

    private static Result min_cost(int[] heights, int[] widths, int maxWidth, int n, int i, ArrayList<Integer> paintingsPerRow, int platforms) {
        // Base case: If we have reached the last painting in our array (i == n)
        if (i == n) {
            // Ensure that numPaintings is explicitly an int[]
            return new Result(platforms, 0, paintingsPerRow.stream().mapToInt(Integer::intValue).toArray());
        }
    
        int minCost = Integer.MAX_VALUE; //the minimum cost will be initially set to the maximum value possible
        int currWidth = 0; //will be used to keep track of the width of the row
        int currMaxHeight = 0; //used to keep track of the tallest painting
        
        ArrayList<Integer> currentRow = new ArrayList<>(paintingsPerRow);
        //loop through array starting from the index passed through minimize_cost helper function (or the current index)
        for (int j = i; j < n; j++) { //while the index is less than the number of total paintings, increment by 1
            currWidth += widths[j]; //add the width at this index to the running width
            if (currWidth > maxWidth) { //if the currWidth greater than maxWidth break (implies new row)
                break; 
            }
            currMaxHeight = Math.max(currMaxHeight, heights[j]); //update the currMaxHeight while iterating
            Result result = min_cost(heights, widths, maxWidth, n, j + 1, currentRow, platforms+1); //calculate the cost for the remaining paintings recursively by incrementing by one
            int cost = currMaxHeight + result.totalHeight;
            if (cost < minCost) {
                minCost = cost;
                paintingsPerRow.clear();
                paintingsPerRow.addAll(currentRow); // Copy over the best configuration
            }
        }
    
        // Return the final result with the minimum cost and the optimal configuration of paintings
        return new Result(platforms, minCost, paintingsPerRow.stream().mapToInt(Integer::intValue).toArray());
    }
    
    //idea: try every way to partition paintings into rows and calculate cost (recursively)
    private static int minimize_cost(int[] heights, int[] widths, int maxWidth, int n, int i) {
        //base case (if we have reached the last painting in our array, in other words, when i == n, where n represents the number of paintings)
        if (i == n) {
            return 0; //return 0 because no more rows left to form
        }
        
        int minCost = Integer.MAX_VALUE; //the minimum cost will be initially set to the maximum value possible
        int currWidth = 0; //will be used to keep track of the width of the row
        int currMaxHeight = 0; //used to keep track of the tallest painting
        
        //loop through array starting from the index passed through minimize_cost helper function (or the current index)
        for (int j = i; j < n; j++) { //while the index is less than the number of total paintings, increment by 1
            currWidth += widths[j]; //add the width at this index to the running width
            if (currWidth > maxWidth) { //if the currWidth greater than maxWidth break (implies new row)
                break; 
            }
            currMaxHeight = Math.max(currMaxHeight, heights[j]); //update the currMaxHeight while iterating
            int cost = currMaxHeight + minimize_cost(heights, widths, maxWidth, n, j + 1); //calculate the cost for the remaining paintings recursively by incrementing by one
            minCost = Math.min(minCost, cost); //update local minCost
        }
        
        return minCost; //return local minCost
    }

    private static Result program3(int n, int w, int[] heights, int[] widths) {
        ArrayList<Integer> paintingsPerRow = new ArrayList<>();
        Result bestResult = min_cost(heights, widths, w, n, 0, paintingsPerRow, 0);
        // // Calculate the number of platforms (rows). This is the number of times we make a partition
        // int numPlatforms = 0;
        // int currentWidth = 0;
        // for (int i = 0; i < n; i++) {
        //     currentWidth += widths[i];
        //     if (currentWidth > w) {
        //         numPlatforms++;
        //         currentWidth = widths[i]; // Start a new platform with the current painting
        //     }
        // }
        // numPlatforms++; // Account for the last platform

        // // Create an array to store the number of paintings in each platform
        // int[] numPaintings = new int[numPlatforms];
        // int platformIndex = 0;
        // currentWidth = 0;
        // for (int i = 0; i < n; i++) {
        //     currentWidth += widths[i];
        //     if (currentWidth > w) {
        //         platformIndex++;
        //         currentWidth = widths[i]; // Start a new platform
        //     }
        //     numPaintings[platformIndex]++;
        // }
        System.out.println(bestResult.totalHeight);
        return new Result(bestResult.numPlatforms, bestResult.totalHeight, bestResult.numPaintings);
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
        Result result = program3(n, W, heights, widths);
        System.out.println(result.numPlatforms);
        System.out.println(result.totalHeight);
        for(int i=0; i<result.numPaintings.length; i++){
            System.out.println(result.numPaintings[i]);
        }
    }
}
