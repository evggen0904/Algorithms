package com.mycompany.trees;

import java.util.PriorityQueue;

@SuppressWarnings("ALL")
class DecodingTree implements Comparable<DecodingTree>
{
    private Node root;

    private static class Node
    {
        private int iData;
        private Character chData;
        private Node leftChild;
        private Node rightChild;

        Node(){}

        Node(int iData){
            this.iData = iData;
        }

        Node(int iData, char chData){
            this.iData = iData;
            this.chData = chData;
        }
    }

    DecodingTree(Node root)
    {
        this.root = root;
    }

    private DecodingTree concatenateTrees(DecodingTree tree){

        int newIData = root.iData + tree.getRoot().iData;
        Node newRoot = new Node(newIData);
        if (root.iData < tree.getRoot().iData){
            newRoot.leftChild = root;
            newRoot.rightChild = tree.getRoot();
        }
        else{
            newRoot.rightChild = root;
            newRoot.leftChild = tree.getRoot();
        }
        root = newRoot;
        return this;
    }

    /**
     * Построение дерева Хаффмана
     * Алгоритм:
     * 1. Создать объект Node для каждого символа, используемого в сообщении.
     * 2. Создать объект дерева для каждого из этих узлов. Узел становится корнем дерева.
     * 3. Вставить эти деревья в приоритетную очередь. Деревья упорядочиваются по частоте,
     * при этом наименьшая частота обладает наибольшим приоритетом. Таким образом,
     * при извлечении всегда выбирается дерево с наименее часто используемым символом.
     * 4. Извлечь два дерева из приоритетной очереди и сделать их потомками нового узла.
     * Частота нового узла является суммой частот потомков. Поле символа может остаться пустым.
     * 5. Вставить новое дерево из двух узлов обратно в приоритетную очередь.
     * 6. Продолжить выполнение шагов 4 и 5. Деревья постепенно увеличиваются, а их
     * количество постепенно сокращается. Когда в очереди останется всего одно дерево, оно
     * представляет собой дерево Хаффмана. Работа алгоритма при этом завершается.
     *
     * @param charFrequencies Массив содержащий частоту символов в сообщении. Номер ячейки соответствует
     * коду символа в ASCII.
     * @return дерево Хаффмана
     */

    public static DecodingTree buildTree(int[] charFrequencies) {
        PriorityQueue<DecodingTree> trees = new PriorityQueue<>();
        // 1. - 3.
        for (int i = 0; i < charFrequencies.length; i++) {
            if (charFrequencies[i] > 0) {
                Node newNode = new Node(charFrequencies[i], (char)i);
                trees.offer(new DecodingTree(newNode));
            }
        }
        // 6. пока в очереди не останется только одно дерево
        while (trees.size() > 1) {
            // 4. - 5.
            DecodingTree first = trees.poll();
            DecodingTree second = trees.poll();
            first.concatenateTrees(second);
            trees.offer(first);
        }
	        /*Когда в очереди останется всего одно дерево, оно представляет собой дерево Хаффмана. */
        return trees.poll();
    }

    public String incode(String text) {
      /*Для кодирования сообщения необходимо создать кодовую таблицу,
       * в которой для каждого символа приводится соответствующий код Хаффмана.*/
        String[] codes = codeTable();
        StringBuilder result = new StringBuilder();
      /*Далее коды Хаффмана раз за разом присоединяются к кодированному сообщению,
       * пока оно не будет завершено.*/
        for (int i = 0; i < text.length(); i++) {
            result.append(codes[text.charAt(i)]);
        }
        return result.toString();
    }

    private String[] codeTable() {
        String[] codeTable = new String[256];
        codeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void codeTable(Node node, StringBuilder code, String[] codeTable) {
        if (node.chData != null) {
        /* добавялем соответствие символа и его байт кода после кодирования в соответствующую ячейку массива
        * */
            codeTable[node.chData] = code.toString();
            return;
        }
        codeTable(node.leftChild, code.append('0'), codeTable);
        code.deleteCharAt(code.length() - 1);
        codeTable(node.rightChild, code.append('1'), codeTable);
        code.deleteCharAt(code.length() - 1);
    }

    public String decode(String biteCode)
    {
        return decode(root, new StringBuilder().append(biteCode), new StringBuilder());
    }

    private String decode(Node localNode, StringBuilder biteCode, StringBuilder result)
    {
        /*
        т.к каждый код символа уникальный и не содержит префиксы другого символа,
        то декодирование производим простым спуском по дереву от корневого элемента.
        Когда элемент найден, снова начинаем проход от корневого элемента до тех пор,
        пока не закончится закодированная строка.
        * */
        if (localNode.leftChild == null && localNode.rightChild == null) {
            result.append(localNode.chData);
            localNode = root;

            if (biteCode.length() == 0)
                return result.toString();
        }
        else if (biteCode.charAt(0) == '0') {
            localNode = localNode.leftChild;
            biteCode.deleteCharAt(0);
        }
        else {
            localNode = localNode.rightChild;
            biteCode.deleteCharAt(0);
        }

        decode(localNode, biteCode, result);

        return result.toString();
    }

    private boolean isLeaf(Node node)
    {
        if (node.leftChild == null && node.rightChild == null)
            return true;
        return false;
    }

    public Node getRoot(){return root;}


    public void printTree()
    {
        inOrder(root);
    }

    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            if (localRoot.chData != null)
                System.out.print(localRoot.chData + " ");
            else
                System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }

    @Override
    public int compareTo(DecodingTree o) {
        return root.iData - o.getRoot().iData;
    }
}

class DemoDecoding
{
    public static void main(String[] args) {

        String text = "SUSIE SAYS IT IS EASY";
        System.out.println("Initial string: " + text);
        int[] charFrequencies = new int[256];
        // считываем символы и считаем их частоту
        for (char c : text.toCharArray()) {
            charFrequencies[c]++;
        }

        DecodingTree theTree = DecodingTree.buildTree(charFrequencies);
        System.out.printf("Size before compression = %d%n", text.length() * 8);
        String afterCoding = theTree.incode(text);
        System.out.printf("Size after compression = %d%n", afterCoding.length());
        System.out.println("Result is: " + afterCoding);
        theTree.printTree();
        System.out.println();
        System.out.println("Reverse decoding: " + theTree.decode(afterCoding));
    }

}
