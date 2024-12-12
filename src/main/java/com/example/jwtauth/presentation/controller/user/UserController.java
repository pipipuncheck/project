package com.example.jwtauth.presentation.controller.user;

import com.example.jwtauth.application.user.service.UserService;
import com.example.jwtauth.presentation.controller.admin.dto.query.UserQuery;
import com.example.jwtauth.presentation.controller.manager.dto.ArtistQuery;
import com.example.jwtauth.presentation.controller.manager.dto.EventQuery;
import com.example.jwtauth.presentation.controller.user.dto.command.ReviewCommand;
import com.example.jwtauth.presentation.controller.user.dto.query.TicketQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserQuery me(@AuthenticationPrincipal UserDetails userDetails){
        return userService.checkMyInfo(userDetails);
    }

    @GetMapping("/tickets")
    public List<TicketQuery> myTickets(@AuthenticationPrincipal UserDetails userDetails){
        return userService.checkMyTickets(userDetails);
    }
    @GetMapping("/expired_tickets")
    public List<TicketQuery> expiredTickets(@AuthenticationPrincipal UserDetails userDetails){
        return userService.checkMyExpiredTickets(userDetails);
    }
    @GetMapping("/artist_info")
    public ArtistQuery artistInfo(@RequestParam("artistId") int artistId){
        return userService.checkArtistInfo(artistId);
    }

    @GetMapping("/schedule/{date}")
    public List<EventQuery> checkAllEventsByDate(@PathVariable("date")LocalDate date){
        return userService.checkAllEventsByDate(date);
    }

    @GetMapping("/schedule/check/{event_name}/buy_ticket")
    public String buyTicket(@AuthenticationPrincipal UserDetails userDetails,
                            @PathVariable("event_name") String name){
        userService.buyTicket(userDetails, name);
        return "билет успешно куплен";
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping("/schedule/check/{event_name}")
    public EventQuery checkEvent(@PathVariable("event_name") String name){
        return userService.checkEventInfo(name);
    }
    @PostMapping("/review")
    public void createReview(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam("artistId") int artistId,
                             @RequestBody ReviewCommand command){
        userService.addReview(userDetails, artistId, command);
    }

}
