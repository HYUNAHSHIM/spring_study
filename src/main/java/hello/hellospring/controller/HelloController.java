package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
        // resources/templates/hello.html을 화면에 띄워라
        // 컨트롤러에서 리턴 값으로 문자를 반환하면 viewResolver가 화면을 찾아 처리
        // 스프링 부트 템플릿엔진 기본 viewName 매핑
        // resources:templates/{viewName}.html
    }

    // mvc 방식
    @GetMapping("hello-mvc") // api url이 localhost:8080/hello-mvc?name=spring 이 됨
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
        // 뷰 리졸버가 hello-template이란 이름의 html파일을 rsources/templates 밑에서 찾아서 처리! 정적 컨텐츠와 다르게 html로 변환을 해서 봔환!

    }

    // api 방식
    // @ResponseBody가 있으면 HttpMessageConverter가 동작해서
    // 객체면 JsonConverter(MappingJackson2HttpMessageConverter), 문자열이면 StringConverter(StringHttpMessageConverter)가 동작
    // 참고) byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음

    // 문자열로 보내기
    @GetMapping("hello-string")
    @ResponseBody // 이 방식은 뷰 리졸버를 쓰지 않음대신 return 할 때 http의 body에 담아서 전송
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // 이 문자가 그대로 http body에 담겨져 내려감
    }


    // 객체로 보내기
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // {"name": "들어온 인지값"} 처럼 json 형태로 반환
    }

    // getter setter를 가진 클래스 생성하기
    // 변수는 private으로 접근할 수 있는 getter setter는 public으로 해서 getter setter 통해서 접근함
    // property 접근 방식이라고도 함
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
