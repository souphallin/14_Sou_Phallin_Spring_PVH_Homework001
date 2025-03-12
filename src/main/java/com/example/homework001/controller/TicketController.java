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
        tickets.add(new Ticket(2, "Sokha", "13-03-2025", "Station C", "Station D", 18, false, "Cancelled", 8));
        tickets.add(new Ticket(3, "CoCa", "14-03-2025", "Station D", "Station C", 10, true, "Completed", 6));
        tickets.add(new Ticket(4, "Monorom", "12-03-2025", "Station G", "Station A", 16, true, "Booked", 6));
        tickets.add(new Ticket(5, "Heng", "15-03-2025", "Station F", "Station D", 9, true, "Completed", 6));
        tickets.add(new Ticket(6, "Lin", "14-03-2025", "Station B", "Station C", 5, true, "Booked", 6));
        tickets.add(new Ticket(7, "Cheng", "17-03-2025", "Station A", "Station G", 3, true, "Cancelled", 6));
        tickets.add(new Ticket(8, "Sey", "17-03-2025", "Station A", "Station G", 3, true, "Booked", 6));
    }
//--------------------------------------------------------------------------------------------------
//    Get All Tickets
    @GetMapping("/GetAllTickets")
    List<Ticket> getAllTickets() {
        return tickets;
    }
//--------------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------------
//    Get Ticket By ID
    @GetMapping("/{getByID}")
    public ResponseEntity<Ticket> getByID(@PathVariable Integer getByID) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == getByID) {
                return ResponseEntity.ok(ticket);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        return null;
    }
//--------------------------------------------------------------------------------------------------
//    Filter By Ticket Status And Travel-Date
    @GetMapping("/filterTicketStatusAndByTravelDate")
    public ResponseEntity<List<Ticket>> filterByTravelDate(@RequestParam String filterByStatus, @RequestParam String filterByTravelDate) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getTicketStatus().equalsIgnoreCase(filterByStatus) && ticket.getTravelDate().toLowerCase().equals(filterByTravelDate.toLowerCase())) {
                ticketList.add(ticket);
            }
        }
        if (ticketList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ticketList);
    }
//--------------------------------------------------------------------------------------------------
//    Create One New Ticket
    @PostMapping("/CreateANewTicket")
    public ResponseEntity<ApiResponse<Ticket>> createNewTicket(@RequestBody TicketRequest ticketRequest) {
        int newId = tickets.size() + 1;
        Ticket newTicket = new Ticket
                (newId, ticketRequest.getPassengerName(), ticketRequest.getTravelDate(),
                        ticketRequest.getSourceStation(), ticketRequest.getDestinationStation(),
                        ticketRequest.getPrice(), true, ticketRequest.getTicketStatus(),
                        ticketRequest.getSeatNumber());
        tickets.add(newTicket);

        ApiResponse<Ticket> apiResponse = new ApiResponse<>
                (true, "Create Ticket Successfully", HttpStatus.OK, newTicket, LocalDateTime.now());
        
        return ResponseEntity.ok(apiResponse);
    }
//--------------------------------------------------------------------------------------------------
    //    Create Multiple New Ticket
    @PostMapping("/CreateMultiNewTicket")
    public ResponseEntity<ApiResponse<List<Ticket>>> createMultiNewTicket(@RequestBody List<TicketRequest> ticketRequest) {
        int newId = tickets.size() + 1;
        List<Ticket> newTickets = new ArrayList<>();

        for (TicketRequest ticket : ticketRequest) {
            Ticket newTicket = new Ticket
                    (newId++, ticket.getPassengerName(), ticket.getTravelDate(),
                            ticket.getSourceStation(), ticket.getDestinationStation(),
                            ticket.getPrice(), true, ticket.getTicketStatus(),
                            ticket.getSeatNumber()
                    );
            tickets.add(newTicket);
            newTickets.add(newTicket);
        }
        if (newTickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    false, "No Ticket were Created", HttpStatus.BAD_REQUEST, null, LocalDateTime.now()
            ));
        }
        ApiResponse<List<Ticket>> apiResponse = new ApiResponse<>(true, "Create New Ticket Successfully", HttpStatus.OK, newTickets, LocalDateTime.now());
        return ResponseEntity.ok(apiResponse);
    }
//--------------------------------------------------------------------------------------------------
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
        ApiResponse<Ticket> deleteNotFound = new ApiResponse<>
                (false, "ID Not Found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deleteNotFound);
    }
//--------------------------------------------------------------------------------------------------
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
        ApiResponse<Ticket> errorResponse = new ApiResponse<>
                (false, "Ticket ID Not Found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
//--------------------------------------------------------------------------------------------------
//    Update ID and Payment Status
    @PutMapping("/{updateByID}/{updatePaymentStatus}")
    public ResponseEntity<ApiResponse<Ticket>> updateIDAndPaymentStatus(@PathVariable int updateByID, @PathVariable boolean updatePaymentStatus) {
        for (Ticket ticket2 : tickets) {
            if (ticket2.getId() == updateByID){
                ticket2.setPaymentStatus(updatePaymentStatus);

                ApiResponse<Ticket> updateResponse = new ApiResponse<>
                        (true, "Update Successfully", HttpStatus.OK, ticket2, LocalDateTime.now());
                return ResponseEntity.ok(updateResponse);
            }
        }
        ApiResponse<Ticket> errorResponse = new ApiResponse<>
                (false, "Ticket ID Not Found", HttpStatus.NOT_FOUND, null, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
