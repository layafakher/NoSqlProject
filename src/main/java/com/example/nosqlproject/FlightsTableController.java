package com.example.nosqlproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;

public class FlightsTableController {

    @FXML
    private TableView<FlightView> flightsTable;
    @FXML
    private TableColumn<FlightView,String> idColumn;
    @FXML
    private TableColumn<FlightView,String> originColumn;
    @FXML
    private TableColumn<FlightView,String> destinationColumn;
    @FXML
    private TableColumn<FlightView,String> typeColumn;
    @FXML
    private TableColumn<FlightView,String> startTimeColumn;
    @FXML
    private TableColumn<FlightView,String> finishTimeColumn;
    @FXML
    private TableColumn<FlightView,String> capacityColumn;
    @FXML
    private TableColumn<FlightView,String> priceColumn;
    @FXML
    private TableColumn<FlightView,String> airlinesColumn;
    @FXML
    private TableColumn<FlightView,String> stopsColumn;

    private List<Flight> flightList;

    private void setTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        finishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        airlinesColumn.setCellValueFactory(new PropertyValueFactory<>("airlineCompany"));
        stopsColumn.setCellValueFactory(new PropertyValueFactory<>("stops"));
    }

    private void setDate(){
        List<FlightView> flightViews = new LinkedList<>();
        for (Flight flight : flightList) {
            FlightView flightView = new FlightView(flight);
            flightViews.add(flightView);
        }
        ObservableList<FlightView> observableList = FXCollections.observableList(flightViews);
        flightsTable.setItems(observableList);
    }

    public void returnMainMenuClick(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Info.getInstance().getStage().setScene(scene);
        }catch (Exception ignored){}
    }

    public void setInitials(List<Flight> flights){
        flightList = flights;
        setTable();
        setDate();
    }
}
