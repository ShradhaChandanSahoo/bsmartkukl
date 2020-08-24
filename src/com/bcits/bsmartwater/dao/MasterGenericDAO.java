package com.bcits.bsmartwater.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.model.BaseEntity;


@SuppressWarnings("rawtypes") 
public interface MasterGenericDAO {

    <T extends BaseEntity> T getById(Class<T> clazz, int id);

    <T extends BaseEntity> T getByCriteria(Class<T> clazz, Map criterias);

    <T extends BaseEntity> T getByNamedQuery(String namedQuery, Map<String, Object> map);

    <T extends BaseEntity> T getByUniqueAttribute(Class<T> clazz, String attributeName, Object value);

    <T extends BaseEntity> List<T> findAll(Class<T> clazz);

    <T extends BaseEntity> List<T> findAll(Class<T> clazz, boolean activeOnly);

    <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz, Map criterias);

    <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz, Map criterias, String orderField, boolean desc);

    <T extends BaseEntity> List<T> findByCriteria(Class<T> clazz, Map criterias, boolean orderResult, String orderField, boolean desc,
	    String groupField);

    <T extends BaseEntity> List<T> findByNamedQuery(String namedQuery, Map<String, Object> map);

    <T extends BaseEntity> List<T> findLimitedByCount(Class<T> clazz, Map criterias, Integer maxCount);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    <T extends BaseEntity> void save(T entity);

    <T extends BaseEntity> void saveOrUpdate(T entity,Integer id);

    <T extends BaseEntity> void delete(T entity);

    <T extends BaseEntity> void deleteById(Class<T> clazz, int id);

    <T extends BaseEntity> List<T> executeSimpleQuery(String query);
    
    <T extends BaseEntity> void executeSimpleUpdateQuery(String query);

    <T extends BaseEntity> List<T> findByCommaSeparatedIds(Class<T> clazz, String ids);
   
    EntityManager getEntityManager();

    <T extends BaseEntity> void update(T entity);

	List<Object[]> executeSimpleObjectQuery(String query);

	Object[] executeSingleObjectQuery(String query);

	int executeSimpleUpdateQuery1(String query);
	
	String getNepaliDateFromEnglishDate(int year,int month,int day);

}