package com.example.homework001.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequest {
    private String passengerName;
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private boolean paymentStatus;
    private String ticketStatus;
    private int seatNumber;

}
