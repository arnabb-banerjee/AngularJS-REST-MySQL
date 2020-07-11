package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Table;

import org.eclipse.persistence.internal.databaseaccess.InOutputParameterForCallableStatement;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.type.TrueFalseType;

@Entity
@Table(name = "input")
@Access(value=AccessType.FIELD)
public class InputInfo {

	@Id   
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="word", unique = true)
	private String word;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	public InputInfo() {
	}


	public InputInfo(int id, String word) {
		this.id = id;
		this.word = word;
	}

	@Override
	public String toString() {
		return "{id=" + this.id + ", word=" + this.word + "}";
	}

	public static boolean save(int ID, String Word)
	{
		try {
			InputInfo obj = new InputInfo(ID, Word);
			return new Helper.HibernateExecution<InputInfo>().save(obj);
		}
		catch (Exception e) {
			throw e;
		}
	}

	public static boolean delete(InputInfo obj)
	{
		try {
			return new Helper.HibernateExecution<InputInfo>().delete(obj);
		}
		catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<InputInfo> getList(String keyward)
	{
		Metadata meta = new MetadataSources(new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build()).getMetadataBuilder().build();  
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
		Session session = factory.openSession();  
		
		Query query = session.createSQLQuery("CALL fetch_word(:p_word)")
				.addEntity(InputInfo.class)
				.setParameter("p_word", keyward);
		List<InputInfo> list = query.getResultList();
		
		session.close();    
    	factory.close();
return list;
		
		//return new Helper.HibernateExecution<InputInfo>().getList("select new entity.InputInfo (B.id, B.word) from InputInfo B", InputInfo.class);
	}
	
	public static InputInfo getDetails(int id)
	{
		return new Helper.HibernateExecution<InputInfo>().getDetails("select new entity.InputInfo (B.id, B.word) from InputInfo B where B.id = :id", id, InputInfo.class);
	}
}
