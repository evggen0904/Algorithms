package com.mycompany.stacksqueues;

import java.awt.*;
import java.util.Random;

////////////////////////////////////////////////////////////////
@SuppressWarnings("ALL")
class Queue
{
    private int maxSize;
    private long[] queArray;
    private int front;
    private int rear;
    private int nItems;
    private Color color;
    //--------------------------------------------------------------
    public Queue(int s)          // constructor
    {
        maxSize = s;
        queArray = new long[maxSize];
        front = 0;
        rear = -1;
        nItems = 0;
        color = genenerateRandomColor();
    }
    //--------------------------------------------------------------
    public void insert(long j)   // put item at rear of queue
    {
        if(rear == maxSize-1)         // deal with wraparound
            rear = -1;
        queArray[++rear] = j;         // increment rear and insert
        nItems++;                     // one more item
    }
    //--------------------------------------------------------------
    public long remove()         // take item from front of queue
    {
        long temp = queArray[front++]; // get value and incr front
        if(front == maxSize)           // deal with wraparound
            front = 0;
        nItems--;                      // one less item
        return temp;
    }
    //--------------------------------------------------------------
    public long peekFront()      // peek at front of queue
    {
        return queArray[front];
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
    }

    public void drawQueue(Graphics g, int x, int y, int width, int height){
        if (!isEmpty()) {
            int n = 0;
            int i = front;
            while (n < nItems) {
                if (i == maxSize)
                    i = 0;
//                g.setColor(Color.black);

                g.setColor(color);
                g.fillRect(x, y - n*height, width, height);
                g.setColor(Color.black);
                g.drawRect(x, y - n*height, width, height);
                g.drawString(Long.toString(queArray[i++]), x+width/3, y + height*(1-n) - height/4);
                n++;
            }
        }
    }

    public int queueSize(){
        return nItems;
    }

    private Color genenerateRandomColor(){
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.WHITE};
        Random random = new Random();
        int idx = random.nextInt(colors.length);
        return colors[idx];
    }

    public Color getColor(){
        return color;
    }
//--------------------------------------------------------------
}  // end class Queue
////////////////////////////////////////////////////////////////
class QueueApp
{
    public static void main(String[] args)
    {
        Queue theQueue = new Queue(5);  // queue holds 5 items

        theQueue.insert(10);            // insert 4 items
        theQueue.insert(20);
        theQueue.insert(30);
        theQueue.insert(40);

        theQueue.remove();              // remove 3 items
        theQueue.remove();              //    (10, 20, 30)
        theQueue.remove();

        theQueue.insert(50);            // insert 4 more items
        theQueue.insert(60);            //    (wraps around)
        theQueue.insert(70);
        theQueue.insert(80);

        theQueue.display();
        System.out.println("");
    }  // end main()
}  // end class QueueApp
////////////////////////////////////////////////////////////////

