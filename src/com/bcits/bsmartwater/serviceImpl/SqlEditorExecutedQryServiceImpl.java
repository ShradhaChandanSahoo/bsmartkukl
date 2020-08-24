package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.SqlEditorExecutedQryDAO;
import com.bcits.bsmartwater.model.SqlEditorExecutedQry;
import com.bcits.bsmartwater.service.SqlEditorExecutedQryService;

@Service
public class SqlEditorExecutedQryServiceImpl implements SqlEditorExecutedQryService {

	@Autowired
	SqlEditorExecutedQryDAO executedQryDAO;
	
	@Override
	public void save(SqlEditorExecutedQry editorExecutedQry) {
		executedQryDAO.customsave(editorExecutedQry);
	}

}
