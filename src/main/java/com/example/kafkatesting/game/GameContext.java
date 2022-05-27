package com.example.kafkatesting.game;

import com.example.kafkatesting.ui.Overlay;
import com.example.kafkatesting.ui.Window;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class GameContext {

    private Overlay overlay;


    public GameContext() {

    }

    @EventListener(ApplicationReadyEvent.class)
    private void start(){
        this.overlay = new Overlay(Window.get("League of Legends (TM) Client"));
        overlay.display();
        Runtime.getRuntime().addShutdownHook(new Thread(overlay::close));

    }
}
