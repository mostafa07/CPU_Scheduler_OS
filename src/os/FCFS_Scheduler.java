/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:35 AM
 */

package os;

import java.util.LinkedList;

public class FCFS_Scheduler extends Scheduler {

    //Constructor

    public FCFS_Scheduler(LinkedList<Process> initialList) {
        super(initialList);
    }


    //Other methods

    @Override
    public void startScheduling() {

        long safetyClock = System.nanoTime();
        int clock = 0;  //for calculating TAT and WT
        int selfClock = 0;
        sortInitialList();
        readyList = initialList;
        processedList = new LinkedList<>();

        while ( !readyList.isEmpty() ) {

            Process currentProcess = readyList.get(0);

            if ( currentProcess.isFinished() ) {
                currentProcess.setDepartureTime(clock);
                readyList.removeFirst();
                processedList.add(new ProcessBurst(currentProcess, selfClock));
                selfClock = 0;
            }
            else {
                currentProcess.decRemTime();
                selfClock++;
                clock++;    //for calculating TAT and WT
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
        //do nothing
    }


}   //FCFS_Scheduler Class
