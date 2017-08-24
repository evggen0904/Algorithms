package com.mycompany.stacksqueues;


public class MyStack extends Dequeue {
    public MyStack(int s) {
        super(s);
    }

    @Override
    public void insertLeft(long j) {
//        super.insertLeft(j);
    }

    @Override
    public long removeLeft() {
//        return super.removeLeft();
        return -1;
    }
}

class MyStackApp{
    public static void main(String[] args) {
        MyStack stack = new MyStack(10);

    }
}