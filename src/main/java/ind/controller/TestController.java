package ind.controller;

import ind.domain.LombokTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author BJQXDN0626
 * @create 2017/12/11
 */
@RestController
public class TestController {

    @RequestMapping("/")
    public String index() {

        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/lombok")
    public String lombokIndex() {
        LombokTest lombokTest = new LombokTest(1, "cc");

        return "Greetings from Spring Boot with" + lombokTest.getName();
    }
}
