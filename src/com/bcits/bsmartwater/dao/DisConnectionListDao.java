package com.bcits.bsmartwater.dao;


import java.util.List;

import com.bcits.bsmartwater.model.DisConnectionListEntity;

public interface DisConnectionListDao extends GenericDAO<DisConnectionListEntity> {

	List<DisConnectionListEntity> genDisList();

	String uniqueDisConnNo(String connection_no);

	List<DisConnectionListEntity> getListDetailsByConnNo(String connectionno);

	List<DisConnectionListEntity> uniqueDisConnEntry(String connectionno);

	List<DisConnectionListEntity> uniqueRecConnEntry(String connectionno);

}
