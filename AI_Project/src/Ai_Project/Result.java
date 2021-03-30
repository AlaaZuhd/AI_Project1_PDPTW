package Ai_Project;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Result {

    private String PickupXY;
    private String DeliveryXY;
    private String PickupTW;
    private String DeliveryTW;
    private int Load;
    private int Request_ID ;
    private double PickupTime ;
    private double DeliveryTime ;

    public Result(String pickupXY, String deliveryXY, String pickupTW, String deliveryTW, int load, int request_ID, double pickupTime, double deliveryTime) {
        PickupXY = pickupXY;
        DeliveryXY = deliveryXY;
        PickupTW = pickupTW;
        DeliveryTW = deliveryTW;
        Load = load;
        Request_ID = request_ID;
        PickupTime = pickupTime;
        DeliveryTime = deliveryTime;
    }

    public String getPickupXY() {
        return PickupXY;
    }

    public void setPickupXY(String pickupXY) {
        PickupXY = pickupXY;
    }

    public String getDeliveryXY() {
        return DeliveryXY;
    }

    public void setDeliveryXY(String deliveryXY) {
        DeliveryXY = deliveryXY;
    }

    public String getPickupTW() {
        return PickupTW;
    }

    public void setPickupTW(String pickupTW) {
        PickupTW = pickupTW;
    }

    public String getDeliveryTW() {
        return DeliveryTW;
    }

    public void setDeliveryTW(String deliveryTW) {
        DeliveryTW = deliveryTW;
    }

    public int getLoad() {
        return Load;
    }

    public int getRequest_ID() {
        return Request_ID;
    }

    public double getPickupTime() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.PickupTime;
        return Double.parseDouble(df.format(temp));
    }

    public void setPickupTime(double pickupTime) {
        PickupTime = pickupTime;
    }

    public double getDeliveryTime() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp=this.DeliveryTime;
        return Double.parseDouble(df.format(temp));
    }

    public void setLoad(int load) {
        Load = load;
    }

    public void setRequest_ID(int request_ID) {
        Request_ID = request_ID;
    }


    public void setDeliveryTime(double deliveryTime) {
        DeliveryTime = deliveryTime;
    }
}
