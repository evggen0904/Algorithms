package com.mycompany.trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

@SuppressWarnings("ALL")
public class CharacterTree{
    private Node root;
    public static final char ROOT_SYMBOL = '+';

    CharacterTree()
    {
        root = null;
    }

    public Node getRoot()
    {
        return root;
    }

    public void insert(char data)
    {
        Node newNode = new Node();
        newNode.chData = data;
        if(root==null){
            root = newNode;
        }
        else if (data == ROOT_SYMBOL )
        {
            newNode.leftChild = root;
            root = newNode;
        }
        else if (root.leftChild != null && root.rightChild != null)
        {
            Node newRoot = new Node();
            newRoot.chData = ROOT_SYMBOL;
            newRoot.leftChild = root;
            root = newRoot;
            root.rightChild = newNode;
        }
        else
        {
            if (root.leftChild != null)
                root.rightChild = newNode;
            else
                root.leftChild = newNode;

        }
    }

    public void concatenateTrees(CharacterTree tree)
    {
        if (root.leftChild != null)
            root.rightChild = tree.getRoot();
        else
            root.leftChild = tree.getRoot();
    }

    // -------------------------------------------------------------
    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while(isRowEmpty==false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');

            while(globalStack.isEmpty()==false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.chData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if(temp.leftChild != null ||
                            temp.rightChild != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<nBlanks*2-2; j++)
                    System.out.print(' ');
            }  // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()
}

class demoCharTree
{
    private static ArrayList<CharacterTree> treeArray;
    public static void main(String[] args) {

        treeArray = new ArrayList<CharacterTree>();
        insertTreeInArray('A');
        insertTreeInArray('B');
        insertTreeInArray('C');
        insertTreeInArray('D');
        insertTreeInArray('E');
        insertTreeInArray('F');
        insertTreeInArray('G');
        insertTreeInArray('H');

        Iterator<CharacterTree> it = treeArray.iterator();
        CharacterTree mainTree = null;
/*        if (it.hasNext()) {
            mainTree = it.next();
            mainTree.insert(CharacterTree.ROOT_SYMBOL);
            it.remove();
        }
        while (it.hasNext())
        {
            CharacterTree tree = it.next();
            mainTree.insert(tree.getRoot().chData);
            it.remove();
        }*/
        createBottomTrees();

        if (mainTree != null)
            mainTree.displayTree();

        treeArray.get(0).displayTree();
    }

    private static void insertTreeInArray(char value)
    {
        CharacterTree letter = new CharacterTree();
        letter.insert(value);
        treeArray.add(letter);
    }

    private static void createBottomTrees()
    {
        if (treeArray.size() == 1)
            return;
        Iterator<CharacterTree> it = treeArray.iterator();
        int step = 1;
        CharacterTree currentMainTree = null;
        while (it.hasNext()){
            if (step % 2 != 0) {
                currentMainTree = it.next();
                currentMainTree.insert(CharacterTree.ROOT_SYMBOL);
            }
            else{
                CharacterTree tree = it.next();
                currentMainTree.concatenateTrees(tree);
                it.remove();
            }
            step++;
        }
        createBottomTrees();
    }

    private static void createUpperTree()
    {
        Iterator<CharacterTree> it = treeArray.iterator();
        while (it.hasNext())
        {

        }
    }
}