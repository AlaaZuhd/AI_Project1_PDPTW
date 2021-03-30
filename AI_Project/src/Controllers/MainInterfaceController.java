package Controllers;

import Ai_Project.AlgorithmImplementation;
import Ai_Project.GenerateFile;
import Ai_Project.Location;
import Ai_Project.Vechile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;

import static Ai_Project.AlgorithmImplementation.size;

public class MainInterfaceController implements Initializable {
    public static ArrayList<Location> pickup = new ArrayList<>();
    public static ArrayList<Location> delivery = new ArrayList<>();
    public static Stack<Location> path = new Stack<>();
    public static int option = 0;


    public static Vechile vechile = new Vechile();


    @FXML
    private MenuButton menuButton;

    @FXML
    private Button btnRandomGenerate;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private ToggleGroup buttonGroup;

    @FXML
    private Button btnLastGeneratedFile;

//    @FXML
        //if the user want to try the same dataset using different option (forward/arcConsistency/backtracking ONLY)
//    void LastGeneratedFile(ActionEvent event) throws Exception {
//        try {
//            path.clear();
//            delivery.clear();
//            pickup.clear();
//            if (readFile("Data.txt")) {
//            }
//            AlgorithmImplementation threadA = new AlgorithmImplementation();
//            threadA.start();
//            while (threadA.isAlive()) ;
//            if (path.size() != size) {
//                Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setContentText("No Solution Available!!");
//                a.show();
//                return;
//            }
//        } catch (Exception e) {
//            if (path.size() != size) {
//                Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setContentText("No Solution Available!!");
//                a.show();
//                return;
//            }
//
//        }
//        openNewWindow(event);
//    }

    @FXML
        //this function generates random data to test the algorithm on
    void RandomGenerate(ActionEvent event) throws IOException {
        try {
            GenerateFile.randomGenerate();
        } catch (IOException e) {
            System.out.println("Unsuccessful generation of random file");
        }
        if (readFile("Data.txt")) {
        }
        try {
            AlgorithmImplementation threadA = new AlgorithmImplementation();
            threadA.start();
            while (threadA.isAlive());
            //AlgorithmImplementation.findingSolution();
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }
        } catch (Exception e) {
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }

        }

        openNewWindow(event);
    }

    @FXML
    void TestCase1(ActionEvent event) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        data.add("-3 0 -1 -2 6 -6 30 75 30 75");
        data.add("8 -7 7 -3 5 -5 42 91 42 91");
        data.add("6 -8 4 6 6 -6 54 90 54 90");
        data.add("8 -3 4 2 4 -4 47 70 47 70");
        data.add("0 6 -3 7 3 -3 70 103 70 103");
        data.add("1 8 -1 6 4 -4 100 127 100 127");
        data.add("6 4 2 0 1 -1 120 142 120 142");
        writeCaseOnFile(17, data);
        if (readFile("Data.txt")) {
        }
        try {
            AlgorithmImplementation threadA = new AlgorithmImplementation();
            threadA.start();
            while (threadA.isAlive()) ;
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }
        } catch (Exception e) {

        }

        openNewWindow(event);

    }

    @FXML
    void TestCase2(ActionEvent event) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        data.add("7 0 6 4 4 -4 26 60 26 60");
        data.add("-6 -1 1 1 5 -5 23 36 23 36");
        data.add("-9 -4 3 4 5 -5 46 78 46 78");
        data.add("3 -2 -2 3 6 -6 67 101 67 101");
        data.add("-5 2 -4 7 5 -5 73 98 73 98");
        data.add("-10 5 -10 9 5 -5 83 121 83 121");
        writeCaseOnFile(20, data);
        if (readFile("Data.txt")) {
        }

        try {
            AlgorithmImplementation threadA = new AlgorithmImplementation();
            threadA.start();
            while (threadA.isAlive()) ;
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }
        } catch (Exception e) {

        }

        openNewWindow(event);

    }

    @FXML
    void TestCase3(ActionEvent event) throws IOException {
        ArrayList<String> data = new ArrayList<>();
        data.add("1 2 2 4 2 -2 1 5 1 5");
        data.add("2 3 4 5 1 -1 3 7 3 7");
        data.add("3 4 5 5 4 -4 4 11 4 11");
        writeCaseOnFile(6, data);
        if (readFile("Data.txt")) {
        }

        try {
            AlgorithmImplementation threadA = new AlgorithmImplementation();
            threadA.start();
            while (threadA.isAlive()) ;
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }
        } catch (Exception e) {

        }

        openNewWindow(event);
    }
    @FXML
    void TestCase4(ActionEvent event) throws IOException{
        ArrayList<String> data = new ArrayList<>();
        data.add("3 -4 10 -8 1 -1 35 69 35 69");
        data.add("4 -8 10 -2 4 -4 31 60 31 60");
        data.add("8 -8 3 6 1 -1 47 88 47 88");
        data.add("-3 4 8 4 2 -2 82 123 82 123");
        data.add("3 -8 4 6 1 -1 85 144 85 144");
        data.add("6 -4 6 1 3 -3 119 135 119 135");
        writeCaseOnFile(16, data);
        if (readFile("Data.txt")) {
        }

        try {
            AlgorithmImplementation threadA = new AlgorithmImplementation();
            threadA.start();
            while (threadA.isAlive()) ;
            if (path.size() != size) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No Solution Available!!");
                a.show();
                return;
            }
        } catch (Exception e) {

        }

        openNewWindow(event);
    }

    @FXML
    void option1Action(ActionEvent event) {
        option = 0;
    }

    @FXML
    void option2Action(ActionEvent event) {
        option = 1;
    }

    @FXML
    void option3Action(ActionEvent event) {
        option = 2;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static boolean readFile(String fileName) {
        try {
            path.clear();
            delivery.clear();
            pickup.clear();
            // open the file to read from it.
            File file = new File(fileName);
            if (!(file.exists())) {// if the file does not exists.
                System.out.println("file dosenot exists");
                return false;
            } else {
                Scanner y = new Scanner(file);
                String reader;
                reader = y.nextLine(); //reading the capacity
                vechile.setCapacity(Integer.parseInt(reader.trim()));
                reader = y.nextLine(); //reading the depot
                String[] data = reader.split(" ");
                Location temp = new Location(Integer.parseInt(data[0].trim()), Integer.parseInt(data[1].trim()), 0, 0, 0, false, 0);
                pickup.add(temp);
                delivery.add(temp);
                int index = 1;
                while (y.hasNext()) {
                    reader = y.nextLine();
                    data = reader.split(" ");
                    Location temp1 = new Location(Integer.parseInt(data[0].trim()), Integer.parseInt(data[1].trim()), Integer.parseInt(data[4].trim()), Integer.parseInt(data[6].trim()), Integer.parseInt(data[7].trim()), false, index);
                    Location temp2 = new Location(Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), Integer.parseInt(data[5].trim()), Integer.parseInt(data[8].trim()), Integer.parseInt(data[9].trim()), false, index);
                    pickup.add(temp1);
                    delivery.add(temp2);
                    index++;
                }
                y.close();
            }
        } catch (IOException x) {
            System.out.println("There is something error in the file");
        }
        return true;
    }

    public void writeCaseOnFile(int capacity, ArrayList<String> data) throws IOException {
        FileWriter file = new FileWriter("Data.txt");
        file.write(capacity + "\n");
        file.write(0 + " " + 0 + "\n");
        for (int i = 0; i < data.size(); i++) {
            file.write(data.get(i) + "\n");
        }
        file.close();


    }

    public void openNewWindow(ActionEvent event) throws IOException {
        //code for chaning stage
        final Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        Parent nextSceneParent = FXMLLoader.load(getClass().getResource("../FXMLs/ResultInterface.fxml"));
        Scene scene11 = new Scene(nextSceneParent);
        stage.setScene(scene11);
        stage.show();
    }
}
