package com.mycompany.graphs;

import java.util.LinkedList;
import java.util.Scanner;

public class HorseMove {
    private int[][] chessMatrix;
    private LinkedList<Vertex>[][] adjMatrix;
    private Vertex[][] horses;
    private int nVertex;
    private int size;
    private int step;
    private Vertex[] possibleSteps;
    StackX chessStack;
    Scanner sc;

    class Vertex{
        boolean wasVisited;
        int x;
        int y;

        Vertex(int x, int y){
            this.x = x;
            this.y = y;
            wasVisited = false;
        }

        void display(){
            System.out.print("(" + x + ", " + y + ")");
        }
    }

    class StackX
    {
        private int size;
        private Vertex[] st;
        private int top;

        public StackX(int size)
        {
            this.size = size;
            st = new Vertex[this.size];
            top = -1;
        }

        public void push(Vertex vertex)
        { st[++top] = vertex; }

        public Vertex pop()
        { return st[top--]; }

        public Vertex peek()
        { return st[top]; }

        public boolean isEmpty()
        { return (top == -1); }

        public boolean isFull(){
            return top == size-1;
        }

    }

    HorseMove(int size){
        chessStack = new StackX(size*size);
        chessMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                chessMatrix[i][j] = 0;
            }
        }
        this.size = size;
        createPossibleSteps();
        createHorses();
        createAdjMatrix();
        sc = new Scanner(System.in);

        nVertex = 0;
        step = 0;
    }

    private void createPossibleSteps(){
        possibleSteps = new Vertex[8];
        possibleSteps[0] = new Vertex(2,1);
        possibleSteps[1] = new Vertex(2,-1);
        possibleSteps[2] = new Vertex(-2,1);
        possibleSteps[3] = new Vertex(-2,-1);
        possibleSteps[4] = new Vertex(1,2);
        possibleSteps[5] = new Vertex(-1,2);
        possibleSteps[6] = new Vertex(1,-2);
        possibleSteps[7] = new Vertex(-1,-2);
    }

    private void createHorses(){
        horses = new Vertex[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                horses[i][j] = new Vertex(i, j);
            }
        }
    }

    private void createAdjMatrix(){
        adjMatrix = new LinkedList[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                adjMatrix[x][y] = new LinkedList<>();
                for (int i = 0; i < possibleSteps.length; i++) {
                    int nextX = x + possibleSteps[i].x;
                    int nextY = y + possibleSteps[i].y;

                    if (isPossibleStep(nextX, nextY))
                        adjMatrix[x][y].add(horses[nextX][nextY]);
//                        adjMatrix[x][y].add(new Vertex(nextX,nextY));

                }
            }
        }
    }

    public void fillTheChessDesc(){
        Vertex currentStep = horses[size/2][size/2];
        currentStep.wasVisited = true;
        chessMatrix[currentStep.x][currentStep.y] = ++step;
        fillTheChessDesc(currentStep);
        displayChessMatrix();
    }

    private boolean fillTheChessDesc(Vertex currentStep){


        while (!chessStack.isFull()){
//            chessStack.push(currentStep);
            boolean stepExists = false;

            try {
                for (Vertex v : adjMatrix[currentStep.x][currentStep.y]) {
                    if (!v.wasVisited) {
                        v.wasVisited = true;
                        chessStack.push(v);
                        chessMatrix[v.x][v.y] = ++step;
                        displayChessMatrix();
                        if (chessStack.isFull())
                            return true;
                        stepExists = fillTheChessDesc(v);
                    }
                }
                if (!stepExists) {
                    if (!chessStack.isEmpty()) {
                        Vertex wrongStep = chessStack.pop();
                        wrongStep.wasVisited = false;
                        chessMatrix[wrongStep.x][wrongStep.y] = 0;
                        step--;
                    }
                    return false;
                }
            }
            catch (Exception e){
                displayChessMatrix();
                e.printStackTrace();
            }
        }
 /*       while (!chessStack.isFull()){
            Vertex nextHorse = makeStep(adjMatrix[currentHorse.x][currentHorse.y]);
            if (nextHorse == null){
                Vertex prevHorse = chessStack.pop();
                prevHorse.wasVisited = false;
                chessMatrix[prevHorse.x][prevHorse.y] = 0;
                step--;
                currentHorse = chessStack.peek();
            }
            else{
                nextHorse.wasVisited = true;
                chessMatrix[nextHorse.x][nextHorse.y] = ++step;
                chessStack.push(nextHorse);
                currentHorse = nextHorse;

                displayChessMatrix();
            }

     /*       displayChessMatrix();
            System.out.println("Press 'a' to continue");
            while (sc.hasNext()){
                String s = sc.next();
                if (s.charAt(0)== 'a') {
                    displayChessMatrix();
                    break;
                }
            }
        }*/

        return true;

    }

    private boolean isPossibleStep(int x, int y){
        if ( x <= size-1 && x >=0 && y <= size-1 && y >= 0){
            if (chessMatrix[x][y] == 0)
                return true;
        }
        return false;
    }

    public void displayAdjMatrix(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (Vertex v : adjMatrix[i][j]){
                    v.display();
                }
                System.out.print("|");
            }
            System.out.println("");
        }
    }

    private void displayChessMatrix(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(chessMatrix[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}

class DemoHorseMove{
    public static void main(String[] args) {
        HorseMove hm = new HorseMove(5);
        hm.displayAdjMatrix();
        hm.fillTheChessDesc();
    }
}
