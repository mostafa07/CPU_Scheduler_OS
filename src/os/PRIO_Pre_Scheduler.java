/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/13/2017, 5:35 AM
 */
package os;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PRIO_Pre_Scheduler extends Scheduler {

    //Member variables
    private int clock;

    //Constructor

    public PRIO_Pre_Scheduler(LinkedList<Process> initialList) {
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
            else if ( anotherWithHigherPriorityAvailable(currentProcess) != null ) {
                processedList.add(new ProcessBurst(currentProcess, selfClock));
                currentProcess = anotherWithHigherPriorityAvailable(currentProcess);
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
    }

    private Process chooseCurrentProcess() {
        for (Process p : readyList) {
            if (p.getArrivalTime() <= clock)
                return p;
        }
        return null;
    }

    private Process anotherWithHigherPriorityAvailable(Process currentProcess) {
        int limit = readyList.indexOf(currentProcess);
        for (int i = 0; i < limit; ++i) {
            Process p = readyList.get(i);
            if (p.getArrivalTime() <= clock)
                return p;
        }
        return null;
    }

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

}   //PRIO_Pre_Scheduler Class
