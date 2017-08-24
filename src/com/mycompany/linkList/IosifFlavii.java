package com.mycompany.linkList;


import java.util.Scanner;

public class IosifFlavii extends LinkList{

    // -------------------------------------------------------------
    public IosifFlavii()              // constructor
    {
        super();
    }

    // -------------------------------------------------------------
    @Override
    public Link delete(int stepToDie)
    {
        Link link = getCurrent();
        if (stepToDie > 0) {
            int count = 0;
            while (true) {
                if (count + 1 == stepToDie){
                    System.out.print("Dead number: ");
                    link.next.displayLink();
                    System.out.println("");

                    setCurrent(link.next.next);
                    link.next = getCurrent();
                    displayList();
                    link = getCurrent();
                    count = 0;
                }
                else {
                    link = link.next;
                    count++;
                }
                if (link == link.next)
                    break;

            }
        }
        return null;
    }
}

class IosifFlaviiApp{
    public static void main(String[] args) {
        int number;
        int stepToDie;
        int current;
        IosifFlavii list = new IosifFlavii();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the number of people: ");
        number = scanner.nextInt();
        System.out.print("Input step to die: ");
        stepToDie = scanner.nextInt();
        System.out.print("Input current position to countdown: ");
        current = scanner.nextInt();

        Link currentLink = null;
        for (int i = 0; i < number; i++) {
            list.insert(i+1);
            if (i == current - 1)
                currentLink = list.getCurrent();
        }
        list.setCurrent(currentLink);
        list.displayList();

        list.delete(stepToDie);
    }
}
