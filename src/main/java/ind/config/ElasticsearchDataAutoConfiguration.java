package ind.config;

import org.elasticsearch.client.Client;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

/**
 * @author BJQXDN0626
 * @create 2018/3/1
 */
//@Configuration
//@ConditionalOnClass({ Client.class, ElasticsearchTemplate.class })
//@AutoConfigureAfter(ElasticsearchAutoConfiguration.class)
public class ElasticsearchDataAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnBean(Client.class)
//    public ElasticsearchTemplate elasticsearchTemplate(Client client,
//            ElasticsearchConverter converter) {
//        try {
//            return new ElasticsearchTemplate(client, converter);
//        }catch (Exception ex) {
//            throw new IllegalStateException(ex);
//        }
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ElasticsearchConverter elasticsearchConverter(
//            SimpleElasticsearchMappingContext mappingContext) {
//        return new MappingElasticsearchConverter(mappingContext);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public SimpleElasticsearchMappingContext mappingContext() {
//        return new SimpleElasticsearchMappingContext();
//    }

}
