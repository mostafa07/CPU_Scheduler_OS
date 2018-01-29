/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:37 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SJF_Non_Pre_Scheduler extends Scheduler {

    //Constructor

    public SJF_Non_Pre_Scheduler(LinkedList<Process> initialList) {
        super(initialList);
    }

    //Other methods

    @Override
    public void startScheduling() {

        long safetyClock = System.nanoTime();
        int clock = 0, selfClock = 0;
        Process currentProcess = null;

        processedList = new LinkedList<>();
        readyList = initialList;
        sortReadyList();    //sorting according to remaining time

        while( !readyList.isEmpty() ) {

            for (Process p : readyList) {
                if ( p.getArrivalTime() <= clock && currentProcess == null ) {
                    currentProcess = p;
                    break;
                }
            }

            if ( currentProcess != null ) {
                if (currentProcess.isFinished()) {
                    currentProcess.setDepartureTime(clock);
                    processedList.add(new ProcessBurst(currentProcess, selfClock));
                    readyList.remove(currentProcess);
                    currentProcess = null;
                    selfClock = 0;
                }
                else {
                    currentProcess.decRemTime();
                    selfClock++;
                    clock++;
                }
            }

            if (System.nanoTime() - safetyClock > 2000000000) {     //Safe exit
                System.out.println("\nError:\tThe program has entered an infinite loop.\n" +
                        "System is not designed to handle idle time where there are no ready processes.");
                System.exit(1);
            }

        }

    }   //startScheduling()

    @Override
    public void sortReadyList() {
        //sorting according to remaining time
        Collections.sort(readyList, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                if (p1.getRemainingTime() > p2.getRemainingTime()) return 1;
                if (p1.getRemainingTime() < p2.getRemainingTime()) return -1;
                return 0;
            }});
    }

    /*
    @Override
    public void startScheduling() {

        int clock = 0, selfClock = 0, processCounter = 0, sumOfRemTime = 0;
        boolean anyArrived = false;

        sortInitialList();
        processedList = new LinkedList<>();
        readyList = new LinkedList<>();

        Process currentProcess = null;

        for (Process p : initialList)
            sumOfRemTime += p.getRemainingTime();


        do {

            //checking for arriving processes and adding them to readyList
            for (int i = processCounter; i < initialList.size(); i++) {

                Process toArrive = initialList.get(processCounter);
                if ( toArrive.getArrivalTime() <= clock ) {
                    readyList.addLast(toArrive);
                    anyArrived = true;
                    processCounter++;
                }
                else
                    break;
            }

            clock++;

            if ( anyArrived && !readyList.isEmpty() ) {

                currentProcess = readyList.get(0);

                if ( currentProcess.isFinished() ) {
                    processedList.add(new ProcessBurst(currentProcess, selfClock));
                    readyList.removeFirst();
                    sortReadyList();
                    selfClock = 0;
                }
                else {
                    selfClock++;
                    currentProcess.decRemTime();
                }

            }

        } while ( !readyList.isEmpty() || clock < sumOfRemTime+100 );   //to account for late arrivals

    }   //startScheduling()
    */


}   //SJF_Non_Pre_Scheduler Class
