package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.WardDetailsDAO;
import com.bcits.bsmartwater.model.WardDetailsEntity;

@Repository
public class WardDetailsDAOImpl extends GenericDAOImpl<WardDetailsEntity>
		implements WardDetailsDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<WardDetailsEntity> getAllWardRecords() {

		List<WardDetailsEntity> ward = null;
		try {
			ward = getCustomEntityManager().createNamedQuery("WardDetailsEntity.getAllWardRecords").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ward;
	}

/*	@Override
	public Long checkForWardNameAvailability(String wardName) {
		Long i = null;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager()
					.createNamedQuery(
							"WardDetailsEntity.checkForWardNameAvailability")
					.setParameter("wardName", wardName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}*/

	@Override
	public int checkForWardNoAvailability(String wardNo) {
		int i = 0;
		try {
			System.out.println(wardNo);
			i = Integer.parseInt(String.valueOf(getCustomEntityManager()
					.createNamedQuery(
							"WardDetailsEntity.checkForWardNoAvailability")
					.setParameter("wardNo", wardNo).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public List<WardDetailsEntity> getWardData() {

		List<WardDetailsEntity> ward = null;
		try {

			

			ward = getCustomEntityManager().createNamedQuery(

					"WardDetailsEntity.getWardData", WardDetailsEntity.class)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ward;
	}

	@Override
	public int uniqueWardNo(int wardNo) {

		

		List<WardDetailsEntity> list = getCustomEntityManager()

				.createNamedQuery("WardDetailsEntity.uniqueWardNo",
						WardDetailsEntity.class).setParameter("wardNo", wardNo)
				.getResultList();
		if (list == null || list.size() == 0) {
			return 0;
		} else {
			return 1;
		}
	}


	@Override
	public List<WardDetailsEntity> findByMunicipalityId(String muncipal_Id) {
		return getCustomEntityManager().createNamedQuery("WardDetailsEntity.findByMunicipalityId",WardDetailsEntity.class).setParameter("muncipal_Id", muncipal_Id).getResultList();
	}


	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String getWardNoById(int wardId) {
		return (String) getCustomEntityManager().createNamedQuery("WardDetailsEntity.getWardNoById").setParameter("wardId", wardId).getSingleResult();
	}



	@Override
	public Long checkForWardNoUpdate(int id, String wardNo) {
		Long i = null;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager()
					.createNamedQuery(
							"WardDetailsEntity.checkForWardNoUpdate").setParameter("id", id)
					.setParameter("wardNo", wardNo).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}


}
