package ind.repository;

import ind.domains.Ci;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author BJQXDN0626
 * @create 2018/3/13
 */
public interface CiRepository extends ElasticsearchRepository<Ci, String> {

    List<Ci> findAllByParagraphsMatches(String paragraphs);

    List<Ci> findAllByIdIsLessThan(String maxId);

}
