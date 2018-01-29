/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/11/2017, 5:34 AM
 */

package os;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

public class MainClass {

    //Shared variables

    public static LinkedList<Process> initialList;
    public static char schedulerType;
    public static int numOfProcesses;
    public static int quantumTime;
    public static Vector<Integer> burstTimes;
    public static Vector<Integer> turnAroundTimes;
    public static Vector<Integer> waitingTimes;

    //main() method

    public static void main (String[] args) {

        do {

            schedulerType = getSchedulerType();
            numOfProcesses = getNumberOfProcesses();
            getProcessesInfo(numOfProcesses);
            saveBurstTime();
            //displayInitialList();
            startScheduling(schedulerType);
            calcWaitingTime();
            displayTimes();

        } while (oneMoreTime());

    }   //main() method


    ////////////////////////////////////////////////////////////////////////////////////////


    //Other methods

    private static char getSchedulerType() {

        System.out.println("Please select Scheduler Type:\n\n" +
                "\t1. First Come First Served\n" +
                "\t2. Shortest Job First (Non-Preemptive)\n" +
                "\t3. Shortest Job First (Preemptive)\n" +
                "\t4. Priority (Non-Preemptive)\n" +
                "\t5. Priority (Preemptive)\n" +
                "\t6. Round Robin\n");

        Scanner sc = new Scanner(System.in);
        char chosen;
        while (true) {
            chosen = sc.next().charAt(0);
            if (chosen < '1' || chosen > '6')
                System.out.println("Retry:");
            else
                break;
        }
        //sc.close();
        return chosen;

    }

    private static int getNumberOfProcesses() {

        System.out.println("\nPlease enter number of processes:\n");
        Scanner sc = new Scanner(System.in);

        int numProcesses;
        while (true) {
            numProcesses = sc.nextInt();
            if (numProcesses < 1)
                System.out.println("Retry:");
            else
                break;
        }
        //sc.close();
        return numProcesses;

    }

    private static void getProcessesInfo(int numProcesses) {

        Scanner sc = new Scanner(System.in);
        initialList = new LinkedList<>();

        for (int i = 0; i < numProcesses; ++i) {
            int pid = i + 1;
            int arrivalTime;
            int remainingTime;

            if (schedulerType == '4' || schedulerType == '5') {     //priority schedulers
                int priority;
                if (i == 0) {
                    System.out.println("\nPlease enter details of each process in the following order:\n" +
                            "Process#ID :\tArrival Time |\tBurst Time |\tPriority\n");
                }

                System.out.print("Process#" + pid + "  :\t");

                arrivalTime = sc.nextInt();
                remainingTime = sc.nextInt();
                priority = sc.nextInt();

                initialList.addLast( new PriorityProcess(pid, arrivalTime, remainingTime, priority) );
            }
            else {
                if (i == 0) {
                    System.out.println("\nPlease enter details of each process in the following order:\n" +
                            "Process#ID :\tArrival Time |\tBurst Time\n");
                }

                System.out.print("Process#" + pid + "  :\t");

                arrivalTime = sc.nextInt();
                remainingTime = sc.nextInt();

                initialList.addLast( new Process(pid, arrivalTime, remainingTime) );
            }

        }

        //sc.close();
    }

    private static void saveBurstTime() {

        burstTimes = new Vector<>();

        for (Process p : initialList) {
            burstTimes.addElement( p.getRemainingTime() );
        }

    }

    private static void calcTurnAroundTime() {

        turnAroundTimes = new Vector<>();

        for (Process p : initialList) {
            turnAroundTimes.addElement( p.getDepartureTime() - p.getArrivalTime() );
        }

    }

    private static void calcWaitingTime() {

        waitingTimes = new Vector<>();

        calcTurnAroundTime();

        int i = 0;
        for (Process p : initialList) {
            waitingTimes.addElement( turnAroundTimes.get(i) - burstTimes.get(i) );
            ++i;
        }

    }

    private static void displayTimes() {

        double sumTAT = 0, sumWT = 0;

        for (int i = 0; i < numOfProcesses; ++i) {
            sumTAT += (double) turnAroundTimes.get(i);
            sumWT += (double) waitingTimes.get(i);
        }

        System.out.println("\nAverage Turnaround Time =\t" + sumTAT / numOfProcesses);
        System.out.println("Average Waiting Time =\t" + sumWT / numOfProcesses);

    }

    private static void displayInitialList() {
        for (Process p : initialList)
            p.display();
        System.out.println();

    }

    private static void startScheduling(char schedulerType) {

        switch (schedulerType) {
            case ('1'): {
                Scheduler FCFS1 = new FCFS_Scheduler(initialList);
                FCFS1.startScheduling();
                FCFS1.displayProcessedList();
                break;
            }
            case ('2'): {
                Scheduler SJF1 = new SJF_Non_Pre_Scheduler(initialList);
                SJF1.startScheduling();
                SJF1.displayProcessedList();
                break;
            }
            case ('3'): {
                Scheduler SJF2 = new SJF_Pre_Scheduler(initialList);
                SJF2.startScheduling();
                SJF2.displayProcessedList();
                break;
            }
            case ('4'): {
                Scheduler prio1 = new PRIO_Non_Pre_Scheduler(initialList);
                prio1.startScheduling();
                prio1.displayProcessedList();
                break;
            }
            case ('5'): {
                Scheduler prio2 = new PRIO_Pre_Scheduler(initialList);
                prio2.startScheduling();
                prio2.displayProcessedList();
                break;
            }
            case ('6'): {
                System.out.println("\nPlease enter quantum time for the Round Robin scheduler:\n");
                Scanner sc = new Scanner(System.in);
                while (true) {
                    quantumTime = sc.nextInt();
                    if (quantumTime <= 0)
                        System.out.println("Retry:");
                    else
                        break;
                }

                Scheduler rr1 = new RR_Scheduler(initialList, quantumTime);
                rr1.startScheduling();
                rr1.displayProcessedList();
                //sc.close();
                break;
            }
            default:
                break;
        }

    }   //startScheduling()

    private static boolean oneMoreTime() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nOne more time?!    y/n\n");

        char answer;
        while (true) {
            answer = sc.next().charAt(0);
            if (answer == 'y' || answer == 'Y') {
                return true;
            }
            else if (answer == 'n' || answer == 'N') {
                sc.close();     //last :D
                return false;
            }

            System.out.println("Retry:");
        }
    }

}   //MainClass Class\
