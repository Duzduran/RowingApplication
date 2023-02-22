package com.myApp.web.controller;

import com.myApp.web.Security.SecurityUtil;
import com.myApp.web.dto.EventDto;
import com.myApp.web.models.Event;
import com.myApp.web.models.UserEntity;
import com.myApp.web.service.EventService;
import com.myApp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {

    private EventService eventService;
    private UserService userService;
    @Autowired
    public EventController(EventService eventService, UserService userService) {

        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model){
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }
    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model){
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    @GetMapping("/events")
    public String eventList(Model model){
        UserEntity user = new UserEntity();
        List<EventDto> events= eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events",events);
        return "events-list";

    }

    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model){
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findByEventId(eventId);
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByEmail(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @ModelAttribute("event")EventDto eventDto,
                                Model model) {
        eventService.createEvent(clubId,eventDto);
        return "redirect:/clubs/" + clubId;
    }
}
