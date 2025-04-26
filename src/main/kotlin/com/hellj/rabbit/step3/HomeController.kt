package com.hellj.rabbit.step3

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
}