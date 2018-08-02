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
    public void load(){
        try {
            int res = ciService.loadFromFiles("D:\\repo\\chinese-poetry\\ci");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fix(){
        ciService.verifyCi(ciService, 2, 1000);
    }

    @Test
    public void outputFile(){
        ciService.outputJSONFromES(2, 1000);
    }
}


