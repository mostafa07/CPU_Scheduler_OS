/**
 * Project: CPU_Scheduler__OS_Project
 * Created by: Mostafa Mahmoud
 * on: 4/12/2017, 5:37 AM
 */

package os;

public class ProcessBurst {

    //Member variables

    private Process process;
    private int timeBurst;

    //Constructor

    public ProcessBurst(Process process, int timeBurst) {
        this.process = process;
        this.timeBurst = timeBurst;
    }


    //Getters and setters

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getTimeBurst() {
        return timeBurst;
    }

    public void setTimeBurst(int timeBurst) {
        this.timeBurst = timeBurst;
    }

}   //ProcessBurst Class
