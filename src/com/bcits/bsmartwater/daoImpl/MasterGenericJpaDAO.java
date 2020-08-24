package com.bcits.bsmartwater.daoImpl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.BaseEntity;


/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @author Ravi Shankar Reddy 
 * @version %I%, %G%
 */
@Transactional(readOnly = true)
@Repository
@SuppressWarnings({ "unchecked" })
public class MasterGenericJpaDAO implements MasterGenericDAO {

	//static Logger logger = LoggerFactory.getLogger(MasterGenericJpaDAO.class);
	
	@PersistenceContext(unitName="defaultPersistenceUnit")
	private EntityManager entityManager;

	@Override
	public <T extends BaseEntity> T getById(Class<T> clazz, int id) {
		return entityManager.find(clazz, id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> T getByCriteria(Class<T> clazz, Map criterias) {
		List<T> items = findByCriteria(clazz, criterias, null, false);
		if (items != null && items.size() > 0)
			return items.get(0);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> T getByUniqueAttribute(Class<T> clazz,
			String attributeName, Object value) {
		Map criterias = new HashMap();
		criterias.put(attributeName, value);
		List<T> items = findByCriteria(clazz, criterias);
		if (items != null && items.size() > 0)
			return items.get(0);
		return null;
	}

	@Override
	public <T extends BaseEntity> T getByNamedQuery(String namedQuery,
			Map<String, Object> map) {
		Query q = entityManager.createNamedQuery(namedQuery);
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setParameter(key, map.get(key));
		}
		List<T> data = q.getResultList();
		if (data != null && data.size() > 0)
			return data.get(0);
		else
			return null;
	}

	@Override
	public <T extends BaseEntity> List<T> executeSimpleQuery(String query) {
		Query q = entityManager.createQuery(query);
		return q.getResultList();
	}
	
	@Override
	public List<Object[]> executeSimpleObjectQuery(String query) {
		Query q = entityManager.createQuery(query);
		return q.getResultList();
	}
	
	@Override
	public Object[] executeSingleObjectQuery(String query) {
		Query q = entityManager.createQuery(query);
		try{
			Object[] obj= (Object[]) q.getSingleResult();
			return obj;
		}catch(Exception e){
			return null;
		}
		

	}
	
	@Override
	public <T extends BaseEntity> void executeSimpleUpdateQuery(String query) {
		Query q = entityManager.createQuery(query);
		q.executeUpdate();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public int executeSimpleUpdateQuery1(String query) {
		Query q = entityManager.createQuery(query);
		return q.executeUpdate();
	}

	@Override
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz) {
		return findByCriteria(clazz, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> List<T> findAll(Class<T> clazz,
			boolean activeOnly) {
		Map criterias = new HashMap();
		criterias.put("status", 1);
		return findByCriteria(clazz, criterias);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz,
			Map criterias) {
		return findByCriteria(clazz, criterias, null, false);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz,
			Map criterias, String sortField, boolean desc) {
		// Build the Query String with Search Criteria
		int count = 0;
		int counter = 0;
		StringBuilder query = new StringBuilder("Select obj from ").append(
				clazz.getSimpleName()).append(" obj where 1=1");
		if (criterias != null && criterias.size() > 0) {
			Object[] keyArray = criterias.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				if (keyArray[i].toString().startsWith("||")) {
					counter++;
				}
			}
			if (counter == 1)
				query = new StringBuilder("Select obj from ").append(
						clazz.getSimpleName()).append(" obj where 1!=1");
			for (int i = 0; i < keyArray.length; i++) {
				if (keyArray[i].toString().startsWith("||")) {
					if (count == 0 && counter > 1)
						query.append(" and ( ");
					else
						query.append(" or ");
					if (criterias.get(keyArray[i]).toString().contains("null")) {
						if (criterias.get(keyArray[i]).toString().contains("!"))
							query.append(" obj.").append(keyArray[i])
									.append(" IS NOT NULL");
						else
							query.append(" obj.").append(keyArray[i])
									.append(" IS NULL");

					} else if (criterias.get(keyArray[i]).toString()
							.contains("<>")
							|| criterias.get(keyArray[i]).toString()
									.contains("!=")) {
						query.append(" obj.").append(keyArray[i]).append("!=:")
								.append("p" + i);
					} else if (criterias.get(keyArray[i]).toString()
							.contains(",")) {
						StringBuilder valBuilder = new StringBuilder();
						String[] values = criterias.get(keyArray[i]).toString()
								.trim().split(",");
						for (int j = 0; j < values.length; j++) {
							values[j] = "'" + values[j] + "',";
							valBuilder.append(values[j]);
						}
						if (valBuilder.length() > 1) {
							valBuilder = valBuilder.deleteCharAt(valBuilder
									.length() - 1);
							query.append(" obj.")
									.append(keyArray[i])
									.append(" IN (" + valBuilder.toString()
											+ ")");
						}
					} else
						query.append(" obj.")
								.append(keyArray[i].toString()
										.substring(
												keyArray[i].toString().indexOf(
														"|") + 2)).append("=:")
								.append("p" + i);
					count++;
				}

			}
			if (count > 1)
				query.append(" )");
			for (int i = 0; i < keyArray.length; i++) {
				if (keyArray[i].toString().startsWith("||")) {
					continue;
				} else if (criterias.get(keyArray[i]).toString()
						.contains("null")) {
					if (criterias.get(keyArray[i]).toString().contains("!"))
						query.append(" and obj.").append(keyArray[i])
								.append(" IS NOT NULL");
					else
						query.append(" and obj.").append(keyArray[i])
								.append(" IS NULL");
				} else if (criterias.get(keyArray[i]).toString().contains("<>")
						|| criterias.get(keyArray[i]).toString().contains("!=")) {
					query.append(" and obj.").append(keyArray[i]).append("!=:")
							.append("p" + i);
				} else if (criterias.get(keyArray[i]).toString().contains(">")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd-MM-yyyy");
					String modifiedValue = criterias.get(keyArray[i])
							.toString().replace(">", "");
					try {
						criterias.put(keyArray[i],
								dateFormat.parse(modifiedValue));
					} catch (Exception e) {

						e.printStackTrace();
					}
					query.append(" and obj.").append(keyArray[i]).append(">:")
							.append("p" + i);
				} else if (criterias.get(keyArray[i]).toString().contains(",")) {
					StringBuilder valBuilder = new StringBuilder();
					String[] values = criterias.get(keyArray[i]).toString()
							.trim().split(",");
					for (int j = 0; j < values.length; j++) {
						values[j] = "'" + values[j] + "',";
						valBuilder.append(values[j]);
					}
					if (valBuilder.length() > 1) {
						valBuilder = valBuilder.deleteCharAt(valBuilder
								.length() - 1);
						query.append(" and obj.").append(keyArray[i])
								.append(" IN (" + valBuilder.toString() + ")");
					}
				} else if (keyArray[i].toString().equalsIgnoreCase("dateField")
						&& criterias.get("from") != null
						&& criterias.get("to") != null) {
					query.append(" and obj.")
							.append(criterias.get("dateField"));
					Timestamp firstDate = new Timestamp(
							((Date) criterias.get("from")).getTime());
					Timestamp secondDate = new Timestamp(
							((Date) criterias.get("to")).getTime() + 86399999);
					query.append(" Between '" + firstDate + "' AND '"
							+ secondDate + "'");
				} else {
					if (keyArray[i].toString().equalsIgnoreCase("dateField")) {
						// Do nothing
					} else if (keyArray[i].toString().equalsIgnoreCase("from")) {
						// Do nothing
					} else if (keyArray[i].toString().equalsIgnoreCase("to")) {
						// Do nothing
					} else
						query.append(" and obj.").append(keyArray[i])
								.append("=:").append("p" + i);
				}
			}
		}
		if (sortField != null) {
			query.append("  ORDER BY obj.").append(sortField);
			if (!desc)
				query.append(" asc");
			else
				query.append(" desc");

		}
		// Build the query Object
		Query jpaQuery = entityManager.createQuery(query.toString());
		// Set the search Parameters for the query
		if (criterias != null && criterias.size() > 0) {
			Object[] keyArray = criterias.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				if (criterias.get(keyArray[i]).toString().contains("!="))
					jpaQuery.setParameter(
							"p" + i,
							criterias
									.get(keyArray[i])
									.toString()
									.substring(
											criterias.get(keyArray[i])
													.toString()
													.lastIndexOf("=") + 1));
				else if (criterias.get(keyArray[i]).toString().contains("null")) {
					// Do nothing
				} else if (criterias.get(keyArray[i]).toString().contains(",")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("dateField")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("from")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("to")) {
					// Do nothing
				} else
					jpaQuery.setParameter("p" + i, criterias.get(keyArray[i]));
			}

		}
		// Execute the query and return List of objects
		return jpaQuery.getResultList();
	}

	@Override
	public <T extends BaseEntity> List<T> findByNamedQuery(String namedQuery,
			Map<String, Object> map) {
		Query q = entityManager.createNamedQuery(namedQuery);
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setParameter(key, map.get(key));
		}
		return q.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T extends BaseEntity> List<T> findLimitedByCount(Class<T> clazz,
			Map criterias, Integer maxCount) {
		Boolean containsOr = false;
		int count = 0;
		StringBuffer queryCount = new StringBuffer("select count(p) from "
				+ clazz.getSimpleName() + " p where 1=1 ");
		StringBuffer searchString = new StringBuffer("");
		if (criterias != null) {
			searchString = createQueryFromCriterias(clazz, criterias,
					containsOr, count);
		}
		if (containsOr && count == 1)
			queryCount = new StringBuffer("select count(p) from "
					+ clazz.getSimpleName() + " p where 1!=1 ");
		queryCount.append(searchString.toString());
		StringBuffer queryStmt = new StringBuffer(" from "
				+ clazz.getSimpleName() + " p where 1=1 ");
		if (containsOr && count == 1)
			queryStmt = new StringBuffer(" from " + clazz.getSimpleName()
					+ " p where 1!=1 ");
		queryStmt.append(searchString.toString());
		return entityManager.createQuery(queryStmt.toString())
				.setMaxResults(maxCount).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseEntity> void save(T entity) {
		entityManager.persist(entity);
		// writeEntityLog("ADD", entity, null);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseEntity> void update(T entity) {
		entityManager.merge(entity);
		// writeEntityLog("ADD", entity, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseEntity> void saveOrUpdate(T entity,Integer id) {
		if (id == null || id == 0) {
			entityManager.persist(entity);
			// writeEntityLog("ADD", entity, null);
		} else {
			entityManager.merge(entity);
			
			// writeEntityLog("UPDATE", entity, oldEntity);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseEntity> void delete(T entity) {
		entityManager.remove(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T extends BaseEntity> void deleteById(Class<T> clazz, int id) {
		//logger.info("deleting instance");
		entityManager.remove(getById(clazz, id));
		//logger.info("deleted Successfully");
		
	}



	

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz,
			Map criterias, boolean orderResult, String sortField, boolean desc,
			String groupField) {
		// Build the Query String with Search Criteria
		StringBuilder query = new StringBuilder("Select obj from ").append(
				clazz.getSimpleName()).append(" obj where 1=1");
		if (criterias != null && criterias.size() > 0) {
			Object[] keyArray = criterias.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				if (criterias.get(keyArray[i]).toString().contains("null")) {
					if (criterias.get(keyArray[i]).toString().contains("!"))
						query.append(" and obj.").append(keyArray[i])
								.append(" IS NOT NULL");
					else
						query.append(" and obj.").append(keyArray[i])
								.append(" IS NULL");

				} else {
					query.append(" and obj.").append(keyArray[i]).append("=:")
							.append("p" + i);
				}

			}
		}
		if (orderResult) {
			if (sortField != null) {
				query.append("  ORDER BY obj.").append(sortField);
				if (!desc)
					query.append(" asc");
				else
					query.append(" desc");

			}
		}
		if (groupField != null) {
			query.append("  GROUP BY obj.").append(groupField);
		}
		// Build the query Object
		int index = 0;
		if (criterias != null && criterias.size() > 0) {
			Object[] keyArray = criterias.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				if (!criterias.get(keyArray[i]).toString().contains("null")) {
					index++;
				}
			}
		}
		Query jpaQuery = entityManager.createQuery(query.toString());
		// Set the search Parameters for the query
		if (criterias != null && criterias.size() > 0) {
			Object[] keyArray = criterias.keySet().toArray();
			for (int i = 0; i < keyArray.length; i++) {
				if (criterias.get(keyArray[i]).toString().contains("!="))
					jpaQuery.setParameter(
							"p" + i,
							criterias
									.get(keyArray[i])
									.toString()
									.substring(
											criterias.get(keyArray[i])
													.toString()
													.lastIndexOf("=") + 1));
				else if (criterias.get(keyArray[i]).toString().contains("null")) {
					// Do nothing
				} else if (criterias.get(keyArray[i]).toString().contains(",")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("dateField")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("from")) {
					// Do nothing
				} else if (keyArray[i].toString().equalsIgnoreCase("to")) {
					// Do nothing
				} else
					jpaQuery.setParameter("p" + i, criterias.get(keyArray[i]));
			}

		}
		// Execute the query and return List of objects
		return jpaQuery.getResultList();
	}

	@Override
	public <T extends BaseEntity> List<T> findByCommaSeparatedIds(
			Class<T> clazz, String ids) {
		StringBuilder query = new StringBuilder("Select obj from ").append(
				clazz.getSimpleName()).append(" obj where ");
		query.append(" obj.id").append(" IN (" + ids + ")");
		return entityManager.createQuery(query.toString()).getResultList();
	}

	@SuppressWarnings("rawtypes")
	private <T extends BaseEntity> StringBuffer createQueryFromCriterias(
			Class<T> clazz, Map criterias, Boolean containsOr, int count) {
		int counter = 0;
		StringBuffer searchString = new StringBuffer("");
		if (criterias != null) {
			for (Object param : criterias.keySet()) {
				if (param.toString().startsWith("||"))
					counter++;
			}
			for (Object param : criterias.keySet()) {
				if (criterias.get(param.toString())!=null) {
					if (param.toString().startsWith("||")) {
						containsOr = true;
						if (count == 0 && counter > 1)
							searchString.append(" and ( ");
						else
							searchString.append(" or ");
						String criteriaKey = param.toString().replace("||", "");
						if (criterias.get(param.toString()) instanceof Integer
								|| criterias.get(param.toString()) instanceof Long) {
							searchString.append(" p." + criteriaKey + "="
									+ criterias.get(param.toString()));
						}
						else if (!criterias.get(param.toString()).toString()
								.contains("null")) {
							if (criterias.get(param.toString()).toString()
									.contains("!"))
								searchString.append(" p." + criteriaKey
										+ " IS NOT NULL");
							else
								searchString.append(" p." + criteriaKey
										+ " IS NULL");
						} else if (criterias.get(param.toString()).toString()
								.contains(",")) {
							StringBuilder valBuilder = new StringBuilder();
							String[] values = criterias.get(param.toString())
									.toString().trim().split(",");
							for (int i = 0; i < values.length; i++) {
								values[i] = "'" + values[i] + "',";
								valBuilder.append(values[i]);
							}
							if (valBuilder.length() > 1) {
								valBuilder = valBuilder.deleteCharAt(valBuilder
										.length() - 1);
								searchString.append(" UPPER(p." + criteriaKey
										+ ") IN (" + valBuilder.toString()
										+ ")");
							}
						} else if (criterias.get(param.toString()).toString()
								.startsWith("!=")
								|| criterias.get(param.toString()).toString()
										.startsWith("<>")) {
							searchString.append(" p."
									+ criteriaKey
									+ " <> '"
									+ criterias.get(param.toString())
											.toString().trim().substring(2)
									+ "'");

						} else if (criterias.get(param.toString()) instanceof Date) {
							if ("from".equalsIgnoreCase(param.toString())) {
								Timestamp firstDate = new Timestamp(
										((Date) criterias.get(param.toString()))
												.getTime());
								Timestamp secondDate = null;
								secondDate = new Timestamp(
										((Date) criterias.get("to")).getTime() + 86399999);
								String fieldName = (String) criterias
										.get("dateField");
								searchString.append(" UPPER(p." + fieldName
										+ ") Between '" + firstDate + "' AND '"
										+ secondDate + "'");
							}
						} else {
							if (!"dateField".equalsIgnoreCase(param.toString()))
								searchString.append(" UPPER(p." + criteriaKey
										+ ") like UPPER('"
										+ criterias.get(param.toString())
										+ "%') ");
						}
						count++;
					}
				}
			}
			if (count > 1)
				searchString.append(" )");
			for (Object param : criterias.keySet()) {
				if (param.toString().startsWith("||"))
					continue;
				else {
					if (criterias.get(param.toString()) instanceof Integer
							|| criterias.get(param.toString()) instanceof Long) {
						searchString.append(" and p." + param + "="
								+ criterias.get(param.toString()));
					}
					else if (StringUtils.isEmpty(criterias.get(param.toString()).toString())) {
						searchString.append("");
					}
					else if (criterias.get(param.toString()).toString()
							.contains("null")) {
						if (criterias.get(param.toString()).toString()
								.contains("!"))
							searchString.append(" and p." + param
									+ " IS NOT NULL");
						else
							searchString.append(" and p." + param + " IS NULL");
					} else if (criterias.get(param.toString()).toString()
							.contains(",")) {
						StringBuilder valBuilder = new StringBuilder();
						String[] values = criterias.get(param.toString())
								.toString().trim().split(",");
						for (int i = 0; i < values.length; i++) {
							values[i] = "'" + values[i] + "',";
							valBuilder.append(values[i]);
						}
						if (valBuilder.length() > 1) {
							valBuilder = valBuilder.deleteCharAt(valBuilder
									.length() - 1);
							searchString.append(" and UPPER(p." + param
									+ ") IN (" + valBuilder.toString() + ")");
						}
					} else if (criterias.get(param.toString()).toString()
							.startsWith("!=")
							|| criterias.get(param.toString()).toString()
									.startsWith("<>")) {
						searchString.append(" and p."
								+ param
								+ " <> '"
								+ criterias.get(param.toString()).toString()
										.trim().substring(2) + "'");

					} else if (criterias.get(param.toString()) instanceof Date) {
						if ("from".equalsIgnoreCase(param.toString())) {
							Timestamp firstDate = new Timestamp(
									((Date) criterias.get(param.toString()))
											.getTime());
							Timestamp secondDate = null;
							secondDate = new Timestamp(
									((Date) criterias.get("to")).getTime() + 86399999);
							String fieldName = (String) criterias
									.get("dateField");
							searchString.append(" and UPPER(p." + fieldName
									+ ") Between '" + firstDate + "' AND '"
									+ secondDate + "'");
						}
					} else {
						if (!"dateField".equalsIgnoreCase(param.toString()))
						{
							if (criterias.get(param).toString().contains("-") )
							{
								searchString.append(" and UPPER(p." + param
									+ ") like UPPER('"
									+ criterias.get(param.toString()) + "%') ");
									// + criterias.get(param.toString()) + "') ");
							}
							else{
								searchString.append(" and UPPER(p." + param
										+ ") like UPPER('"
										//+ criterias.get(param.toString()) + "%') ");
										 + criterias.get(param.toString()) + "') ");
								}
						}
					}
				}
				
			}
		}
		return searchString;
	}

	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	// Return Nepali Date in the Format 2073-10-06(Year-month-day)in nepali
			public   String getNepaliDateFromEnglishDate(int year, int month,int day){

				Map<Integer, int[]> nepaliMap = new HashMap<Integer, int[]>();
				nepaliMap.put(2000, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2001, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2002, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2003, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2004, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2005, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2006, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2007, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2008, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
				nepaliMap.put(2009, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2010, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2011, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2012, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
				nepaliMap.put(2013, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2014, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2015, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2016, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
				nepaliMap.put(2017, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2018, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2019, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2020, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2021, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2022, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
				nepaliMap.put(2023, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2024, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2025, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2026, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2027, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2028, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2029, new int[] { 0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2030, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2031, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2032, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2033, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2034, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2035, new int[] { 0, 30, 32, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
				nepaliMap.put(2036, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2037, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2038, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2039, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
				nepaliMap.put(2040, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2041, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2042, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2043, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
				nepaliMap.put(2044, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2045, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2046, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2047, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2048, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2049, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
				nepaliMap.put(2050, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2051, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2052, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2053, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
				nepaliMap.put(2054, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2055, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2056, new int[] { 0, 31, 31, 32, 31, 32, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2057, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2058, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2059, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2060, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2061, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2062, new int[] { 0, 30, 32, 31, 32, 31, 31, 29, 30, 29, 30, 29, 31 });
				nepaliMap.put(2063, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2064, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2065, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2066, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 29, 31 });
				nepaliMap.put(2067, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2068, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2069, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2070, new int[] { 0, 31, 31, 31, 32, 31, 31, 29, 30, 30, 29, 30, 30 });
				nepaliMap.put(2071, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2072, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2073, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 31 });
				nepaliMap.put(2074, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2075, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2076, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
				nepaliMap.put(2077, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31 });
				nepaliMap.put(2078, new int[] { 0, 31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2079, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30 });
				nepaliMap.put(2080, new int[] { 0, 31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30 });
				nepaliMap.put(2081, new int[] { 0, 31, 31, 32, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2082, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2083, new int[] { 0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2084, new int[] { 0, 31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2085, new int[] { 0, 31, 32, 31, 32, 30, 31, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2086, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2087, new int[] { 0, 31, 31, 32, 31, 31, 31, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2088, new int[] { 0, 30, 31, 32, 32, 30, 31, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2089, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });
				nepaliMap.put(2090, new int[] { 0, 30, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30 });


				int startingEngYear = 1944;

				int startingEngMonth = 1;

				int startingEngDay = 1;

				int dayOfWeek = Calendar.SATURDAY; 




				int startingNepYear = 2000;

				int startingNepMonth = 9;

				int startingNepDay = 17;




				int engYear = year;

				int engMonth = month;

				int engDay = day;




				Calendar currentEngDate = new GregorianCalendar();

				currentEngDate.set(engYear, engMonth, engDay);


				Calendar baseEngDate = new GregorianCalendar();

				baseEngDate.set(startingEngYear, startingEngMonth, startingEngDay);


				 
				
				long totalEngDaysCount = daysBetween(baseEngDate, currentEngDate);


				int nepYear = startingNepYear;
				int nepMonth = startingNepMonth;
				int nepDay = startingNepDay;



				while (totalEngDaysCount != 0) {


					int daysInIthMonth = nepaliMap.get(nepYear)[nepMonth];


					nepDay++; 

					if (nepDay > daysInIthMonth) {
						nepMonth++;
						nepDay = 1;
					}
					if (nepMonth > 12) {
						nepYear++;
						nepMonth = 1;
					}

					dayOfWeek++;
					if (dayOfWeek > 7) {
						dayOfWeek = 1;
					}

					totalEngDaysCount--;

				}
				//System.out.println("year in nepali ====>"+nepYear);
				//System.out.println("month in nepali ====>"+nepMonth);
				//System.out.println("day in nepali ====>"+nepDay);
				//System.out.println("day count from sunday =====>"+dayOfWeek);
				
				
				if(dayOfWeek==1){
					//System.out.println("THE DAY IS SUNDAY");
				}
				else if (dayOfWeek==2) {
					//System.out.println("THE DAY IS MONDAY");

				}
				else if (dayOfWeek==3) {
					//System.out.println("THE DAY IS TUESDAY");

				}

				else if (dayOfWeek==4) {
					//System.out.println("THE DAY IS WEDNESDAY");

				}

				else if (dayOfWeek==5) {
					//System.out.println("THE DAY IS THURSDAY");

				}

				else if (dayOfWeek==6) {
					//System.out.println("THE DAY IS FRIDAY");

				}

				else if (dayOfWeek==7) {
					//System.out.println("THE DAY IS SATURDAY");

				}
				else {
					return "";
				}
				return nepYear+"-"+leftPad(nepMonth,2)+"-"+leftPad(nepDay,2);

			
				
			}

			public  long daysBetween(Calendar startDate, Calendar endDate) {
				Calendar date = (Calendar) startDate.clone();
				long daysBetween = 0;
				while (date.before(endDate)) {
					date.add(Calendar.DAY_OF_MONTH, 1);
					daysBetween++;
				}
				return daysBetween;
			}
			
					public  String leftPad(int n, int padding) {
			    return String.format("%0" + padding + "d", n);
			}
	
	
}
