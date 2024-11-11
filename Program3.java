import java.util.ArrayList;
import java.util.Scanner;

class Program3{
    public record Result(int numPlatforms, int totalHeight, int[] numPaintings) {}
   
    /**
    * Solution to program 3
    * @param n number of paintings
    * @param w width of the platform
    * @param heights array of heights of the paintings
    * @param widths array of widths of the paintings
    * @return Result object containing the number of platforms, total height of the paintings and the number of paintings on each platform
    */

    private static int minTotalHeight = Integer.MAX_VALUE;
    private static int bestNumPlatforms = 0; 
    private static int[] bestNumPaintings;

    private static Result program3(int n, int w, int[] heights, int[] widths) {
       
        bestNumPaintings = new int[n];

        findMinHeight(heights, widths, w, 0, new ArrayList<>(), 0, new ArrayList<>());
        
        int[] finalNumPaintings = new int[bestNumPlatforms];
        return new Result(bestNumPlatforms, minTotalHeight, finalNumPaintings);
        //time: O(n^(2n-1))
    }

    private static void findMinHeight(int[] heights, int[] widths, int W, int index, ArrayList<Integer> currentRow, int currentHeight, ArrayList<ArrayList<Integer>> platforms){
        int n = heights.length; 

        //base case
        if (index == n) {
            if (currentHeight < minTotalHeight) {
                minTotalHeight = currentHeight;
                bestNumPlatforms = platforms.size();
                for (int i = 0; i < bestNumPlatforms; i++) {
                    bestNumPaintings[i] = platforms.get(i).size();
                }
            }
            return;
        }

        int paintingWidth = widths[index];
        int paintingHeight = heights[index];

        //case 1: place painting in new platform / row
        ArrayList<Integer> newPlatform = new ArrayList<>(); 
        newPlatform.add(index);
        platforms.add(newPlatform);
        findMinHeight(heights, widths, W, index+1, newPlatform, currentHeight + paintingHeight, platforms);
        platforms.remove(platforms.size()-1);

        //case 2: place painting in existing platform or row
        if (!currentRow.isEmpty()){
            int currentRowWidth = currentRow.stream().mapToInt(i -> widths[i]).sum();
            int maxRowHeight = currentRow.stream().mapToInt(i -> heights[i]).max().orElse(0);

            if (currentRowWidth + paintingWidth <= W){
                currentRow.add(index);
                int updatedHeight = currentHeight - maxRowHeight + Math.max(maxRowHeight, paintingHeight);
                findMinHeight(heights, widths, W, index+1, currentRow, updatedHeight, platforms);
                currentRow.remove(currentRow.size()-1);
            }
        }
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