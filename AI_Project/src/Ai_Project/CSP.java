package Ai_Project;


public class CSP {


    // Most constraining variable
    public static int capacityConstraint(int currentLoad , int capacity , int locationLoad) {
        if(locationLoad > 0) { //  pickup
            if(currentLoad + locationLoad > capacity )
                return -1;
            else
                return currentLoad + locationLoad ;

        }
        else { // delivery
            return currentLoad + locationLoad;

        }
    }

    // MRV Minimum Remaining Values
    public static double timeConstraint(double currentTime , double distance , Location check ) {
        if(currentTime + distance >= check.getE_time() && currentTime + distance <= check.getL_time()){
            check.setWait(-1);
            return check.getL_time() - currentTime - distance ;
        }
        else if(currentTime + distance < check.getE_time()) {
            check.setWait((check.getE_time()-distance-currentTime));
            return check.getL_time() - currentTime - distance ;
        }
        else
            return -1 ;
    }

    // Least constraint value
    public static double distanceConstraint(int x1,int y1,int x2,int y2) {
        double distance = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        return distance;
    }



}
