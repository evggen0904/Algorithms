package com.mycompany.trees;

import java.util.*;

@SuppressWarnings("ALL")
public class AlphabetTree {
    private ArrayList<Character> alphabetArray;
    private Node root;

    AlphabetTree()
    {
        alphabetArray = new ArrayList<Character>();
        root = null;
    }

    public void addCharacterToArray(char letter)
    {
        alphabetArray.add(letter);
    }

    public void buildTree()
    {
        sortArray();
        buildTreeRec(1, null);
    }

    private void buildTreeRec(int index, Node parent)
    {
        if (index > alphabetArray.size())
            return;

        Node newNode = new Node();
        newNode.chData = alphabetArray.get(index-1);
        if (root == null)
        {
            root = newNode;
        }
        else
        {
            if (index % 2 == 0)
                parent.leftChild = newNode;
            else
                parent.rightChild = newNode;
        }
        buildTreeRec(2*index, newNode);
        buildTreeRec(2*index + 1, newNode);

    }

    private void sortArray()
    {
        Collections.sort(alphabetArray, new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return o1-o2;
            }
        });
    }

    public void displayArray()
    {
        System.out.print("{");
        for (int i = 0; i < alphabetArray.size(); i++) {
            System.out.print(alphabetArray.get(i) + " ");
        }
        System.out.print("}");
        System.out.println();
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

class DemoAlphabetTree
{
    public static void main(String[] args) {
        AlphabetTree theTree = new AlphabetTree();
        theTree.addCharacterToArray('B');
        theTree.addCharacterToArray('A');
        theTree.addCharacterToArray('C');
        theTree.addCharacterToArray('D');
        theTree.addCharacterToArray('F');
        theTree.addCharacterToArray('G');
        theTree.addCharacterToArray('I');
        theTree.addCharacterToArray('J');
        theTree.addCharacterToArray('H');
        theTree.addCharacterToArray('E');
        theTree.addCharacterToArray('L');
        theTree.addCharacterToArray('K');


//        theTree.sortArray();
        theTree.buildTree();
        theTree.displayArray();
        theTree.displayTree();
    }
}
