package com.gmail.dao;

/**
 * 
 * @author datkach
 * Interface DAO assumes the CRUD realization
 */

public interface Dao {
	
	public abstract Object[] getAllObjects();
	public abstract Object getObject(int id);
	public abstract int updateObject(Object obj);
	public abstract void removeObject(Object obj);
	

}
