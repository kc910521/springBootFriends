package ind.repository;

import ind.domains.Hero;
import org.springframework.stereotype.Repository;

/**
 * @author BJQXDN0626
 * @create 2018/3/1
 */
@Repository
public interface TestRepository extends CrudRepository<Hero, Long>, PagingAndSortingRepository<Hero, Long> {
}
