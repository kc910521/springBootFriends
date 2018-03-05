package ind.controller;

import ind.domains.Hero;
import ind.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public List<Hero> pinges() {

        return testService.listHeros();
    }

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    public String heroPost() {

        //        templet
        //        testService.testES(UUID.randomUUID().toString());
        //        jpa
        testService.testESJPA(UUID.randomUUID() + "_JPA");
        return "Greetings from Spring Boot!";
    }
}
