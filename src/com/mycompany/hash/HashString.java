package com.mycompany.hash;



class HashTableString
{
    private DataItem[] hashArray;    // array holds hash table
    private int arraySize;
    private DataItem nonItem;        // for deleted items
    private float loadFactor;
    private int numItems;

    private class DataItem
    {
        private String value;

        public DataItem(String data)
        {
            value = data;
        }

        public String getKey()
        { return value; }
    }
    // -------------------------------------------------------------
    public HashTableString(int size)
    {
        arraySize = size;
        hashArray = new DataItem[arraySize];
        nonItem = new DataItem(null);
        loadFactor = 0;
        numItems = 0;
    }
    // -------------------------------------------------------------
    public void displayTable()
    {
        System.out.print("Table: ");
        for(int j=0; j<arraySize; j++)
        {
            if(hashArray[j] != null)
                System.out.print(hashArray[j].getKey() + " ");
            else
                System.out.print("** ");
        }
        System.out.println("");
    }
    // -------------------------------------------------------------
    public int hashFunc(String key)
    {
        int hashVal = key.charAt(0) - 96;
        for (int i = 1; i < key.length() ; i++) {
            int letter = key.charAt(i) - 96;
            hashVal = ((hashVal*27) + letter) % arraySize;
        }
        return hashVal;
    }
    // -------------------------------------------------------------
    public void insert(String key) // insert a DataItem
    {
        if (loadFactor > 0.5)
            rehash();
        DataItem item = new DataItem(key);
        int hashVal = hashFunc(key);  // hash the key
        // until empty cell or -1,
        while(hashArray[hashVal] != null)
        {
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;      // wraparound if necessary
        }
        hashArray[hashVal] = item;    // insert item
        numItems++;
        loadFactor = (float)numItems/arraySize;
    }

    private void rehash(){
        arraySize = getNewArraySize(arraySize);
        DataItem[] newArr = new DataItem[arraySize];
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null){
                String key = hashArray[i].getKey();
                int hashVal = hashFunc(key);

                while(newArr[hashVal] != null)
                {
                    ++hashVal;
                    hashVal %= arraySize;
                }
                newArr[hashVal] = hashArray[i];
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
    // -------------------------------------------------------------
    public DataItem delete(String key)  // delete a DataItem
    {
        int hashVal = hashFunc(key);  // hash the key

        while(hashArray[hashVal] != null)  // until empty cell,
        {                               // found the key?
            if(hashArray[hashVal].getKey().equals(key))
            {
                DataItem temp = hashArray[hashVal]; // save item
                hashArray[hashVal] = nonItem;       // delete item
                return temp;                        // return item
            }
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;      // wraparound if necessary
        }
        return null;                  // can't find item
    }  // end delete()
    // -------------------------------------------------------------
    public DataItem find(String key)    // find item with key
    {
        int hashVal = hashFunc(key);  // hash the key

        while(hashArray[hashVal] != null)  // until empty cell,
        {                               // found the key?
            if(hashArray[hashVal].getKey().equals(key))
                return hashArray[hashVal];   // yes, return item
            ++hashVal;                 // go to next cell
            hashVal %= arraySize;      // wraparound if necessary
        }
        return null;                  // can't find item
    }
// -------------------------------------------------------------
    public int size(){
        return arraySize;
    }
}

class DemoHashString{
    public static void main(String[] args) {
        HashTableString hashTable = new HashTableString(13);
        hashTable.insert("hello");
        hashTable.insert("how are you");
        hashTable.insert("one");
        hashTable.insert("two");
        hashTable.insert("three");
        hashTable.insert("four");
        hashTable.insert("five");
        System.out.println("old size: " + hashTable.size());
        hashTable.displayTable();
        hashTable.insert("six");
        hashTable.displayTable();
        System.out.println("new size: " + hashTable.size());
    }
}