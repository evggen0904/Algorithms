package com.mycompany.linkList;

class MatrixLink<T>
{
    public T data;              // data item
    public MatrixLink rNext;              // next link in list
    public MatrixLink dNext;
    public MatrixLink lPrevious;
    public MatrixLink uPrevious;
    // -------------------------------------------------------------
    public MatrixLink(T data)
    {
        this.data = data;                 // initialize data
    }


    // -------------------------------------------------------------
    public void displayLink()
    {
        System.out.print("{" + data + "} ");
    }
}

public class Matrix {
    private int MAX_ROW = 7; // matrix size
    private int MAX_COL = 10;
    private MatrixLink first;
    private MatrixLink last;
    private long lastRow = -1;
    private long lastColumn = -1;

    public int getColumnSize() {
        return MAX_COL;
    }

    public int getRowSize() {
        return MAX_ROW;
    }

    Matrix(){}

    Matrix(int row, int col){
        this.MAX_ROW = row;
        this.MAX_COL = col;
    }

    public MatrixLink insert(int row, int col, long data){
        if ( row >= 0 && col >= 0 && row < MAX_ROW && col < MAX_COL) {
            MatrixLink newLink = new MatrixLink(data);
            boolean isInsert = false;
            if (!isEmpty()) {
                boolean setColumnLinks = true;
                if (col > 0) {
                    int cnt = row;
                    MatrixLink current = null;
                    while (cnt <= MAX_ROW) {
                        current = getRowLink(row);
                        current = getColumnLink(current, col - 1);
                        if (current != null)
                            break;
                        cnt++;
                    }
                    if (current != null) {
                        setSideLinks(newLink, current);
                    }
                    else
                        setColumnLinks = false;
                }
                if (setColumnLinks) {
                    setColumnLink(newLink, row, col);
                    isInsert = true;
                }
            }
            else{
                first = newLink;
                isInsert = true;
            }
            if (isInsert) {
                setLastElement(row, col, newLink);
                return newLink;
            }
        }
        return null;
    }

    public MatrixLink deleteKey(int row, int col){
        if ( row >= 0 && col >= 0 && row < MAX_ROW && col < MAX_COL) {
            if (!isEmpty()){
                MatrixLink tmp;
                if (row == 0 && col == 0){

                }
                else {
                    MatrixLink current = getRowLink(row);
                }

            }
        }
        return null;
    }

    private void deleteLinks(MatrixLink link){
        MatrixLink tmp;
        if (link.rNext != null){

        }
    }

    private void setLastElement(int row, int col, MatrixLink newLink){
        if (row >= lastRow && col >= lastColumn){
            lastRow = row;
            lastColumn = col;
            last = newLink;
        }
    }

    private void setSideLinks(MatrixLink newLink, MatrixLink current){
        MatrixLink tmpRightNextLink = null;
        if (current.rNext != null)
            tmpRightNextLink = current.rNext.rNext;
//      меняем указатели с последующего за нашим элементом с новым элементом
        if (tmpRightNextLink != null)
            tmpRightNextLink.lPrevious = newLink;
        newLink.rNext = tmpRightNextLink;
//        меняем указатели с предыдущего за нашим элементом с новым элементом
        newLink.lPrevious = current;
        current.rNext = newLink;
    }


    private void setColumnLink(MatrixLink current, int currentRow, int currentColumn){
//          уставливаем связь с верхней строкой
//            находим верхнюю строку
        MatrixLink link = getRowLink(currentRow -1);
        if (link != null) {
//            находим соответсвующий столбец
            link = getColumnLink(link, currentColumn);
            if (link != null) {
//                необходимо найти элемент на который указывал предыдущий в этом ряду
/*                т.е, если было {1}->{2}->{3} и нам нужно вставить вместо элемента {2} например {4}
                  то из {1} мы можем получить ссылку на {3} через  link.dNext.dNext
 */

                MatrixLink tmpDownLink = null;
                if (link.dNext != null)
                    tmpDownLink = link.dNext.dNext;
                current.uPrevious = link;
                link.dNext = current;
                current.dNext = tmpDownLink;
                if (tmpDownLink != null)
                    tmpDownLink.uPrevious = current;
            }
        }
        else {
//        устанавливаем связь с нижней строкой
            link = getRowLink(currentRow + 1);
//            находим соответсвующий столбец
            if (link != null) {
                link = getColumnLink(link, currentColumn);
                if (link != null) {
                    current.dNext = link;
                    link.uPrevious = current;
                }
            }
        }
    }

    private MatrixLink getRowLink(int destRow){
        int rowIndex = 0;
        MatrixLink link = null;
        if (destRow >= 0) {
            link = first;
            while (rowIndex != destRow) {
                link = link.dNext;
                rowIndex++;
            }
        }
        return link;
    }

    private MatrixLink getColumnLink(MatrixLink link, int destColumn){
        int columnIndex = 0;
        while(link!= null && columnIndex != destColumn){
            link = link.rNext;
            columnIndex++;
        }
        return link;
    }

    public void display(){
        if (!isEmpty()){
            MatrixLink currentColumn = first;
            MatrixLink currentRow;
            for (int i = 0; i < MAX_ROW; i++) {
                if (currentColumn != null) {
                    currentColumn.displayLink();
                    currentRow = currentColumn.rNext;
                }
                else
                    break;
                for (int j = 1; j < MAX_COL; j++) {
                    if (currentRow != null) {
                        currentRow.displayLink();
                        currentRow = currentRow.rNext;
                    }
                    else
                        break;
                }
                currentColumn = currentColumn.dNext;
                System.out.println("");
            }
            System.out.println("");
        }
    }

    public void displayBackward(){
        if (!isEmpty()){
            MatrixLink currentColumn;
            MatrixLink currentRow = last;
            MatrixLink lastLink = null;
            for (int i = 0; i < MAX_ROW; i++) {
                if (currentRow != null) {
                    currentRow.displayLink();
                    currentColumn = currentRow.lPrevious;
                }
                else
                    break;
                for (int j = 1; j < MAX_COL; j++) {
                    if (currentColumn != null) {
                        currentColumn.displayLink();
                        currentColumn = currentColumn.lPrevious;
                    }
                    else
                        break;
                }
                /*т.к струкутра может быть заполнена неравномерно
                1 2 3
                4 5
                6 7 8 9
                то необходимо найти начало следующей строки*/

                    boolean findNextRow = false;
                    MatrixLink tmp = currentRow;
                    while(tmp.lPrevious != null){

                        if (tmp.uPrevious != null) {
                            currentRow = tmp.uPrevious;
                            findNextRow =true;
                            break;
                        }
                        tmp = tmp.lPrevious;
                    }
                    if (!findNextRow)
                        currentRow = null;
                    else if (currentRow.rNext != null){
                        tmp = currentRow;
                        while (tmp.rNext != null){
                            currentRow = tmp.rNext;
                            tmp = tmp.rNext;
                        }
                    }
                System.out.println("");
            }
            System.out.println("");
        }
    }

 /*   public void displayTransposed(){
        if (!isEmpty()){
            MatrixLink currentColumn = first;
            MatrixLink currentRow;
            for (int i = 0; i < MAX_COL; i++) {
                if (currentColumn != null) {
                    currentColumn.displayLink();
                    currentRow = currentColumn.dNext;
                }
                else
                    break;
                MatrixLink tmp = currentColumn;
                for (int j = 1; j < MAX_ROW; j++) {
                    if (currentRow != null) {
                        currentRow.displayLink();
                        currentRow = currentRow.dNext;
                    }
                    else{

                    }

                }
//                if (currentColumn.rNext != null)
                    currentColumn = currentColumn.rNext;

                System.out.println("");
            }
            System.out.println("");
        }
    }
*/
    public boolean isEmpty(){
        return (first == null);
    }
}

class DemoMatrix{
    public static void main(String[] args) {
//        System.out.println("null");
        int row = 3;
        int col = 4;
        Matrix matrix = new Matrix(row, col);
 /*       for (int i = 0; i < matrix.getColumnSize(); i++) {
            for (int j = 0; j < matrix.getRowSize(); j++) {
                String value = (i+1)+ "" +(j+1);
                matrix.insert(i, j, Long.parseLong(value));
            }

        }*/

        System.out.println("Initial matrix [" + row + ";" + col + "]: ");
        matrix.insert(0,0, 11);
        matrix.insert(0,1, 12);
        matrix.insert(0,2, 13);
        matrix.insert(1,0, 21);
        matrix.insert(1,1, 22);
        matrix.insert(2,0, 31);
        matrix.insert(2,1, 32);
        matrix.insert(2,2, 33);
        matrix.insert(0,3, 14);

        System.out.println("Forward display: ");
        matrix.display();
//        System.out.println("Transposed display: ");
//        matrix.displayTransposed();
        System.out.println("Backward display: ");
        matrix.displayBackward();

        System.out.println("Insert element[1,4] = 10 :" + (matrix.insert(0,3, 10) == null ? " false" : " true"));
        matrix.display();

        System.out.println("Insert element[2,4] = 55 :" + (matrix.insert(1,3, 55) == null ? " false" : " true"));
        matrix.display();

        System.out.println("Insert element[2,3] = 99 :" + (matrix.insert(1,2, 99) == null ? " false" : " true"));
        matrix.display();

        System.out.println("Backward display: ");
        matrix.displayBackward();
    }
}
