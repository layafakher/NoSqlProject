package com.example.nosqlproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FlightsTable.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1026, 563);
        List<Flight> flights = Info.getInstance().getClient().getFlightsInPriceRange(100,600,null,null);;
        FlightsTableController flightsTableController = fxmlLoader.getController();
        flightsTableController.setInitials(flights);
        stage.setTitle("NoSQL Project");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Info.getInstance().getClient().close();
        });
        Info.getInstance().setStage(stage);
    }

    public static void main(String[] args) {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 9042);
        Info.getInstance().setClient(client);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            List<noSQL.Flight> flights = client.getFlightsBySpecificDate(formatter.parse("2022-05-03"),"economy",new noSQL.OrderBy("DESC","price"));
//            for (noSQL.Flight flight : flights) {
//                System.out.println(flight.toString());
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        client.getMinMaxPrice("Imam airport","Istanbul Airport",null);
        System.out.println(client.getCheapestFlight("Imam airport","Istanbul Airport",100,500,null,null,"economy"));
//        List<noSQL.Flight> flights = client.getFlightsInPriceRange(100,600);

//        Session session = client.getSession();
//        String query = "INSERT INTO FLIGHT (flightid,airlinecompany,capacity,classType,destination,destinationCity,destinationCountry,finishtime,flightduration,origin,origincity,origincountry,price,starttime,stops) values (1,['iran air'],250,'economy','Istanbul Airport','Istanbul','Turkey','2022-05-03 12:20:00',4,'Imam airport','Tehran','Iran',200,'2022-05-03 16:20:00',[]); ";
//        session.execute(query);
        launch();
    }
}
