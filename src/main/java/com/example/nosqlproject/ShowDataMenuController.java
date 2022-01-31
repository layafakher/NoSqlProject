package com.example.nosqlproject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ShowDataMenuController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField minPriceInput;
    @FXML
    private TextField maxPriceInput;
    @FXML
    private TextField originInput;
    @FXML
    private TextField destinationInput;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private ChoiceBox<String> sortChoiceBox;
    @FXML
    private DatePicker firstDatePicker;
    @FXML
    private DatePicker secondDatePicker;
    @FXML
    private TextField capacityInput;
    @FXML
    private TextField limitInput;

    public void getAllFlightsClick(){
        List<Flight> flights = Info.getInstance().getClient().getAllFlights();
        showTable(flights);
    }

    public void query1Click() {
        if(datePicker.getValue() != null){
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            List<Flight> flights = Info.getInstance().getClient().getFlightsBySpecificDate(date,getType(),getOrderBy(),getLimit());
            showTable(flights);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query2Click() {
        if(!minPriceInput.getText().isEmpty() && !maxPriceInput.getText().isEmpty()){
            List<Flight> flights = Info.getInstance().getClient().getFlightsInPriceRange(Double.parseDouble(minPriceInput.getText()),Double.parseDouble(maxPriceInput.getText()),getType(),getOrderBy(),getLimit());
            showTable(flights);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query3Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty()){
            String result = Info.getInstance().getClient().getMinMaxPrice(originInput.getText(),destinationInput.getText(),getType());
            showTextArea(result);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query4Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty()){
            String result = Info.getInstance().getClient().getAvgSumPrice(originInput.getText(),destinationInput.getText(),getType());
            showTextArea(result);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query6Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty() && !minPriceInput.getText().isEmpty() && !maxPriceInput.getText().isEmpty()){
            List<Flight> flights = Info.getInstance().getClient().getCheapestFlight(originInput.getText(),destinationInput.getText(),Double.parseDouble(minPriceInput.getText()),Double.parseDouble(maxPriceInput.getText()),getFirstDate(),getSecondDate(),getType(),getOrderBy(),getLimit());
            showTable(flights);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query7Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty() && !capacityInput.getText().isEmpty()){
            List<Flight> flights = Info.getInstance().getClient().getFlightsByOriginDestCap(originInput.getText(),destinationInput.getText(),Integer.parseInt(capacityInput.getText()),getFirstDate(),getSecondDate(),getType(),getOrderBy(),getLimit());
            showTable(flights);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query9Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty() && datePicker.getValue() != null){
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            List<String> airlines = Info.getInstance().getClient().getAirlineCompany(date,originInput.getText(),destinationInput.getText(),getOrderBy(),getLimit());
            StringBuilder result = new StringBuilder();
            for (String airline : airlines) {
                result.append(airline);
            }
            showTextArea(result.toString());
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void query13Click() {
        if(!originInput.getText().isEmpty() && !destinationInput.getText().isEmpty() && datePicker.getValue() != null){
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            String result = Info.getInstance().getClient().airportsName(date,originInput.getText(),destinationInput.getText());
            showTextArea(result);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void returnMainMenuClick() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Info.getInstance().getStage().setScene(scene);
        }catch (Exception ignored){}
    }

    private void showTextArea(String text){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SimpleTextArea.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            SimpleTextAreaController simpleTextAreaController = fxmlLoader.getController();
            simpleTextAreaController.init(text);
            Info.getInstance().getStage().setScene(scene);
        } catch (Exception ignored){}
    }

    private void showTable(List<Flight> flights){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FlightsTable.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1026, 563);
            FlightsTableController flightsTableController = fxmlLoader.getController();
            flightsTableController.setInitials(flights);
            Info.getInstance().getStage().setScene(scene);
        } catch (Exception ignored){}
    }

    private int getLimit(){
        try{
            if(!limitInput.getText().isEmpty() && Integer.parseInt(limitInput.getText()) > 0){
                return Integer.parseInt(limitInput.getText());
            }
        } catch (Exception e){
            return -1;
        }
        return -1;
    }

    private Date getFirstDate(){
        if(firstDatePicker.getValue() != null){
            return Date.from(firstDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        else{
            return null;
        }
    }

    private Date getSecondDate(){
        if(secondDatePicker.getValue() != null){
            return Date.from(secondDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        else{
            return null;
        }
    }

    private String getType(){
        switch (typeChoiceBox.getValue()) {
            case "Economy":
                return "economy";
            case "Business":
                return "business";
            default:
                return null;
        }
    }

    private OrderBy getOrderBy(){
        switch (sortChoiceBox.getValue()) {
            case "قیمت-صعودی":
                return new OrderBy("ASC", "Price");
            case "قیمت-نزولی":
                return new OrderBy("DESC", "Price");
            case "زمان-نزولی":
                return new OrderBy("DESC", "Date");
            case "زمان-صعودی":
                return new OrderBy("ASC", "Date");
            default:
                return null;
        }
    }

    @FXML
    private void initialize(){
        typeChoiceBox.setItems(FXCollections.observableList(Arrays.asList("economy","business","all")));
        typeChoiceBox.setValue("all");
        sortChoiceBox.setItems(FXCollections.observableList(Arrays.asList("قیمت-صعودی","قیمت-نزولی","زمان-صعودی","زمان-نزولی","همه حالات")));
        sortChoiceBox.setValue("همه حالات");
    }
}
