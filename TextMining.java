
import java.io.*;
import java.util.*;

/**
 * This class reads in text files and trains a classifier model. It uses a split of 70-30 for the training and testing data.
 * It uses 30% of the files to test the model and calculates the accuracy.
 */
public class TextMining {
    TreeMap<String, TreeMap<String, Integer>> confMatrix = new TreeMap<>();
    File file;
    File[] files;
    HashSet<String> stopWords = new HashSet<>();
    HashMap<String, HashMap<String, Integer>> folderDocs = new HashMap<>();
    HashSet<Node> testingDocs = new HashSet<>();

    /**
     * main() function reads in the command line arguments that specifies the path to the newsgroups file and the stopwords file.
     * It then calls various functions to read the data, train the model and then test it.
     * @param args      command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // if incorrect number of command line arguments are given
        if (args.length != 2) {
            System.out.println("Enter correct arguments: newsgroup folder path, stop words file path");
            System.exit(0);
        }
        long time1 = System.currentTimeMillis();

        TextMining txtMining = new TextMining();
        txtMining.readData(args[0], args[1]);
        txtMining.setConfusionMatrix();
        float accuracy =  txtMining.testModel();
        System.out.println("PRINTING CONFUSION MATRIX: \n");
        txtMining.printConfusionMatrix();
        System.out.println();
        long time2 = System.currentTimeMillis();
        System.out.println("Accuracy: " + accuracy + "%");
        System.out.println("Time taken in millis: " + ((time2 - time1)/1000) + " seconds");


    } // main

    /**
     * readData() reads in the data and splits it into training and testing data. It then forms the bag of words for
     * each of the classifier values as well as a separate bag of words for each test file.
     * @param path      path where the data is to be read from
     * @param stopWordsPath     path for the stopwords file
     * @throws IOException
     */
    public void readData(String path, String stopWordsPath) throws IOException {
        file = new File(path);
        files = file.listFiles();
        BufferedReader br2 = new BufferedReader(new FileReader(stopWordsPath));
        String line2;
        while ((line2 = br2.readLine()) != null) {
            String words[] = line2.trim().split(",");
            for (String x : words) {
                x = x.trim();
                stopWords.add(x);
            }
        }
        // reading folders and files
        for (File f : files) {
            //going inside one folder at a time
            if (f.isDirectory()) {
                // creating confusion matrix entry
                if (!confMatrix.containsKey(f.getName())) {
                    TreeMap<String, Integer> conf = new TreeMap<>();
                    conf.put(f.getName(), 0);
                    confMatrix.put(f.getName(), conf);
                }
                //go inside the directory,take 60 %files for training,and 40 for testing
                HashMap<String, Integer> wordCount = new HashMap<>();
                File[] filesinside = f.listFiles();
                //70% of files for training
                int trainingCount = (int) (filesinside.length * 0.7);
                int testingCount = filesinside.length - trainingCount;
                // going inside each file one at a time
                for (int i = 0; i < trainingCount; i++) {
                    FileReader f1 = new FileReader(filesinside[i]);
                    BufferedReader br = new BufferedReader(f1);
                    String line = "";
                    // for each line, switching to lower case
                    while ((line = br.readLine()) != null) {
                        if(line.indexOf("Newsgroups: ")!= 0 || line.indexOf("newsgroups: ") == 0)    {
                            line = line.toLowerCase();

                            String[] words = line.split(" ");
                            for (String x : words) {
                                x = x.trim();
                                if (x.matches("[a-zA-Z]+")) {
                                    // checking if the word is a stop word
                                    if (!stopWords.contains(x)) { // not a stop word
                                        if (wordCount.containsKey(x)) // if the word already exists
                                            wordCount.put(x, wordCount.get(x) + 1);
                                        else
                                            wordCount.put(x, 1);
                                    }
                                }
                            } // for each word
                        }
                    }// single test file
                }
                // adding data to folder docs
                folderDocs.put(f.getName(), wordCount);
                // for testing data
                for (int i = trainingCount; i < filesinside.length; i++) {
                    FileReader f1 = new FileReader(filesinside[i]);
                    BufferedReader br = new BufferedReader(f1);
                    String line = "";
                    // for each line, switching to lower case
                    wordCount = new HashMap<>();
                    while ((line = br.readLine()) != null) {
                        line = line.toLowerCase();
                        //System.out.println(line);
                        String[] words = line.split(" ");
                        for (String x : words) {
                            x = x.trim();
                            if (x.matches("[a-zA-Z]+")) {
                                // checking if the word is a stop word
                                if (!stopWords.contains(x)) { // not a stop word
                                    if (!wordCount.containsKey(x)) // if the word already exists
                                        wordCount.put(x, 1);
                                    else
                                        wordCount.put(x, wordCount.get(x) + 1);
                                }
                            }
                        } // for each word
                    }// single test file
                    Node n1 = new Node();
                    n1.wordCount = wordCount;
                    n1.actualClassfierValue = f.getName();
                    testingDocs.add(n1);
                } // for testing file in a folder
                // System.out.println("Folder being browsed: " + f.getName());
            }
        }
    }

    /**
     * This function sets the placeholders for the confusion matrix
     */
    public void setConfusionMatrix() {
        // setting up the placeholders in confusion matrix
        for (String confusionMatrixNode : confMatrix.keySet()) {
            for (String confusionMatrixNode2 : confMatrix.keySet()) {
                TreeMap<String, Integer> conf = confMatrix.get(confusionMatrixNode2);
                if (!conf.containsKey(confusionMatrixNode)) {
                    conf.put(confusionMatrixNode, 0);
                }
            }
        }
    }

    /**
     * This function tests the model against the test files and calculates the accuracy of model.
     * It matches the bag of words of the test file with each of the classfier's bag of words and
     * selects the classifier as predicted classifier, whose match is the highest.
     * @return      the value of accuracy
     */
    public float testModel() {
        int totalTestDocs = 0; // total number of testing documents
        int correctlyClassifiedTestDocs = 0;    // total number of correctly classified documents
        Iterator<Node> iterator = testingDocs.iterator();
        // iterating over each test document
        while (iterator.hasNext()) {
            Node n1 = iterator.next();
            totalTestDocs++;
            // picking up a classifier
            for (String folder : folderDocs.keySet()) {
                int countForFolder = 0;
                HashMap folderWords = folderDocs.get(folder);
                HashMap<String, Integer> wordCount = n1.wordCount;
                // checking if the word is present in classifier's bag of words
                for (String singleWord : wordCount.keySet()) {
                    if (folderWords.containsKey(singleWord)) {
                        countForFolder++;
                    }
                }// for each word in the test doc
                n1.foundCount.put(folder, countForFolder);
            }// for each folder
            n1.predictClassifier();
            String actual = n1.actualClassfierValue;
            String predicted = n1.predictClassifierValue;
            TreeMap<String, Integer> conf = confMatrix.get(actual);
            conf.put(predicted, conf.get(predicted) + 1); // setting up the confusion matrix
            confMatrix.put(actual, conf);
            correctlyClassifiedTestDocs += n1.sameClassification();
        }
        // calculating the accuracy
        float accuracy = (float) correctlyClassifiedTestDocs / totalTestDocs * 100;
        return accuracy;
    }

    /**
     * This function prints the confusion matrix
     */
    public void printConfusionMatrix() {
        for (String actual : confMatrix.descendingKeySet()) {
            int len = actual.length();
            int rem = 30 - len;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rem; i++)
                sb.append(" ");

            System.out.print(actual + sb.toString() + "\t");
            TreeMap<String, Integer> conf = confMatrix.get(actual);
            for (String predict : conf.descendingKeySet()) {
                System.out.print(conf.get(predict) + "\t");
            }
            System.out.println();
        }
    }
} // class



