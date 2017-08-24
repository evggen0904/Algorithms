package com.mycompany.stacksqueues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.Toolkit.getDefaultToolkit;

public class CashBoxGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
    private final int MAX_SIZE = 10;
    PaintPanel paintPanel;
    ButtonPanel buttonPanel;
    ArrayList<QueueThread> queues = new ArrayList<QueueThread>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CashBoxGUI();
            }
        });
    }

    CashBoxGUI(){
        setTitle("Queue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int y = screenSize.height/2 - getHeight()/2;
        int x = screenSize.width/2 - getWidth()/2;
        setLocation(x, y);

        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
        add(basic);
        paintPanel = new PaintPanel(queues);
        paintPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel = new ButtonPanel(queues, paintPanel);

        basic.add(paintPanel);
        basic.add(buttonPanel);

        generateDefaultQueues();

        setVisible(true);
    }

    private void generateDefaultQueues(){
        Queue q1 = new Queue(10);
        q1.insert(10);
        q1.insert(20);
        q1.insert(30);
        q1.insert(40);
        q1.insert(50);
        Queue q2 = new Queue(10);
        q2.insert(40);
        q2.insert(50);
        Queue q3 = new Queue(10);
        q3.insert(40);
        q3.insert(50);
        q3.insert(60);
        q3.insert(70);
        q3.insert(80);
        Queue q4 = new Queue(10);
        q4.insert(40);
        q4.insert(50);

        queues.add(new QueueThread(q1, paintPanel));
        queues.add(new QueueThread(q2, paintPanel));
        queues.add(new QueueThread(q3, paintPanel));
        queues.add(new QueueThread(q4, paintPanel));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        createErrorMessage(e);
    }

    private void createErrorMessage(Throwable e){
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        String msg;
        if(stackTraceElements.length == 0){
            msg = "Пустой StackTrace";
        } else {
            msg = e.getClass().getCanonicalName() + ": " + e.getMessage() + "\n" +
                    stackTraceElements[0].toString();
        }
        JOptionPane.showMessageDialog(null, msg, "Exception: ", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}

