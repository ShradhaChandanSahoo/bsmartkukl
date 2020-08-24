package com.bcits.bsmartwater.daoImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcits.bsmartwater.dao.GenericDAO;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @author Rajasekhara Reddy
 * @version %I%, %G%
 */
@Transactional(propagation = Propagation.REQUIRED)
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

	@PersistenceContext(unitName="defaultPersistenceUnit")
	protected EntityManager entityManager;

	private Class<T> type;

	@PersistenceContext(unitName="TRIPUR_1115")
	protected EntityManager TRIPUR_1115;

	@PersistenceContext(unitName="BANESH_1112")
	protected EntityManager BANESH_1112;

	@PersistenceContext(unitName="TESTLOC_2222")
	protected EntityManager TESTLOC_2222;
	
	@PersistenceContext(unitName="LALITPUR_1118")
	protected EntityManager LALITPUR_1118;

	@PersistenceContext(unitName="BHAKTAPUR_1116")
	protected EntityManager BHAKTAPUR_1116;
	
	@PersistenceContext(unitName="CHHETRA_1114")
	protected EntityManager CHHETRA_1114;
	
	@PersistenceContext(unitName="MAHAR_1111")
	protected EntityManager MAHAR_1111;
	
	@PersistenceContext(unitName="THIMI_1117")
	protected EntityManager THIMI_1117;
	
	@PersistenceContext(unitName="KIRTIPUR_1119")
	protected EntityManager KIRTIPUR_1119;
	
	@PersistenceContext(unitName="MAHAN_1110")
	protected EntityManager MAHAN_1110;
	
	@PersistenceContext(unitName="KAMALADI_1113")
	protected EntityManager KAMALADI_1113;
	
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
	}

	/**
	 * Perform an initial save of a previously unsaved Entity. All subsequent
	 * persist actions of this entity should use the #update() method. This
	 * operation must be performed within the a database transaction context for
	 * the entity's data to be permanently saved to the persistence store, i.e.,
	 * database. This method uses the
	 * {@link javax.persistence.EntityManager#persist(Object)
	 * EntityManager#persist} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.save(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity to persist
	 * @return Entity instance
	 * @since 0.1
	 */
	@Override
	public T save(final T t) {		
		Session session = entityManager.unwrap(Session.class);
		BsmartWaterLogger.logger.info("saving "+t+" instance");
		try {
			session.persist(t);
			BsmartWaterLogger.logger.info(t+" saved successful");
			session.flush();
			session.clear();
			return t;
			
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("saving "+t+" failed", re);
			throw re;
		}
	}
	
	@Override
	public T customsave(final T t) {		
		Session session = getCustomEntityManager().unwrap(Session.class);
		BsmartWaterLogger.logger.info("saving "+t+" instance");
		try {
			session.persist(t);
			BsmartWaterLogger.logger.info(t+" saved successful");
			session.flush();
			session.clear();
			return t;
			
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("saving "+t+" failed", re);
			throw re;
		}
	}
	/**
	 * Delete a persistent Entity. This operation must be performed within the a
	 * database transaction context for the entity's data to be permanently
	 * deleted from the persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#remove(Object)
	 * EntityManager#delete} operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * UsersDAO.delete(entity);
	 * txManager.commit(txn);
	 * entity = null;
	 * </pre>
	 * 
	 * @param id
	 *            Entity property
	 * @since 0.1
	 */
	@Override
	public void delete(final Object id) {
		
		BsmartWaterLogger.logger.info("deleting instance");
		try {
			this.entityManager.remove(this.entityManager.getReference(type, id));
			BsmartWaterLogger.logger.info("delete successful");
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public void customdelete(final Object id) 
	{
		Session session = getCustomEntityManager().unwrap(Session.class);
		BsmartWaterLogger.logger.info("deleted "+id+" instance");
		try {
			session.beginTransaction();
			T t1 = (T) session.get(type,(int)id);
			session.delete(t1);
			session.getTransaction().commit();
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("delte "+id+" failed", re);
			throw re;
		}
		
	}
	/**
	 * Persist a previously saved Entity and return it or a copy of it to the
	 * sender. A copy of the Users entity parameter is returned when the JPA
	 * persistence mechanism has not previously been tracking the updated
	 * entity. This operation must be performed within the a database
	 * transaction context for the entity's data to be permanently saved to the
	 * persistence store, i.e., database. This method uses the
	 * {@link javax.persistence.EntityManager#merge(Object) EntityManager#merge}
	 * operation.
	 * <p>
	 * User-managed Spring transaction example:
	 * 
	 * <pre>
	 * TransactionStatus txn = txManager
	 * 		.getTransaction(new DefaultTransactionDefinition());
	 * entity = UsersDAO.update(entity);
	 * txManager.commit(txn);
	 * </pre>
	 * 
	 * @param t
	 *            Entity instance to update
	 * @return Updated entity instance
	 * @since 0.1
	 */
	@Override
	public T update(final T t) {
		
		BsmartWaterLogger.logger.info("updating "+t+" instance");
		try {
			T result = this.entityManager.merge(t);
			BsmartWaterLogger.logger.info("update successful");
			return result;
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("update failed", re);
			throw re;
		}
	}

	@Override
	public T customupdate(final T t) {
		Session session = getCustomEntityManager().unwrap(Session.class);
		BsmartWaterLogger.logger.info("updating "+t+" instance");
		try {
			session.merge(t);
			BsmartWaterLogger.logger.info(t+" updating successful");
			session.flush();
			session.clear();
			return t;
			
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("updating "+t+" failed", re);
			throw re;
		}
	}
	/**
	 * Find all Entity instances with a specific property value.

	 * @param id
	 *            property to query
	 * @return T found by query
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public T find(final Object id) {		
		BsmartWaterLogger.logger.info("finding instance with id: " + id);
		try {
			return (T) this.entityManager.find(type, id);
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("find failed", re);
			throw re;
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public T customfind(final Object id) {		
		BsmartWaterLogger.logger.info("finding instance with id: " + id);
		try {
			return (T) getCustomEntityManager().find(type, id);
		} catch (RuntimeException re) {
			BsmartWaterLogger.logger.error("find failed", re);
			throw re;
		}
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public long countAll(final Map<String, Object> params) 
	{

	        final StringBuffer queryString = new StringBuffer(
	                "SELECT count(o) from ");

	        queryString.append(type.getSimpleName()).append(" o ");
	        queryString.append(this.getQueryClauses(params, null));

	        final Query query = this.entityManager.createQuery(queryString.toString());

	        return (Long) query.getSingleResult();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<?> selectObjectQuery(final Map<String, Object> params) 
	{
		final StringBuffer queryString = new StringBuffer(
	        		"SELECT o from ");

	    queryString.append(type.getSimpleName()).append(" o ");
	    queryString.append(this.getQueryClauses(params, null));

	    final Query query = this.entityManager.createQuery(queryString.toString());

	    return (List<?>) query.getResultList();
	}
	
	 private String getQueryClauses(final Map<String, Object> params, final Map<String, Object> orderParams) 
	 {
	     final StringBuffer queryString = new StringBuffer();
	     if ((params != null) && !params.isEmpty()) 
	     {
	             queryString.append(" where ");
	             for (final Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) 
	             {
	                     final Map.Entry<String, Object> entry = it.next();
	                     if (entry.getValue() instanceof Boolean) 
	                     {
	                             queryString.append(entry.getKey()).append(" is ").append(entry.getValue()).append(" ");
	                     } 
	                     else 
	                     {
	                            if (entry.getValue() instanceof Number) 
	                            {
	                                    queryString.append(entry.getKey()).append(" = ")
	                                                   .append(entry.getValue());
	                            }
	                            else 
	                            {
	                                    // string equality
	                                    queryString.append(entry.getKey()).append(" = '")
	                                                   .append(entry.getValue()).append("'");
	                            }
	                     }
	                     if (it.hasNext()) 
	                     {
	                            queryString.append(" and ");
	                     }
	             }
	     }
	     if ((orderParams != null) && !orderParams.isEmpty()) 
	     {
	             queryString.append(" order by ");
	             for (final Iterator<Map.Entry<String, Object>> it = orderParams
	                            .entrySet().iterator(); it.hasNext();) 
	             {
	                     final Map.Entry<String, Object> entry = it.next();
	                     queryString.append(entry.getKey()).append(" ");
	                     if (entry.getValue() != null) 
	                     {
	                             queryString.append(entry.getValue());
	                     }
	                     if (it.hasNext()) 
	                     {
	                            queryString.append(", ");
	                     }
	             }
	     }
	     return queryString.toString();
	}

	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public List<T> getByNamedQuery(String namedQuery,
			 Map<String, Object> map) {
		 Query q = entityManager.createNamedQuery(namedQuery);

		 if(map!=null){
			 Iterator<String> it = map.keySet().iterator();
			 while (it.hasNext()) {
				 String key = it.next();
				 q.setParameter(key, map.get(key));
			 }
		 }
		 return q.getResultList();
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public List<T> executeSimpleQuery(String query) {
		 Query q = entityManager.createQuery(query);
		 return q.getResultList();
	 }
	 
	 @SuppressWarnings("unchecked")
	 @Transactional(propagation = Propagation.SUPPORTS)
	 @Override
	 public T getSingleResult(String query) {
		 try{
			Query q = entityManager.createQuery(query);
		 	return (T) q.getSingleResult();
		 }catch(Exception e){
			//e.printStackTrace();
		 }
		 return null;
	 }
	 
	 @Transactional(propagation = Propagation.REQUIRED)
	 @Override
	 public void executeDeleteQuery(String query) {
		entityManager.createQuery(query).executeUpdate();
	 }
	 
		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public List<?> selectQuery(final String className, final List<String> attributesList, final Map<String, Object> params) 
		{
			final StringBuffer queryString = new StringBuffer(
		        		"SELECT ");

			if((attributesList == null) || (attributesList.size() == 0))
			{
				queryString.append("o");
			}
			
			else if(attributesList.size() == 1)
			{
				queryString.append("o.");
				queryString.append(attributesList.get(0));
			}
			
			else if(attributesList.size() > 1)
			{
				for (Iterator<String> iterator = attributesList.iterator(); iterator.hasNext();)
				{
					String string = (String) iterator.next();
					
					queryString.append("o.");
					queryString.append(string);
					queryString.append(", ");
				}
				
				queryString.replace(0, queryString.length() - 1, queryString.substring(0, queryString.length() - 2));
			}
			else
				queryString.append("o");
			
			queryString.append(" from ");
		    queryString.append(className).append(" o ");
		    queryString.append(this.getQueryClauses(params, null));

		    final Query query = this.entityManager.createQuery(queryString.toString());

		    return (List<?>) query.getResultList();
		}
		
		@Override
		public int batchSave(final List<T> entityList) {				
		Session session = entityManager.unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < entityList.size(); ++i) {
			try {
				    session.persist(entityList.get(i));
					session.flush();
					session.clear();
				
				BsmartWaterLogger.logger.info(entityList.get(i) + " saved successful");
			} catch (RuntimeException re) {
				BsmartWaterLogger.logger.error("saving " + entityList.get(i)+ " failed", re);
				throw re;
			}
			insertedCount++;
		}
		return insertedCount;
		} 	
		
		@Override
		public int custombatchSave(final List<T> entityList) {				
			Session session = this.getCustomEntityManager().unwrap(Session.class);
			int insertedCount = 0;
			for (int i = 0; i < entityList.size(); ++i) {
				try {
					   session.persist(entityList.get(i));
				
						session.flush();
						session.clear();
					
					BsmartWaterLogger.logger.info(entityList.get(i) + " saved successful");
				} catch (RuntimeException re) {
					BsmartWaterLogger.logger.error("saving " + entityList.get(i)+ " failed", re);
					throw re;
				}
				insertedCount++;
			}
			return insertedCount;
		} 	
		
		
		@Override
		public int batchUpdate(final List<T> entityList) {				
		Session session = entityManager.unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < entityList.size(); ++i) {
			try {
				    session.merge(entityList.get(i));
				
					session.flush();
					session.clear();
				
				BsmartWaterLogger.logger.info(entityList.get(i) + " saved successful");
			} catch (RuntimeException re) {
				BsmartWaterLogger.logger.error("saving " + entityList.get(i)+ " failed", re);
				throw re;
			}
			insertedCount++;
		}
		return insertedCount;
		} 	
		
		@Override
		public int custombatchUpdate(final List<T> entityList) {				
		Session session = getCustomEntityManager().unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < entityList.size(); ++i) {
			try {
				    session.merge(entityList.get(i));
					session.flush();
					session.clear();
				    BsmartWaterLogger.logger.info(entityList.get(i) + " saved successful");
				    
			} catch (RuntimeException re) {
				    BsmartWaterLogger.logger.error("saving " + entityList.get(i)+ " failed", re);
				    throw re;
			}
			insertedCount++;
		}
		return insertedCount;
		} 	
		
		 public Date getDate2(String dateString)
		 {
			  DateFormat formatter = null;
		        Date convertedDate = null;
		        try{
		        formatter = new SimpleDateFormat("MM/dd/yyyy");
		        convertedDate =  formatter.parse(dateString);
		        }
		        catch(Exception e) {
		     	   e.printStackTrace();
		 		}
			 
			 return convertedDate;
		  
		 }
		 
		 public String getDate3(Date date)
		 {
			 SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			 String datevalue=sdf.format(date);		 
			 return datevalue;
		 }
		
		@SuppressWarnings("unchecked")
		@Transactional(propagation = Propagation.SUPPORTS)
		@Override
		public String getCheckConstraints(String tableName,String constraintName)
		{		
			List<String> res= entityManager.createNamedQuery("constraint.getChecks").setParameter("tableName", tableName).setParameter("constraintName", constraintName).getResultList();
		    
		    String[] splitRes=null;
		    if(res.size()>1)
		    {
		    	splitRes=res.get(1).split(" OR ");
		    }
		    else
		    {
		    	splitRes=res.get(0).split(" OR ");
		    }
		    
			int subLength = 0;
			String[] test = null;
			if(splitRes.length > 0)
			{
				test = splitRes[0].split("'");
				subLength = test[0].length()+1;
			}
			String finalRes="";
			String result = "";
			//List list = new ArrayList();
			for(int i=0;i<splitRes.length;i++)
			{
				finalRes=splitRes[i].trim();
				 //System.out.println("Spl--"+finalRes);	
				result = result + (finalRes.substring(subLength, finalRes.length()-1)) + ",";
			    
			}
			result = result.substring(0,result.length()-1);
			//STALogger.logger.info("value"+result);	
					return result;
			 
		}

		public EntityManager getCustomEntityManager() {
			ServletRequestAttributes reqAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = reqAttributes.getRequest();
			HttpSession session=request.getSession(false);
			String schemaName=(String)session.getAttribute("schemaName");
			String schemaName1=(String)session.getAttribute("schemaName1");
			
			if(("CORPORATE").equalsIgnoreCase(schemaName1)){
				return entityManager;
			}
			
			else{

				switch(schemaName){
				case "TRIPUR_1115": 
					return TRIPUR_1115;
				case "BANESH_1112": 
					return BANESH_1112;
				case "TESTLOC_2222":
					return TESTLOC_2222;
				case "LALITPUR_1118":
					return LALITPUR_1118;
				case "BHAKTAPUR_1116":
					return BHAKTAPUR_1116;
				case "CHHETRA_1114":
					return CHHETRA_1114;
				case "MAHAR_1111":
					return MAHAR_1111;
				case "THIMI_1117":
					return THIMI_1117;
				case "KIRTIPUR_1119":
					return KIRTIPUR_1119;
				case "MAHAN_1110":
					return MAHAN_1110;
				case "KAMALADI_1113":
					return KAMALADI_1113;

				}

			}
			return null; 
		}
		public EntityManager getCustomEntityManager(String schemaName) {
			switch(schemaName){
			case "TRIPUR_1115": 
				return TRIPUR_1115;
			case "BANESH_1112": 
				return BANESH_1112;
			case "TESTLOC_2222":
				return TESTLOC_2222;
			case "LALITPUR_1118":
				return LALITPUR_1118;
			case "BHAKTAPUR_1116":
				return BHAKTAPUR_1116;
			case "CHHETRA_1114":
				return CHHETRA_1114;
			case "MAHAR_1111":
				return MAHAR_1111;
			case "THIMI_1117":
				return THIMI_1117;
			case "KIRTIPUR_1119":
				return KIRTIPUR_1119;
			case "MAHAN_1110":
				return MAHAN_1110;
			case "KAMALADI_1113":
				return KAMALADI_1113;
				
			}
			return null; 
		}
		
		
		
		
}