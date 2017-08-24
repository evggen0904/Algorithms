package com.mycompany.arrays;


////////////////////////////////////////////////////////////////
class ArrayBub
{
    private long[] a;                 // ref to array a
    private int nElems;               // number of data items
    //--------------------------------------------------------------
    public ArrayBub(int max)          // constructor
    {
        a = new long[max];                 // create the array
        nElems = 0;                        // no items yet
    }
    //--------------------------------------------------------------
    public void insert(long value)    // put element into array
    {
        a[nElems] = value;             // insert it
        nElems++;                      // increment size
    }
    //--------------------------------------------------------------
    public void display()             // displays array contents
    {
        for(int j=0; j<nElems; j++)       // for each element,
            System.out.print(a[j] + " ");  // display it
        System.out.println("");
    }
    //--------------------------------------------------------------
    public void bubbleSort()
    {
        int out, in;
        long cnt = 0;
        for(out=nElems-1; out>1; out--)   // outer loop (backward)
            for(in=0; in<out; in++)        // inner loop (forward)
                if( a[in] > a[in+1] ) {      // out of order?
                    swap(in, in + 1);          // swap them
                    cnt++;
                }

        System.out.println("bubbleSort cnt = " + cnt);
    }  // end bubbleSort()
    //--------------------------------------------------------------

    public void oddEvenSort(){
        long cnt = 0;
        for(int i=0; i<nElems; i++) {
            boolean isSort = true;
            for (int j = (i % 2 == 0)? 0 : 1; j < nElems - 1; j += 2) {
                if (a[j] > a[j + 1]) {
                    swap(j, j + 1);
                    isSort = false;
                    cnt++;
                }
            }
            if (isSort)
                break;
        }
        System.out.println("oddEvenSort cnt = " + cnt);
    }

    private void swap(int one, int two)
    {
        long temp = a[one];
        a[one] = a[two];
        a[two] = temp;
    }

//--------------------------------------------------------------
}  // end class ArrayBub
////////////////////////////////////////////////////////////////
class BubbleSortApp
{
    public static void main(String[] args)
    {
        int maxSize = 100000;            // array size
        ArrayBub arr;                 // reference to array
        arr = new ArrayBub(maxSize);  // create the array

//        arr.insert(77);               // insert 10 items
//        arr.insert(99);
//        arr.insert(44);
//        arr.insert(55);
//        arr.insert(22);
//        arr.insert(88);
//        arr.insert(11);
//        arr.insert(00);
//        arr.insert(66);
//        arr.insert(33);

        randomFillArr(arr, maxSize);
        arr.display();                // display items

//        arr.bubbleSort();             // bubble sort them
        arr.oddEvenSort();

        arr.display();                // display them again
    }  // end main()

    public static void randomFillArr(ArrayBub a, int maxSize) {
        for (int j = 0; j < maxSize; j++) // Заполнение массива
        { // случайными числами
            long n = (long) (java.lang.Math.random() * (maxSize - 1));
            a.insert(n);
        }
    }
}  // end class BubbleSortApp
////////////////////////////////////////////////////////////////
