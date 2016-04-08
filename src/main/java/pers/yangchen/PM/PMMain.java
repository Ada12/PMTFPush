package pers.yangchen.PM;

import java.io.IOException;

/**
 * Created by yangchen on 16-4-7.
 */
public class PMMain {
    public static void main(String[] args){
//        GetPMTF getPMTF = new GetPMTF();
//        String result = getPMTF.getMethod();
//        System.out.print(result);
        GetPosition getPosition = new GetPosition();
        try {
            getPosition.getMethod();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
