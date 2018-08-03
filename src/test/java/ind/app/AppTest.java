package ind.app;

import ind.domains.Ci;
import ind.repository.CiRepository;
import ind.service.CiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author CaiKun
 * @date 2018/4/4
 *
 * elasticsearch server 5.6.x
 *
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

    @Autowired
    private CiRepository ciRepository;

    @Test
    public void listAll() {
        Page<Ci> cis = ciRepository.findByFromFileIndexEqualsOrderByOCodeAsc("1000", new QPageRequest(0 ,2000));
        System.out.println(cis.getTotalElements() + ", " + cis.getContent().size());
    }

    @Test
    public void fix(){
        ciService.verifyCi(ciService, "1000",0, 2000);
    }

    @Test
    public void outputFile(){
        ciService.outputJSONFromES("1000",0, 2000);
    }
}


