package com.mycompany.stacksqueues;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaintPanel extends JPanel {
    private Insets insets;
    private int queueSize;
    private List<QueueThread> queues;
    private final int sideIndent = 20;
    private final int upDownIndent = 20;
    private static int stepWidth = 50;
    private int MAX_ELEMENTS = 10;

    PaintPanel(List<QueueThread> queues){
        this.queues = queues;
    }
    @Override
    protected void paintComponent (Graphics g){
        super.paintComponent(g);

        queueSize = getQueueSize();
        if (queueSize > 0) {
            int paintSize = queues.size() * stepWidth + (queues.size() - 1) * stepWidth / 2;
            int right = getWidth() - upDownIndent;
            if (paintSize > right - sideIndent) {
                stepWidth /= 2;
                paintSize = queues.size() * stepWidth + (queues.size() - 1) * stepWidth / 2;
            }

//        размер на экране, занимаемый очередями
            int indent = (right - sideIndent - paintSize) / 2 + sideIndent;
            int bottom = getHeight() - upDownIndent - stepWidth/2;
            int stepHeight = (bottom - upDownIndent) / queueSize ;

            for (QueueThread q : queues) {
                q.getQueue().drawQueue(g, indent, bottom, stepWidth, stepHeight);
                indent += stepWidth * 3 / 2;
            }
        }
    }

    private int getQueueSize(){
        int size = 0;
        for (QueueThread q: queues) {
            if (q.getQueue().queueSize() > size)
                size = q.getQueue().queueSize();
        }
        if (size == 0 )
            return size;
        return (size > MAX_ELEMENTS) ? size : MAX_ELEMENTS;
    }

}
