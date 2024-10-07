package com.geosemantica.articleservice.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.geosemantica.articleservice.brokerfacades.model.events.UserDeactivatedEventFacade;

@RequiredArgsConstructor
@Service
public class UserEventService {
    private CommentService commentService;

    @EventListener
    public void handleUserDeactivated(UserDeactivatedEventFacade event) {
        commentService.removeAllComments(event::userId);
    }
}
