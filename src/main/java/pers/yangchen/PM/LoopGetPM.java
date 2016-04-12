package pers.yangchen.PM;

import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Created by yangchen on 16-4-11.
 */
public class LoopGetPM extends Thread{
    public LoopGetPM(){}

    public void run(){
        for(int i=0;i<10;i++){
        GetPMTF getPMTF = new GetPMTF();
        getPMTF.getMethod();
        try {
            this.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    public static void main(String[] args){
        LoopGetPM loopGetPM = new LoopGetPM();
        Thread thread = new Thread(loopGetPM);
        thread.run();
    }
}
