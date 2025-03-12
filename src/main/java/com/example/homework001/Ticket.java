package com.example.homework001;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Ticket {
    private int id;
    private String passengerName;
//    private LocalDate travelDate = LocalDate.now();
    private String travelDate;
    private String sourceStation;
    private String destinationStation;
    private double price;
    private boolean paymentStatus;
    private String ticketStatus;
    private int seatNumber;
}
