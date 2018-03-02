package ind.repository;


import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author BJQXDN0626
 * @create 2018/3/1
 */
public interface CrudRepository <T, ID extends Serializable>
        extends Repository<T, ID> {

    <S extends T> S save(S entity);

    Optional<T> findById(ID primaryKey);

    Iterable<T> findAll();

    long count();

    void delete(T entity);

    boolean existsById(ID primaryKey);

    // … more functionality omitted.
}