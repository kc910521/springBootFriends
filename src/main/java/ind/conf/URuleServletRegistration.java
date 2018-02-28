package ind.conf;

import com.bstek.urule.console.servlet.URuleServlet;
import ind.controller.IndexServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author BJQXDN0626
 * @create 2018/2/27
 */
@Component
public class URuleServletRegistration {
    @Bean
    public ServletRegistrationBean registerURuleServlet(){
        return new ServletRegistrationBean(new URuleServlet(),"/urule/*");
    }

    @Bean
    public ServletRegistrationBean registerIndexServlet(){
        return new ServletRegistrationBean(new IndexServlet(),"/urule");
    }
}
