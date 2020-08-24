package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.WardDetailsDAO;
import com.bcits.bsmartwater.model.ConsumerMasterApproval;
import com.bcits.bsmartwater.model.WardDetailsEntity;
import com.bcits.bsmartwater.service.WardDetailsService;

@Service
public class WardDetailsServiceImpl implements WardDetailsService {

	@Autowired
	WardDetailsDAO wardDetailsDAO;

	@Override
	public List<WardDetailsEntity> getAllWardRecords() {

		List<WardDetailsEntity> ward = wardDetailsDAO.getAllWardRecords();
		return ward;
	}

	/*@Override
	public Long checkForWardNameAvailability(String wardName) {
		Long l = wardDetailsDAO.checkForWardNameAvailability(wardName);
		return l;
	}*/

	@Override
	public int checkForWardNoAvailability(String wardNo) {
		int l = wardDetailsDAO.checkForWardNoAvailability(wardNo);

		return l;
	}

	@Override
	public void save(WardDetailsEntity wardDetailsEntity) {
		wardDetailsDAO.customsave(wardDetailsEntity);

	}

	@Override
	public void update(WardDetailsEntity wardDetailsEntity) {
		wardDetailsDAO.customupdate(wardDetailsEntity);

	}

	@Override
	public WardDetailsEntity find(int id) {

		return wardDetailsDAO.customfind(id);
	}

	@Override
	public List<WardDetailsEntity> getWardData() {
		List<WardDetailsEntity> ward = wardDetailsDAO.getWardData();
		return ward;
	}

	@Override
	public int uniqueWardNo(int wardNo) {

		return wardDetailsDAO.uniqueWardNo(wardNo);
	}


	@Override
	public Object findByMunicipalityId(String muncipal_Id) {
		List<WardDetailsEntity> res=wardDetailsDAO.findByMunicipalityId(muncipal_Id);
			return res;
	}


	@Override
	public String getWardNoById(String ward_no) {
		return wardDetailsDAO.getWardNoById(Integer.parseInt(ward_no)) ;
	}



	@Override
	public Long checkForWardNoUpdate(int id, String wardNo) {
		Long name=wardDetailsDAO.checkForWardNoUpdate(id,wardNo);
		return name;
	}


	

}