package Ai_Project;

import javafx.scene.control.Alert;

import static Controllers.MainInterfaceController.pickup;
import static Controllers.MainInterfaceController.delivery;
import static Controllers.MainInterfaceController.vechile;
import static Controllers.MainInterfaceController.option;

import java.util.ArrayList;
import java.util.Arrays;

import static Controllers.MainInterfaceController.path;

public class AlgorithmImplementation extends Thread {
    public static int size = 2 * pickup.size() - 1; //the number of location that should be visited
    static int counter = 0;
    public static double[] visited = new double[size]; //this array stores the time when a location is visited at, its first half representing the pickups and the second representing the deliveries,so reaching a pickup index would be using its index, but a delivery would be using its index plus (size/2)
    public static int[] testing = new int[size]; //used in backtracking, more details there

    public static int totalNumberOfBackTacking = 0;

    public static Location B;
    public static Location currentLocation;
    public static double currentTime = 0;
    static int j = 1;
    public static Location min;
    public static boolean flag = false;
    public static boolean fflag = true;

    public void run() {
        //initializing the used variables
        size = 2 * pickup.size() - 1;
        counter = 0;
        visited = new double[size];
        testing = new int[size];
        min = null;
        Arrays.fill(visited, -1);
        Arrays.fill(testing, 0);
        totalNumberOfBackTacking = 0;
        currentTime = 0;
        flag = false;
        fflag = true;
        vechile.setCurrentLoad(0);

        if (size <= 1) { //If there is only the depot
            System.out.println("Finish :)");
            return;
        }
        int i = 0;
        Location check; //is used when finiding the next minimum location
        currentLocation = pickup.get(0); //depot
        path.add(pickup.get(0));  // add depot to the path
        visited[0] = 0;
        System.out.println("We are here!");
        if(!Test()) try {
            throw new Exception("");
        } catch (Exception e) {
        }
        L:
        while (path.size() < size && fflag) { // need to visit all locations
            j = 1;
            min = chooseFirstUnvisitedPickup();
            if (j < pickup.size()) {
                for (i = j; i < pickup.size(); i++) { // finding the min in the pickups
                    if (visited[i] == -1) {
                        check = pickup.get(i);
                        calculateHeuristcs(check);
                        if (isCheckMin(min, check))
                            min = check;
                    }
                }
                j = 1;
                for (i = j; i < delivery.size(); i++) { // minimum between min of pickup and the delivery
                    if (visited[i] != -1 && visited[i + size / 2] == -1) { // the precedence of pickup ( pickup -> delivery )
                        check = delivery.get(i);
                        calculateHeuristcs(check);
                        if (isCheckMin(min, check))
                            min = check;
                    }
                }
            } else { // if all pickups are done we should consider the delivery
                j = 1;
                min = chooseFirstUnvistedDelivery();
                if (j < delivery.size()) {
                    for (i = j; i < delivery.size(); i++) {
                        if (visited[i] != -1 && visited[i + size / 2] == -1) { // special loop
                            check = delivery.get(i);
                            calculateHeuristcs(check);
                            if (isCheckMin(min, check))
                                min = check;
                        }
                    }
                }
            }
            int index = 0;
            calculateHeuristcs(min);
            if (min.getHeuristic_T() == -1) {
                //System.out.println("entered from here! -1 ");
                try {
                    BackTracking(min);
                    if (option == 0) {   //this case is used when backtracking is chosen ONLY
                        if (min.lastIndex == path.indexOf(min)) {  //is the last index a location has been in after backtracking is the same after
                            upDatePathByPopping();                 //this backtracking, then we need to go deeper in backtracking
                            BackTracking(min);
                        }
                        min.lastIndex = path.indexOf(min);
                    }
                } catch (Exception e) {
                    System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    fflag = false;
                    break L;
                }
            }
            if (option == 0) {
                System.out.println(min);
                if (path.indexOf(min) < 0) {
                    upDatePathByPushing(min);
                    counter++;   //this counter is increamented, keeping track of the visited locations so far.
                }
            }
            if (option == 1) {
                //System.out.println("Forwrd");
                if (path.indexOf(min) < 0) { // forward
                    upDatePathByPushing(min);
                    //We add a location to the path, then checks if after adding it, all other unvisited location can still be visited without
                    //violating the constraints
                    while (!forwardChecking()) {
                        //if no, we check for an alternative for the choesen node
                        if (checkAlternativeForward() && path.indexOf(min) < 0) {
                            calculateHeuristcs(min);
                            upDatePathByPushing(min);
                            //break;
                        } else { //if no availabe alternatives, we backtrack the chosen node
                            calculateHeuristcs(min);       // new
                            try {
                                BackTracking(min);
                            } catch (Exception e) {

                            }
                        }
                    }
                } else {
                    while (!forwardChecking()) {
                        if (checkAlternativeForward() && path.indexOf(min) < 0) {
                            calculateHeuristcs(min);
                            upDatePathByPushing(min);
                        } else {
                            calculateHeuristcs(min);
                            try {
                                BackTracking(min);
                            } catch (Exception e) {
                            }
                        }
                    }
                    counter++;
                }
            }
            // arc consistancy

            if (option == 2) {
                // System.out.println("ARC");
                //same logic used in forward checking is implemented here, but with arcConsistency, we are considerng a node that is good for two steps ahead
                if (path.indexOf(min) < 0) {
                    upDatePathByPushing(min);
                    int result = arcConsistancy();
                    while (result == -1 || result == 0) {
                        if (checkAlternativeArcConsistancy() && path.indexOf(min) < 0) {
                            calculateHeuristcs(min);
                            upDatePathByPushing(min);
                        } else {
                            calculateHeuristcs(min);
                            try {
                                BackTracking(min);
                            } catch (Exception e) {
                                fflag = false;
                                break L;
                            }
                        }
                        result = arcConsistancy();
                    }
                } else {
                    int result = arcConsistancy();
                    while (result == 0 || result == -1) {
                       //  System.out.println("enter from 1\n");
                        if (checkAlternativeArcConsistancy() && path.indexOf(min) < 0) {
                            calculateHeuristcs(min);
                            upDatePathByPushing(min);
                        } else {
                            calculateHeuristcs(min);
                            try {
                                BackTracking(min);
                            } catch (Exception e) {
                                fflag = false;
                                break L;
                            }
                        }
                    }

                    counter++;
                }
            }
        }
        System.out.print("Number of BackTracking  = " + totalNumberOfBackTacking + "\n");
        for (int k = 0; k < path.size(); k++) {
            System.out.println(path.get(k).toString());
        }


    }


    //This method as its name indicates, calculates the heuritics of a given node with respect to the current location
    public static void calculateHeuristcs(Location node) {
        node.setHeuristic_C(CSP.capacityConstraint(vechile.currentLoad, vechile.capacity, node.getLoad()));
        node.setHeuristic_D(CSP.distanceConstraint(node.getX_coordinate(), node.getY_coordinate(), currentLocation.getX_coordinate(), currentLocation.getY_coordinate()));
        node.setHeuristic_T(CSP.timeConstraint(currentTime, node.getHeuristic_D(), node));
    }

    //This method updates the heuritics for all unvisited nodes with respect to the current location
    //it is called after adding a new location to the path or after poping a location
    public static void updateHeuristicsForAllUnvisited() {
        //pickups
        for (int i = 1; i < pickup.size(); i++) {
            if (visited[i] == -1)
                calculateHeuristcs(pickup.get(i));
        }
        //deliveries
        for (int i = 1; i < delivery.size(); i++) {
            if (visited[i + size / 2] == -1 && visited[i] != -1)
                calculateHeuristcs(delivery.get(i));
        }

    }

    //This method is called when we want to add a location to the path,
    //it updates the current time and set it to the old time plus the distance(as the spped is 1) needed to reach this node, waiting time is also considered here
    //it also updates the current load of the vehicle so that the capacity constraint is always satisfied
    //it updates the current Location to the new top of the stack path now
    //it setes the visiting time for a location in the visited array
    //then it calls the function that calculates the heuritics for all other unvisited nodes
    public static void upDatePathByPushing(Location node) {
        // update
        path.add(node);
        if (node.getWait() != -1)
            currentTime += node.getHeuristic_D() + node.getWait();
        else
            currentTime += node.getHeuristic_D();
        currentLocation = node;
        vechile.setCurrentLoad(vechile.getCurrentLoad() + node.getLoad());
        if (node.getLoad() < 0)
            visited[node.getIndex() + size / 2] = currentTime;
        else
            visited[node.getIndex()] = currentTime;
        updateHeuristicsForAllUnvisited();

    }

    //same concept of upDatePathByPushing
    public static Location upDatePathByPopping() {

        Location top = path.pop();
        currentLocation = path.peek();
        if (top.getWait() != -1)
            currentTime = currentTime - top.getHeuristic_D() - top.getWait();
        else
            currentTime -= top.getHeuristic_D();
        vechile.setCurrentLoad(vechile.getCurrentLoad() - top.getLoad());
        if (top.getLoad() < 0)
            visited[top.getIndex() + size / 2] = -1;
        else
            visited[top.getIndex()] = -1;

        updateHeuristicsForAllUnvisited();
        return top;

    }

    public static void BackTracking(Location BT) throws Exception {
        System.out.println("XXXX");
        totalNumberOfBackTacking++; //this counter is used to keep track of the total number of backtracking
        //when no solution is found, backtracking can either got stuck between two nodes and then run forever, or until the depot is found,
        //detecting going back to depot is easy, however for keep changing between two nodes will be detected using a testing counter
        //each cell represents how many time a location has entered backtracking, if it is more than the size*10 (which is never the case untill there is no solution), no solution will be detected
        testing[BT.getIndex()]++;
        if (testing[BT.getIndex()] >= size * 10) {
            System.out.println("No Solution !!!!!!!!!!");
            fflag = false;
            throw new Exception("No Solution hahaha yarab yozbot");
        }
        //debugging
        System.out.println("enter BackTraking");
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i).toString());
        }
        System.out.println("BT ===>> " + BT.toString());

        if (path.size() <= 1) {
            System.out.println("No Solution!!");
            fflag = false;
            throw new Exception("No Solution hahaha yarab yozbot");
        }
        if (BT.getLoad() > 0 || (BT.getLoad() < 0 && BT.getIndex() != path.peek().getIndex())) {  //we can pop the top of the path
           // System.out.println("Enter backtraking 1");
            upDatePathByPopping();
            calculateHeuristcs(BT);
            while (BT.getHeuristic_C() == -1 || BT.getHeuristic_T() == -1) {
                try {
                    BackTracking(BT);
                    return;
                } catch (Exception e) {
                    throw e;
                }
            }
            calculateHeuristcs(BT);
            upDatePathByPushing(BT);  //if added
            return;

        } else { //we need to backtrack the pickup and maybe then we need to backtrack the delivery or may not we see if we need to do that
            try {
              //System.out.println("Enter backtracking 2");
                Location top = upDatePathByPopping(); // here i have just consider backtracking for the pickup without the delivery
                calculateHeuristcs(top);
                calculateHeuristcs(BT);
                top.setHeuristic_T(-1);  //to indicate a problem with this location
                 BackTracking(top);
                //System.out.println("Done for pickup, back to delivery!");
                if (path.indexOf(BT) < 0) {
                  //System.out.println("Should enter for delivery:");
                    calculateHeuristcs(BT);
                    if (BT.getHeuristic_C() != -1 && BT.getHeuristic_T() != -1) {
                       // System.out.println("Entererd should 1");
                        upDatePathByPushing(BT);
                        return;
                    } else {
                       // System.out.println("Entered should 2");
                        calculateHeuristcs(BT);
                        try {
                            BackTracking(BT);
                        } catch (Exception e) {
                            throw e;
                        }
                    }

                }
            } catch (Exception e) {
                throw e;
            }
        }
        return;
    }
    public static boolean isCheckMin(Location min, Location check) { //if true-->check is the min
        if ((check.getHeuristic_T() < min.getHeuristic_T()) && check.getHeuristic_C() != -1)
            return true;
        else if (check.getHeuristic_T() == min.getHeuristic_T() && check.getHeuristic_C() != -1) {
            //case1 : one with wait and the other without wait
            if (check.getWait() == -1 && min.getWait() != -1)
                return true;
            if (check.getWait() != -1 && min.getWait() == -1)
                return false;
            //case2 : both without wait, use capacity
            if (check.getWait() == -1 && min.getWait() == -1)
                return (check.getHeuristic_C() < min.getHeuristic_C());
            //case3 : both with wait
            if (check.getWait() != -1 && min.getWait() != -1) {
                if (check.getWait() == min.getWait()) {   //both with same wait, then use capacity
                    return (check.getHeuristic_C() < min.getHeuristic_C());
                } else {
                    return (check.getWait() < min.getWait());
                }
            }
        }
        //if(check.getHeuristic_T()> min.getHeuristic_T()) or capacity does not apply
        return false;
    }


    //detecting the first unvisited pickup that does not violate the constraint so that it can be compared with other unvisited to find the minimum
    public static Location chooseFirstUnvisitedPickup() {
        //  j=1;
        Location node = min;
        while (j < pickup.size() && visited[j] != -1) j++; // to get to the first unvisited pickup
        if (j < pickup.size()) {
            do {
                node = pickup.get(j);
                node.setHeuristic_C(CSP.capacityConstraint(vechile.currentLoad, vechile.capacity, node.getLoad()));
                node.setHeuristic_D(CSP.distanceConstraint(node.getX_coordinate(), node.getY_coordinate(), currentLocation.getX_coordinate(), currentLocation.getY_coordinate()));
                node.setHeuristic_T(CSP.timeConstraint(currentTime, node.getHeuristic_D(), node));
                j++;
            } while (node.getHeuristic_C() == -1 && j < pickup.size());
            j--;
        }
        return node;
    }

    //detecting the fisrt unvisited delivery that does not violate the constraint so that it can be compared with other unvisited to find the minimum
    public static Location chooseFirstUnvistedDelivery() {
        //j=1;
        Location node = min;
        while (j < delivery.size() && visited[j + size / 2] != -1) j++;
        if (j < delivery.size()) {
            do {
                node = delivery.get(j);
                node.setHeuristic_C(CSP.capacityConstraint(vechile.currentLoad, vechile.capacity, node.getLoad()));
                node.setHeuristic_D(CSP.distanceConstraint(node.getX_coordinate(), node.getY_coordinate(), currentLocation.getX_coordinate(), currentLocation.getY_coordinate()));
                node.setHeuristic_T(CSP.timeConstraint(currentTime, node.getHeuristic_D(), node));
                j++;
            } while (node.getHeuristic_C() == -1 && j < delivery.size());
            j--;
        }

        return node;

    }

    //when a location does not work with forward checking, we use this method to find an alternative other than the minimum that satisfies forward checkign
    public static boolean checkAlternativeForward() {
        Location toEdit = upDatePathByPopping();
        ArrayList<Location> forwardList = new ArrayList<>();
        for (int i = 1; i < pickup.size(); i++) { // check pickup for conflicting with the current min
            if (pickup.get(i).getIndex() != min.getIndex() && visited[i] == -1) { // assign this pickup then do forward checking and finally do unassign
                if (pickup.get(i).getHeuristic_C() != -1 && pickup.get(i).getHeuristic_T() != -1) {
                    upDatePathByPushing(pickup.get(i));
                    if (forwardChecking()) {
                        forwardList.add(pickup.get(i));
                    }
                    upDatePathByPopping();
                }
            }
        }
        for (int i = 1; i < delivery.size(); i++) {
            if (visited[i + size / 2] == -1.0 && delivery.get(i).getIndex() != min.getIndex()) { // if the current delivery is not visited
                if (visited[i] != -1) { // if the pickup of that delivery is visited
                    if (delivery.get(i).getHeuristic_C() != -1 && delivery.get(i).getHeuristic_T() != -1) {
                        upDatePathByPushing(delivery.get(i));
                        if (forwardChecking()) {
                            forwardList.add(delivery.get(i));
                        }
                        upDatePathByPopping();
                    }
                }
            }
        }
        //choosign the mimimun again of all the location that we are sure that apllies to forward checking
        if (forwardList.size() != 0) {
            Location temp = forwardList.get(0);
            for (int i = 1; i < forwardList.size(); i++) {
                if (forwardList.get(i).getHeuristic_T() < temp.getHeuristic_T())
                    temp = forwardList.get(i);
            }
            if ((temp.getLoad() > 0 && toEdit.lastAlternative == temp.getIndex()) || (temp.getLoad() < 0 && toEdit.lastAlternative == temp.getIndex() + size / 2)) {
                min = toEdit;
               // System.out.println("false mn hon!!!");
                return false;

            } else {
                if (temp.getLoad() > 0)
                    toEdit.lastAlternative = temp.getIndex();
                else
                    toEdit.lastAlternative = temp.getIndex() + size / 2;
            }
            min = temp;
            return true;
        }
        min = toEdit;
        return false;
    }


    public static boolean checkAlternativeArcConsistancy() {
        Location toEdit = upDatePathByPopping();
        ArrayList<Location> consistancyList = new ArrayList<>();
        for (int i = 1; i < pickup.size(); i++) { // check pickup for conflicting with the current min
            if (visited[i] == -1 && pickup.get(i).getIndex() != min.getIndex()) { // assign this pickup then do forward checking and finally do unassign
                if (pickup.get(i).getHeuristic_T() != -1 && pickup.get(i).getHeuristic_C() != -1) { //need to keep the constraints unviolated
                    upDatePathByPushing(pickup.get(i));
                    if (arcConsistancy() == 1) {
                        consistancyList.add(pickup.get(i));
                    }
                    upDatePathByPopping();
                }
            }
        }
        for (int i = 1; i < delivery.size(); i++) {
            if (visited[i + size / 2] == -1 && delivery.get(i).getIndex() != min.getIndex()) { // if the current delivery is not visited
                if (visited[i] != -1) { // if the pickup of that delivery is visited
                    if (delivery.get(i).getHeuristic_C() != -1 && delivery.get(i).getHeuristic_T() != -1) {
                        upDatePathByPushing(delivery.get(i));
                        if (arcConsistancy() == 1) {
                            consistancyList.add(delivery.get(i));
                        }
                        upDatePathByPopping();
                    }
                }
            }
        }
        if (consistancyList.size() != 0) {
            Location temp = consistancyList.get(0);
            for (int i = 1; i < consistancyList.size(); i++) {
                if (consistancyList.get(i).getHeuristic_T() < temp.getHeuristic_T())
                    temp = consistancyList.get(i);
            }
            if ((temp.getLoad() > 0 && toEdit.lastAlternative == temp.getIndex()) || (temp.getLoad() < 0 && toEdit.lastAlternative == temp.getIndex() + size / 2)) {
                min = toEdit;
                //System.out.println("false mn hon!!!");
                return false;

            } else {
                if (temp.getLoad() > 0)
                    toEdit.lastAlternative = temp.getIndex();
                else
                    toEdit.lastAlternative = temp.getIndex() + size / 2;
            }
            min = temp;
            return true;
        }
        min = toEdit;
        return false;
    }

    public static boolean forwardChecking() { // if true then there is no problem , else there is a problem with the current assignment for the min ==> bacTracking(min)
        for (int i = 1; i < pickup.size(); i++) { // check pickup for conflicting with the current min
            if (visited[i] == -1) {
                Location temp = pickup.get(i);
                temp.setHeuristic_D(CSP.distanceConstraint(temp.getX_coordinate(), temp.getY_coordinate(), currentLocation.getX_coordinate(), currentLocation.getY_coordinate()));
                temp.setHeuristic_T(CSP.timeConstraint(currentTime, temp.getHeuristic_D(), temp));
                if (temp.getHeuristic_T() == -1) return false;
            }
        }

        for (int i = 1; i < delivery.size(); i++) {
            if (visited[i + size / 2] == -1) { // if the current delivery is not visited
                if (visited[i] != -1) { // if the pickup of that delivery is visited
                    Location temp = delivery.get(i);
                    temp.setHeuristic_D(CSP.distanceConstraint(temp.getX_coordinate(), temp.getY_coordinate(), currentLocation.getX_coordinate(), currentLocation.getY_coordinate()));
                    temp.setHeuristic_T(CSP.timeConstraint(currentTime, temp.getHeuristic_D(), temp));
                    if (temp.getHeuristic_T() == -1) return false;
                }
            }
        }
        return true;
    }

    public static int arcConsistancy() { // false backtracking for min , else keep going
        //1 no problem
        //0 the problem in forward checking
        //-1 the problem in the second stage, so choose min of unconsistency

//        System.out.println("Path in arcConsistancy ==> ");
//        for (int k = 0; k < path.size(); k++) {
//            System.out.println(path.get(k).toString());
//        }

        boolean flag = false;
        if (!forwardChecking()) {
            return 0;
        }
        else { // else go the second step
            if (path.size() == size)
                return 1;
            for (int i = 1; i < pickup.size(); i++) { // check pickup for conflicting with the current min
                if (visited[i] == -1) { // assign this pickup then do forward checking and finally do unassign
                    upDatePathByPushing(pickup.get(i));
                    if (forwardChecking()) {
                        upDatePathByPopping();
                        return 1;
                    }
                    upDatePathByPopping();
                }
            }
            for (int i = 1; i < delivery.size(); i++) {
                if (visited[i + size / 2] == -1) { // if the current delivery is not visited
                    if (visited[i] != -1) { // if the pickup of that delivery is visited
                        upDatePathByPushing(delivery.get(i));
                        if (forwardChecking()) {
                            upDatePathByPopping();
                            return 1;
                        }
                        upDatePathByPopping();
                    }
                }
            }
        }
        return 1;
    }

    public Boolean Test(){
        for(int i=1;i<pickup.size();i++){
            if(pickup.get(i).getLoad()>vechile.getCapacity())
                return false;
        }
        return true;
    }

}
