package com.mycompany.stacksqueues;


public class Dequeue {
    private int maxSize;
    private long[] queArray;
    private int front;
    private int rear;
    private int nItems;
    //--------------------------------------------------------------
    public Dequeue(int s)          // constructor
    {
        maxSize = s;
        queArray = new long[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
    }
    //--------------------------------------------------------------
    public void insertRight(long j)   // put item at rear of queue
    {
        if(rear == maxSize-1)         // deal with wraparound
            rear = -1;
        queArray[++rear] = j;         // increment rear and insert
        nItems++;                     // one more item
    }

    //--------------------------------------------------------------
    public void insertLeft(long j)
    {
        if(front == 0)
            front = maxSize;
        queArray[--front] = j;
        nItems++;
    }
    //--------------------------------------------------------------
    public long removeLeft()         // take item from front of queue
    {
        long temp = queArray[front++]; // get value and incr front
        if(front == maxSize)           // deal with wraparound
            front = 0;
        nItems--;                      // one less item
        return temp;
    }

    //--------------------------------------------------------------
    public long removeRight()
    {
        long temp = queArray[rear--];
        if(rear == 0)
            rear = maxSize;
        nItems--;
        return temp;
    }
    //--------------------------------------------------------------
    public boolean isEmpty()    // true if queue is empty
    {
        return (nItems==0);
    }
    //--------------------------------------------------------------
    public boolean isFull()     // true if queue is full
    {
        return (nItems==maxSize);
    }
    //--------------------------------------------------------------
    public int size()           // number of items in queue
    {
        return nItems;
    }
    public void display(){
        if (!isEmpty()) {
            int n = 0;
            int i = front;
            while (n++ < nItems) {
                if (i == maxSize)
                    i = 0;
                System.out.print(queArray[i++] + " ");
            }
        }
        System.out.println();
    }
}

class DequeueApp{
    public static void main(String[] args) {
        Dequeue theDeque = new Dequeue(10);

        theDeque.insertRight(10);
        theDeque.insertRight(20);
        theDeque.insertRight(30);

        theDeque.insertLeft(40);
        theDeque.insertLeft(50);
        theDeque.insertLeft(60);

        theDeque.display();
        theDeque.removeLeft();
        theDeque.removeRight();
        theDeque.display();
    }
}