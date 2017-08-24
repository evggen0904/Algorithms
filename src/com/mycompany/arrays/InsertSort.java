package com.mycompany.arrays;

class ArrayIns
{
    private long[] a;                 // ref to array a
    private int nElems;               // number of data items
    //--------------------------------------------------------------
    public ArrayIns(int max)          // constructor
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
        System.out.print("{");
        for(int j=0; j<nElems; j++)       // for each element,
            System.out.print(a[j] + ", ");  // display it
        System.out.print("}");
        System.out.println("");
    }

    public void changeValues(int source, int dest){
        long tmp = a[source];
        a[source] = a[dest];
        a[dest] = tmp;
    }
    //--------------------------------------------------------------
    public void insertionSort()
    {
        int in, out;
        long iter = 0;
        for(out=1; out<nElems; out++)     // out is dividing line
        {
            long temp = a[out];            // remove marked item
            in = out;                      // start shifts at out
            while(in>0) // until one is smaller,
            {
                if (a[in-1] >= temp) {
                    a[in] = a[in - 1];            // shift item to right
                    iter += 2;                    // go left one position
                    --in;
                }
                else
                    break;
            }
            a[in] = temp;                  // insert marked item
            iter++;
        }  // end for

        System.out.println("iterations: " + iter);
    }  // end insertionSort()

    public long median(){
        if (nElems%2 != 0)
            return a[nElems/2];
        else
            return (a[nElems/2]+ a[nElems/2 -1])/2;
    }

    public void noDups(){
        long[] tmpArr = new long[a.length];
        int dupsCnt = 0;
        int newSize = nElems;
        int step = 0;
        for (int i = 1; i <= nElems; i++) {
            if (a[i-1]==a[i]){
                dupsCnt++;
            }
            else{
                tmpArr[step++] = a[i-1];
                newSize -= dupsCnt;
                dupsCnt = 0;
            }
        }
        a = tmpArr;
        nElems = newSize;
    }
//--------------------------------------------------------------
}  // end class ArrayIns
////////////////////////////////////////////////////////////////
class InsertSortApp
{
    public static void main(String[] args)
    {
        int maxSize = 10000;            // array size
        ArrayIns arr;                 // reference to array
        arr = new ArrayIns(maxSize);  // create the array

        arr.insert(77);               // insert 10 items
        arr.insert(99);
        arr.insert(44);
        arr.insert(55);
        arr.insert(22);
        arr.insert(88);
        arr.insert(11);
        arr.insert(00);
        arr.insert(66);
        arr.insert(33);
        arr.insert(33);
        arr.insert(33);
        arr.insert(66);
        arr.insert(22);
        arr.insert(22);
        arr.insert(59);

//        randomFillArr(arr, maxSize);
        arr.display();                // display items

        arr.insertionSort();          // insertion-sort them

        arr.display();                // display them again

//        System.out.println("median is: " + arr.median());
//        arr.noDups();
//        arr.display();
    }  // end main()

    public static void randomFillArr(ArrayIns a, int maxSize) {
        for (int j = 0; j < maxSize; j++) // Заполнение массива
        { // случайными числами
            long n = (long) (java.lang.Math.random() * (maxSize - 1));
            a.insert(n);
        }
        a.insertionSort();
        a.changeValues(0, maxSize-1);
        a.changeValues(4, 5000);
        a.changeValues(999, 2999);
    }
}  // end class InsertSortApp
