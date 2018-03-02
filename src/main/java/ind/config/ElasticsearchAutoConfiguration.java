package ind.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author BJQXDN0626
 * @create 2018/3/1
 */
//@Configuration
//@ConditionalOnClass(value={org.elasticsearch.client.Client.class,org.springframework.data.elasticsearch.client.TransportClientFactoryBean.class})
//@ConditionalOnProperty(prefix="spring.data.elasticsearch",
//        name="cluster-nodes",
//        matchIfMissing=false)
//@EnableConfigurationProperties(value=ElasticsearchProperties.class)
public class ElasticsearchAutoConfiguration {
}
