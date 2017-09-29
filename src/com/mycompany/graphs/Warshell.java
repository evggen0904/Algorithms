package com.mycompany.graphs;


class GraphW
{
    private final int MAX_VERTS = 20;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][];      // adjacency matrix
    private int nVerts;          // current number of vertices
    private StackX theStack;

    class Vertex
    {
        public char label;        // label (e.g. 'A')
        public boolean wasVisited;
        // -------------------------------------------------------------
        public Vertex(char lab)   // constructor
        {
            label = lab;
            wasVisited = false;
        }

    }
    // -------------------------------------------------------------
    public GraphW()               // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        for(int j=0; j<MAX_VERTS; j++)      // set adjacency
            for(int k=0; k<MAX_VERTS; k++)   //    matrix to 0
                adjMat[j][k] = 0;
        theStack = new StackX();
    }  // end constructor
    // -------------------------------------------------------------
    public void addVertex(char lab)
    {
        vertexList[nVerts++] = new Vertex(lab);
    }
    // -------------------------------------------------------------
    public void addEdge(int start, int end)
    {
        adjMat[start][end] = 1;
    }
    // -------------------------------------------------------------
    public void displayVertex(int v)
    {
        System.out.print(vertexList[v].label);
    }

    // -------------------------------------------------------------
/*реализация алгоритма уоршелла
* Если есть путь от вершины А->B и от С->А, следовательно есть и путь С->B
* */
    public void createShortWays(){
        /*перебираем все строки*/
        for (int y = 0; y < nVerts ; y++) {
            /*перебираем каждый элемент в строке*/
            for (int x = 0; x < nVerts; x++) {
                if (adjMat[y][x] == 1){
                    /*если найдена связь с вершиной, то ищем в соответствующем столбце матрицы*/
                    for (int z = 0; z < nVerts; z++) {
                        /*Если есть путь от вершины А->B и от С->А, следовательно есть и путь С->B*/
                        if (adjMat[z][y] == 1)
                            adjMat[z][x] = 1;
                    }
                }
            }
        }
    }

    public void printMatrix(){
        for (int i = 0; i < nVerts; i++) {
            for (int j = 0; j < nVerts; j++) {
                System.out.print(adjMat[i][j] + " ");
            }
            System.out.println("");
        }
    }
}  // end class Graph
////////////////////////////////////////////////////////////////
class WarshellDemo
{
    public static void main(String[] args)
    {
        GraphW theGraph = new GraphW();

        theGraph.addVertex('A');    // 0  (start for bfs)
        theGraph.addVertex('B');    // 1
        theGraph.addVertex('C');    // 2
        theGraph.addVertex('D');    // 3
        theGraph.addVertex('E');    // 4

        theGraph.addEdge(0, 1);     // AB
        theGraph.addEdge(1, 2);
        theGraph.addEdge(0, 3);
        theGraph.addEdge(3, 4);

        System.out.println("Initial matrix: ");
        theGraph.printMatrix();
        theGraph.createShortWays();
        System.out.println("After Worshell transformation: ");
        theGraph.printMatrix();
        System.out.println();
    }
}

