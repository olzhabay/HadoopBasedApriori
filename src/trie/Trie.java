package trie;

import java.util.ArrayList;

import list.ItemSet;
import list.Transaction;

/**
 * Trie are used for efficiently searching for a pattern of items in a transaction in frequent
 * itemset mining algorithms. This represents the structure of a Trie.
 */

public class Trie
{
    TrieNode rootNode;
    final int height;

    public Trie(int height) {
        rootNode = new TrieNode();
        this.height = height;
    }

    public boolean add(ItemSet itemSet) {
        /** COMPLETE **/
    }

    public boolean contains(ItemSet itemSet) {
        /** COMPLETE **/
        return true;
    }

    public TrieNode getRootNode() {
        return rootNode;
    }

    public void findItemSets(ArrayList<ItemSet> matchedItemSet, Transaction transaction) {
        /** COMPLETE **/
    }
}

