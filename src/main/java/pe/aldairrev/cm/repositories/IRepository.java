package pe.aldairrev.cm.repositories;

import java.util.List;

public interface IRepository<T, ID> {
    public List<T> findAll(); 
    public T findById(ID id); 
    public T create(T t); 
    public T update(T t);
    public boolean deleteById(ID id);
}
