package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // localhost:8080/ 로 들어오면 가장 기본적으로 호출될 부분
    @GetMapping("/")
    public String home() {
        // resources/templates/home.html을 호출
        return "home";
    }
}
