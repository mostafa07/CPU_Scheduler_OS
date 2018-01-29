/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/12/2017, 5:37 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public abstract class Scheduler {

    protected LinkedList<Process> initialList;
    protected LinkedList<Process> readyList;
    protected LinkedList<ProcessBurst> processedList;

    //Constructor

    public Scheduler(LinkedList<Process> initialList) {
        this.initialList = (LinkedList) initialList.clone();
    }


    //Getters and setters

    public LinkedList<Process> getInitialList() {
        return initialList;
    }

    public void setInitialList(LinkedList<Process> initialList) {
        this.initialList = initialList;
    }

    public LinkedList<Process> getReadyList() {
        return readyList;
    }

    public void setReadyList(LinkedList<Process> readyList) {
        this.readyList = readyList;
    }

    public LinkedList<ProcessBurst> getProcessedList() {
        return processedList;
    }

    public void setProcessedList(LinkedList<ProcessBurst> processedList) {
        this.processedList = processedList;
    }


    //Other Methods

    public abstract void startScheduling();

    public abstract void sortReadyList();

    public void sortInitialList() {
        //sorting according to arrival time
        Collections.sort(initialList, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                if (p1.arrivalTime > p2.arrivalTime) return 1;
                if (p1.arrivalTime < p2.arrivalTime) return -1;
                return 0;
            }});
    }

    public void displayProcessedList() {

        System.out.println("\nScheduling...\n");

        /*
        for(ProcessBurst p : processedList) {
            System.out.println("Process#" + p.getProcess().getPid() + "\t| Burst Time = " + p.getTimeBurst());
        }
        */

        double step = 1.0;
        double sumOfBursts = 0;

        for (ProcessBurst p : processedList) {
            sumOfBursts += p.getTimeBurst();
        }

        if(sumOfBursts <= 20)
            step = 0.25;
        else if(sumOfBursts <= 40)
            step = 0.5;
        else if(sumOfBursts <= 80)
            step = 1.0;
        else if (sumOfBursts <= 160)
            step = 2.0;
        else if (sumOfBursts > 160)
            step = 4.0;

        double end = sumOfBursts + 4 * processedList.size();
        for (double i = 0; i < end; i += step)
            System.out.print('_');
        System.out.println();


        int line = 0;
        while (line < 2) {

            for (ProcessBurst p : processedList) {

                double len = p.getTimeBurst();
                System.out.print('|');
                for (double i = 0; i < len / 2; i += step) {
                    System.out.print(' ');
                }
                if (line == 0)
                    System.out.print("P" + p.getProcess().getPid());
                else
                    System.out.print("  ");

                for (double i = len / 2 + step; i < len; i += step) {
                    System.out.print(' ');
                }
            }
            System.out.println('|');
            line++;
        }

        double itr = 0;
        for (ProcessBurst p : processedList) {

            double len = p.getTimeBurst();
            for (double i = 0; i < len; i += step) {
                System.out.print(' ');
                itr += step;
            }
            System.out.print( (int)itr );
        }

        System.out.println();

    }   //displayProcessedList()

}   //Scheduler Class
