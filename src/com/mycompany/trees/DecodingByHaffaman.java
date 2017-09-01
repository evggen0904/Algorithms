package com.mycompany.trees;

import java.util.*;

class DecodingTree
{
    private Node root;

    DecodingTree()
    {
        root = null;
    }

    public void insert(int iData, char chData)
    {
        Node newNode = new Node();
        newNode.iData = iData;
        newNode.chData = chData;
        if (root == null)
            root = newNode;
    }

    public void buildTree()
    {

    }

    public Node getRoot(){return root;}
}

class PriorityQueue
{
//    private ArrayList<DecodingTree> decodingArray;
    TreeSet<DecodingTree> array;
    private int nElems;

    PriorityQueue()
    {
//        decodingArray = new ArrayList<DecodingTree>();
        array = new TreeSet<DecodingTree>(new Comparator<DecodingTree>() {
            @Override
            public int compare(DecodingTree o1, DecodingTree o2) {
                return o1.getRoot().iData - o2.getRoot().iData;
            }
        });
        nElems = 0;
    }

    public void insert(DecodingTree tree)
    {
        Node root = tree.getRoot();
        int priority = root.iData;

        array.add(tree);
 /*       if (nElems == 0) {
            decodingArray.add(tree);
            nElems++;
        }
        else {
            int i;
            for (i = decodingArray.size()-1; i <= 0; i--) {
                DecodingTree previousTree = decodingArray.get(i);
                if (priority > previousTree.getRoot().iData) {
                    decodingArray.add(previousTree);
                    decodingArray.
                }
                else
                    break;
            }
            decodingArray.add(i, tree);
        }*/
    }

    public void displayArray()
    {
        Iterator<DecodingTree> it = array.iterator();
        while (it.hasNext()){
            System.out.print(it.next().getRoot().chData + " ");
        }
    }
}

class DemoDecoding
{
    public static void main(String[] args) {
        PriorityQueue theQueue = new PriorityQueue();
        DecodingTree tree = new DecodingTree();
        tree.insert(10, 'A');
        theQueue.insert(tree);
        tree = new DecodingTree();
        tree.insert(2, 'B');
        theQueue.insert(tree);
        tree = new DecodingTree();
        tree.insert(12, 'C');
        theQueue.insert(tree);
        tree = new DecodingTree();
        tree.insert(1, 'D');
        theQueue.insert(tree);

        theQueue.displayArray();
    }
}
