package ind.service;

import ind.domains.Hero;
import ind.repository.TestRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @author BJQXDN0626
 * @create 2018/3/2
 */
@Component
public class TestService {

    private final ElasticsearchTemplate template;

    private final TestRepository testRepository;

    public TestService(ElasticsearchTemplate template, TestRepository testRepository) {
        this.template = template;
        this.testRepository = testRepository;
    }

    public void testES(String name) {
        Hero hr = getObject(name);
        template.createIndex(Hero.class);
        template.refresh(Hero.class);
        template.index(new IndexQueryBuilder().withObject(hr).build());
    }

    public void testESJPA(String name) {
        Hero hr = getObject(name);
        testRepository.save(hr);
    }

    private Hero getObject(String name){
        Hero hr = new Hero();
        hr.setId(System.currentTimeMillis());
        hr.setName(name);
        return hr;
    }

}
