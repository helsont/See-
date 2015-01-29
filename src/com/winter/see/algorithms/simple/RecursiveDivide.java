package com.winter.see.algorithms.simple;
import java.util.ArrayList;


public class RecursiveDivide {

    private ArrayList<Integer> list = new ArrayList<Integer>();
    private static int QUARTER = 25, DIME = 10, NICKEL = 5;

    public RecursiveDivide(int amount) {
        if(amount%5!=0)
            System.out.println(amount + " can't be changed");
        else
            System.out.println("Number of possibilities = " + divide(amount));
    }

    public void print(int combinations, ArrayList<Integer> list) {
        System.out.print(combinations + ": ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    public boolean isLessThanEqualToPrevious(int val, int idx) {
        if(idx==0)
            return true;
        return val <= list.get(idx-1);
    }
    /**
     * Divides change into quarters, dimes, and nickels. Outputs combinations as it runs.
     *
     * @param combinations The number of combinations found
     * @param depth The depth of the search tree
     * @param change The change to divide
     * @param currency The previous coin found
     * @return The number of possibilities
     */
    public int divide(int combinations, int depth, int change, int currency) {
        int nickels = change/5;

        // We can only add a quarter if there are more than 5 nickels. Also,
        // the previous coin cannot be less than the current one. (Repeats
        // checked possibilities.
        if(nickels>=5 && isLessThanEqualToPrevious(QUARTER, depth)){
            // We have at least one quarter, add it to the list.
            list.add(25);
            combinations = divide(combinations, depth+1, change-QUARTER, QUARTER);
            // Now that we have exhausted that tree of combinations, clear the left overs.
            clear(list,depth);
        }

        if(nickels>=2  && isLessThanEqualToPrevious(DIME, depth)){
            // We have at least one dime.
            list.add(10);
            combinations = divide(combinations, depth +1, change-DIME, DIME);
            clear(list,depth);
        }

        if(nickels>=1 && isLessThanEqualToPrevious(NICKEL, depth)){
            // We have at least one nickel.
            list.add(5);
            combinations = divide(combinations, depth + 1, change-NICKEL, NICKEL);
            // Don't clear the list because we will need to output the combinations.
        }

        // If there are no more coins possible, we've reached the end of the branch.
        if(nickels==0) {
            // Output the branch
            print(combinations, list);
            // One more combination as been found.
            combinations++;
        }
        return combinations;
    }

    public int divide(int change) {
        // Start with largest possible coin for division
        return divide(0,0,change,QUARTER);
    }

    public void clear(ArrayList<Integer> arr, int start) {
        for(int i =list.size()-1; i >= start; i--){
            arr.remove(i);
        }
    }

    public static void main(String[]args) {
        new RecursiveDivide(Integer.parseInt("45"));
    }
}