/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/11/2017, 5:36 AM
 */

package os;

public class Process {

    //Member variables

    protected int pid;
    protected int arrivalTime;
    protected int remainingTime;
    protected int departureTime;


    //Constructor

    public Process(int pid, int arrivalTime, int remainingTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.remainingTime = remainingTime;
    }


    //Getters and setters

    public int getPid() { return pid; }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getDepartureTime() { return departureTime; }

    public void setDepartureTime(int departureTime) { this.departureTime = departureTime; }


    //Other methods
    public boolean isFinished() {
        return remainingTime == 0? true: false;
    }

    public void decRemTime() {
        remainingTime--;
    }

    public void display() {
        System.out.println("PID = " + this.pid + " | Arrival Time = " + this.arrivalTime +
                            " | Burst Time = " + this.remainingTime);
    }

}   //Process Class
