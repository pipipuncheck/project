package com.example.jwtauth.presentation.controller.manager;

import com.example.jwtauth.application.manager.service.ManagerService;
import com.example.jwtauth.presentation.controller.manager.dto.ArtistCommand;
import com.example.jwtauth.presentation.controller.manager.dto.EventCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @GetMapping
    public String managerPage(){
        return "Welcome to manager page";
    }

    @PostMapping("/new_event")
    public String createEvent(@RequestBody EventCommand eventCommand){
        managerService.createEvent(eventCommand);
        return "мероприятие успешно добавлено";
    }

    @PostMapping("/new_artist")
    public String createArtist(@RequestBody ArtistCommand artistCommand){
        managerService.createArtist(artistCommand);
        return "артист успешно добавлен";
    }

    @GetMapping("/artists")
    public List<String> showAllArtists(){
        return managerService.showAllArtists();
    }
}



