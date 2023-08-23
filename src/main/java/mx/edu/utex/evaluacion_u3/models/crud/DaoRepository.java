package mx.edu.utex.evaluacion_u3.models.crud;

import java.util.List;

public interface DaoRepository<T> {
    List<T> findAll();
    List<T> findAllAdmin();
    T findOne(Long id);
    boolean save(T object);
    boolean update(T object);
    boolean delete(Long id);
}
