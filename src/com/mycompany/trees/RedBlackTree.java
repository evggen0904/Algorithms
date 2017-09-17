package com.mycompany.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Красно-черные правила:
 * 1) каждый узел окрашен в черный или красный цвет
 * 2) корень всегда окрашен в черный
 * 3) если узел красный, то его потомки всегда черные(у черного узла могут быть черные потомки)
 * 4) все пути от корня к узлу или пустому потомку должны иметь одинаковое количество черных узлов(черная высота)
 *
 */
@SuppressWarnings("ALL")
public class RedBlackTree<K,V> {
    private Node root;
    private Comparator<K> comparator;
    private final boolean RED = true;
    private final boolean BLACK = false;

    private class Node
    {
        private K key;
        private V value;
        private boolean isRed;
        private Node parent;
        private Node leftChild;
        private Node rightChild;

        Node(K key, V value, boolean isRed, Node parent){
            this.key = key;
            this.value = value;
            this.isRed = isRed;
            this.parent = parent;
        }

        void setColor(boolean isRed){
            this.isRed = isRed;
        }

        boolean changeColor(){
            if (this.isRed)
                this.isRed = false;
            else
                this.isRed = true;
            return this.isRed;
        }

        boolean isRed(){
            return isRed;
        }
    }

    public RedBlackTree(){
     /*
        comparator = new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) {
                int result = 0;
                if (o1 instanceof Integer)
                    result = Integer.compare((int)o1, (int)o2);
                if (o1 instanceof Double)
                    result = Double.compare((double)o1, (double)o2);
                if (o1 instanceof String)
                    result = ((String) o1).compareTo((String)o2);
                if (o1 instanceof Character)
                    result = Character.compare((char)o1, (char)o2);
                return result;
            }
        };*/
    }

    public void insert(K key, V value) {
        Node newNode;
        if(root==null) {
            newNode = new Node(key, value, BLACK, null);
            root = newNode;
        }
        else
        {
            Node current = root;
            Node parent;
            while(true)
            {
                parent = current;
                Comparable<K> k = (Comparable<K>) key;
                if( k.compareTo(current.key) < 0)  // go left?
                {
                    current = current.leftChild;
                    if(current == null)
                    {                 // insert on left
                        newNode = new Node(key, value, RED, parent);
                        parent.leftChild = newNode;
                        if (parent.parent != null)
                            flipColors(parent.parent);
                        break;
                    }
                }
                else                    // or go right?
                {
                    current = current.rightChild;
                    if(current == null)
                    {                 // insert on right
                        newNode = new Node(key, value, RED, parent);
                        parent.rightChild = newNode;
                        if (parent.parent != null)
                            flipColors(parent.parent);
                        break;
                    }
                }
            }
        }
        fixTree(newNode);
    }

/* TODO: доделать удаление*/
    public boolean delete(K key)
    {
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;
        Comparable<K> k = (Comparable<K>) key;

        while(k.compareTo(current.key) != 0)        // search for node
        {
            parent = current;
            if(k.compareTo(current.key) < 0)
            {
                isLeftChild = true;
                current = current.leftChild;
            }
            else
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if(current == null)
                return false;
        }

        // if no children, simply delete it
        if(current.leftChild==null && current.rightChild==null)
        {
            if(current == root)             // if root,
                root = null;                 // tree is empty
            else if(isLeftChild)
                parent.leftChild = null;
            else
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if(current.rightChild==null)
            if(current == root)
                root = current.leftChild;
            else if(isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if(current.leftChild==null)
            if(current == root)
                root = current.rightChild;
            else if(isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

 /*       else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if(current == root)
                root = successor;
            else if(isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)*/
        return true;                                // success
    }  // end delete()

    /*
 * Вставка узла в дерево
 * 1) Р - черный
 *      Просто вставляем красный узел
 * 2) Р - красный, Х - внешний потомок деда
 *      а) переключаем цвет предка
 *      б) переключаем цвет родителя
 *      в) выполняем поворот с узлом дедом в направлении поднимающем Х
 * 3) Р - красный, Х - внутренний потомок деда
 *      а) изменяем цвет деда
 *      б) изменяем цвет Х
 *      в) выполняем поворот с узлом Р в направлении, поднимающем Х
 *      г) выполняем поворот с узлом, верхним узлом которого является предок Х
* */
    private void fixTree(Node currentNode){
/* проходим дерево от вставленного элемента до корня и если есть связка красный-красный
* делаем повороты и перекрашивание*/
        while (currentNode != null && currentNode.parent != null){
            if (currentNode.isRed && currentNode.parent.isRed) {
             /*если новый элемент левый внешний внук*/
                if (isLeftChild(currentNode) && isLeftChild(currentNode.parent)) {
//                 2)
                    currentNode.parent.parent.changeColor();
                    currentNode.parent.changeColor();
                    rotateRight(currentNode.parent.parent);
                }
            /*если правый внешний внук*/
                else if (isRightChild(currentNode) && isRightChild(currentNode.parent)) {
                    currentNode.parent.parent.changeColor();
                    currentNode.parent.changeColor();
                    rotateLeft(currentNode.parent.parent);
                }
            /*если правый внутренний внук*/
                else if (isRightChild(currentNode) && isLeftChild(currentNode.parent)) {
                    currentNode.parent.parent.changeColor();
                    currentNode.changeColor();
                    rotateLeft(currentNode.parent);
                    rotateRight(currentNode.parent);
                }
            /*если левый внутрениий внук*/
                else if (isLeftChild(currentNode) && isRightChild(currentNode.parent)) {
                    currentNode.parent.parent.changeColor();
                    currentNode.changeColor();
                    rotateRight(currentNode.parent);
                    rotateLeft(currentNode.parent);
                }
            }
            currentNode = currentNode.parent;
        }
        root.setColor(BLACK);
    }

    public boolean find(K key){
        Node current = root;
        Comparable<K> k = (Comparable<K>) key;

        while(k.compareTo(current.key) != 0)        // search for node
        {
            if(k.compareTo(current.key) < 0)
                current = current.leftChild;
            else
                current = current.rightChild;
            if(current == null)
                return false;
        }
        return true;
    }

    private void flipColors(Node node){
        /*если узел черный и имеет красных потомков нужно поменять цвета всех узлов
        * цвет корневого узла не изменяем*/

        if (!node.isRed && node.leftChild != null && node.rightChild != null) {
            if (node != root)
                node.setColor(RED);
            node.leftChild.setColor(BLACK);
            node.rightChild.setColor(BLACK);
        }
    }

    private boolean isLeftChild(Node node){
        return  node.parent.leftChild == node;
    }

    private boolean isRightChild(Node node){
        return  node.parent.rightChild == node;
    }

    private void rotateRight(Node rotateNode){
        if (rotateNode != null && rotateNode.leftChild != null){
            /*присоеднияем на место правого потомка для опорного узла левого потомка его правого потомка*/
            Node parentNode = rotateNode.parent;
            Node leftNode = rotateNode.leftChild;

            rotateNode.leftChild = leftNode.rightChild;
            /*для вновь присоединенного узла меняем родителя*/
            if (rotateNode.leftChild != null) {
                rotateNode.leftChild.parent = rotateNode;
            }
            /*проверяем не является ли опорный узел рутовым*/
            if (rotateNode.parent == null)
                root = leftNode;
            /*если опорный узел не был рутовым, нужно поменять потомка для дедушки*/
            else if (rotateNode.parent.leftChild == rotateNode)
                rotateNode.parent.leftChild = leftNode;
            else if (rotateNode.parent.rightChild == rotateNode)
                rotateNode.parent.rightChild = leftNode;

            /*заменяем родителей для смещаемых узлов*/
//            if (leftNode != null)
            leftNode.rightChild = rotateNode;
            rotateNode.parent = leftNode;
            leftNode.parent = parentNode;
        }
    }

    private void rotateLeft(Node rotateNode){
        if (rotateNode != null && rotateNode.rightChild != null){
            /*присоеднияем на место левого потомка для опорного узла правого потомка его левого потомка*/
            Node parentNode = rotateNode.parent;
            Node rightNode = rotateNode.rightChild;
            rotateNode.rightChild = rightNode.leftChild;
            /*для вновь присоединенного узла меняем родителя*/
            if (rotateNode.rightChild != null) {
                rotateNode.rightChild.parent = rotateNode;
            }
            /*проверяем не является ли опорный узел рутовым*/
            if (rotateNode.parent == null)
                root = rightNode;
            /*если опорный узел не был рутовым, нужно поменять потомка для дедушки*/
            else if (rotateNode.parent.leftChild == rotateNode)
                rotateNode.parent.leftChild = rightNode;
            else if (rotateNode.parent.rightChild == rotateNode)
                rotateNode.parent.rightChild = rightNode;

            /*заменяем родителей для смещаемых узлов*/
            rightNode.leftChild = rotateNode;
            rotateNode.parent = rightNode;
            rightNode.parent = parentNode;
        }
    }

    public void printTree()
    {
        System.out.println("root is: (" + root.key + "," + root.value + "," + (root.isRed ? "RED":"BLACK")+")");
        inOrder(root);
        System.out.println("");
    }

    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            System.out.print("("+localRoot.key + "," + localRoot.value + "," + (localRoot.isRed ? "RED":"BLACK")+")");
            inOrder(localRoot.rightChild);
        }
    }

    public HashMap<K,V> getElements(){
        HashMap<K,V> map = new HashMap<K, V>();
        getElemsRec(root,map);
        return map;
    }

    private void getElemsRec(Node localRoot, HashMap<K,V> map){
        if (localRoot != null) {
            getElemsRec(localRoot.leftChild, map);
            map.put(localRoot.key, localRoot.value);
            getElemsRec(localRoot.rightChild, map);
        }
    }
}

class DemoRBTree{
    public static void main(String[] args) {
        RedBlackTree<Integer, String> theTree = new RedBlackTree<>();
        System.out.println("Outer grandson insert test: ");
        theTree.insert(50,"50");
        theTree.insert(25,"25");
        theTree.insert(75,"75");
        theTree.insert(12,"12");
        theTree.printTree();
        theTree.insert(37,"37");
        theTree.insert(6,"6");
        theTree.insert(18,"18");
        theTree.insert(3,"3");
        theTree.printTree();

        System.out.println("\nInner grandson insert test: ");
        RedBlackTree<Integer, String> theTree2 = new RedBlackTree<>();
        theTree2.insert(50,"50");
        theTree2.insert(25,"25");
        theTree2.insert(75,"75");
        theTree2.insert(12,"12");
        theTree2.insert(37,"37");
        theTree2.insert(31,"31");
        theTree2.insert(43,"43");
        theTree2.printTree();
        theTree2.insert(28,"28");
        theTree2.printTree();

        System.out.println("Find '43' in tree2: " + theTree2.find(43));
        System.out.println("Find '90' in tree2: " + theTree2.find(90));
//        HashMap<Integer, String> map = theTree2.getElements();
//        System.out.println(map.toString());
    }
}
