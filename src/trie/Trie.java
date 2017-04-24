package trie;

import list.ItemSet;
import list.Transaction;

import java.util.ArrayList;

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
        TrieNode node = rootNode;
        for (Integer item: itemSet) {
            if (!node.containsKey(item)){
                TrieNode newNode = new TrieNode();
                node.put(item, newNode);
                if (item.equals(itemSet.get(itemSet.size()-1))) {
                    newNode.setLeafNode(true);
                    newNode.add(itemSet);
                    return true;
                }
            }
            node = node.get(item);
        }
        return true;
    }

    public boolean contains(ItemSet itemSet) {
        /** COMPLETE **/
        TrieNode node = rootNode;
        for (Integer item: itemSet) {
            if (!node.containsKey(item)) {
                return false;
            }
            node = node.get(item);
        }
        return true;
    }

    public TrieNode getRootNode() {
        return rootNode;
    }

    public void findItemSets(ArrayList<ItemSet> matchedItemSet, Transaction transaction) {
        /** COMPLETE **/
        findItemSets(matchedItemSet, rootNode, transaction, 0);
    }

    public void findItemSets(ArrayList<ItemSet> matchedItemSet,
                             TrieNode node,
                             Transaction transaction,
                             int index) {
        if (node.isLeafNode()) {
            matchedItemSet.add(node.getItemSet());
        }
        for (int i = index; i < transaction.size(); i++) {
            if (node.containsKey(transaction.get(i))) {
                findItemSets(matchedItemSet,
                        node.get(transaction.get(i)),
                        transaction, i+1);
            }
        }
    }
}

