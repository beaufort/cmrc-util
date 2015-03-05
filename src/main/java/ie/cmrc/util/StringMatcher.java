/* 
 * Copyright 2015 Coastal and Marine Research Centre (CMRC), Beaufort,
 * Environmental Research Institute (ERI), University College Cork (UCC).
 * Yassine Lassoued <y.lassoued@gmail.com, y.lassoued@ucc.ie>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ie.cmrc.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A utility class for computing similarity between two strings based on the
 * overlap between their respective sets of adjacent 2-character sub-strings.
 * 
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public class StringMatcher {

    /** 
     * 
     * @return an array of adjacent letter pairs contained in the input string
     */
    private static String[] letterPairs(String str) {
        int numPairs = str.length()-1;
        String[] pairs;
        if (numPairs>0) pairs = new String[numPairs];
        else pairs = new String[0];
        for (int i=0; i<numPairs; i++) {
            pairs[i] = str.substring(i,i+2);
        }
        return pairs;
    }


    /**
     * 
     * @return an ArrayList of 2-character Strings.
     */
   private static ArrayList wordLetterPairs(String str) {
       ArrayList allPairs = new ArrayList();
       // Tokenize the string and put the tokens/words into an array
       String[] words = str.split("\\s");
        // For each word
        for (String word : words) {
            // Find the pairs of characters
            String[] pairsInWord = letterPairs(word);
            allPairs.addAll(Arrays.asList(pairsInWord));
        }
       return allPairs;
   }


   /**
    * computes the lexical similarity between two strings based on the
    * overlap between their respective sets of adjacent 2-character sub-strings
    * @param str1 First string
    * @param str2 Second string
    * @return lexical similarity value in the range [0,1]
    */
   public static double compareStrings(String str1, String str2) {
        if (str1!=null && str2!=null) {
            ArrayList pairs1 = wordLetterPairs(str1.toUpperCase());
            ArrayList pairs2 = wordLetterPairs(str2.toUpperCase());
            int intersection = 0;
            int union = pairs1.size() + pairs2.size();
            for (Object pair1 : pairs1) {
                for(int j=0; j<pairs2.size(); j++) {
                    Object pair2=pairs2.get(j);
                    if (pair1.equals(pair2)) {
                        intersection++;
                        pairs2.remove(j);
                        break;
                    }
                }
            }
           return (2.0*intersection)/union;
        }
        else return 0;
   }



}
