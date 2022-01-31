package com.example.nosqlproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class MainMenuController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField airlineInput;
    @FXML
    private TextField capacityInput;
    @FXML
    private TextField flightIdInput;
    @FXML
    private TextField originInput;
    @FXML
    private TextField destinationInput;

    public void onShowDataButtonClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ShowDataMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1026, 563);
        Info.getInstance().getStage().setScene(scene);
    }

    public void onCrawlDataButtonClick(){
        try{
            int insertCount = 0;
            FlyScrapper flyScrapper = new FlyScrapper(Info.getFlyURL());
            Converter converter = new Converter();
            List<SMFFlight> smfFlights = flyScrapper.getResult();
            List<Flight> flights = converter.FlyConverter(smfFlights);
            for (Flight flight : flights) {
                try{
                    Info.getInstance().getClient().insertFlight(flight);
                    insertCount++;
                } catch (Exception ignored){}
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION,insertCount+" flights added successfully", ButtonType.OK);
            alert.show();
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
            alert.show();
        }
    }

    public void deleteClick(MouseEvent event){
        if(datePicker.getValue() != null && !airlineInput.getText().isEmpty()){
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Info.getInstance().getClient().deleteByDateName(date,airlineInput.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Flights deleted successfully", ButtonType.OK);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void oneFlightCapacityClick(MouseEvent event){
        if(!capacityInput.getText().isEmpty() && !flightIdInput.getText().isEmpty()){
            Info.getInstance().getClient().changeCapacityById(Integer.parseInt(flightIdInput.getText()),Integer.parseInt(capacityInput.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Capacity changed successfully", ButtonType.OK);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }

    public void allFlightsCapacityClick(MouseEvent event){
        if(!capacityInput.getText().isEmpty() && !airlineInput.getText().isEmpty() && !originInput.getText().isEmpty() && !destinationInput.getText().isEmpty() && datePicker.getValue() != null){
            Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Info.getInstance().getClient().changeCapacityByDateOriginDest(airlineInput.getText(),Integer.parseInt(capacityInput.getText()),date,originInput.getText(),destinationInput.getText()    );
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Capacity changed successfully", ButtonType.OK);
            alert.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please fill the required fields", ButtonType.OK);
            alert.show();
        }
    }
}
