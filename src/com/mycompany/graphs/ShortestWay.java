package com.mycompany.graphs;


import java.util.*;
/*
* Реализовать нахождение всех минимальных путей,
* для конкретной вершины ненаправленного взвешенного графа
* */
public class ShortestWay {
    private Vertex[] vertexList;
    private Vertex[] tmpVertexList;
    private Map<String, Integer> ways;
    private Map<String, Integer> sortedWays;
    private int[][] adjMatrix;
    private final int INF = Integer.MAX_VALUE;
    private final int SIZE = 10;
    private int nElems;

    class Vertex
    {
        public char label;
        public boolean wasVisited;

        public Vertex(char lab)
        {
            label = lab;
            wasVisited = false;
        }
    }

    ShortestWay(){
        vertexList = new Vertex[SIZE];
        tmpVertexList = new Vertex[SIZE];
        adjMatrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                adjMatrix[i][j] = INF;

        ways = new HashMap<String, Integer>();

        nElems = 0;
    }

    public void addEdge(int start, int end, int value){
        adjMatrix[start][end] = value;
        adjMatrix[end][start] = value;
    }

    public void addVertex(char name){
        Vertex newVertex = new Vertex(name);
        vertexList[nElems] = newVertex;
        tmpVertexList[nElems] = newVertex;
        nElems++;
    }

    public void doWays(char lab){
        setFirstVertex(lab);
        doAllWays(nElems-1);
        sortedWays = sortByValues(ways);
    }

/*
* автоматическая сортировка всех путей по их весу
* */
    private  <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k1).compareTo(map.get(k2));
                        if (compare == 0)
                            return 1;
                        else
                            return map.get(k1).compareTo(map.get(k2));
                    }
                };

        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

/*
* для того, чтобы рекурсивно  не находить абослютно все перестановки, а только
* для заданного узла, делаем этот узел первым в списке
* */
    private void setFirstVertex(char lab){
        int i;
        for (i = 0; i < nElems; i++) {
            if (vertexList[i].label == lab){
                break;
            }
        }
        tmpVertexList[0] = vertexList[i];
        tmpVertexList[i] = vertexList[0];
    }
/*
Рекурсивно методом перестановок находим все возможные комбинации обхода
для конкренто заданной вершины
* */
    private void doAllWays(int newSize)
    {
        int limit;
        if(newSize == 1)
            return;

        for(int j=0; j<newSize; j++)
        {
            doAllWays(newSize-1);
            if(newSize==2) {
//                displayWay();
                addWay();
            }
            rotate(newSize);
        }
    }

/*
* для того, чтобы определить стоимость конкретного пути в матрице смежности
* необходимо сопоставить индексы во временном массиве с перестановками
* и индексы исходного массива с имеющимися вершинами
* Составляем замкнутый путь и вычисляем стоимость сразу.
* */
    private void addWay(){
        int value = 0;
        StringBuilder way = new StringBuilder();
        way.append(tmpVertexList[0].label);
        for (int i = 1; i < nElems; i++) {
            int x = getIdx(tmpVertexList[i-1]);
            int y = getIdx(tmpVertexList[i]);
            if (adjMatrix[x][y] < INF && value != INF)
                value += adjMatrix[x][y];
            else
                value = INF;
            way.append(tmpVertexList[i].label);
        }
        way.append(tmpVertexList[0].label);
        int x = getIdx(tmpVertexList[nElems-1]);
        int y = getIdx(tmpVertexList[0]);
        if (adjMatrix[x][y] < INF && value != INF)
            value += adjMatrix[x][y];
        else
            value = INF;
        ways.put(way.toString(), value);

    }

    private int getIdx(Vertex v){
        for (int i = 0; i < nElems; i++) {
            if (v.label == vertexList[i].label)
                return i;
        }
        return -1;
    }

/*
* метод смещает влево все символы до текущего и ставит текущий на место последнего
* */
    private void rotate(int newSize)
    {
        int j;
        int position = nElems - newSize;
        Vertex temp = tmpVertexList[position];
        for(j=position+1; j<nElems; j++)
            tmpVertexList[j-1] = tmpVertexList[j];
        tmpVertexList[j-1] = temp;
    }

    private void displayWay(){
        for (int i = 0; i < nElems; i++) {
            System.out.print(tmpVertexList[i].label);
        }
        System.out.println("");
    }

    public void printWays(){
        if (sortedWays == null){
            System.out.println("Пути отсутствуют.");
            return;
        }
        Iterator<Map.Entry<String, Integer>> it = sortedWays.entrySet().iterator();
        Map.Entry<String, Integer> entry = it.next();
        int min = entry.getValue();
        System.out.print("Путь: " + entry.getKey());
        System.out.println(" .Стоимость: " + ((min < INF) ? min : "Невозможно построить путь."));

        while (it.hasNext()){
            entry = it.next();
            if (entry.getValue() > min)
                return;
            System.out.print("Путь: " + entry.getKey());
            System.out.println(" .Стоимость: " + ((entry.getValue() < INF) ? entry.getValue() : "Невозможно построить путь."));
        }
    }
}

class demoShortWays{
    public static void main(String[] args) {
        ShortestWay sw = new ShortestWay();
        sw.addVertex('A');
        sw.addVertex('B');
        sw.addVertex('C');
        sw.addVertex('D');
        sw.addVertex('E');

        sw.addEdge(0,1, 100);//AB
        sw.addEdge(1,2, 40);//BC
        sw.addEdge(1,4, 80);//BE
        sw.addEdge(2,4, 10);//CE
        sw.addEdge(2,3, 50);//CD
        sw.addEdge(3,4, 200);//ED
        sw.addEdge(0,3, 140);//AD

        sw.doWays('B');
        System.out.println("Пути с минимальной стоимостью: ");
        sw.printWays();
    }
}
