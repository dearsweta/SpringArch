package com.spring.internal_working.internal_work.controller;

import com.spring.internal_working.internal_work.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @RequestMapping("/wtf")
    public Message sayHello() {
        return new Message("Hello wtf!" );
    }
}
