package com.reservation;

public class Reservation {
    private String pnr;
    private String passengerName;
    private int trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String source;
    private String destination;

    public Reservation() {}

    public Reservation(String pnr, String passengerName, int trainNumber, String trainName,
                       String classType, String journeyDate, String source, String destination) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.source = source;
        this.destination = destination;
    }

    // Getters and setters (omitted for brevity – generate them in your IDE)
    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public int getTrainNumber() { return trainNumber; }
    public void setTrainNumber(int trainNumber) { this.trainNumber = trainNumber; }
    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public String getJourneyDate() { return journeyDate; }
    public void setJourneyDate(String journeyDate) { this.journeyDate = journeyDate; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}