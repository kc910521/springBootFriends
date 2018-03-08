package ind.ck.controller;

import ind.ck.entity.User;
import ind.ck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BJQXDN0626
 * @create 2017/12/11
 */
@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/acl/{uid}")
    @ResponseBody
    public User ping(@PathVariable int uid) {
//        userService.aclTry("ursa");
        userService.findAll();
        return userService.findUserById(uid);
    }

    @RequestMapping("/acl/user/all")
    @ResponseBody
    public Map<String, Object> allUsers() {
        //        userService.aclTry("ursa");
        Map<String,Object> rMap = new HashMap<>();
        rMap.put("total", userService.findAll());
        rMap.put("mine", userService.findAllInLaw());
        return rMap;
    }

    @RequestMapping("/acl/{ustr}/s")
    @ResponseBody
    public User acl(@PathVariable String ustr) {
        userService.aclSave(ustr);
        return null;
    }

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
