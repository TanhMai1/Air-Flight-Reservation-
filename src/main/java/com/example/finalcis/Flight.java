package com.example.finalcis;

import javafx.beans.property.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    private final IntegerProperty flightId = new SimpleIntegerProperty();
    private final StringProperty fromCity = new SimpleStringProperty();
    private final StringProperty toCity = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> departureDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> departureTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> arrivalDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> arrivalTime = new SimpleObjectProperty<>();
    private final IntegerProperty capacity = new SimpleIntegerProperty();

    public Flight(int flightId, String fromCity, String toCity, LocalDate departureDate,
                  LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, int capacity) {
        this.flightId.set(flightId);
        this.fromCity.set(fromCity);
        this.toCity.set(toCity);
        this.departureDate.set(departureDate);
        this.departureTime.set(departureTime);
        this.arrivalDate.set(arrivalDate);
        this.arrivalTime.set(arrivalTime);
        this.capacity.set(capacity);
    }


    // Getters and setters for JavaFX properties
    public int getFlightId() { return flightId.get(); }
    public IntegerProperty flightIdProperty() { return flightId; }


    public StringProperty fromCityProperty() { return fromCity; }


    public StringProperty toCityProperty() { return toCity; }
    public void setToCity(String toCity) { this.toCity.set(toCity); }

    public ObjectProperty<LocalDate> departureDateProperty() { return departureDate; }


    public ObjectProperty<LocalTime> departureTimeProperty() { return departureTime; }


    public ObjectProperty<LocalDate> arrivalDateProperty() { return arrivalDate; }


    public ObjectProperty<LocalTime> arrivalTimeProperty() { return arrivalTime; }


    public IntegerProperty capacityProperty() { return capacity; }

}
