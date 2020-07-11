package Helper;

import java.util.List;

import org.eclipse.persistence.exceptions.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.query.Query;

import Helper.common;

public class HibernateExecution<T> {
	private SessionFactory factory;  
	private Session session;
	
	public HibernateExecution(){
		Metadata meta = new MetadataSources(common.ssr).getMetadataBuilder().build();  
		factory = meta.getSessionFactoryBuilder().build();  
		session = factory.openSession();  
	}
		
	public boolean save(T obj) 
	{
		Transaction t = session.beginTransaction();   
		boolean flag = false;
		
		try 
	    {
			session.save(obj);
			flag = true;
			t.commit();	
		}
		catch(QueryException e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		catch(org.hibernate.QueryException e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		catch(Exception e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		finally 
	    {
		    session.close();    
	    	factory.close();
	    }
		
		return flag;
	}	

	public boolean delete(T obj) 
	{
		Transaction t = session.beginTransaction();   
		boolean flag = false;
		
		try 
	    {
			session.delete(obj);;
			flag = true;
			t.commit();	
		}
		catch(QueryException e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		catch(org.hibernate.QueryException e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		catch(Exception e)
	    {
	    	t.rollback();
	    	common.ErrorLog("Execute Helper >> Save", e);
	    }
		finally 
	    {
		    session.close();    
	    	factory.close();
	    }
		
		return flag;
	}	

	@SuppressWarnings("unchecked")
	public List<T> getList(String SQL, Class type){
		return session.createQuery(SQL, type).getResultList();
	}

	@SuppressWarnings("unchecked")
	public T getDetails(String SQL, long id, Class type){
		Query<T> query = session.createQuery(SQL);
		query.setParameter("id", id);
		if(query.getResultList()==null || (query.getResultList()!=null && query.getResultList().size()==0)) 
		{
			return null;
		}
		
		return query.getResultList().get(0);
	}
}
