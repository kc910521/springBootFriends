package ind.app;

import ind.service.CiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author CaiKun
 * @date 2018/4/4
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {

    @Autowired
    private CiService ciService;

    @Test
    public void outputFile(){
        ciService.outputJSONFromES();
    }

    @Test
    public void andSave(){
        try {
            int res = ciService.loadFromFiles("C:\\Users\\BJQXDN0626\\Downloads\\chinese-poetry-master\\chinese-poetry-master\\ci\\cix");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fix(){
        ciService.verifyCi(ciService);
    }


}
