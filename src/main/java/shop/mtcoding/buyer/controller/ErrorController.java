package shop.mtcoding.buyer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/notfound")
    public String error() {
        System.out.println("테스트 : error 메서드 호출됨");
        return "error/error";
    }
}
