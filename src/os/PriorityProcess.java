/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/11/2017, 5:36 AM
 */

package os;

public class PriorityProcess extends Process {

    //Member variables

    private int priority;


    //Constructor

    public PriorityProcess(int pid, int arrivalTime, int remainingTime, int priority) {
        super(pid, arrivalTime, remainingTime);
        this.priority = priority;
    }


    //Getters and setters

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    //Other methods

    @Override
    public void display() {
        System.out.println("PID = " + super.pid + " | Arrival Time = " + super.arrivalTime +
                " | Burst Time = " + super.remainingTime + " | Priority = " + this.priority);
    }

}   //PriorityProcess Class
