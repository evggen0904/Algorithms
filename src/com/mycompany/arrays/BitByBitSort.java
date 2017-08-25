package com.mycompany.arrays;


import java.util.LinkedList;

class ArrayBitByBit {
    private long[] theArray;          // ref to array theArray
    private int nElems;               // number of data items
    //--------------------------------------------------------------
    public ArrayBitByBit(int max)          // constructor
    {
        theArray = new long[max];      // create the array
        nElems = 0;                    // no items yet
    }
    //--------------------------------------------------------------
    public void insert(long value)    // put element into array
    {
        theArray[nElems] = value;      // insert it
        nElems++;                      // increment size
    }
    //--------------------------------------------------------------
    public void display()             // displays array contents
    {
        System.out.print("A=");
        for(int j=0; j<nElems; j++)    // for each element,
            System.out.print(theArray[j] + " ");  // display it
        System.out.println("");
    }

    public void sort(){
        int length = getMaxLengthDec();
        bitByBitSort(length, 10);
    }

    private void bitByBitSort(int length, int range){
        LinkedList<Long>[] lists = new LinkedList[range];
        for (int i = 0; i < range; i++) {
            lists[i] = new LinkedList<Long>();
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < nElems; j++) {
                int index = (int)(theArray[j] % (int)Math.pow(range, i+1))/(int)(Math.pow(range, i));
                lists[index].add(theArray[j]);
            }

            int k = 0;
            for(LinkedList<Long> list : lists){
                while (!list.isEmpty()){
                    theArray[k++] = list.remove(0);
                }
            }
        }

    }

    private int getMaxLengthDec(){
        long max = theArray[0];
        for (int i = 1; i < nElems; i++) {
            if (theArray[i] > max)
                max = theArray[i];
        }
        int length = 0;
        while (max > 0){
            max = max/10;
            length++;
        }
        return  length;
    }
}

class DemoBitByBit{
    public static void main(String[] args) {
        int maxSize = 10;             // array size
        ArrayBitByBit arr = new ArrayBitByBit(maxSize);  // create the array

        for(int j=0; j<maxSize; j++)  // fill array with
        {                          // random numbers
            long n = (long)(java.lang.Math.random()*1000);
            arr.insert(n);
        }
    /*    arr.insert(0b1111);
        arr.insert(0x1111);
        arr.insert(0b10110);*/

        System.out.println("Initial array: ");
        arr.display();
        arr.sort();
        System.out.println("Sorted array: ");
        arr.display();

//        System.out.println("one".getBytes()[0]);
//        System.out.println( "one".getBytes()[0] >> 1);
//        System.out.println(6>>1&(-1));


    }
}
