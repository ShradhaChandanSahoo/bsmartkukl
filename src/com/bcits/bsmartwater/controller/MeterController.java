package com.bcits.bsmartwater.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.dao.DisConnectionListDao;
import com.bcits.bsmartwater.dao.MeterChangeApproveDao;
import com.bcits.bsmartwater.dao.MeterDetailsDao;
import com.bcits.bsmartwater.dao.MeterObservationDao;
import com.bcits.bsmartwater.dao.MeterReaderDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.DisConnectionListEntity;
import com.bcits.bsmartwater.model.MeterChangeApproveEntity;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.MeterObservationEntity;
import com.bcits.bsmartwater.model.MeterReaderEntity;
import com.bcits.bsmartwater.model.ObservationEntity;
import com.bcits.bsmartwater.model.WardToMrEntity;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.MeterChangeApproveService;
import com.bcits.bsmartwater.service.MeterDetailsService;
import com.bcits.bsmartwater.service.MeterObservationService;
import com.bcits.bsmartwater.service.MeterReaderService;
import com.bcits.bsmartwater.service.ObservationMasterService;
import com.bcits.bsmartwater.service.WardToMrService;

@Controller
public class MeterController 
{
	@Autowired
	MeterReaderService meterReaderService; 

	@Autowired
	ObservationMasterService observationMasterService;

	@Autowired
	MeterDetailsService meterDetailsService;

	@Autowired
	MeterObservationService meterObservationService;

	@Autowired
	ConsumerMasterService consumerMasterService;

	@Autowired
	MeterReaderDao meterReaderDao;

	@Autowired
	MeterDetailsDao meterDetailsDao;

	@Autowired
	MeterObservationDao meterObservationDao;

	@Autowired
	ConsumerMasterDao consumerMasterDao;

	@Autowired
	MeterChangeApproveDao meterChangeApproveDao;

	@Autowired
	MeterChangeApproveService meterChangeApproveService;

	@Autowired
	DisConnectionListDao disConnectionListDao;

	@Autowired
	WardToMrService wardToMrService;
	
	@Autowired
	BillingLedgerService billingLedgerService;
	
	int createflag=0;
	int createflagBA=0;

	@RequestMapping(value="/meterObservation",method={RequestMethod.POST,RequestMethod.GET})
	public String meterObservation(@ModelAttribute("meterObservationEntity")MeterObservationEntity meterObservationEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Meter Observation");

		HttpSession session=request.getSession(false);
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		List<String> mrNos=consumerMasterService.getDistictMrNos();

		for (Iterator<String> iterator = mrNos.iterator(); iterator.hasNext();) {
			String mrcode =(String)(iterator.next());
			appIdMap1 = new HashMap<String, Object>();
			appIdMap1.put("mrcode", mrcode);
			result1.add(appIdMap1);
		}
		String mrcode=(String)session.getAttribute("mrcode");
		if(mrcode!=null ){
			model.addAttribute("mrcode",mrcode);
		}
		model.addAttribute("mrNoList", result1);

		if(createflag==1){
			model.addAttribute("msg","Observation Registered Successfully");
			createflag=0;
		}
		if(createflag==2){
			model.addAttribute("msg","Observation Deatils Updated Successfully");
			createflag=0;
		}

		return "meterMaster/meterObservation";	
	}

	@RequestMapping(value="/meterDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String meterDetails(@ModelAttribute("meterDetailsEntity")MeterDetailsEntity meterDetailsEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Meter Details");
		model.addAttribute("mtrDetailsList",meterDetailsDao.readMeterDetails());

		if(createflag==1){
			model.addAttribute("msg","Meter Details Added Successfully");
			createflag=0;
		}
		if(createflag==2){
			model.addAttribute("msg","Meter Details Updated Successfully");
			createflag=0;
		}

		return "meterMaster/meterDetails";	
	}

	@RequestMapping(value="/mrDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String mrDetails(@ModelAttribute("meterReaderEntity")MeterReaderEntity meterReaderEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Meter Reader Details");
		model.addAttribute("mtrReaderList",meterReaderService.readMrDetails());

		if(createflag==1){
			model.addAttribute("msg","Meter Reader Added Successfully");
			createflag=0;
		}
		if(createflag==2){
			model.addAttribute("msg","Meter Reader Updated Successfully");
			createflag=0;
		}

		if(createflag==3){
			model.addAttribute("msg","Meter Reader Deleted Successfully");
			createflag=0;
		}

		return "meterMaster/mrDetails";	
	}

	@RequestMapping(value="/meterChangeDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String meterChangeDetails(@ModelAttribute("meterChangeApproveEntity")MeterChangeApproveEntity meterChangeApproveEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Meter Change Details");

		if(createflag==1){
			model.addAttribute("msg","Meter Change Details Sent for Approval");
			createflag=0;
		}

		return "meterMaster/meterChangeDetails";	
	}

	@RequestMapping(value="/disConnectionList",method={RequestMethod.POST,RequestMethod.GET})
	public String disConnectionList(@ModelAttribute("disConnListGen")ConsumerMaster consumerMaster,BindingResult bingingResult,Model model,ModelMap modelMap, HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Disconnection List");
		model.addAttribute("generateDisList",disConnectionListDao.genDisList());

		HttpSession session=request.getSession(false);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		List<String> wardNos=consumerMasterService.getDistictWardNos();

		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			String wardNo =(String)(iterator.next());
			appIdMap = new HashMap<String, Object>();
			appIdMap.put("wardNo", wardNo);
			result.add(appIdMap);
		}
		String wardNo=(String)session.getAttribute("wardNo");
		if(wardNo!=null ){
			model.addAttribute("wardNo",wardNo);
		}
		model.addAttribute("wardNoList", result);

		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		List<String> connCategory=consumerMasterService.getDistictConnCategory();

		for (Iterator<String> iterator = connCategory.iterator(); iterator.hasNext();) {
			String connCat =(String)(iterator.next());
			appIdMap1 = new HashMap<String, Object>();
			appIdMap1.put("connCat", connCat);
			result1.add(appIdMap1);
		}
		String connCat=(String)session.getAttribute("connCat");
		if(connCat!=null ){
			model.addAttribute("connCat",connCat);
		}
		model.addAttribute("connCatList", result1);

		List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap2 = null;
		List<Double> pSize=consumerMasterService.getDistictpipeSize();

		for (Iterator<Double> iterator = pSize.iterator(); iterator.hasNext();) {
			Double pipeSize =(iterator.next());
			appIdMap2 = new HashMap<String, Object>();
			appIdMap2.put("pipeSize", pipeSize);
			result2.add(appIdMap2);
		}
		String pipeSize=(String)session.getAttribute("pipeSize");
		if(pipeSize!=null ){
			model.addAttribute("pipeSize",pipeSize);
		}
		model.addAttribute("pipeSizeList", result2);

		return "meterMaster/disConnectionList";	
	}

	@RequestMapping(value="/disConnectionEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String disConnectionEntry(@ModelAttribute("disConnListEntry")DisConnectionListEntity disConnectionListEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Disconnection Entry");

		return "meterMaster/disConnectionEntry";	
	}

	@RequestMapping(value="/reConnection",method={RequestMethod.POST,RequestMethod.GET})
	public String reConnection(@ModelAttribute("reConnEntry")DisConnectionListEntity disConnectionListEntity,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Reconnection");
		return "meterMaster/reConnection";	
	}

	@RequestMapping(value="/routePlanGeneration",method={RequestMethod.POST,RequestMethod.GET})
	public String routePlanGeneration(@ModelAttribute("mtrRoutePlanGen")ConsumerMaster consumerMaster,BindingResult bingingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Metering");
		model.addAttribute("childHead1", "Route Plan Generation");

		if(createflag==2){
			model.addAttribute("msg","Connection Deatils Updated Successfully");
			createflag=0;
		}

		HttpSession session=request.getSession(false);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		List<String> areaNos=consumerMasterService.getDistictAreaNos();

		for (Iterator<String> iterator = areaNos.iterator(); iterator.hasNext();) {
			String areaNo =(String)(iterator.next());
			appIdMap = new HashMap<String, Object>();
			appIdMap.put("areaNo", areaNo);
			appIdMap.put("areaNo", areaNo);
			result.add(appIdMap);
		}
		String areaNumber=(String)session.getAttribute("areaNo");
		if(areaNumber!=null ){
			model.addAttribute("areaNo",areaNumber);
		}
		model.addAttribute("areaNoList", result);

		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		List<String> mrNos=consumerMasterService.getDistictMrNos();

		for (Iterator<String> iterator = mrNos.iterator(); iterator.hasNext();) {
			String mrCode =(String)(iterator.next());
			appIdMap1 = new HashMap<String, Object>();
			appIdMap1.put("mrCode", mrCode);
			result1.add(appIdMap1);
		}
		String mrCode=(String)session.getAttribute("mrCode");
		if(mrCode!=null ){
			model.addAttribute("mrCode",mrCode);
		}
		model.addAttribute("mrNoList", result1);

		List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap2 = null;
		List<Integer> rDays=consumerMasterService.getDistictReadingDays();

		for (Iterator<Integer> iterator = rDays.iterator(); iterator.hasNext();) {
			Integer readingDay =(iterator.next());
			appIdMap2 = new HashMap<String, Object>();
			appIdMap2.put("readingDay", readingDay);
			result2.add(appIdMap2);
		}
		String readingDay=(String)session.getAttribute("readingDay");
		if(mrCode!=null ){
			model.addAttribute("readingDay",readingDay);
		}
		model.addAttribute("readingDayList", result2);

		List<Map<String, Object>> result3 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap3 = null;
		List<String> wardNos=consumerMasterService.getDistictWardNos();

		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			String wardNo =(String)(iterator.next());
			appIdMap3 = new HashMap<String, Object>();
			appIdMap3.put("wardNo", wardNo);
			result3.add(appIdMap3);
		}
		String wardNo=(String)session.getAttribute("wardNo");
		if(wardNo!=null ){
			model.addAttribute("wardNo",wardNo);
		}
		model.addAttribute("wardNoList", result3);

		return "meterMaster/routePlanGeneration";	
	}

	@RequestMapping(value="/addMeterReaderDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String addMeterReaderDetails(@ModelAttribute("meterReaderEntity")MeterReaderEntity meterReaderEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{
		String mob=request.getParameter("mobileno");
		if(mob==null || "null".equalsIgnoreCase(mob) || "".equalsIgnoreCase(mob)){
			
		}else{
			meterReaderEntity.setMobileno(Long.parseLong(mob));/*(Long.parseLong(mob))*/;
		}
		System.out.println("MOBILE :"+meterReaderEntity.getMobileno());

		model.addAttribute("mtrReaderList",meterReaderService.readMrDetails());
		meterReaderDao.customsave(meterReaderEntity);
		createflag=1;
		return "redirect:/mrDetails";
	}

	@RequestMapping(value="/editMeterReaderDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String editMeterReaderDetails(@ModelAttribute("meterReaderEntity")MeterReaderEntity meterReaderEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{
		meterReaderService.readMrDetails();
		createflag=2;
		meterReaderDao.customupdate(meterReaderEntity);
		return "redirect:/mrDetails";
	}
	@RequestMapping(value="/changeMrReaderName",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String changeMrReaderName(HttpServletRequest request)
	{
		try{
		int id=Integer.parseInt(request.getParameter("idVal1"));
		String mrCode=request.getParameter("mrCode1");
		String oldMrRrName=request.getParameter("oldMrName");
		String newMrRrName=request.getParameter("newMrName");
		String changeDate=request.getParameter("chDatenep");
		String mob=request.getParameter("mob");
		String add=request.getParameter("addrNew");
		MeterReaderEntity mre=meterReaderDao.getMeterReaderEntityById(id,mrCode);
		
		mre.setMrName(newMrRrName);
		mre.setOldMrName(oldMrRrName);
		mre.setMrNameChangeDate(changeDate);
		mre.setMobileno(Long.parseLong(mob));
		mre.setAddress(add);
		meterReaderDao.customupdate(mre);
		
		 return "Meter Reader name changed successfully";
		}catch(Exception e)
		{
			e.printStackTrace();
			return "Meter Reader name Fail To change";
		}
		
	}
	
	
	@RequestMapping(value="/uniqueMtrReader/{mrCode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueMtrReader(@PathVariable String mrCode,HttpServletRequest request)
	{
		String str=meterReaderDao.uniqueMtrReader(mrCode);
		return str;
	}
	/*@RequestMapping(value="/deleteMeterReaderDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String  deleteMeterReaderDetails(@ModelAttribute("meterReaderEntity")MeterReaderEntity meterReaderEntity,BindingResult bingingResult,@PathVariable int idVal,ModelMap model,HttpServletRequest request)
	{
		meterReaderService.readMrDetails();
		createflag=3;
		return meterReaderDao.meterReaderDelete(idVal);
	}*/

	@RequestMapping(value="/uniqueMeterDetails/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueMeterDetails(@PathVariable String connectionno,HttpServletRequest request)
	{
		System.out.println("====uniqueMeterDetails====>"+connectionno);
		meterDetailsDao.readMeterDetails();
		String str=meterDetailsDao.uniqueMeterDetails(connectionno);
		return str;
	}

	@RequestMapping(value="/checkConnMeterDetails/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkConnMeterDetails(@PathVariable String connectionno,HttpServletRequest request)
	{
		System.out.println("====uniqueMeterDetails====>"+connectionno);
		meterDetailsDao.readMeterDetails();
		String str=meterDetailsDao.checkConnMeterDetails(connectionno);
		return str;
	}

	@RequestMapping(value="/viewConnMeterDetails/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object viewConnMeterDetails(@PathVariable String connectionno,HttpServletRequest request)
	{
		List<List<?>> result=new ArrayList<>();
		List<?> viewconnectionData=meterDetailsService.viewConnMeterDetails(connectionno);
		result.add(viewconnectionData);
		
		BillingLedgerEntity ble=billingLedgerService.getLastMonthRecord(connectionno);
		Double fr=ble.getPresent_reading();
		Double pr=ble.getPrevious_reading();
		System.out.println(fr+"=========="+pr);
		
		Map<String,String> map=new HashMap<>();
		map.put("prevReading", pr+"");
		map.put("presentReading", fr+"");
		List<Map<String,String>> l=new ArrayList<>();
		l.add(map);
		result.add(l);
		return result;
	}

	@RequestMapping(value="/uniqueMeterDetailsForEdit/{connectionno}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueMeterDetailsForEdit(@PathVariable String connectionno,@PathVariable int id,HttpServletRequest request)
	{
		meterDetailsDao.readMeterDetails();
		String str=meterDetailsDao.uniqueMeterDetailsForEdit(connectionno,id);
		return str;
	}

	@RequestMapping(value="/addMeterDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String addMeterDetails(@ModelAttribute("meterDetailsEntity")MeterDetailsEntity meterDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		model.addAttribute("mtrDetailsList",meterDetailsDao.readMeterDetails());
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		String ins_date_eng=request.getParameter("ins_date_eng");
		String metcon_date_eng=request.getParameter("metcon_date_eng");
		String cal_date_eng=request.getParameter("cal_date_eng");
		String ent_date_eng=request.getParameter("ent_date_eng");
		
		if(ins_date_eng!=null&&!ins_date_eng.isEmpty()){
			meterDetailsEntity.setIns_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(ins_date_eng));
		}

		if(metcon_date_eng!=null&&!metcon_date_eng.isEmpty()){
			meterDetailsEntity.setMetcon_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(metcon_date_eng));
		}

		if(cal_date_eng!=null&&!cal_date_eng.isEmpty()){
			meterDetailsEntity.setCal_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(cal_date_eng));
		}

		if(ent_date_eng!=null&&!ent_date_eng.isEmpty()){
			meterDetailsEntity.setEnt_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(ent_date_eng));
		}
		
		meterDetailsEntity.setAdded_by(userName);
		meterDetailsEntity.setAdded_date(new Date());
		meterDetailsEntity.setStatus(0);
		meterDetailsService.save(meterDetailsEntity);
		createflag=1;
		return "redirect:/meterDetails";
	}

	@RequestMapping(value = "/editMeterDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String editMeterDetails(@ModelAttribute("meterDetailsEntity")MeterDetailsEntity meterDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String ins_date_eng=request.getParameter("ins_date_eng");
		String metcon_date_eng=request.getParameter("metcon_date_eng");
		String cal_date_eng=request.getParameter("cal_date_eng");
		String ent_date_eng=request.getParameter("ent_date_eng");

		if(ins_date_eng!=null&&!ins_date_eng.isEmpty()){
			meterDetailsEntity.setIns_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(ins_date_eng));
		}

		if(metcon_date_eng!=null&&!metcon_date_eng.isEmpty()){
			meterDetailsEntity.setMetcon_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(metcon_date_eng));
		}

		if(cal_date_eng!=null&&!cal_date_eng.isEmpty()){
			meterDetailsEntity.setCal_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(cal_date_eng));
		}

		if(ent_date_eng!=null&&!ent_date_eng.isEmpty()){
			meterDetailsEntity.setEnt_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(ent_date_eng));
		}
		meterDetailsEntity.setAdded_by(userName);
		meterDetailsEntity.setAdded_date(new Date());
		meterDetailsDao.customupdate(meterDetailsEntity);
		createflag=2;
		return "redirect:/meterDetails";

	}

	@RequestMapping(value="/addNewObservation",method={RequestMethod.POST,RequestMethod.GET})
	public String addNewObservation(@ModelAttribute("meterObservationEntity")MeterObservationEntity meterObservationEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		String obs_date_eng=request.getParameter("obs_date_eng");
		String entereddate_eng=request.getParameter("entereddate_eng");

		if(obs_date_eng!=null&&!obs_date_eng.isEmpty()){
			meterObservationEntity.setObs_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(obs_date_eng));
		}

		if(entereddate_eng!=null&&!entereddate_eng.isEmpty()){
			meterObservationEntity.setEntereddate_eng(new SimpleDateFormat("yyyy/MM/dd").parse(entereddate_eng));
		}

		meterObservationService.save(meterObservationEntity);
		createflag=1;
		return "redirect:/meterObservation";
	}

	@RequestMapping(value="/uniqueObsNo/{observationno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueObsNo(@PathVariable String observationno,HttpServletRequest request)
	{
		String str=meterObservationDao.uniqueObsNo(observationno);
		return str;
	}

	@RequestMapping(value="/searchConnNo/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object searchConnNo(@ModelAttribute("meterObservationEntity")MeterObservationEntity meterObservationEntity,BindingResult bingingResult,ModelMap model,@PathVariable String connectionno,HttpServletRequest request)
	{

		return meterObservationDao.searchConnNo(connectionno);

	}

	@RequestMapping(value="/viewRtPlanGen/{mrCode}/{readingDay}/{wardNo}/{areaNumber}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object viewRtPlanGen(@PathVariable String mrCode,@PathVariable int readingDay,@PathVariable int wardNo,@PathVariable String areaNumber,@ModelAttribute("mtrRoutePlanGen")ConsumerMaster consumerMaster,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{

		return consumerMasterDao.viewRtPlanGen(mrCode,readingDay,wardNo,areaNumber);

	}

	@RequestMapping(value="/uniqueObsSearchNoForEdit/{observationno}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueObsSearchNoForEdit(@PathVariable String observationno,@PathVariable int id,HttpServletRequest request)
	{
		//meterDetailsDao.readMeterDetails();
		String str=meterObservationDao.uniqueObsSearchNoForEdit(observationno,id);
		return str;
	}

	@RequestMapping(value = "/editObsSearch", method = {RequestMethod.GET, RequestMethod.POST})
	public String editObsSearch(@ModelAttribute("meterObservationEntity")MeterObservationEntity meterObservationEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{
		try 
		{
			String obs_date_eng=request.getParameter("obs_date_eng");
			String entereddate_eng=request.getParameter("entereddate_eng");
			System.out.println(obs_date_eng);
			System.out.println(entereddate_eng);

			if(obs_date_eng!=null&&!obs_date_eng.isEmpty()){
				meterObservationEntity.setObs_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(obs_date_eng));
			}

			if(entereddate_eng!=null&&!entereddate_eng.isEmpty()){
				meterObservationEntity.setEntereddate_eng(new SimpleDateFormat("yyyy/MM/dd").parse(entereddate_eng));
			}
			meterObservationDao.customupdate(meterObservationEntity);
			createflag=2;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "redirect:/meterObservation";
	}

	@RequestMapping(value = "/updateRpgConns", method = {RequestMethod.GET, RequestMethod.POST})
	public  String updateRpgConns(@ModelAttribute("mtrRoutePlanGen")ConsumerMaster consumerMaster,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{

		System.out.println("updateRpgConns=====>");
		try 
		{
			String length=request.getParameter("lengthVal");

			for(int i=0;i<Integer.parseInt(length);i++)
			{
				String id=request.getParameter("id"+i);
				String conn=request.getParameter("connection_no"+i);
				String mtr_ser=request.getParameter("mtr_ser_no"+i);
				String rday=request.getParameter("reading_day"+i);
				String ward=request.getParameter("ward_no"+i);
				String seqno=request.getParameter("seq_no"+i);
				String seqabbr=request.getParameter("seq_abbr"+i);
				String areano=request.getParameter("area_no"+i);

				ConsumerMaster con=	consumerMasterDao.find(Integer.parseInt(id));
				con.setConnection_no(conn);
				con.setMtr_ser_no(mtr_ser);
				con.setReading_day(Integer.parseInt(rday));
				con.setWard_no(ward);
				con.setSeq_no(seqno+""+seqabbr);
				con.setArea_no(areano);

				consumerMasterDao.customupdate(con);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		createflag=2;	
		return "redirect:/routePlanGeneration";

	}

	@RequestMapping(value="/metering/connectionDetails/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<ConsumerMaster> connectionDetails(@PathVariable String connectionNo,HttpServletRequest request)
	{
		List<ConsumerMaster> str=consumerMasterDao.findByConnNo(connectionNo);
		return str;
	}

	@SuppressWarnings("null")
	@RequestMapping(value="/addNewMtrChangeDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String addNewMtrChangeDetails(@ModelAttribute("meterChangeApproveEntity")MeterChangeApproveEntity meterChangeApproveEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String monthyearnep=(String) session.getAttribute("monthyearNepLMY");
		
		String newmtrconndate_Eng=request.getParameter("new_metcon_date_eng");
		String newinstndate_Eng=request.getParameter("new_ins_date_eng");
		String newcaldate_Eng=request.getParameter("new_cal_date_eng");
		String releasedate_Eng=request.getParameter("release_date_eng");
		String givendate_Eng=request.getParameter("given_date_eng");
		String entereddate_Eng=request.getParameter("new_ent_date_eng");

		//System.out.println("====1==="+entereddate_Eng);

		if(newmtrconndate_Eng!=null&&!newmtrconndate_Eng.isEmpty()){
			meterChangeApproveEntity.setNew_metcon_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(newmtrconndate_Eng));
		}

		if(newinstndate_Eng!=null&&!newinstndate_Eng.isEmpty()){
			meterChangeApproveEntity.setNew_ins_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(newinstndate_Eng));
		}

		if(newcaldate_Eng!=null&&!newcaldate_Eng.isEmpty()){
			meterChangeApproveEntity.setNew_cal_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(newcaldate_Eng));
		}

		if(releasedate_Eng!=null&&!releasedate_Eng.isEmpty()){
			meterChangeApproveEntity.setRelease_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(releasedate_Eng));
		}

		if(givendate_Eng!=null&&!givendate_Eng.isEmpty()){
			meterChangeApproveEntity.setGiven_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(givendate_Eng));
		}

		if(entereddate_Eng!=null&&!entereddate_Eng.isEmpty()){
			meterChangeApproveEntity.setNew_ent_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(entereddate_Eng));
		}
		meterChangeApproveEntity.setMtr_change_app_status(0);
		meterChangeApproveEntity.setSubmitted_by(userName);
		meterChangeApproveEntity.setSubmitted_date(new Date());
		meterChangeApproveEntity.setMonthyearnep(monthyearnep);
		meterChangeApproveDao.customsave(meterChangeApproveEntity);

		createflag=1;
		return "redirect:/meterChangeDetails";
	}

	@RequestMapping(value="/metering/approveMeterDetails/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object approveMeterDetails(@PathVariable String connectionNo,HttpServletRequest request)
	{
		List<List<?>> result=new ArrayList<>();
		List<?> meterData= meterChangeApproveService.findByConnectionNo(connectionNo);
		result.add(meterData);

		return result;
	}

	@RequestMapping(value="/genDisConnList/{wardNo}/{connCat}/{pipeSize}/{entereddate_Nep}/{entereddate_Eng}/{cutoffAmtfrm}/{cutoffAmtto}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object genDisConnList(@PathVariable String wardNo,@PathVariable String connCat,@PathVariable Double pipeSize,@PathVariable String entereddate_Nep,@PathVariable String entereddate_Eng,@PathVariable Double cutoffAmtfrm,@PathVariable Double cutoffAmtto,@ModelAttribute("disConnListEntry")DisConnectionListEntity disConnectionListEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws Exception
	{
		//System.out.println("=====genDisConnList==>>wardNo==="+wardNo+"==connCat=="+connCat+"===pipeSize==="+pipeSize+"==entereddate_Nep=="+entereddate_Nep+"===entereddate_Eng==="+entereddate_Eng+"===cutoffAmtfrm=="+cutoffAmtfrm+"===cutoffAmtto=="+cutoffAmtto);
		String enDate=new SimpleDateFormat("yyyy/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(entereddate_Eng));
		String str=null;
		List<Object[]> list=consumerMasterDao.genDisConnList(wardNo,connCat,pipeSize,cutoffAmtfrm,cutoffAmtto);
		DisConnectionListEntity entity=null;
		List<DisConnectionListEntity> disEntity= new ArrayList<DisConnectionListEntity>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) 
		{
			Object[] objects = (Object[]) iterator.next();
			ConsumerMaster consumerMaster=(ConsumerMaster) objects[0];
			BillingLedgerEntity billingLedgerEntityList=(BillingLedgerEntity) objects[1];
			str=disConnectionListDao.uniqueDisConnNo(consumerMaster.getConnection_no());
			if(str.equals("AlreadyExist"))
			{
				return new StringBuilder(str);
			}
			else
			{
				entity=new DisConnectionListEntity();
				entity.setConnectionno(consumerMaster.getConnection_no());
				entity.setMonthyear(billingLedgerEntityList.getMonthyear());
				entity.setStatus(0);
				entity.setArrears(billingLedgerEntityList.getArrears());
				entity.setList_date(new Date());
				entity.setEnt_date_nep(entereddate_Nep);
				entity.setEnt_date_eng(new SimpleDateFormat("yyyy/MM/dd").parse(enDate));
				entity.setName_nep(consumerMaster.getName_nep());
				entity.setName_eng(consumerMaster.getName_eng());
				entity.setAddress_eng(consumerMaster.getAddress_eng());
				entity.setAddress_nep(consumerMaster.getAddress_nep());
				entity.setPipe_size(consumerMaster.getPipe_size());
				entity.setCon_category(consumerMaster.getCon_category());
				entity.setWard_no(consumerMaster.getWard_no());
				entity.setArea_no(consumerMaster.getArea_no());

				disEntity.add(entity);
			}
		}

		disConnectionListDao.custombatchSave(disEntity);
		List<DisConnectionListEntity> result=disConnectionListDao.genDisList();
		System.out.println("======"+result+"=="+result.size());
		return result;
	}

	@RequestMapping(value="/getListDetailsByConnNo/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<DisConnectionListEntity> getListDetailsByConnNo(@PathVariable String connectionno,HttpServletRequest request)
	{
		List<DisConnectionListEntity> str=disConnectionListDao.getListDetailsByConnNo(connectionno);
		return str;
	}

	@RequestMapping(value="/disConnEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String disConnEntry(@ModelAttribute("disConnListEntry")DisConnectionListEntity disConnectionListEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{
		DisConnectionListEntity disConnectionListEntity1=disConnectionListDao.customfind(disConnectionListEntity.getListno());
		disConnectionListEntity.setMonthyear(disConnectionListEntity1.getMonthyear());
		disConnectionListEntity.setArrears(disConnectionListEntity1.getArrears());
		disConnectionListEntity.setList_date(disConnectionListEntity1.getList_date());
		disConnectionListEntity.setEnt_date_eng(disConnectionListEntity1.getEnt_date_eng());
		disConnectionListEntity.setEnt_date_nep(disConnectionListEntity1.getEnt_date_nep());
		disConnectionListEntity.setName_eng(disConnectionListEntity1.getName_eng());
		disConnectionListEntity.setName_nep(disConnectionListEntity1.getName_nep());
		disConnectionListEntity.setAddress_eng(disConnectionListEntity1.getAddress_eng());
		disConnectionListEntity.setAddress_nep(disConnectionListEntity1.getAddress_nep());
		disConnectionListEntity.setPipe_size(disConnectionListEntity1.getPipe_size());
		disConnectionListEntity.setCon_category(disConnectionListEntity1.getCon_category());
		disConnectionListEntity.setWard_no(disConnectionListEntity1.getWard_no());
		disConnectionListEntity.setArea_no(disConnectionListEntity1.getArea_no());

		disConnectionListDao.customupdate(disConnectionListEntity);
		return "redirect:/disConnectionEntry";
	}

	@RequestMapping(value="/uniqueDisConnEntry/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<DisConnectionListEntity> uniqueDisConnEntry(@PathVariable String connectionno,HttpServletRequest request)
	{
		List<DisConnectionListEntity> str=disConnectionListDao.uniqueDisConnEntry(connectionno);
		return str;
	}

	@RequestMapping(value="/reConnEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String reConnEntry(@ModelAttribute("disConnListEntry")DisConnectionListEntity disConnectionListEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{
		DisConnectionListEntity disConnectionListEntity1=disConnectionListDao.customfind(disConnectionListEntity.getListno());
		disConnectionListEntity.setMonthyear(disConnectionListEntity1.getMonthyear());
		disConnectionListEntity.setArrears(disConnectionListEntity1.getArrears());
		disConnectionListEntity.setList_date(disConnectionListEntity1.getList_date());
		disConnectionListEntity.setEnt_date_eng(disConnectionListEntity1.getEnt_date_eng());
		disConnectionListEntity.setEnt_date_nep(disConnectionListEntity1.getEnt_date_nep());
		disConnectionListEntity.setName_eng(disConnectionListEntity1.getName_eng());
		disConnectionListEntity.setName_nep(disConnectionListEntity1.getName_nep());
		disConnectionListEntity.setAddress_eng(disConnectionListEntity1.getAddress_eng());
		disConnectionListEntity.setAddress_nep(disConnectionListEntity1.getAddress_nep());
		disConnectionListEntity.setPipe_size(disConnectionListEntity1.getPipe_size());
		disConnectionListEntity.setCon_category(disConnectionListEntity1.getCon_category());
		disConnectionListEntity.setWard_no(disConnectionListEntity1.getWard_no());
		disConnectionListEntity.setArea_no(disConnectionListEntity1.getArea_no());
		disConnectionListEntity.setDis_date_nep(disConnectionListEntity1.getDis_date_nep());
		disConnectionListEntity.setDis_date_eng(disConnectionListEntity1.getDis_date_eng());
		disConnectionListEntity.setDis_fr(disConnectionListEntity1.getDis_fr());

		disConnectionListEntity.setStatus(2);
		disConnectionListDao.customupdate(disConnectionListEntity);
		return "redirect:/reConnection";
	}

	@RequestMapping(value="/uniqueRecConnEntry/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<DisConnectionListEntity> uniqueRecConnEntry(@PathVariable String connectionno,HttpServletRequest request)
	{
		List<DisConnectionListEntity> str=disConnectionListDao.uniqueRecConnEntry(connectionno);
		return str;
	}



	@RequestMapping(value="/observationDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String observationDetails(Model model,HttpServletRequest request)	
	{
		List<ObservationEntity> observe=observationMasterService.getAllObservationRecords();
		model.addAttribute("observe",observe);
		model.addAttribute("observationDetails",new ObservationEntity());
		model.addAttribute("mainHead","Metering");
		model.addAttribute("childHead1", "Observation Master");

		return "meterMaster/observationMaster";

	}
	
	@RequestMapping(value="/changeObservationStatus/{id}/{status}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String changeObservationStatus(@PathVariable int id,@PathVariable String status,HttpServletRequest request){
		try{
		
			ObservationEntity observationEntity =observationMasterService.findById(id);
			
			
			if("Active".equalsIgnoreCase(status)){
				observationEntity.setStatus("Active");
				observationMasterService.update(observationEntity);
				return "User Activated Successfully";
				
			}else{
				observationEntity.setStatus("Inactive");
				observationMasterService.update(observationEntity);
				return "User DeActivated Successfully";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "Something went wring Please try again..";
		}
	}


	@RequestMapping(value = "/addObservationDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String addObservationDetails(@ModelAttribute("observationDetails") ObservationEntity observationEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		HttpSession session=request.getSession(false);
		try {

			System.out.println("Name ===> "+observationEntity.getObservationName());
			Long flag = observationMasterService.checkForObservationNameAvailability(observationEntity.getObservationName());
			if(flag > 0){
				session.setAttribute("msg","This Observation Name Already Exists!!! Please Try Other.");
			}else{
				observationMasterService.save(observationEntity);
				session.setAttribute("msg","Observation Details Submitted Successfully.");

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/observationDetails";
	}


	@RequestMapping(value="getObserevationDataForEditing/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getObserevationDataForEditing(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) 
	{
		List<ObservationEntity> observeData = observationMasterService.getObserevationDataForEditing(id);
		return observeData;
	}

	@RequestMapping(value = "/updateObservationDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateObservationDetails(@ModelAttribute("observationDetails") ObservationEntity observationEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		HttpSession session=request.getSession(false);
	

			try {
				Long flag = observationMasterService
						.checkForObservationNameAvailability(observationEntity
								.getObservationName());
				if (flag > 0) {
					session.setAttribute("msg",
							"This Observation Name Alreday Exists!!! Please Try Other.");
				}
				else {
					observationMasterService.update(observationEntity);
					session.setAttribute("msg",
							"Observation Details Modified Successfully.");
				}
				
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/observationDetails";

	}
	
	
	@RequestMapping(value="/getMeterApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getMeterApproveList(){
		return meterChangeApproveService.getMeterApproveList();
	}
	
	@RequestMapping(value="/uniqueMeterNoChk/{meterNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueMeterNoChk(@PathVariable String meterNo,HttpServletRequest request)
	{
		System.out.println("====uniqueMeterNoDetails====>"+meterNo);
		
		String str=meterDetailsDao.uniqueMeterNoChk(meterNo);
		return str;
	}
	
	@RequestMapping(value="/wardToMr")
	public String wardToMr(@ModelAttribute("wardToMrEntity")WardToMrEntity wardToMrEntity, Model model,HttpServletRequest request){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		List<Map<String, Object>> readingResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> readingMap = null;
		
		try{
		
		List<String> wardNos=consumerMasterService.getDistictWardNos();
		List<Integer> readingDayList=consumerMasterService.getDistictReadingDays();

		model.addAttribute("billApproveEntity",new BillApproveEntity());
		
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		
		for (Iterator<Integer> iterator = readingDayList.iterator(); iterator.hasNext();) {
			Integer readingDay =(Integer)(iterator.next());
			 readingMap = new HashMap<String, Object>();
			 readingMap.put("readingDay", readingDay);
			 readingResult.add(readingMap);
		}
		List<?> meterReaderList=meterReaderService.readMrDetails();
		model.addAttribute("wardNoList", result);
		model.addAttribute("readingDayList", readingResult);
		model.addAttribute("meterReaderList", meterReaderList);
		model.addAttribute("mtrDetailsList",wardToMrService.getAllMeterReader());

		if(createflag==1){
			model.addAttribute("msg","Meter Reader Details Added Successfully");
			createflag=0;
		}
		if(createflag==2){
			model.addAttribute("msg","Meter Reader Details Updated Successfully");
			createflag=0;
		}
		
		
		return "meterMaster/wardToMr";
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login";	
		}
	}
	
	@RequestMapping(value="/addWardToMrEntity",method={RequestMethod.POST,RequestMethod.GET})
	public String addWardToMrEntity(@ModelAttribute("wardToMrEntity")WardToMrEntity wardToMrEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		model.addAttribute("mtrDetailsList",meterDetailsDao.readMeterDetails());
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		
		wardToMrEntity.setAdded_by(userName);
		wardToMrEntity.setAdded_date(new Date());
		wardToMrService.save(wardToMrEntity);
		createflag=1;
		return "redirect:/wardToMr";
	}
	
	@RequestMapping(value="/uniqueWardToMrDetails/{ward_no}/{reading_day}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueWardToMrDetails(@PathVariable String ward_no,@PathVariable int reading_day,HttpServletRequest request)
	{
		String str=wardToMrService.uniqueWardToMrDetails(ward_no,reading_day);
		return str;
	}
	
	@RequestMapping(value="/updateWardToMrEntity",method={RequestMethod.POST,RequestMethod.GET})
	public String updateWardToMrEntity(@ModelAttribute("wardToMrEntity")WardToMrEntity wardToMrEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request) throws ParseException
	{
		model.addAttribute("mtrDetailsList",meterDetailsDao.readMeterDetails());
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String added_date=request.getParameter("added_date");
		//System.out.println("Added Date----"+added_date);
		SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		wardToMrEntity.setAdded_date(formatter5.parse(added_date));
		wardToMrEntity.setUpdated_by(userName);
		wardToMrEntity.setUpdated_date(new Date());
		wardToMrService.update(wardToMrEntity);
		createflag=2;
		return "redirect:/wardToMr";
	}
	
	
}