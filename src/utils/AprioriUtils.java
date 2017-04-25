package utils;

import list.ItemSet;
import list.Transaction;

import java.util.*;

/*
 * Contains utility methods for Apriori algorithm
 */
public class AprioriUtils
{

// Returns a transaction object for the input txn record.
    public static Transaction getTransaction(int id, String txnRecord) {
        String currLine = txnRecord.trim();
        String[] words = currLine.split(" ");
        Transaction transaction = new Transaction(id);

        for (int i = 0; i < words.length; i++) {
            transaction.add(Integer.parseInt(words[i].trim()));
        }

        return transaction;
    }


// Determines if an item with the specified frequency has minimum support or not.
    public static boolean hasMinSupport(double minSup, int numTxns, int itemCount) {
        /** TODO **/
         return true;
   }


//    L_k : frequent itemSets of length k.
//    C_k : candidate frequent itemSets of length k.

//  1) Self-join L_k to create itemSets of size k+1.
//  2) For each subset of the above itemSets, we check if it is in L_k; we do this check approximately by keeping hashcode of itemsets in L_k in a set and check against this set.
//     If all of the subsets are in L_k, then we add the itemSet to C_(k+1).   (pruning)
//  3) Add the itemSets in C_(k+1) into the Trie (implemented in the previous assignment).

/*
  In self-joining step, you can only generate the (k+1)-itemSet with k-itemSets having the same itemSet up to (k-1) items
  For example,
      Case 1
        Suppose we have 2 itemSet that have length 3 (list[0..2])
          [1, 2, 3], [1, 2, 4]

        In this case, you should check that the itemSets are the same from index 0 to index 1 and generate 4-newItemSet and pruning.

      Case 2
        Suppose we have 2 itemSet the have length 3 (list[0..2])
          [1, 2, 3], [2, 3, 4]

       In this case, you should only check index 0 and break.
       you don't need to generate the (k+1) itemSet

       Think about why consider generating (k+1)-newItemSet only for the same thing up to (k-1) consecutively.
*/

    public static List<ItemSet> getCandidateItemSets(List<ItemSet> prevPassItemSets, int itemSetSize) {
        List<ItemSet> candidateItemSets = new ArrayList<>();
        Map<Integer, ItemSet> itemSetMap = generateItemSetMap(prevPassItemSets);
        Collections.sort(prevPassItemSets);
        int prevPassItemSetsSize = prevPassItemSets.size();
        for (int i = 0; i < prevPassItemSetsSize; i++) {
            int j = i + 1;
            while (isCandidateGenerationPossible(prevPassItemSets.get(i), prevPassItemSets.get(j))) {
                ItemSet candidate = generateCandidateItemSet(prevPassItemSets.get(i), prevPassItemSets.get(j));
                if (prune(itemSetMap, candidate)) {
                    candidateItemSets.add(candidate);
                }
                j++;
            }
        }
        return candidateItemSets;
    }

    private static boolean isCandidateGenerationPossible(ItemSet itemSet1, ItemSet itemSet2) {
        int size = itemSet1.size();
        for (int i = 0; i < size-1; i++) {
            if (!Objects.equals(itemSet1.get(i), itemSet2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static ItemSet generateCandidateItemSet(ItemSet itemSet1, ItemSet itemSet2) {
        ItemSet candidate = new ItemSet();
        candidate.addAll(itemSet1);
        candidate.add(itemSet2.get(itemSet2.size()-1));
        return candidate;
    }


// Generates a map of hashcode and the corresponding ItemSet. Since multiple entries can
// have the same hashcode, there would be a list of ItemSets for any hashcode.
// It is used to verify that the subset of C_(K+1) belongs to L_K during the pruning process.

    public static Map<Integer, ItemSet> generateItemSetMap(List<ItemSet> itemSets) {
        Map<Integer, ItemSet> itemSetMap = new HashMap<>();
        for (ItemSet itemSet : itemSets) {
            int hashCode = itemSet.hashCode();
            if (!itemSetMap.containsKey(hashCode)) {
                itemSetMap.put(hashCode, itemSet);
            }
        }
        return itemSetMap;
    }

// This method checks that the subset of C_(K+1) belongs to L_K.
// If all of the subsets are in L_K, then it return true, otherwise false.
// Refer to Lecture 9.

    static boolean prune(Map<Integer, ItemSet> itemSetsMap, ItemSet newItemSet) {
        List<ItemSet> subsets = getSubSets(newItemSet);
        for (ItemSet subItemSet : subsets) {
            int hashCodeToSearch = subItemSet.hashCode();
            if (!itemSetsMap.containsKey(hashCodeToSearch)) {
                return false;
            }
        }
        return true;
    }

//  Generate all possible k-1 subsets for ItemSet (preserves order)
    static List<ItemSet> getSubSets(ItemSet itemSet) {
        List<ItemSet> subSets = new ArrayList<>();
        generateSubSets(subSets, itemSet, new ItemSet(), 0 , itemSet.size()-1);
        return subSets;
    }

    static void generateSubSets(List<ItemSet> subSets, ItemSet itemSet, ItemSet subset, int index, int size) {
        while (subset.size() < size) {
            subset.add(itemSet.get(index));
            generateSubSets(subSets, itemSet, subset, index+1, size);
            subset.remove(itemSet.get(index));
            index++;
        }
        if (!subSets.contains(itemSet)) {
            subSets.add(subset);
        }
    }
}
