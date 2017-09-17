package com.mycompany.hash;

import com.mycompany.trees.RedBlackTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ALL")
class HashBinaryTree<K,V>{
    private RedBlackTree<K,V>[] hashArray;    // array holds hash table
    private int arraySize;
    private float loadFactor;
    private int numItems;


    HashBinaryTree(int size){
        arraySize = size;
        hashArray = new RedBlackTree[arraySize];
        loadFactor = 0;
        numItems = 0;
    }

    public int hashFunc(K key)
    {
        int hashVal = 0;
        if (key instanceof String) {
            String sKey = (String)key;
            hashVal = sKey.charAt(0) - 96;
            for (int i = 1; i < sKey.length(); i++) {
                int letter = sKey.charAt(i) - 96;
                hashVal = ((hashVal * 27) + letter) % arraySize;
            }

        }
        else if (key instanceof Integer){
            int iKey = (Integer) key;
            hashVal = iKey % arraySize;
        }
        return hashVal;
    }

    public void insert(K key, V value) // insert a DataItem
    {
        int hashVal = hashFunc(key);  // hash the key
        /*исключаем добавление дубликатов*/
        if (!find(hashVal, key)) {
            /*для связанных списков коэфф заполнения должен не превышать единицу*/
            if (loadFactor > 1)
                rehash();

            if (hashArray[hashVal] == null)
                hashArray[hashVal] = new RedBlackTree<K, V>();
            hashArray[hashVal].insert(key, value);    // insert item
            numItems++;
            loadFactor = (float) numItems / arraySize;
        }
    }

    public boolean find(K key){
        int hashVal = hashFunc(key);
        return find(hashVal, key);
    }

    private boolean find(int hashVal, K key){
        RedBlackTree<K, V> tree = hashArray[hashVal];
        if (tree != null){
            return tree.find(key);
        }
        return false;
    }

    private void rehash(){
        arraySize = getNewArraySize(arraySize);
        RedBlackTree<K,V> [] newArr = new RedBlackTree[arraySize];
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null){
                HashMap<K,V> elements = hashArray[i].getElements();
                Iterator<Map.Entry<K,V>> iterator = elements.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<K,V> element = iterator.next();
                    int hashVal = hashFunc(element.getKey());
                    if (newArr[hashVal] == null)
                        newArr[hashVal] = new RedBlackTree<K,V>();
                    newArr[hashVal].insert(element.getKey(), element.getValue());
                }
            }
        }
        hashArray = newArr;
    }

    private int getNewArraySize(int prevSize){
        int newSize = prevSize*2;
        while(true){
            if(isSimpleNumber(newSize))
                return newSize;
            newSize++;
        }
    }

    private boolean isSimpleNumber(int number){
        int[] dividers = {2,3,5,7};
        for (int i = 0; i < dividers.length; i++) {
            if (number % dividers[i] == 0)
                return false;
        }
        return true;
    }

    public void displayTable()
    {
        System.out.print("Table: ");
        for(int j=0; j<arraySize; j++)
        {
            if(hashArray[j] != null)
                hashArray[j].printTree();
      /*      else
                System.out.println("** ");*/
        }
        System.out.println("");
    }
}

class DemoHashBinaryTree{
    public static void main(String[] args) {
        int initialSize = 29;
        HashBinaryTree<Integer, String> theTree = new HashBinaryTree<>(initialSize);
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            theTree.insert(i, ""+i);
            theTree.insert(i+initialSize, ""+(i+initialSize));
            theTree.insert(i+initialSize*2, ""+(i+initialSize*2));
        }

        theTree.displayTable();

        System.out.println("Search for element '63': " + theTree.find(63));
        System.out.println("Search for element '50': " + theTree.find(50));
    }
}