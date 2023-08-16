package com.example.springboot01;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String hello(){
        return "hello";
    }
    @RequestMapping("/hello")
    public String hello2(){
        return "이 서비스 정말 좋아요 저는 또 이용하고 싶어요 별점 5개만점중에 500개 드릴게요";
    }

}
