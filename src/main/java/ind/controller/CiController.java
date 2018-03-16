package ind.controller;

import ind.domains.Ci;
import ind.service.CiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/14
 */
@RestController
@RequestMapping("/ci")
public class CiController {

    @Autowired
    private CiService ciService;

    @RequestMapping(value = "")
    public List<Ci> listCi(@RequestParam @NotBlank String str){
        return ciService.findByParas(str);
    }

    @RequestMapping(value = "/mis/count")
    public String countMissingCi(){
        return ciService.verifyCi();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveCi(){
        try {
            int res = ciService.loadFromFiles("C:\\Users\\BJQXDN0626\\Downloads\\chinese-poetry-master\\chinese-poetry-master\\ci\\cix");
            return res + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "wtf?";
    }
}
