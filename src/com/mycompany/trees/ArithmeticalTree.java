package com.mycompany.trees;

import java.util.Stack;

@SuppressWarnings("Duplicates")
public class ArithmeticalTree {
    private Node root;

    ArithmeticalTree()
    {
        root = null;
    }

    public void insert(char data)
    {
        Node newNode = new Node();
        newNode.chData = data;
        if(root==null){
            root = newNode;
        }
        else
        {
        }
    }

    public void concatenateTrees(ArithmeticalTree tree)
    {
        if (root.leftChild != null)
            root.rightChild = tree.getRoot();
        else
            root.leftChild = tree.getRoot();
    }

    public Node getRoot()
    {
        return root;
    }

    // -------------------------------------------------------------
    private void preOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print(localRoot.chData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print("(");
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.chData + " ");
            inOrder(localRoot.rightChild);
            System.out.print(")");
        }
    }
    // -------------------------------------------------------------
    private void postOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.chData + " ");
        }
    }

    public void display()
    {
        System.out.print("Симметричный обход: ");
        inOrder(root);
        System.out.println("");
        System.out.print("Прямой обход: ");
        preOrder(root);
        System.out.println("");
        System.out.print("Обратный обход: ");
        postOrder(root);
        System.out.println("");
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

class ParsePost
{
    private Stack theStack;
    private String input;
    //--------------------------------------------------------------
    public ParsePost(String s)
    { input = s; }
    //--------------------------------------------------------------
    public ArithmeticalTree doParse()
    {
        theStack = new Stack<ArithmeticalTree>();             // make new stack
        char ch;
        int j;
        ArithmeticalTree leftNode, rightNode;

        for(j=0; j<input.length(); j++)       // for each char,
        {
            ch = input.charAt(j);              // read from input
            Node newNode = new Node();
            newNode.chData = ch;
            ArithmeticalTree tree = new ArithmeticalTree();
            if(ch != '+' && ch != '-' && ch != '*' && ch != '/' ) {
                tree.insert(ch);
                theStack.push(tree); //   push it
            }
            else                               // it's an operator
            {
                rightNode = (ArithmeticalTree)theStack.pop();          // pop operands
                leftNode = (ArithmeticalTree)theStack.pop();

                tree.insert(ch);
                tree.concatenateTrees(leftNode);
                tree.concatenateTrees(rightNode);
                theStack.push(tree);
            }
        }
        return (ArithmeticalTree) theStack.pop();
    }
}

class DemoArithmeticalTree
{
    public static void main(String[] args) {
        /* A*(B+C)-D/(E+F)*/
        ParsePost parsePost = new ParsePost("ABC+*DEF+/-");
        ArithmeticalTree theTree = parsePost.doParse();
        theTree.displayTree();
        theTree.display();
    }
}
