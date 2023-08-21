import java.util.ArrayList;

public class MergeSort {

    public static void merge(ArrayList<String> arr, int l, int m, int r, int[] counters) {

        // Creating copy arrays to store the left and right arrays we are going to be mergeing from the input array
        ArrayList<String> left = new ArrayList<String>(arr.subList(l, m+1));
        ArrayList<String> right = new ArrayList<String>(arr.subList(m+1, r+1));

        // The pointer is the current location of the array where we going to be inserting sorted words into
        int pointer = l;

        // While we still have items in both the left and right arrays
        while (!(left.isEmpty() || right.isEmpty())) {

            // Comparaison counter
            counters[0]++;

            // Check to see which item comes first.
            if (left.get(0).compareToIgnoreCase(right.get(0)) < 0) {
                // Left item comes first so we remove this from the left array and set the pointer position to the array to it's value.
                // Pointer location incremented after setting value.
                arr.set(pointer++, left.remove(0));
            } else {
                // Right item comes first so we remove this from the right array and set the pointer position to the array to it's value.
                // Pointer location incremented after setting value.
                arr.set(pointer++, right.remove(0));
            }

            // Move counter
            counters[1]++;
        }

        // Any remaining items in the left array once right array is emptry
        while (!left.isEmpty()) {
            arr.set(pointer++, left.remove(0));

            // Move counter
            counters[1]++;
        }

        // Any remaining items in the right array once left array is emptry
        while (!right.isEmpty()) {
            arr.set(pointer++, right.remove(0));

            // Move counter
            counters[1]++;
        }
    }

    public static void mergeSort(ArrayList<String> arr, int l, int r, int[] counters) {
        // Check to see if we have more than one element to sort
        if (l < r) {
            // Finding middle index between l and r indexs
            int m = Math.floorDiv((l+r), 2);

            // Sort the array between indexes l and m
            mergeSort(arr, l, m, counters);
            // Sort the array between indexes m+1 and r
            mergeSort(arr, m+1, r, counters);

            // Merge the two sorted sections of the array
            merge(arr, l, m, r, counters);
        }
    }

    public static long average(long[] arr) {
        // Method to calculate the average from a given array
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum / arr.length;
    }

    public static void main(String[] args) {

        // Check if expected number of argurments have been provided
        if (args.length != 2) {
            System.err.println("2 argurments are required.\n- First argurment the reference words,\n- Second argurment the test file.");
            System.exit(1);
        }

        // Import and fiter words using code from Q1
        ArrayList<String> words = InputWords.inputAndFilterWords(args[0], args[1]);

        // Check to see if we have any words to sort
        if (words.size() == 0) {
            System.out.println("There are no words to sort.\nPlease check the correct files have been provided.");
            System.exit(0);
        }

        // ArrayList to store ArrayLists of words
        ArrayList<ArrayList<String>> words100 = new ArrayList<ArrayList<String>>();

        // Adding a new Arraylist to words100 which contains first x number of words, where x starts at 100 an increses in increments of 100.
        for (int i = 100; i < words.size(); i+=100) {
            words100.add(new ArrayList<String>(words.subList(0, i)));
        }

        // Adding the full word list to the list of ArrayLists.
        words100.add(new ArrayList<String>(words));

        for (ArrayList<String> arr : words100) {
            // Array to store timings
            long[] times = new long[100000];

            // Counter array to store number of moves and comparisions
            int[] counters = {0, 0};

            // Doing some iterations to warm up JVM before timing
            for (int i = 0; i < 1000; i++) {
                // Creating new copy of the ArrayList to sort
                ArrayList<String> arrayToSort = new ArrayList<String>(arr);

                // Resetting counter variables so that they aren't ever increasing ad potentially overflow
                counters[0] = 0;
                counters[1] = 0;

                // Sort the array
                mergeSort(arrayToSort, 0, arrayToSort.size()-1, counters);
            }

            // Timing the merge sort method muiltiple times
            for (int i = 0; i < 100000; i++) {
                // Creating new copy of the ArrayList to sort
                ArrayList<String> arrayToSort = new ArrayList<String>(arr);

                // Resetting counter variables so that they aren't ever increasing ad potentially overflow
                counters[0] = 0;
                counters[1] = 0;

                // Start time
                long start = System.nanoTime();

                // Sort the array
                mergeSort(arrayToSort, 0, arrayToSort.size()-1, counters);

                // End time
                long end = System.nanoTime();

                // Save the time of this iteration to an array
                times[i] = end-start;
            }

            System.out.println("First " + arr.size() + " words comparisions: " + counters[0] + ", moves: " + counters[1] + ", avarage timing: " + average(times));
        }
    }
}
