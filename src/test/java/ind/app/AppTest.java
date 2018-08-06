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
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void packageWork(){
        List<String> workList = Arrays.asList(
//                "2000", "3000", "4000",
//                "5000", "6000",
//                "7000",
                "8000",
                "9000", "10000",
                "11000", "12000",
                "13000", "14000",
                "15000", "16000",
                "17000", "18000",
                "19000", "20000",
                "21000"
                );
        workList.forEach(wPage -> {
            ciService.verifyCi(ciService, wPage,0, 2000);
            ciService.outputJSONFromES(wPage,0, 2000);
        });
    }
}


