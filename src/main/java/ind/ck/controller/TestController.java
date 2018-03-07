package ind.ck.controller;

import ind.ck.entity.User;
import ind.ck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author BJQXDN0626
 * @create 2017/12/11
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        System.out.println("dd:" + userService.findAll());
        return "index";
    }

    @RequestMapping("/ping")
    @ResponseBody
    public String ping() {
        userService.aclTry();
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        User user = new User();
        user.setName("wwccss");
        userService.create(user);
        return "login";
    }
}
