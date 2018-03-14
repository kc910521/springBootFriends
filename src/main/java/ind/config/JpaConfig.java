package ind.config;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author BJQXDN0626
 * @create 2018/3/1
 */
@EnableJpaRepositories
@EnableElasticsearchRepositories(basePackages = "ind.repository")
public class JpaConfig {
}
