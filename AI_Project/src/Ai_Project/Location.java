package Ai_Project;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Location implements Comparable<Location>{
    private int x_coordinate;
    private int y_coordinate;
    private int load;
    private double e_time;
    private double l_time;
    boolean visited; // no need
    private double heuristic_C=0;
    private double heuristic_T=0;
    private double heuristic_D=0;
    private int index ;
    private double Wait  = -1 ;
    private double serTime;
    int lastAlternative = 0 ; //used in forward checking and arcConsistency
    int lastIndex=0; //used in backtracking

    public Location(int x_coordinate, int y_coordinate, int load, double e_time, double l_time, boolean visited, int index) {
        super();
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.load = load;
        this.e_time = e_time;
        this.l_time = l_time;
        this.visited = visited;
        this.index = index ;
    }

    public double getSerTime() {
        return serTime;
    }

    public void setSerTime(double serTime) {
        this.serTime = serTime;
    }

    public double getWait() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.Wait;
        return Double.parseDouble(df.format(temp));
    }



    public void setWait(double wait) {
        Wait = wait;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getX_coordinate() {
        return x_coordinate;
    }
    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }
    public int getY_coordinate() {
        return y_coordinate;
    }
    public void setY_coordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
    public int getLoad() {
        return load;
    }
    public void setLoad(int load) {
        this.load = load;
    }
    public double getE_time() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.e_time;
        return Double.parseDouble(df.format(temp));
    }
    public void setE_time(double e_time) {
        this.e_time = e_time;
    }
    public double getL_time() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.l_time;
        return Double.parseDouble(df.format(temp));
    }
    public void setL_time(double l_time) {
        this.l_time = l_time;
    }
    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double getHeuristic_C() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.heuristic_C;
        return Double.parseDouble(df.format(temp));
    }
    public void setHeuristic_C(double heuristic_C) {
        this.heuristic_C = heuristic_C;
    }
    public double getHeuristic_T() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.heuristic_T;
        return Double.parseDouble(df.format(temp));
    }
    public void setHeuristic_T(double heuristic_T) {
        this.heuristic_T = heuristic_T;
    }
    public double getHeuristic_D() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.heuristic_D;
        return Double.parseDouble(df.format(temp));
    }
    public void setHeuristic_D(double heuristic_D) {
        this.heuristic_D = heuristic_D;
    }
    @Override
    public String toString() {
        return "Location [x_coordinate=" + x_coordinate + ", y_coordinate=" + y_coordinate + ", load=" + load + ", e_time="
                + e_time + ", l_time=" + l_time + ", heuristic_C=" + heuristic_C + ", heuristic_T=" + heuristic_T
                + ", heuristic_D=" + heuristic_D + ", index=" + index + "]";
    }
    public int compareTo(Location l){ //the class implements comparabel so that sorting when displaying the result is easier
        if(this.serTime==l.serTime)
            return 0;
        else if(this.serTime>l.serTime)
            return 1;
        else
            return -1;
    }

}
