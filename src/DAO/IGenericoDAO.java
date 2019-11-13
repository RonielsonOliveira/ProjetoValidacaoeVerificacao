package DAO;

import java.sql.SQLException;

public interface IGenericoDAO<T> {
	 boolean adiciona(T t) ;
	    
	    public T le(String s);
}
