import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputWords {

    // Method to read words from a file into an array list
    public static ArrayList<String> readFile(String fileName, boolean vocabularyWords) {
        ArrayList<String> words = new ArrayList<String>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file, "UTF-8");

            // If it's a vocabulary word we want to store this in lowercase for the later on.
            if (vocabularyWords) {
                while (reader.hasNext()) {
                    words.add(reader.next().toLowerCase());
                }
            } else {
                while (reader.hasNext()) {
                    words.add(reader.next());
                }
            }
            reader.close();
        }
        // Catch file does not exists exception
        catch (FileNotFoundException e) {
            System.err.println("The file " + fileName + " cannot be found.");
            System.exit(1);
        }
        return words;
    }

    public static void printWords(ArrayList<String> words) {
        // Prints out the words in a given array list to the console window
        for (String word : words) {
            System.out.println(word);
        }
    }

    // Function to check if a test word appears in the vocabulary word list
    public static boolean checkWord(ArrayList<String> vocabularyWords, String test) {

        Pattern pattern = Pattern.compile("[A-Za-z](?:.*[A-Za-z])?");
        Matcher matcher = pattern.matcher(test);

        // Check to see if we have a word provided and not just punctuation
        if (matcher.find()) {
            // The test string will be the input with the leading and trailing punctuation removed
            String testString = matcher.group(0);
            // Check to see if the test string is in the vocabulary
            return vocabularyWords.contains(testString.toLowerCase());
        }

        return false;
    }

    public static ArrayList<String> inputAndFilterWords(String vocabularyFileName, String inputFileName) {

        // Import vocabulary and words from files
        ArrayList<String> vocabularyWords = readFile(vocabularyFileName, true);
        ArrayList<String> input = readFile(inputFileName, false);

        // Iterator to loop through each input word
        // I have used the iterator as this allows me to remove items from the ArrayList while looping through the items.
        // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Iterator.html
        Iterator<String> itr = input.iterator();

        while(itr.hasNext()) {
            // Check if word next is in vocabulary
            if (!checkWord(vocabularyWords, itr.next())) {
                // If word is not in vocabulary we remove from the ArrayList
                itr.remove();
            }
        }
        return input;
    }

    public static void main(String[] args) {

        // Check if expected number of argurments have been provided
        if (args.length != 2) {
            System.err.println("2 argurments are required.\n- First argurment the vocabulary words\n- Second argurment the input file.");
            System.exit(1);
        }

        ArrayList<String> words = inputAndFilterWords(args[0], args[1]);

        if (words.size() > 0) {
            // Output words with word count
            System.out.println("Words are as follows: ");
            printWords(words);
            System.out.println("Total number of words: " + words.size());
        } else {
            System.out.println("There are no words in the input file which exists in the provided vocabulary.\nPlease check the correct files have been provided.");
        }
    }
}
