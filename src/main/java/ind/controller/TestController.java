package ind.controller;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import ind.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author BJQXDN0626
 * @create 2017/12/11
 */
@RestController
public class TestController {

    @Autowired
    private ApplicationContext ctx;

    public void testA() throws IOException {
        Customer customer = new Customer();
        customer.setName("sa");
        customer.setAge(12);
        System.out.println("customer.isSexual():" + customer.isSexual());
        //从spring上下文中获取KnowledgeService对象实例
        KnowledgeService knowledgeService=(KnowledgeService) ctx.getBean(KnowledgeService.BEAN_ID);

        //通过KnowledgeService获取指定的知识包（项目名+"/"+知识包ID）
        //你没有进行过配置本处应该报错，请自行在urule配置决策集
        KnowledgePackage knowledgePackage=knowledgeService.getKnowledge("mydemo/first001");

        //创建一个KnowledgeSession对象
        KnowledgeSession session= KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        session.insert(customer);
        session.fireRules();
        System.out.println("customer.isSexual():" + customer.isSexual());
    }

    @RequestMapping("/ping")
    public String index() {
        try {
            testA();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Greetings from Spring Boot with urule(http://localhost:9522/urule/frame)!";
    }
}
