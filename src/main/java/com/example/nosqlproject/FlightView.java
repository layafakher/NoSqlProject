package com.example.nosqlproject;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class FlightView {

    private SimpleStringProperty id;
    private SimpleStringProperty origin;
    private SimpleStringProperty destination;
    private SimpleStringProperty type;
    private SimpleStringProperty startTime;
    private SimpleStringProperty finishTime;
    private SimpleStringProperty capacity;
    private SimpleStringProperty price;
    private SimpleStringProperty airlineCompany;
    private SimpleStringProperty stops;

    public FlightView(Flight flight) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        id = new SimpleStringProperty(String.valueOf(flight.getFlightId()));
        origin = new SimpleStringProperty(flight.getOrigin() + "(" + flight.getOriginCity() + "," + flight.getOriginCountry() + ")");
        destination = new SimpleStringProperty(flight.getDestination() + "(" + flight.getDestinationCity() + "," + flight.getDestinationCountry() + ")");
        type = new SimpleStringProperty(flight.getClassType());
        startTime = new SimpleStringProperty(formatter.format(flight.getStartTime()));
        finishTime = new SimpleStringProperty(formatter.format(flight.getFinishTime()));
        capacity = new SimpleStringProperty(String.valueOf(flight.getCapacity()));
        price = new SimpleStringProperty(String.valueOf(flight.getPrice()));
        airlineCompany = new SimpleStringProperty(String.join(",",flight.getAirlineCompany()));
        stops = new SimpleStringProperty(String.join(",",flight.getStops()));
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getOrigin() {
        return origin.get();
    }

    public SimpleStringProperty originProperty() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin.set(origin);
    }

    public String getDestination() {
        return destination.get();
    }

    public SimpleStringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public SimpleStringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getFinishTime() {
        return finishTime.get();
    }

    public SimpleStringProperty finishTimeProperty() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime.set(finishTime);
    }

    public String getCapacity() {
        return capacity.get();
    }

    public SimpleStringProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity.set(capacity);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getAirlineCompany() {
        return airlineCompany.get();
    }

    public SimpleStringProperty airlineCompanyProperty() {
        return airlineCompany;
    }

    public void setAirlineCompany(String airlineCompany) {
        this.airlineCompany.set(airlineCompany);
    }

    public String getStops() {
        return stops.get();
    }

    public SimpleStringProperty stopsProperty() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops.set(stops);
    }
}
