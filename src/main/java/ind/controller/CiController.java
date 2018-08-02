package ind.controller;

import ind.domains.Ci;
import ind.service.CiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String countMissingCi(@RequestParam Integer page, @RequestParam Integer pageSize){
        return ciService.verifyCi(null, page, pageSize);
    }

    @PutMapping(value = "/mis/fix")
    public String fixInEs(@RequestParam Integer page, @RequestParam Integer pageSize){
        return ciService.verifyCi(ciService, page, pageSize);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String saveCi(){
        try {
            int res = ciService.loadFromFiles("D:\\repo\\chinese-poetry\\ci");
            return res + "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "wtf?";
    }

    @PostMapping("/es/to/file")
    public void saveToFile(@RequestParam Integer page, @RequestParam Integer pageSize){
        ciService.outputJSONFromES(page, pageSize);
    }
}
