/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:35 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PRIO_Non_Pre_Scheduler extends Scheduler {

    //Constructor
    public PRIO_Non_Pre_Scheduler(LinkedList<Process> initialList) {
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
        sortReadyList();

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
                } else {
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
        //sorting according to priority
        Collections.sort(readyList, (o1, o2) -> {
            PriorityProcess p1 = (PriorityProcess) o1;
            PriorityProcess p2 = (PriorityProcess) o2;
            if (p1.getPriority() > p2.getPriority()) return 1;
            if (p1.getPriority() < p2.getPriority()) return -1;
            return 0;
        });
    }

}   //PRIO_Non_Pre_Scheduler Class
