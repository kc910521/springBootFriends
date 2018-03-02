package ind.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author BJQXDN0626
 * @create 2018/3/2
 *
 * from
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 */
public interface PagingAndSortingRepository<T, ID extends Serializable>
        extends CrudRepository<T, ID> {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);
}