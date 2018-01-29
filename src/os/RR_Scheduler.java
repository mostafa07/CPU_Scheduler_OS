/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:35 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class RR_Scheduler extends Scheduler {

    //Member variables
    private int quantumTime;
    private int clock;

    //Constructor

    public RR_Scheduler(LinkedList<Process> initialList, int qTime) {
        super(initialList);
        this.quantumTime = qTime;
    }


    //Other methods

    @Override
    public void startScheduling() {

        long safetyClock = System.nanoTime();

        int qTime = quantumTime, selfClock = 0;
        clock = 0;
        readyList = initialList;
        processedList = new LinkedList<>();
        sortReadyList();    //sorting according to arrival time

        Process currentProcess = null;

        while ( !readyList.isEmpty() ) {

            if (currentProcess == null) {
                currentProcess = chooseCurrentProcess();
            }
            else if ( currentProcess.isFinished() ) {
                currentProcess.setDepartureTime(clock);
                processedList.add( new ProcessBurst(currentProcess, selfClock) );
                readyList.remove(currentProcess);
                selfClock = 0;
                currentProcess = chooseCurrentProcess();
                qTime = quantumTime;
            }
            else if (qTime == 0) {
                //Process tmp = currentProcess;
                processedList.add( new ProcessBurst(currentProcess, quantumTime) );
                readyList.addLast(currentProcess);
                readyList.removeFirstOccurrence(currentProcess);
                selfClock = 0;
                currentProcess = chooseCurrentProcess();
                qTime = quantumTime;
            }
            else {
                currentProcess.decRemTime();
                selfClock++;
                qTime--;
                clock++;
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
        //sorting according to arrival time
        Collections.sort(readyList, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                if (p1.arrivalTime > p2.arrivalTime) return 1;
                if (p1.arrivalTime < p2.arrivalTime) return -1;
                return 0;
            }});
    }

    private Process chooseCurrentProcess() {
        for (Process p : readyList) {
            if (p.getArrivalTime() <= clock)
                return p;
        }
        return null;
    }


}   //RR_Scheduler Class

