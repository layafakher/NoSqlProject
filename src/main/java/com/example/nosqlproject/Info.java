package com.example.nosqlproject;

import javafx.stage.Stage;

public class Info {

    private static Info instance;
    private final static String flyURL = "https://sacramento.aero/smf/flight-and-travel/flight-status";
    private CassandraConnector client;
    private Stage stage;

    private Info(){

    }

    public static Info getInstance(){
        if(instance == null){
            instance = new Info();
        }
        return instance;
    }

    public CassandraConnector getClient() {
        return client;
    }

    public void setClient(CassandraConnector client) {
        this.client = client;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static String getFlyURL() {
        return flyURL;
    }
}
