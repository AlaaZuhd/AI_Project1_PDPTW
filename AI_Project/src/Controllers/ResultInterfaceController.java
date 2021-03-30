/**
 * Sample Skeleton for 'ResultInterface.fxml' Controller Class
 */

package Controllers;

import Ai_Project.Location;
import Ai_Project.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static Ai_Project.AlgorithmImplementation.*;
import static Controllers.MainInterfaceController.*;
import static Controllers.MainInterfaceController.pickup;
import static Controllers.MainInterfaceController.option;

public class ResultInterfaceController implements Initializable {
    static double totalTravelDistance = 0;
    static double totalWaitingTime = 0;
    static double totalNeededTime = 0;

    private ObservableList<Result> dataList;

    @FXML // fx:id="resultPane"
    private AnchorPane resultPane; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<Result> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="depotCoordinate"
    private TextField depotCoordinate; // Value injected by FXMLLoader

    @FXML // fx:id="Load"
    private TableColumn<Result, Integer> Load; // Value injected by FXMLLoader

    @FXML // fx:id="Request_ID"
    private TableColumn<Result, Integer> Request_ID; // Value injected by FXMLLoader

    @FXML
    private TableColumn<Result, String> PickupXY;

    @FXML
    private TableColumn<Result, String> DeliveryXY;

    @FXML
    private TableColumn<Result, String> PickupTW;

    @FXML
    private TableColumn<Result, String> DeliveryTW;


    @FXML // fx:id="PickupTime"
    private TableColumn<Result, Double> PickupTime; // Value injected by FXMLLoader

    @FXML // fx:id="DeliveryTime"
    private TableColumn<Result, Double> DeliveryTime; // Value injected by FXMLLoader


    @FXML // fx:id="totalCapacity"
    private TextField totalCapacity; // Value injected by FXMLLoader

    @FXML
    private TextField numberOfBackTracking;

    @FXML
    private Button btnGetResult;

    @FXML
    private TextField TotalWaitingTime;

    @FXML
    private TextField TotalTravelDistance;

    @FXML
    private TextField numberOfRequest;

    @FXML
    private TextField vechileSpeed;

    @FXML
    private TextField TotalNeededTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        totalNeededTime = 0;
        totalTravelDistance = 0;
        totalWaitingTime = 0;
        int depotX=path.get(0).getX_coordinate();
        int depotY=path.get(0).getY_coordinate();
        depotCoordinate.setText("("+depotX+","+depotY+")");
        totalCapacity.setText("" + vechile.getCapacity());
        numberOfBackTracking.setText(""); // add them latter
        numberOfRequest.setText("" + size / 2);
        vechileSpeed.setText("1");
        if(option==2&&totalNumberOfBackTacking!=0){
            totalNumberOfBackTacking--;
        }
        numberOfBackTracking.setText("" + totalNumberOfBackTacking);
        calculateData();
        TotalWaitingTime.setText("" + totalWaitingTime + " Unit Time");
        TotalTravelDistance.setText("" + totalTravelDistance + " Unit Distance");
        TotalNeededTime.setText("" + totalNeededTime + " Unit Time");
//        for (int i = 0; i < visited.length; i++) {
//            System.out.println(visited[i]);
//        }
        showTable();
    }

    //this function calculates the totalWaitingTime, totalNeededTime,totalTravelDistance as follows
    public static void calculateData() {
        for (int i = 1; i < path.size(); i++) { //totalWaitingTime is the summation of each wait at each different location
            if (path.get(i).getWait() != -1)
                totalWaitingTime += path.get(i).getWait();
        }
        Location easy = path.get(path.size() - 1);
        if (easy.getLoad() > 0)
            totalNeededTime = visited[easy.getIndex()];
        else
            totalNeededTime = visited[easy.getIndex() + size / 2]; //totalNeededTime is the time that last location on the path is visited at
        totalTravelDistance = totalNeededTime - totalWaitingTime; //totalTravelDistance is the difference between totalNeededTime and the WaitTime

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Double temp = totalNeededTime;
        totalNeededTime = Double.parseDouble(df.format(temp));
        temp = totalWaitingTime;
        totalWaitingTime = Double.parseDouble(df.format(temp));
        temp = totalTravelDistance;
        totalTravelDistance = Double.parseDouble(df.format(temp));

    }

    public void showTable() { //printing a table containing the value of all variables(locations)

        ArrayList<Result> temp = new ArrayList<>();
        for (int i = 1; i < pickup.size(); i++) {
            String pickupXY= "("+pickup.get(i).getX_coordinate()+" , "+pickup.get(i).getY_coordinate()+")";
            String deliveryXY="("+delivery.get(i).getX_coordinate()+" , "+delivery.get(i).getY_coordinate()+")";
            String pickupTW="["+pickup.get(i).getE_time()+" , "+pickup.get(i).getL_time()+"]";
            String deliveryTW="["+delivery.get(i).getE_time()+" , "+delivery.get(i).getL_time()+"]";
            Result es=new Result(pickupXY,deliveryXY,pickupTW,deliveryTW,pickup.get(i).getLoad(),
                    pickup.get(i).getIndex(), visited[pickup.get(i).getIndex()], visited[pickup.get(i).getIndex() + size / 2]);
            temp.add(es);
        }

        Request_ID.setCellValueFactory(new PropertyValueFactory<Result, Integer>("Request_ID"));
        PickupXY.setCellValueFactory(new PropertyValueFactory<Result, String>("PickupXY"));
        DeliveryXY.setCellValueFactory(new PropertyValueFactory<Result, String>("DeliveryXY"));
        PickupTW.setCellValueFactory(new PropertyValueFactory<Result, String>("PickupTW"));
        DeliveryTW.setCellValueFactory(new PropertyValueFactory<Result, String>("DeliveryTW"));
        Load.setCellValueFactory(new PropertyValueFactory<Result, Integer>("Load"));
        PickupTime.setCellValueFactory(new PropertyValueFactory<Result, Double>("PickupTime"));
        DeliveryTime.setCellValueFactory(new PropertyValueFactory<Result, Double>("DeliveryTime"));
        dataList = FXCollections.observableArrayList(temp);
        tableView.setItems(dataList);

    }

    @FXML
    void getResultVisualize(ActionEvent event) {

        //moving from stack to arraylist then sorting based on time
        ArrayList<Location> pathL = new ArrayList<>();
        Location t=new Location(path.get(0).getX_coordinate(),path.get(0).getY_coordinate(),path.get(0).getLoad(), path.get(0).getE_time(), path.get(0).getL_time(), true, path.get(0).getIndex());
        pathL.add(t);
        for (int i = 1; i < path.size(); i++) {
            Location temp = new Location(path.get(i).getX_coordinate(), path.get(i).getY_coordinate(), path.get(i).getLoad(), path.get(i).getE_time(), path.get(i).getL_time(), true, path.get(i).getIndex());
            if (path.get(i).getLoad() > 0)
                temp.setSerTime(visited[path.get(i).getIndex()]);
            else
                temp.setSerTime(visited[path.get(i).getIndex() + size / 2]);
            pathL.add(temp);
        }
        Collections.sort(pathL);
        Group root = new Group();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        // Creating the chart
        LineChart<Number, Number> lineChart = new LineChart(xAxis, yAxis);
        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        XYChart.Series series = new XYChart.Series();
        series.setName("Visualizing The Path");
        for (int i = 0; i < pathL.size(); i++) {
            XYChart.Data chartData;
            double x = pathL.get(i).getX_coordinate();
            double y = pathL.get(i).getY_coordinate();
            chartData = new XYChart.Data(x, y);
            chartData.setNode(new Label(i  + ""));
            series.getData().add(chartData);

        }
        Line line = new Line();
        lineChart.getData().add(series);
        final Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //to access main stage
        stage.initOwner(app_stage);
        Scene scene = new Scene(lineChart, 854, 677);
        //Setting title to the scene
        stage.setTitle("Visualizing");
        //Adding the scene to the stage
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();

    }

}
