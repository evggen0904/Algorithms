package com.mycompany.stacksqueues;

public class QueueThread implements Runnable{
    Thread thread;
    Queue queue;
    PaintPanel paintPanel;
    boolean isAlive;

    QueueThread(Queue queue, PaintPanel paintPanel){
        thread = new Thread(this);
        this.queue = queue;
        this.paintPanel = paintPanel;
        isAlive = true;
        thread.start();
    }

    @Override
    public void run() {
        decreaseQueue();
    }

    private void decreaseQueue(){
        while (isAlive) {
            while (!queue.isEmpty()) {
                long value = queue.peekFront();
                try {
                    Thread.sleep(value * 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.remove();
                paintPanel.repaint();
            }

            synchronized (queue) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        System.out.println("Thread " + thread.getName() + " stopped");
    }

    public Queue getQueue(){
        return this.queue;
    }

    public void killThread(){
        isAlive = false;
        synchronized (queue) {
            queue.notify();
        }
    }
}
