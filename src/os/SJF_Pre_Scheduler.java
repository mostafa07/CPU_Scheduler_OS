/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:37 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SJF_Pre_Scheduler extends Scheduler {

    //Member variables
    private int clock;

    //Constructor

    public SJF_Pre_Scheduler(LinkedList<Process> initialList) {
        super(initialList);
    }


    //Other methods

    @Override
    public void startScheduling() {

        long safetyClock = System.nanoTime();
        clock = 0;
        int selfClock = 0;
        processedList = new LinkedList<>();
        readyList = initialList;
        sortReadyList();

        Process currentProcess = null;

        while ( !readyList.isEmpty() ) {

            if (currentProcess == null) {
                currentProcess = chooseCurrentProcess();
            }
            else if ( currentProcess.isFinished() ) {
                currentProcess.setDepartureTime(clock);
                processedList.add( new ProcessBurst(currentProcess, selfClock) );
                readyList.remove(currentProcess);
                //leave process in place in list
                selfClock = 0;
                currentProcess = chooseCurrentProcess();
            }
            else if ( anotherWithShorterJobAvailable(currentProcess) != null ) {
                processedList.add(new ProcessBurst(currentProcess, selfClock));
                currentProcess = anotherWithShorterJobAvailable(currentProcess);
                selfClock = 0;
            }

            if (currentProcess != null) {
                currentProcess.decRemTime();
                selfClock++;
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
        //sorting according to remaining time
        Collections.sort(readyList, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                if (p1.getRemainingTime() > p2.getRemainingTime()) return 1;
                if (p1.getRemainingTime() < p2.getRemainingTime()) return -1;
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

    private Process anotherWithShorterJobAvailable(Process currentProcess) {
        for (int i = 0; i < readyList.indexOf(currentProcess); ++i) {
            Process p = readyList.get(i);
            if (p.getArrivalTime() <= clock)
                return p;
        }
        return null;
    }


}   //SJF_PRE_Scheduler Class
