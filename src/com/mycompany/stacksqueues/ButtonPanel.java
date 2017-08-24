package com.mycompany.stacksqueues;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ButtonPanel extends JPanel implements ActionListener {
    private JButton buttonAddElement;
    private JButton buttonNewQueue;
    private JButton buttonRemoveQueue;
    private List<QueueThread> queues;
    private PaintPanel paintPanel;

    ButtonPanel(List<QueueThread> queues, PaintPanel paintPanel){
        this.queues = queues;
        this.paintPanel = paintPanel;
        buttonAddElement = new JButton("Add element to Queue");
        buttonNewQueue = new JButton("Create a new Queue");
        buttonRemoveQueue = new JButton ("Remove empty Queues");
        setAlignmentX(1f);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(buttonNewQueue);
        add(Box.createRigidArea(new Dimension(5,40)));
        add(buttonAddElement);
        add(Box.createRigidArea(new Dimension(5,40)));
        add(buttonRemoveQueue);
        add(Box.createRigidArea(new Dimension(5,40)));

        buttonNewQueue.addActionListener(this);
        buttonAddElement.addActionListener(this);
        buttonRemoveQueue.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == buttonNewQueue){
            Queue q = new Queue(10);
            q.insert(generateRandomUnit());
            queues.add(new QueueThread(q, paintPanel));

            paintPanel.repaint();
        }
        if (src == buttonAddElement){
            Queue q = chooseQueue();
            synchronized (q) {
                if (q != null) {
                    q.insert(generateRandomUnit());
                    paintPanel.repaint();
                    q.notify();
                }
            }
        }
        if (src == buttonRemoveQueue){
            Iterator<QueueThread> iterator = queues.iterator();
            while (iterator.hasNext()){
                QueueThread q = iterator.next();
                if (q.getQueue().isEmpty()) {
                    q.killThread();
                    iterator.remove();
                }
            }
            paintPanel.repaint();
        }
    }

    private Queue chooseQueue(){
        Queue q = null;
        if (queues.size() > 0) {
             q = queues.get(0).getQueue();
            int min = q.queueSize();
            for (QueueThread queue : queues) {
                if (queue.getQueue().queueSize() < min) {
                    min = queue.getQueue().queueSize();
                    q = queue.getQueue();
                }
            }
        }
        return q;
    }

    private int generateRandomUnit(){
        Random random = new Random();
        return 1 + random.nextInt(100);
    }
}
