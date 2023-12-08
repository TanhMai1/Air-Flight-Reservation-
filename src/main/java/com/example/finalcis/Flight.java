package com.example.finalcis;

import javafx.beans.property.*;

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
    public void setFlightId(int flightId) { this.flightId.set(flightId); }

    public String getFromCity() { return fromCity.get(); }
    public StringProperty fromCityProperty() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity.set(fromCity); }

    public String getToCity() { return toCity.get(); }
    public StringProperty toCityProperty() { return toCity; }
    public void setToCity(String toCity) { this.toCity.set(toCity); }

    public LocalDate getDepartureDate() { return departureDate.get(); }
    public ObjectProperty<LocalDate> departureDateProperty() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) { this.departureDate.set(departureDate); }

    public LocalTime getDepartureTime() { return departureTime.get(); }
    public ObjectProperty<LocalTime> departureTimeProperty() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime.set(departureTime); }

    public LocalDate getArrivalDate() { return arrivalDate.get(); }
    public ObjectProperty<LocalDate> arrivalDateProperty() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) { this.arrivalDate.set(arrivalDate); }

    public LocalTime getArrivalTime() { return arrivalTime.get(); }
    public ObjectProperty<LocalTime> arrivalTimeProperty() { return arrivalTime; }
    public void setArrivalTime(LocalTime arrivalTime) { this.arrivalTime.set(arrivalTime); }

    public int getCapacity() { return capacity.get(); }
    public IntegerProperty capacityProperty() { return capacity; }
    public void setCapacity(int capacity) { this.capacity.set(capacity); }
}
