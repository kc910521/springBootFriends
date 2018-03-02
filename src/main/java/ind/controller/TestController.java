package ind.controller;

import ind.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author BJQXDN0626
 * @create 2017/12/11
 */
@RestController
public class TestController {


    @Autowired
    private TestService testService;

    @RequestMapping("/")
    public String index() {

        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/ping")
    public String pinges() {

//        templet
//        testService.testES(UUID.randomUUID().toString());
//        jpa
        testService.testESJPA(UUID.randomUUID() + "_JPA");
        return "Greetings from Spring Boot!";
    }
}
