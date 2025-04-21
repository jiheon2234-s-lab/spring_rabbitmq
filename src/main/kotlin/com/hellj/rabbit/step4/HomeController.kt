package com.hellj.rabbit.step4

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/home")
    fun home(model: Model): String {
        model.addAttribute("message", "Welcome to RabbitMQ Sample!!")
        return "home"
    }

    @GetMapping("/news")
    fun news(model: Model): String {
        return "news"
    }
}