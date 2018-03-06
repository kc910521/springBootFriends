/**
 * 
 */
package ind.ck.config;


import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Siva
 *
 */
@Configuration
//@EnableTransactionManagement
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages= "ind.ck.repository")
public class PersistenceConfig {

//	@Autowired
//	private Environment env;
//
//	@Value("${init-db:false}")
//	private String initDatabase;
//
//	@Bean
//	public PlatformTransactionManager transactionManager()
//	{
//		EntityManagerFactory factory = entityManagerFactory().getObject();
//		return new JpaTransactionManager(factory);
//	}
//
	@Bean
	@ConfigurationProperties("app.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	// low
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);
		Map<String, Object> rMap = new HashMap<>();
		rMap.put("hibernate.hbm2ddl.auto", "update");
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(vendorAdapter, rMap, null);
		return builder.dataSource(dataSource)
				.packages("ind.ck.entity")
				.build();
	}

}
