import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Shobhit on 11/16/2016.
 */
public class Node {
    HashMap<String, Integer> wordCount = new HashMap<>();
    String actualClassfierValue;
    HashMap<String, Integer> foundCount = new HashMap<>();
    HashSet<String > predictedClassifiers = new HashSet<>();
    String predictClassifierValue;
    public void predictClassifier() {
        int max = 0;
        String predicted = "";
        for(String key: foundCount.keySet())    {
            if(foundCount.get(key)> max)    {
                max = foundCount.get(key);
                predicted = key;
            }
        }
    predictClassifierValue = predicted;
        // used in case of multiple classifications
//        int predictedCount = foundCount.get(predictClassifierValue);
//        for(String key: foundCount.keySet())    {
//            if(foundCount.get(key)>= (int) Math.round(0.90*predictedCount))
//                predictedClassifiers.add(key);
//        }
//        if(predictedClassifiers.size()>2) {
//            System.out.println("Total classifiers found: " + predictedClassifiers.size());
//            StringBuilder sb = new StringBuilder();
//
//        }
        }


    public int sameClassification() {
        if(actualClassfierValue.equalsIgnoreCase(predictClassifierValue))
            return 1;
        else return 0;
    }
}
