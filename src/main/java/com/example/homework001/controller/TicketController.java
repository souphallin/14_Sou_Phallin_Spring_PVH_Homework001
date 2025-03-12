package com.example.homework001.controller;

import com.example.homework001.ApiResponse;
import com.example.homework001.Ticket;
import com.example.homework001.request.TicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    List<Ticket> tickets = new ArrayList<>();

    public TicketController() {
        tickets.add(new Ticket(1, "WoEhEh", "12-03-2025", "Station A", "Station B", 15, true, "Booked", 3));
        tickets.add(new Ticket(2, "Sokha", "13-03-2025", "Station A", "Station C", 18, false, "Cancelled", 8));
        tickets.add(new Ticket(3, "CoCa", "14-03-2025", "Station A", "Station D", 15, true, "Booked", 6));
    }

//    Get All Tickets
    @GetMapping("/GetAllTickets")
    List<Ticket> getAllTickets() {
        return tickets;
    }

//    Create a New Ticket
    @PostMapping("/CreateANewTicket")
    public ResponseEntity<ApiResponse<Ticket>> createNewTicket(@RequestBody TicketRequest ticketRequest) {
        int newId = tickets.size() + 1;
        Ticket newTicket = new Ticket(newId, ticketRequest.getPassengerName(), ticketRequest.getTravelDate(), ticketRequest.getSourceStation(), ticketRequest.getDestinationStation(), ticketRequest.getPrice(), true, ticketRequest.getTicketStatus(), ticketRequest.getSeatNumber());
        tickets.add(newTicket);
        
        ApiResponse<Ticket> apiResponse = new ApiResponse<>
                (true, "Create Ticket Successfully", HttpStatus.OK, newTicket, LocalDateTime.now());
        
        return ResponseEntity.ok(apiResponse);
    }

//    Delete ticket by ID
    @DeleteMapping("/{deleteId}")
    public ResponseEntity<ApiResponse<Ticket>> deleteTicket(@PathVariable int deleteId) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == deleteId) {
                tickets.remove(ticket);

                ApiResponse<Ticket> apiResponse = new ApiResponse<>
                        (true, "Deleted Successfully", HttpStatus.OK, ticket, LocalDateTime.now());

                return ResponseEntity.ok(apiResponse);
//                return "Ticket deleted";
            }
        }
        return null;
    }

//    Search by Name
    @GetMapping("/search")
    public List<Ticket> searchTicket(@RequestParam String passengerName) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getPassengerName().toLowerCase().contains(passengerName.toLowerCase())) {
                ticketList.add(ticket);
            }
        }
        return ticketList;
    }

//    Update an existing tickets by ID
    @PutMapping("{updateId}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(@PathVariable int updateId, @RequestBody TicketRequest ticketRequest) {

        for (Ticket ticket1 : tickets) {
            if (ticket1.getId() == updateId) {
                ticket1.setPassengerName(ticketRequest.getPassengerName());
                ticket1.setTravelDate(ticketRequest.getTravelDate());
                ticket1.setSourceStation(ticketRequest.getSourceStation());
                ticket1.setDestinationStation(ticketRequest.getDestinationStation());
                ticket1.setPrice(ticketRequest.getPrice());
                ticket1.setTicketStatus(ticketRequest.getTicketStatus());
                ticket1.setSeatNumber(ticketRequest.getSeatNumber());

                ApiResponse<Ticket> apiResponse = new ApiResponse<>
                        (true, "Update Successfully", HttpStatus.OK, ticket1, LocalDateTime.now());

                return ResponseEntity.ok(apiResponse);
            }
        }
        return null;
    }

}
