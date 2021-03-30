package Ai_Project;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * @author Main
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateFile {
    public static void randomGenerate() throws IOException {
        int randomCapacity = ThreadLocalRandom.current().nextInt(15, 25 + 1);
        int numOfLocation = ThreadLocalRandom.current().nextInt(3, 6 + 1);
        int depotX = 0;
        int depotY = 0;
        int pickupX;
        int pickupY;
        int deliveryX;
        int deliveryY;
        int load;
        int earlyTime;
        int lateTime;
        int preEarlyTime = 10;
        int preLateTime = 5;

        FileWriter file = new FileWriter("Data.txt");
        file.write(randomCapacity + "\n");
        file.write(depotX + " " + depotY + "\n");


        for (int i = 0; i < numOfLocation; i++) {
            pickupX = ThreadLocalRandom.current().nextInt(-8, 8 + 1);
            pickupY = ThreadLocalRandom.current().nextInt(-8, 8 + 1);
            deliveryX = ThreadLocalRandom.current().nextInt(Math.max(-8, pickupX - 5), 10 + 1);
            deliveryY = ThreadLocalRandom.current().nextInt(Math.max(-8, pickupY - 5), 10 + 1); // 8 -> 10
            load = ThreadLocalRandom.current().nextInt(1, randomCapacity - 10 + 1);
            earlyTime = ThreadLocalRandom.current().nextInt(preEarlyTime - 8, preEarlyTime + 40 + 1);  //20-->13 40-->20 // + 13 in the first term
            int dis = (int) Math.sqrt(Math.pow(deliveryX - pickupX, 2) + Math.pow(deliveryY - pickupY, 2));
            lateTime = ThreadLocalRandom.current().nextInt(earlyTime + dis + 8, earlyTime + dis + 50 + 1);  //30-->13 60-->39 // 50 -> 100
            file.write(pickupX + " " + pickupY + " " + deliveryX + " " + deliveryY + " " + load + " " + -1 * load + " " + earlyTime + " " + lateTime + " " + earlyTime + " " + lateTime + "\n");
            //For next iteration
            preEarlyTime = earlyTime;
            preLateTime = lateTime;
        }

        file.close();
    }

}

