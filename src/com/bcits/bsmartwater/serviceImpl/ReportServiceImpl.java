package com.bcits.bsmartwater.serviceImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.ReportDAO;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;
import com.bcits.bsmartwater.service.ReportService;
import com.bcits.bsmartwater.utils.DataSourceRequest;
import com.bcits.bsmartwater.utils.DataSourceResult;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO;

	@Autowired
	private MasterGenericDAO masterGenericDAO;

	@Override
	public DataSourceResult dailyCashCounterRead(DataSourceRequest request) {
		return reportDAO.dailyCashCounterRead(request);
	}

	@Override
	public List<?> categoryWiseSalesReportReadData(Date fromdate, Date todate, String mnthyrnep) {
		List<?> list = reportDAO.categoryWiseSalesReportReadData(fromdate, todate, mnthyrnep);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("con_type", (String) obj[0]);
			data.put("con_category", (String) obj[1]);
			data.put("water_charges", Double.valueOf(df.format(((Double) obj[2]))));
			data.put("sw_charges", Double.valueOf(df.format(((Double) obj[3]))));
			data.put("mtr_rent", Double.valueOf(df.format(((Double) obj[4]))));
			data.put("total_bill", Double.valueOf(df.format(((obj[2] == null ? 0 : (double) obj[2])
					+ (obj[3] == null ? 0 : (double) obj[3]) + (obj[4] == null ? 0 : (double) obj[4])+(obj[7] == null ? 0 : (double) obj[7])))));
			data.put("penalty", Double.valueOf(df.format(((Double) obj[7]))));
			data.put("arrears", Double.valueOf(df.format((obj[5] == null ? 0 : (double) obj[5]))));
			data.put("net_amount", Double.valueOf(df.format(((Double) obj[6]))));
			result.add(data);
		}

		return result;
	}

	@Override
	public List<?> wardWiseSalesReportReadData(String fromdate, String todate) {
		List<?> list = reportDAO.wardWiseSalesReportReadData(fromdate, todate);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);
			data.put("slNo", slNo++);
			data.put("wardno", (String) obj[0]);
			data.put("con_category", (String) obj[1]);
			data.put("water_charges", Double.valueOf(df.format(((Double) obj[2]))));
			data.put("sw_charges", Double.valueOf(df.format(((Double) obj[3]))));
			data.put("mtr_rent", Double.valueOf(df.format(((Double) obj[4]))));
			data.put("totalbill", Double.valueOf(df.format((obj[2] == null ? 0 : (double) obj[2])
					+ (obj[3] == null ? 0 : (double) obj[3]) + (obj[4] == null ? 0 : (double) obj[4])+(obj[8] == null ? 0 : (double) obj[8]))));
			data.put("arrears", Double.valueOf(df.format(((Double) obj[5]))));
			data.put("net_amount", Double.valueOf(df.format(((Double) obj[6]))));
			data.put("pipesize", obj[7]);
			data.put("penalty", Double.valueOf(df.format(((Double) obj[8]))));
			// data.put("reading_day", obj[8]);
			result.add(data);
		}

		return result;
	}

	@Override
	public List<?> monthlyObservationReportReadData(String yearnep, String month, Model model) {
		List<?> list = reportDAO.monthlyObservationReportReadData(yearnep, month, model);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		// DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("wordNo", obj[0]);
			if (obj[1] == null) {
				data.put("observation", "");
			} else {
				int t = ((java.math.BigDecimal) obj[1]).intValue();
				if (t == 1) {
					data.put("observation", "Door Lock");
				} else if (t == 2) {
					data.put("observation", "Meter Block");
				} else if (t == 3) {
					data.put("observation", "Meter Burried");
				} else if (t == 4) {
					data.put("observation", "Meter Damaged");
				} else if (t == 5) {
					data.put("observation", "House not found");
				} else if (t == 6) {
					data.put("observation", "No Water Supply");
				} else if (t == 7) {
					data.put("observation", "Meter Removed(No Meter)");
				} else if (t == 8) {
					data.put("observation", "Low Water Supply");
				} else if (t == 9) {
					data.put("observation", "Dog Presence");
				} else if (t == 10) {
					data.put("observation", "Meter Sheild Broken");
				} else if (t == 11) {
					data.put("observation", "Temporary hole block");
				} else if (t == 12) {
					data.put("observation", "Permanent hole block");
				} else if (t == 13) {
					data.put("observation", "Service Line disconnected");
				} else if (t == 14) {
					data.put("observation", "House Collapse(Earthquake)");
				} else if (t == 15) {
					data.put("observation", "Unmetered");
				} else if (t == 16) {
					data.put("observation", "Reading");
				} else if (t == 17) {
					data.put("observation", "No Reading");
				} else if (t == 18) {
					data.put("observation", "Dual Record");
				} else if (t == 19) {
					data.put("observation", "PID");
				}
			}
			data.put("sa", obj[2]);
			data.put("tha", obj[3]);
			data.put("con_type", obj[4]);
			// data.put("mrName", obj[5]);

			/*
			 * data.put("THRSA", obj[3]+""); data.put("FOURTHA", obj[4]+"");
			 * data.put("FIVESA", obj[5]+""); data.put("SIXTHA", obj[6]+"");
			 * data.put("SEVENSA", obj[7]+""); data.put("EIGHTTHA", obj[8]+"");
			 * data.put("NINESA", obj[9]+""); data.put("TENTHA", obj[10]+"");
			 * data.put("TWELWBSA", obj[11]+""); data.put("THIRBTHA",
			 * obj[12]+""); data.put("FOURTNSA", obj[13]+"");
			 * data.put("FIFTNTHA", obj[14]+""); data.put("SIXTNSA",
			 * obj[15]+""); data.put("SEVENTNTHA", obj[16]+"");
			 * data.put("EIGHTNSA", obj[17]+""); data.put("NINTNTHA",
			 * obj[18]+""); data.put("TWENTYASA", obj[19]+"");
			 * data.put("TONETHA", obj[20]+""); data.put("TTWOSA", obj[21]+"");
			 * data.put("TTHREETHA", obj[22]+""); data.put("TFOURSA",
			 * obj[23]+""); data.put("TFIVETHA", obj[24]+""); data.put("TSIXSA",
			 * obj[25]+""); data.put("TSEVENTHA", obj[26]+"");
			 * data.put("TEIGHTSA", obj[27]+""); data.put("TNINETHA",
			 * obj[28]+""); data.put("THIRTYSA", obj[29]+"");
			 * data.put("THONETHA", obj[30]+""); data.put("THTWOSA",
			 * obj[31]+""); data.put("THTHREETHA", obj[32]+"");
			 * data.put("THFOURSA", obj[33]+""); data.put("THFIVETHA",
			 * obj[34]+"");
			 */
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> wardWiseMrReaderReportRead(String yearnep, String month) {
		List<?> list = reportDAO.wardWiseMrReaderReportRead(yearnep, month);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		// DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("wordNo", obj[0]);
			if (obj[1] == null) {
				data.put("observation", "");
			} else {
				int t = ((java.math.BigDecimal) obj[1]).intValue();
				if (t == 1) {
					data.put("observation", "Door Lock");
				} else if (t == 2) {
					data.put("observation", "Meter Block");
				} else if (t == 3) {
					data.put("observation", "Meter Burried");
				} else if (t == 4) {
					data.put("observation", "Meter Damaged");
				} else if (t == 5) {
					data.put("observation", "House not found");
				} else if (t == 6) {
					data.put("observation", "No Water Supply");
				} else if (t == 7) {
					data.put("observation", "Meter Removed(No Meter)");
				} else if (t == 8) {
					data.put("observation", "Low Water Supply");
				} else if (t == 9) {
					data.put("observation", "Dog Presence");
				} else if (t == 10) {
					data.put("observation", "Meter Sheild Broken");
				} else if (t == 11) {
					data.put("observation", "Temporary hole block");
				} else if (t == 12) {
					data.put("observation", "Permanent hole block");
				} else if (t == 13) {
					data.put("observation", "Service Line disconnected");
				} else if (t == 14) {
					data.put("observation", "House Collapse(Earthquake)");
				} else if (t == 15) {
					data.put("observation", "Unmetered");
				} else if (t == 16) {
					data.put("observation", "Reading");
				} else if (t == 17) {
					data.put("observation", "No Reading");
				} else if (t == 18) {
					data.put("observation", "Dual Record");
				} else if (t == 19) {
					data.put("observation", "PID");
				}
			}
			data.put("mrName", obj[2]);
			data.put("obcount", obj[3]);

			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> missedBillsReportRead(String yearnep, String month) {
		List<?> list = reportDAO.missedBillsReportRead(yearnep, month);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		// DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("ConNo", obj[0]);
			data.put("Name", obj[1]);
			data.put("WardNo", obj[2]);
			data.put("Rday", obj[3]);
			data.put("Area", obj[4]);
			data.put("Pipe", obj[5]);
			data.put("ConType", obj[6]);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> cashCollectiontthRead(String fromdate, String todate) {
		List<?> list = reportDAO.cashCollectiontthRead(fromdate, todate);
		DecimalFormat df = new DecimalFormat("#.##");
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		// DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			double due2072 = Double.parseDouble(df.format(
					((java.math.BigDecimal) obj[5]).doubleValue() - ((java.math.BigDecimal) obj[4]).doubleValue()));
			double pencurent = Double.parseDouble(df.format(((java.math.BigDecimal) obj[7]).doubleValue()));
			double pen2072 = (due2072 / 2);
			double pen2064 = pencurent - pen2072;
			double paid = Double.parseDouble(df.format(((java.math.BigDecimal) obj[8]).doubleValue()));
			due2072 = (paid == 0 ? due2072 : (paid - pencurent));
			// System.out.println("pen2064==========="+pen2064);
			if (due2072 >= 10000) {
				data.put("id", i++);

				data.put("slNo", slNo++);
				data.put("receipt", obj[0]);
				data.put("date", obj[1]);
				data.put("Name", obj[2]);
				data.put("ConNo", obj[3]);
				data.put("due2064", obj[4]);
				data.put("pen2064", (pen2064 < 0) ? 0 : df.format(pen2064));
				data.put("due2072", (df.format(due2072)));
				data.put("pen2072", df.format(pen2072));
				data.put("penaltyCurrent", obj[7]);
				data.put("totalCurrent", Double.parseDouble(df.format(((java.math.BigDecimal) obj[6]).doubleValue()
						+ ((java.math.BigDecimal) obj[7]).doubleValue())));
				data.put("lastPaid", paid);
				result.add(data);
			}
		}
		return result;
	}

	@Override
	public List<?> missedBillsReportLedger(String yearnep, String month) {
		List<?> list = reportDAO.missedBillsReportLedger(yearnep, month);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("ConNo", obj[0]);
			data.put("Name", obj[1]);
			data.put("WardNo", obj[2]);
			try{
				BigDecimal db=(BigDecimal)obj[3];
				int a=db.intValue();
				if(a<10) {
			      
				data.put("Rday", "0"+obj[3]);
			}else{
				data.put("Rday", obj[3]);
			   }
			}catch(NullPointerException e)
			{
				data.put("Rday",obj[3]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			data.put("Area", obj[4]);
			data.put("Pipe", obj[5]);
			data.put("ConType", obj[6]);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> monthlySalesSummaryReportReadData(String fromdate, String todate, String fdate, String tdate) {
		List<?> list = reportDAO.monthlySalesSummaryReportReadData(fromdate, todate, fdate, tdate);
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);
			data.put("slNo", slNo++);
			data.put("wardNo", (String) obj[0]);
			data.put("con_type", obj[1]);
			data.put("opening_balance", obj[2]);
			data.put("water_charges", obj[3]);
			data.put("sw_charges", obj[4]);
			data.put("mtr_rent", obj[5]);
			data.put("total_bill", obj[6]);
			data.put("net_amount", obj[7]);
			data.put("misc", obj[8]);
			data.put("penalty", obj[9]);
			data.put("rebate", obj[10]);
			data.put("ramount", obj[11]);
			data.put("adv", df.format(((java.math.BigDecimal) obj[13]).doubleValue()));
			data.put("adv_reb", df.format(((java.math.BigDecimal) obj[13]).doubleValue() * 0.03));
			data.put("cbalance",
					Double.parseDouble(df.format((((java.math.BigDecimal) obj[7]).doubleValue()
							+ ((java.math.BigDecimal) obj[8]).doubleValue()
							+ ((java.math.BigDecimal) obj[9]).doubleValue())
							+ ((java.math.BigDecimal) obj[13]).doubleValue()
							+ ((java.math.BigDecimal) obj[13]).doubleValue() * 0.03
							- (((java.math.BigDecimal) obj[10]).doubleValue()
									+ ((java.math.BigDecimal) obj[11]).doubleValue()))));
			result.add(data);
		}

		return result;
	}

	@Override
	public List<?> detailedCashCollReportReadData(String fromdate, String todate) {
		List<?> list = reportDAO.detailedCashCollReportReadDataNew(fromdate, todate);
		List<Map<String, Object>> result = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("connection_no", (String) obj[0]);
			data.put("amount", obj[1] == null ? 0 : ((java.math.BigDecimal) obj[1]).doubleValue());
			data.put("receipt_no", (String) obj[2]);
			//data.put("rdate", obj[3] == null ? "" : reportDAO.getDate3((Date) obj[3]));
			if (obj[3] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[3]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			
			data.put("counterno", (String) obj[4]);
			data.put("towards", (String) obj[5]);
			
			int paymode=((java.math.BigDecimal) obj[6]).intValue();
			if(paymode==1) {
				data.put("pay_mode", "Cash");
			} else if(paymode==2) {
				data.put("pay_mode", "Cheque");
			} else {
				data.put("pay_mode", "DD");
			}
			
			data.put("cdno", (String) obj[7]==null?"":(String) obj[7]);
			data.put("water_charges", obj[8] == null ? 0 : ((java.math.BigDecimal) obj[8]).doubleValue());
			data.put("sw_charges", obj[9] == null ? 0 : ((java.math.BigDecimal) obj[9]).doubleValue());
			data.put("meter_rent", obj[10] == null ? 0 : ((java.math.BigDecimal) obj[10]).doubleValue());
			data.put("miscellaneous_cost", obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
			data.put("penalty", obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
			data.put("rebate", obj[13] == null ? 0 : ((java.math.BigDecimal) obj[13]).doubleValue());
			data.put("frecamount", obj[14] == null ? 0 : ((java.math.BigDecimal) obj[14]).doubleValue());
			data.put("advance", obj[15] == null ? 0 : ((java.math.BigDecimal) obj[15]).doubleValue());
			data.put("advance_rebate", obj[16] == null ? 0 : ((java.math.BigDecimal) obj[16]).doubleValue());

			/*
			 * double old; double
			 * b_amt=(obj[17]==null?0:((java.math.BigDecimal)obj[17]).
			 * doubleValue()); double
			 * o_amt=(obj[18]==null?0:((java.math.BigDecimal)obj[18]).
			 * doubleValue()); old=(o_amt==0?b_amt:o_amt);
			 */

			double amt = (obj[1] == null ? 0 : ((java.math.BigDecimal) obj[1]).doubleValue());
			double wc = (obj[8] == null ? 0 : ((java.math.BigDecimal) obj[8]).doubleValue());
			double sc = (obj[9] == null ? 0 : ((java.math.BigDecimal) obj[9]).doubleValue());
			double mr = (obj[10] == null ? 0 : ((java.math.BigDecimal) obj[10]).doubleValue());
			double misc = (obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
			double pen = (obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
			double reb = (obj[13] == null ? 0 : ((java.math.BigDecimal) obj[13]).doubleValue());
			double adv = (obj[15] == null ? 0 : ((java.math.BigDecimal) obj[15]).doubleValue());
			double old = ((amt - (wc + sc + mr + misc + pen - reb)) - adv);
			data.put("old_balance", df.format(old));
			data.put("user_id", (String) obj[19]);
			data.put("chqDate", (String) obj[20]);
			result.add(data);
		}

		return result;
	}
	
	@Override
	public Object detailedMiscCollReportReadData(String fromDate, String toDate) {
		List<?> list = reportDAO.detailedMiscCollReportReadData(fromDate, toDate);
		List<Map<String, Object>> result = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("connection_no", (String) obj[0]);
			data.put("amount", obj[1] == null ? 0 : ((java.math.BigDecimal) obj[1]).doubleValue());
			data.put("receipt_no", (String) obj[2]);
			if (obj[3] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[3]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("11".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			data.put("application_id", obj[4]==null?"":obj[4]);
			data.put("counterno", (String) obj[5]);
			//data.put("pay_mode", (String) obj[6]);
			data.put("miscellaneous_cost", obj[7]);
			data.put("advance", obj[8] == null ? 0 : ((java.math.BigDecimal) obj[8]).doubleValue());
			data.put("nc_tap", obj[9] == null ? 0 : ((java.math.BigDecimal) obj[9]).doubleValue());
			data.put("nc_deposit", obj[10] == null ? 0 : ((java.math.BigDecimal) obj[10]).doubleValue());
			data.put("meter_value", obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
			data.put("temporaty_hole_block", obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
			data.put("name_change", obj[13] == null ? 0 : ((java.math.BigDecimal) obj[13]).doubleValue());
			data.put("nc_inst", obj[14] == null ? 0 : ((java.math.BigDecimal) obj[14]).doubleValue());
			data.put("user_id", (String) obj[15]);
			data.put("hole_ch", obj[16] == null ? 0 : ((java.math.BigDecimal) obj[16]).doubleValue());
			data.put("hole_main", obj[17] == null ? 0 : ((java.math.BigDecimal) obj[17]).doubleValue());
			data.put("app_ch", obj[18] == null ? 0 : ((java.math.BigDecimal) obj[18]).doubleValue());
			data.put("tender", obj[19] == null ? 0 : ((java.math.BigDecimal) obj[19]).doubleValue());
			data.put("card_ch", obj[20] == null ? 0 : ((java.math.BigDecimal) obj[20]).doubleValue());
			data.put("others", obj[21] == null ? 0 : ((java.math.BigDecimal) obj[21]).doubleValue());
			
			data.put("ilg_con_amt", obj[22]);
			result.add(data);
		}

		return result;
		
	}

	@Override
	public Object meterReadingListReportRead(String wardno, int readinday, String monthyearnep, double pipesize) {
		List<Map<String, Object>> result = new ArrayList<>();
		int i = 1;
		int slNo = 1;

		List<?> list = reportDAO.meterReadingListReportRead(wardno, readinday, monthyearnep, pipesize);
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("conn_no", (String) obj[0]);
			String area=(String)obj[1];
			try{
			String[] areas=area.split("-");
			int days=Integer.parseInt(areas[1]);
			if(days<10)
			{
				
				String area_no=areas[0]+"-0"+days+"-"+areas[2];
				data.put("area_no", area_no);
			}else{
			
			data.put("area_no", (String) obj[1]);
			     }
			}catch(Exception e)
			{
				data.put("area_no", (String) obj[1]);
			}

			data.put("name", (String) obj[2]);
			data.put("address", (String) obj[4]);

			if ((String) obj[2] == null) {
				data.put("name", (String) obj[3]);
			} else {
				data.put("name", (String) obj[2]);
			}

			if ((String) obj[4] == null) {
				data.put("address", (String) obj[5]);
			} else {

				data.put("address", (String) obj[4]);
			}

			data.put("reading_status", "");

			if (obj[7] == null) {
				data.put("pipe_size", "");
			} else {

				if (((BigDecimal) obj[7]).doubleValue() == 0.5) {

					data.put("pipe_size", "SA");
				} else {
					data.put("pipe_size", "THA");

				}

			}
			// int mnthyrnep=Integer.parseInt(monthyearnep)-1;

			/*
			 * if(mnthyrnep<=startmonth){
			 * 
			 * String
			 * obsname=billingLedgerDao.findObsByConnectionNoByMYN((String)obj[0
			 * ], ""+mnthyrnep);
			 * 
			 * if(obsname!=null){ data.put("prev_obs", obsname); }else{
			 * data.put("prev_obs", ""); } }else{ data.put("prev_obs", ""); }
			 */

			if (obj[6] == null) {
				data.put("prev_obs", "");
			} else {

				if (((BigDecimal) obj[6]).intValue() == 1) {
					data.put("prev_obs", "Door Lock");
				} else if (((BigDecimal) obj[6]).intValue() == 2) {
					data.put("prev_obs", "Meter Block");
				} else if (((BigDecimal) obj[6]).intValue() == 3) {
					data.put("prev_obs", "Meter Burried");
				} else if (((BigDecimal) obj[6]).intValue() == 4) {
					data.put("prev_obs", "Meter Damaged");
				} else if (((BigDecimal) obj[6]).intValue() == 5) {
					data.put("prev_obs", "House not found");
				} else if (((BigDecimal) obj[6]).intValue() == 6) {
					data.put("prev_obs", "No Water Supply");
				} else if (((BigDecimal) obj[6]).intValue() == 7) {
					data.put("prev_obs", "Meter Removed");
				} else if (((BigDecimal) obj[6]).intValue() == 8) {
					data.put("prev_obs", "Low Water Supply");
				} else if (((BigDecimal) obj[6]).intValue() == 9) {
					data.put("prev_obs", "Dog Presence");
				} else if (((BigDecimal) obj[6]).intValue() == 10) {
					data.put("prev_obs", "Meter Sheild Broken");
				} else if (((BigDecimal) obj[6]).intValue() == 11) {
					data.put("prev_obs", "Temporary hole block");
				} else if (((BigDecimal) obj[6]).intValue() == 12) {
					data.put("prev_obs", "Permanent hole block");
				} else if (((BigDecimal) obj[6]).intValue() == 13) {
					data.put("prev_obs", "Service Line disconnect");
				} else if (((BigDecimal) obj[6]).intValue() == 14) {
					data.put("prev_obs", "House Collapse");
				} else if (((BigDecimal) obj[6]).intValue() == 15) {
					data.put("prev_obs", "Unmetered");
				} else if (((BigDecimal) obj[6]).intValue() == 16) {
					data.put("prev_obs", "Reading");
				} else if (((BigDecimal) obj[6]).intValue()== 56) {
					data.put("prev_obs", "Self Reading");
				} else if (((BigDecimal) obj[6]).intValue() == 17) {
					data.put("prev_obs", "No Reading");
				} else if (((BigDecimal) obj[6]).intValue() == 18) {
					data.put("observation", "Dual Record");
				} else if (((BigDecimal) obj[6]).intValue() == 19) {
					data.put("observation", "PID");
				}

			}

			data.put("remarks", "");
            data.put("meter_no", obj[8]);
            data.put("ir", obj[9]);
			result.add(data);
			i++;
		}
		return result;
	}

	@Override
	public Object consumerBalanceReportRead(String amount, String criteria, String monthYear, String flag) {

		List<?> list = reportDAO.consumerBalanceReportRead(amount, criteria, monthYear, flag);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("slNo", slNo++);
			map.put("CONNECTION_NO", obj[0]);
			map.put("NAME_ENG", obj[1]);
			map.put("WATER_CHARGES", obj[2]);
			map.put("SW_CHARGES", obj[3]);
			map.put("MTR_RENT", obj[4]);
			map.put("ARREARS", obj[5]);
			map.put("NET_AMOUNT", obj[6]);
			map.put("pipeSize", obj[7]);
			map.put("area_no", obj[8]);
			map.put("ward_no", obj[9]);
			map.put("reading_day", obj[10]);
			result.add(map);
		}

		return result;
	}

	@Override
	public List<?> multiPaymentRead(String amount, String criteria, String date1, String date2, String flag,
			String con_type) {
		List<?> list = reportDAO.multiPaymentRead(amount, criteria, date1, date2, flag, con_type);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("slNo", slNo++);
			map.put("CONNECTION_NO", obj[0]);
			map.put("NAME_ENG", obj[1]);
			map.put("RecptNo", obj[2]);
			// map.put("Rdate",obj[3]);
			map.put("ARREARS", obj[4]);
			map.put("WATER_CHARGES", obj[5]);
			map.put("SW_CHARGES", obj[6]);
			map.put("MTR_RENT", obj[7]);
			map.put("Misc", obj[8]);
			map.put("Paid", obj[9]);

			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[3]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);

			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}

			if ("04".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("06".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("09".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}

			int cyear = Integer.parseInt(english[2]);

			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			map.put("Rdate", nepalimonthyear);
			result.add(map);
		}

		return result;
	}

	@Override
	public Object cashCollectionListReportRead(String fromdate, String todate) {

		List<?> list = reportDAO.cashCollectionListReportRead(fromdate, todate);

		DecimalFormat df = new DecimalFormat("#.##");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;
		// DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("slNo", slNo++);
			/*
			 * map.put("CONNECTION_NO",obj[0]); map.put("NAME_ENG", obj[1]);
			 * map.put("WATER_CHARGES", obj[2]); map.put("SW_CHARGES", obj[3]);
			 * map.put("METER_RENT", obj[4]); map.put("BALANCE_AMOUNT", obj[5]);
			 * map.put("MISCELLANEOUS_COST", obj[6]); map.put("PENALTY",
			 * obj[7]); map.put("AMOUNT1", obj[8]); double
			 * amount=(((java.math.BigDecimal)obj[2]).doubleValue())+(((java.
			 * math.BigDecimal)obj[3]).doubleValue())+
			 * (((java.math.BigDecimal)obj[4]).doubleValue())+(((java.math.
			 * BigDecimal)obj[6]).doubleValue())+
			 * +(((java.math.BigDecimal)obj[5]).doubleValue())+(((java.math.
			 * BigDecimal)obj[7]).doubleValue()); map.put("AMOUNT",
			 * df.format(amount)); map.put("RDATE", obj[9]);
			 * map.put("RECEIPT_NO",obj[10]); map.put("FROM_MON_YEAR", obj[11]);
			 * map.put("TO_MON_YEAR", obj[12]);
			 */

			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[1]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);
			double call = ((java.math.BigDecimal) obj[4]).doubleValue();
			double pen=0.0;
			if(obj[5]==null  || obj[5]=="")
			{
			    pen=0.0;
			}else {
			pen = ((java.math.BigDecimal) obj[5]).doubleValue();
			}
			double amt = ((java.math.BigDecimal) obj[6]).doubleValue();
			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}
			if ("04".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("06".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("09".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}

			int cyear = Integer.parseInt(english[2]);
			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			map.put("RECEIPT_NO", obj[0]);
			map.put("RDATE", nepalimonthyear);
			map.put("CONNECTION_NO", obj[2]);
			map.put("NAME_ENG", obj[3]);
			// data.put("sw_charges",
			// Double.valueOf(df.format(((Double)obj[6]))));
			//System.out.println(df.format((call)) + "----df.format((call))");

			/*
			 * map.put("COL_UPTO", df.format((call))); map.put("PENALTY",
			 * df.format((pen))); map.put("AMOUNT", df.format((amt)));
			 */

			map.put("COL_UPTO", String.format("%.2f", call));
			map.put("PENALTY", String.format("%.2f", pen));
			map.put("AMOUNT", String.format("%.2f", amt));

			map.put("FROM_MON_YEAR", obj[7]);
			map.put("TO_MON_YEAR", obj[8]);

			result.add(map);

		}
		return result;
	}

	@Override
	public Object cashCollectionListReportReadAll(String fromdate, String todate) {
		List<?> list = reportDAO.cashCollectionListReportReadAll(fromdate, todate);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("total_amt", obj[0]);
			map.put("total_pen", obj[1]);
			result.add(map);
		}
		return result;
	}

	@Override
	public Object consumerListReportReadData() {
		List<?> list = reportDAO.consumerListReportReadData();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("id", i++);
			map.put("slNo", slNo++);
			map.put("con_no", (String) obj[0]);
			map.put("area_no", (String) obj[1]);

			if ((String) obj[2] == null) {
				map.put("name", (String) obj[6]);
			} else {
				map.put("name", (String) obj[2]);
			}

			if ((String) obj[3] == null) {
				map.put("address", (String) obj[7]);
			} else {

				map.put("address", (String) obj[3]);
			}

			map.put("con_type", (String) obj[4]);
			map.put("pipe_size", obj[5]);
			map.put("ward_no", obj[8]);

			map.put("meter_no", obj[9]);
			result.add(map);

		}
		return result;

	}

	@Override
	public Object holeBlockReportReadData(String con_status) {

		List<?> list = reportDAO.holeBlockReportReadData(con_status);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("slNo", i++);

			map.put("slNo", slNo++);
			map.put("con_no", (String) obj[0]);
			map.put("area_no", (String) obj[1]);
			map.put("name", (String) obj[2]);
			map.put("address", (String) obj[3]);
			map.put("con_type", (String) obj[4]);
			map.put("pipe_size", Double.valueOf(df.format(obj[5])));
			map.put("con_status", (String) obj[6]);

			result.add(map);

		}
		return result;

	}

	@Override
	public List<?> generatedailyCashCounterCollReport(String fromDateeng, String toDateeng,String counterno,String wardno,String pipesize) {
		return reportDAO.generatedailyCashCounterCollReport(fromDateeng, toDateeng,counterno,wardno,pipesize);
	}

	@Override
	public Object conTypeReportReadData(String con_Type) {
		List<?> list = reportDAO.conTypeReportReadData(con_Type);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("slNo", i++);
			map.put("con_no", (String) obj[0]);
			map.put("area_no", (String) obj[1]);
			if (obj[2] == null) {
				map.put("name", (String) obj[7]);
			} else {
				map.put("name", (String) obj[2]);
			}
			if (obj[3] == null) {
				map.put("address", (String) obj[8]);
			} else {
				map.put("address", (String) obj[3]);
			}
			map.put("con_type", (String) obj[4]);
			map.put("pipe_size", obj[5]);
			map.put("con_Type", (String) obj[6]);
			map.put("con_category", (String) obj[9]);

			result.add(map);

		}
		return result;
	}

	@Override
	public Object dailyRevenueReportRead(String fromdate, String todate, String counter_no) {

		List<?> list = reportDAO.dailyRevenueReportRead(fromdate, todate, counter_no);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i++);
			map.put("ward_no", obj[0]);
			map.put("opening_arrears", obj[1]);// pending
			map.put("water_charge", obj[2]);
			map.put("sw_charge", obj[3]);
			map.put("meter_rent", obj[4]);
			map.put("net_ammount", obj[5]);
			map.put("penalty", obj[6]);
			map.put("rebate", obj[7]);
			map.put("miscellaneous", obj[8]);
			map.put("total_toBePaid", obj[9]);
			map.put("total_paid", obj[10]);
			map.put("advance", obj[11]);
			map.put("advance_rebate", obj[12]);
			map.put("closing_balance", obj[13]);

			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[14]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);

			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}
			if ("04".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("06".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("09".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			int cyear = Integer.parseInt(english[2]);

			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);

			map.put("rdate", nepalimonthyear);
			result.add(map);
		}
		return result;

	}

	@Override
	public Object revenueReportRead(String fromdate, String todate, String counter_no, String myn) {
		DecimalFormat df = new DecimalFormat("#.##");
		List<?> list = reportDAO.revenueReportRead(fromdate, todate, counter_no);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int monthyrN=Integer.parseInt(myn);
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i++);
			map.put("water_charge", obj[0]);
			map.put("sw_charge", obj[1]);
			map.put("miscellaneous", obj[2]);
			map.put("meter_rent", obj[3]);
			map.put("penalty", obj[4]);
			map.put("rebate", obj[5]);
			map.put("total_paid", obj[6]);
			map.put("advance", obj[7]);
			map.put("tot_col", obj[8]);
			if(monthyrN<207404){
				map.put("advance_rebate", df.format(((java.math.BigDecimal) obj[7]).doubleValue() * 0.03));
			} else{
				map.put("advance_rebate", obj[9]);
			}
			map.put("old_balance", obj[10]);
			map.put("total_toBePaid", obj[11]);// pending
			map.put("arrears", obj[12]);
			// map.put("rdate", obj[13]);

			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[13]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);

			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}
			if ("04".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("06".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("09".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			int cyear = Integer.parseInt(english[2]);

			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			map.put("rdate", nepalimonthyear);

			map.put("tot_bill", ((java.math.BigDecimal) obj[0]).doubleValue()
					+ ((java.math.BigDecimal) obj[1]).doubleValue() + ((java.math.BigDecimal) obj[3]).doubleValue());
			result.add(map);
		}
		return result;
	}

	@Override
	public Object wardWiseConsumerCountReport() {

		List<?> list = reportDAO.wardWiseConsumerCountReport();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("slNo", slNo++);
			map.put("ward_no", obj[0]);
			map.put("countno", obj[1]);
			map.put("reading_day", obj[2]);

			result.add(map);

		}
		return result;

	}

	@Override
	public Object custObservationReportRead(String monthYearNep, int mc_status) {
		List<?> list = reportDAO.custObservationReportRead(monthYearNep, mc_status);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("CONNECTION_NO", obj[0]);
			data.put("NAME_ENG", obj[1]);
			data.put("area_no", obj[2]);
			data.put("pipe_size", obj[3]);
			data.put("present_reading", obj[4]);
			data.put("previous_reading", obj[5]);
			data.put("consumption",
					((java.math.BigDecimal) obj[4]).doubleValue() - ((java.math.BigDecimal) obj[5]).doubleValue());
			data.put("arrears", ((java.math.BigDecimal) obj[6]).doubleValue());
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> categoryCollectionReportRead(String from, String to, String category) {
		DecimalFormat df = new DecimalFormat("#.##");
		List<?> list = reportDAO.categoryCollectionReportRead(from, to, category);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("currentRevenue", Double.valueOf(df.format(((java.math.BigDecimal) obj[0]).doubleValue())));
			data.put("currentPenalty", Double.valueOf(df.format(((java.math.BigDecimal) obj[1]).doubleValue())));
			data.put("paidBilling", Double.valueOf(df.format(
					((java.math.BigDecimal) obj[2]).doubleValue() - ((java.math.BigDecimal) obj[1]).doubleValue())));  
			data.put("pamount", Double.valueOf(df.format(((java.math.BigDecimal) obj[2]).doubleValue())));
			data.put("pamountBoard", Double.valueOf(df.format(((java.math.BigDecimal) obj[3]).doubleValue())));
			data.put("category", obj[4]);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> monthlySalesReadData(String monthyearnep) {

		DecimalFormat df = new DecimalFormat("#.##");

		List<?> list = reportDAO.monthlySalesReadData(monthyearnep);
		//List<?> list1=reportDAO.advanceCollectionReportReadNew(monthyearnep);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;
		/*Iterator<?> list1Iterator=list1.listIterator();*/
		
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			
			Object[] obj = (Object[]) iterator.next();
			
			Map<String, Object> data = new HashMap<String, Object>();
			double posAdj =(obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
			double negAdj=(obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
			//System.out.println("posAdj======"+posAdj+" =====negAdj===="+negAdj+" =======FOR===="+obj[1] + "" + obj[2]);
			data.put("slNo", slNo++);
			data.put("wardNo", obj[1] + "" + obj[2]);
			data.put("con_type", obj[3]);
			data.put("opening_balance", obj[3]);
			data.put("water_charges", obj[4]);
			data.put("sw_charges", obj[5]);
			data.put("mtr_rent", obj[6]);
			data.put("misc", obj[7]);
			data.put("ramount", obj[8]);
			data.put("adpenalty", obj[9]);
			data.put("penalty", obj[10]);
			data.put("posadj", (posAdj+negAdj));
			data.put("negadj",obj[12]);
			data.put("rebate", obj[13]);
			data.put("cbalance", obj[14]);
			data.put("grandBill", Double.valueOf(df.format((((java.math.BigDecimal) obj[3]).doubleValue())
					+ (((java.math.BigDecimal) obj[4]).doubleValue()) + (((java.math.BigDecimal) obj[5]).doubleValue())
					+ (((java.math.BigDecimal) obj[6]).doubleValue()))));

			data.put("totalbill", Double.valueOf(df.format((((java.math.BigDecimal) obj[3]).doubleValue())
					+ (((java.math.BigDecimal) obj[4]).doubleValue()) + (((java.math.BigDecimal) obj[5]).doubleValue())
					+ (((java.math.BigDecimal) obj[6]).doubleValue()) + (((java.math.BigDecimal) obj[7]).doubleValue())
					+ (((java.math.BigDecimal) obj[9]).doubleValue()) + (((java.math.BigDecimal) obj[10]).doubleValue())
					+(posAdj+negAdj)- (((java.math.BigDecimal) obj[13]).doubleValue()))));
			data.put("suspense", obj[15]);
			data.put("adv_reb", obj[16]);
			data.put("current_adv_amt", obj[18]);
			data.put("current_adv_rebate", obj[19]);
			/*if(list1Iterator.hasNext()) {
				Object[] obj1 = (Object[]) list1Iterator.next();
				double adv_reb=(obj[16]==null?0:((java.math.BigDecimal) obj[16]).doubleValue());
				double adv_rebNew=(obj1[9]==null?0:((java.math.BigDecimal) obj1[9]).doubleValue());
				data.put("current_adv_amt", obj1[8]);
				data.put("current_adv_rebate", obj1[9]);
				
				while()
				
			}*/
			
			result.add(data);

		}
		return result;
	}

	@Override
	public List<?> newMonthlySalesCategory(String monthyearnep, String con_category) {

		DecimalFormat df = new DecimalFormat("#.##");

		List<?> list = reportDAO.newMonthlySalesCategory(monthyearnep, con_category);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("wardNo", obj[1] + "" + obj[2]);
			data.put("con_type", obj[3]);
			data.put("opening_balance", obj[4]);
			data.put("water_charges", obj[5]);
			data.put("sw_charges", obj[6]);
			data.put("mtr_rent", obj[7]);
			data.put("misc", obj[8]);
			data.put("ramount", obj[9]);
			data.put("adpenalty", obj[10]);
			data.put("penalty", obj[11]);
			data.put("posadj", obj[12]);
			data.put("negadj", obj[13]);
			data.put("rebate", obj[14]);
			data.put("cbalance", obj[15]);

			data.put("totalbill", Double.valueOf(df.format((((java.math.BigDecimal) obj[4]).doubleValue())
					+ (((java.math.BigDecimal) obj[5]).doubleValue()) + (((java.math.BigDecimal) obj[6]).doubleValue())
					+ (((java.math.BigDecimal) obj[7]).doubleValue()) + (((java.math.BigDecimal) obj[8]).doubleValue())
					+ (((java.math.BigDecimal) obj[10]).doubleValue())
					+ (((java.math.BigDecimal) obj[11]).doubleValue())
					+ (((java.math.BigDecimal) obj[12]).doubleValue())
					+ (((java.math.BigDecimal) obj[13]).doubleValue()))));
			data.put("suspense", obj[16]);
			data.put("adv_reb", obj[17]);
			result.add(data);

		}
		return result;

	}

	@Override
	public List<?> counterRevenueReportRead(String fromdate, String todate, String myn) {
		List<?> list = reportDAO.counterRevenueReportRead(fromdate, todate);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int monthyrN=Integer.parseInt(myn);
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i++);
			map.put("water_charge", obj[0]);
			map.put("sw_charge", obj[1]);
			map.put("miscellaneous", obj[2]);
			map.put("meter_rent", obj[3]);
			map.put("penalty", obj[4]);
			map.put("rebate", obj[5]);
			map.put("total_paid", obj[6]);
			map.put("advance", obj[7]);
			map.put("tot_col", obj[8]);
			if(monthyrN<207404){
				map.put("advance_rebate", ((java.math.BigDecimal) obj[7]).doubleValue() * 0.03);
			} else{
				map.put("advance_rebate", obj[9]);
			}
			map.put("old_balance", obj[10]);
			map.put("total_toBePaid", obj[11]);// pending
			map.put("arrears", obj[12]);
			// map.put("rdate", obj[13]);
			map.put("counterno", obj[14]);
			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[13]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);

			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}

			int cyear = Integer.parseInt(english[2]);

			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			map.put("rdate", nepalimonthyear);

			map.put("tot_bill", ((java.math.BigDecimal) obj[0]).doubleValue()
					+ ((java.math.BigDecimal) obj[1]).doubleValue() + ((java.math.BigDecimal) obj[3]).doubleValue());
			result.add(map);
		}
		return result;
	}

	@Override
	public List<?> ledgerVerificationRead(String wardno, String monthyear, String reading_day) {
		List<?> list = reportDAO.ledgerVerificationRead(wardno, monthyear, reading_day);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("CONNECTION_NO", obj[0]);
			data.put("NAME_ENG", obj[1]);
			data.put("watercharge", obj[2]);
			data.put("swcharge", obj[3]);
			data.put("mtrrent", obj[4]);
			data.put("arrears", obj[5]);
			data.put("net", obj[6]);
			data.put("penalty", obj[7]);
			data.put("rebate", obj[8]);
			data.put("lastpaid", obj[9]);
			data.put("closingbal", obj[10]);
			data.put("receipt", obj[11]);
			//data.put("rdate", obj[12]);
			if (obj[12] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[12]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			
			
			data.put("misc", obj[13]);
			data.put("board", obj[14]);
			data.put("kukl", (obj[10] != null ? obj[10] : obj[6]));
			data.put("areano", obj[15]);
			data.put("l_reading", obj[16]);
			data.put("p_reading", obj[17]);
			data.put("consumption", obj[18]);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> wardWiseCustomerBillingReportRead(String wardNo, String myn, String category) {
		List<?> list = reportDAO.wardWiseCustomerBillingReportRead(wardNo, myn, category);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("CONNECTION_NO", obj[0]);
			data.put("name", obj[1]);
			data.put("area", obj[2]);
			data.put("pipe_size", obj[3]);
			data.put("arrears", obj[4]);
			data.put("water_ch", obj[5]);
			data.put("sw_ch", obj[6]);
			data.put("mtr_rent", obj[7]);
			data.put("net_amt", obj[8]);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<?> monthlyAdjustmentReportRead(String myn) {
		List<?> list = reportDAO.monthlyAdjustmentReportRead(myn);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("CONNECTION_NO", obj[0]);
			data.put("name", obj[1]);
			data.put("area", obj[2]);
			data.put("arrear_corr", obj[3]==null?0:obj[3]);
			data.put("penaty_corr", obj[4]==null?0:obj[4]);
			data.put("bill_adj", obj[5]==null?0:obj[5]);
			data.put("penalty_adj", obj[6]==null?0:obj[6]);
			data.put("submitted_by", obj[7]==null?"":obj[7]);
			if (obj[8] == null) {
				data.put("submitted_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[8]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("submitted_date", nepalimonthyear);
			}
			
			data.put("aproved_by", obj[9]==null?"":obj[9]);
			if (obj[10] == null) {
				data.put("aproved_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[10]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("aproved_date", nepalimonthyear);
			}
			
			String adj_type=(String)obj[13];
			if("BDJ".equalsIgnoreCase(adj_type)){
				data.put("ho_approve_by", obj[11]==null?"":obj[11]);
				if (obj[12] == null) {
					data.put("ho_approve_date", "");
				} else {
					String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[12]);
					String english[] = date.split("-");
					int cday = Integer.parseInt(english[0]);
					if ("02".equalsIgnoreCase(english[1])) {
						cday = cday + 2;
					}
					if ("04".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("06".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("09".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					int cmonth = Integer.parseInt(english[1]);
					int cyear = Integer.parseInt(english[2]);
					String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					data.put("ho_approve_date", nepalimonthyear);
				}
				
			} else{
				data.put("ho_approve_by", "");
				data.put("ho_approve_date", "");
			}
			result.add(data);
		}
		return result;
	}
	
	@Override
	public List<?> categoryBoardCollectionReportRead(String from, String to) {
		List<?> list = reportDAO.categoryBoardCollectionReportRead(from, to);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("category", obj[0]);
			data.put("contype", obj[1]);
			data.put("openingBal", obj[2]);
			data.put("arrears", obj[3]);
			data.put("penalty", obj[4]);
			data.put("totalPaid", obj[5]);
			data.put("closingBal", obj[6]);
			data.put("penaltyAdj", obj[7]);
			result.add(data);
		}
		return result;
	}
	
	@Override
	public List<?> cancelReceiptReportRead(String from, String to) {
		List<?> list = reportDAO.cancelReceiptReportRead(from, to);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			double paid=((java.math.BigDecimal) obj[5]).doubleValue();
			if(paid>0)
			{
				data.put("slNo", slNo++);
				data.put("conNo", obj[0]);
				data.put("name", obj[1]);
				data.put("area", obj[2]);
				data.put("receipt", obj[3]);
				//data.put("rdate", obj[4]);
				if (obj[4] == null) {
					data.put("rdate", "");
				} else {
					String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[4]);
					String english[] = date.split("-");
					int cday = Integer.parseInt(english[0]);
					if ("02".equalsIgnoreCase(english[1])) {
						cday = cday + 2;
					}
					if ("04".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("06".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("09".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					int cmonth = Integer.parseInt(english[1]);
					int cyear = Integer.parseInt(english[2]);
					String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					data.put("rdate", nepalimonthyear);
				}
				data.put("paidAmt", paid);
				data.put("rebate", obj[6]);
				data.put("penalty", obj[7]);
				data.put("billAmt", obj[8]);
				data.put("closeBal", obj[9]);
				result.add(data);
			}
		}
		return result;
	}
	
	@Override
	public List<?> boardSalesReportRead(String from, String to) {
		List<?> list = reportDAO.boardSalesReportRead(from, to);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("wardNo", obj[0]);
			data.put("openingBal", obj[1]);
			data.put("arrears", obj[2]);
			data.put("penalty", obj[3]);
			data.put("penaltyAdj", obj[4]);
			data.put("paid", obj[5]);
			data.put("closingBal", obj[6]);
			
			result.add(data);
		}
		return result;
	}
	
	@Override
	public List<?> advanceCollectionReportRead(String monthyearnep) {
		
		DecimalFormat df = new DecimalFormat("#.##");

		List<?> list = reportDAO.advanceCollectionReportRead(monthyearnep);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			
			data.put("slNo", slNo++);
			data.put("wardNo", (obj[0]==null?(obj[8]==null?obj[10]:obj[8]):obj[0]));
			data.put("denoted_by", (obj[1]==null?(obj[9]==null?obj[11]:obj[9]):obj[1]));
			data.put("opening_adv", obj[2]);
			data.put("water_charges", obj[3]);
			data.put("sw_charges", obj[4]);
			data.put("mtr_rent", obj[5]);
			double total_bill=Double.valueOf(df.format((((java.math.BigDecimal) obj[3]).doubleValue())+
					(((java.math.BigDecimal) obj[4]).doubleValue())+(((java.math.BigDecimal) obj[5]).doubleValue())));
			data.put("total_bill",total_bill );
			data.put("advance", obj[6]);
			data.put("advance_rebate", obj[7]);
			double closing=Double.valueOf(df.format((((java.math.BigDecimal) obj[2]).doubleValue())-total_bill+
					(((java.math.BigDecimal) obj[6]).doubleValue())+(((java.math.BigDecimal) obj[7]).doubleValue())));
			data.put("closing_adv", closing);
			
			result.add(data);

		}
		return result;
	}

	@Override
	public List<?> generatedailyMiscColReport(String fdate, String tdate,String counterNO) {
		return reportDAO.generatedailyMiscColReport(fdate, tdate,counterNO);
	}
	
	@Override
	public List<?> getAbnormalBillingReport(String monthyearnep, String diff) {
		List<?> list = reportDAO.getAbnormalBillingReport(monthyearnep, diff);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			
			data.put("slNo", slNo++);
			data.put("connection_No", obj[0]);
			data.put("current_bill_amt", df.format((((java.math.BigDecimal) obj[1]).doubleValue())));
			data.put("avg_bill_amt", df.format((((java.math.BigDecimal) obj[2]).doubleValue())));
			data.put("diff", df.format((((java.math.BigDecimal) obj[3]).doubleValue())));
			
			result.add(data);

		}
		return result;
	}
	
	@Override
	public List<?> categoryTypeReportReadData(String con_category) {
		List<?> list = reportDAO.categoryTypeReportReadData(con_category);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("slNo", i++);

			map.put("slNo", slNo++);
			map.put("con_no", (String) obj[0]);
			map.put("area_no", (String) obj[1]);
			map.put("name", (String) (obj[2])==null?obj[7]:obj[2]);
			map.put("address", (String) (obj[3]==null?obj[8]:obj[3]));
			map.put("con_type", (String) obj[4]);
			map.put("pipe_size", Double.valueOf(df.format(obj[5])));
			map.put("con_category", (String) obj[6]);

			result.add(map);

		}
		return result;
	}

	@Override
	public List<?> advanceCollectionReportReadNew(String monthyearnep) {

		DecimalFormat df = new DecimalFormat("#.##");

		List<?> list = reportDAO.advanceCollectionReportReadNew(monthyearnep);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("wardNo", obj[1] + "" + obj[2]);
			data.put("con_type", obj[3]);
			data.put("opening_adv", obj[3]);
			data.put("water_charges", obj[4]);
			data.put("sw_charges", obj[5]);
			data.put("mtr_rent", obj[6]);
			data.put("total_bill", Double.valueOf(df.format( (((java.math.BigDecimal) obj[4]).doubleValue()) + (((java.math.BigDecimal) obj[5]).doubleValue())
					+ (((java.math.BigDecimal) obj[6]).doubleValue()))));
			
			data.put("adjustments", obj[7]);
			data.put("current_adv_amt", obj[8]);
			data.put("current_adv_rebate", obj[9]);
			data.put("closing_balance", obj[10]);
			
			result.add(data);

		}
		return result;
		
	}

	@Override
	public List<?> customerWiseAdvCollRepDetails(String monthyearnep) {
		DecimalFormat df = new DecimalFormat("#.##");
		List<?> list = reportDAO.customerWiseAdvCollRepDetails(monthyearnep);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int slNo = 1;

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("connNo", obj[0]);
			data.put("areaNo", obj[1]);
			data.put("openbal", obj[2]);
			data.put("water_charges", obj[3]);
			data.put("sw_charges", obj[4]);
			data.put("mtr_rent", obj[5]);
		    /*data.put("total_bill", Double.valueOf(df.format( (((java.math.BigDecimal) obj[3]).doubleValue()) + (((java.math.BigDecimal) obj[4]).doubleValue())
					+ (((java.math.BigDecimal) obj[5]).doubleValue()))));
		    */
		    double totbill= Double.valueOf(df.format( (((java.math.BigDecimal) obj[3]).doubleValue()) + (((java.math.BigDecimal) obj[4]).doubleValue())
					+ (((java.math.BigDecimal) obj[5]).doubleValue())));
		    
			data.put("totalBill", totbill);
			
			//data.put("ntAmt", obj[6]);
			data.put("cbalance", obj[7]);
			data.put("advColl", obj[8]);
			data.put("advRebate", obj[9]);
		
		
			result.add(data);

		}
		
		return result;
	}
	@Override
	public List<?> newConnectionReportDetails(String frmDt, String toDt, String citeCode, int catagory)
	{
		
			List<?> list = reportDAO.newConnectionReportDetails(frmDt, toDt,citeCode,catagory);
			List<Map<String, Object>> result = new ArrayList<>();
			int slNo = 1;
			for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

				Object[] obj = (Object[]) iterator.next();
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("slNo", slNo++);
				data.put("connNo", obj[0]);
				data.put("name", obj[1]);
				data.put("areaNo", obj[2]);
				data.put("approvedBy", obj[5]);
				//data.put("approvedDate",new SimpleDateFormat("yyyy-MM-dd").format(obj[4]));
				if (obj[4] == null) {
					data.put("approvedDate", "");
				} else {
					String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[4]);
					String english[] = date.split("-");
					int cday = Integer.parseInt(english[0]);
					if ("02".equalsIgnoreCase(english[1])) {
						cday = cday + 2;
					}
					if ("04".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("06".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("09".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					int cmonth = Integer.parseInt(english[1]);
					int cyear = Integer.parseInt(english[2]);
					String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					data.put("approvedDate", nepalimonthyear);
				}
				//data.put("uname", obj[5]);
				
				result.add(data);
				
			}
			return result;
		}

	@Override
	public Object boarReportDetailstReadData(String fromdate, String todate) 
	{
		
		List<?> list = reportDAO.boarReportDetailstReadData(fromdate, todate);
		
		List<Map<String, Object>> result = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("#.##");
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", i++);

			data.put("slNo", slNo++);
			data.put("connection_no", (String) obj[0]);
			data.put("amount", obj[1] == null ? 0 : ((java.math.BigDecimal) obj[1]).doubleValue());
			data.put("receipt_no", (String) obj[2]);
			data.put("rdate", obj[3] == null ? "" : reportDAO.getDate3((Date) obj[3]));
			if (obj[3] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[3]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			
			data.put("counterno", (String) obj[4]);
			//data.put("towards", (String) obj[5]);
			
			int paymode=((java.math.BigDecimal) obj[5]).intValue();
			if(paymode==1) {
				data.put("pay_mode", "Cash");
			} else if(paymode==2) {
				data.put("pay_mode", "Cheque");
			} else {
				data.put("pay_mode", "DD");
			}
			
    		data.put("cdno", (String) obj[6]==null?"":(String) obj[6]);
    		
    		data.put("chqDate", obj[7] == null ? "" : reportDAO.getDate3((Date) obj[7]));
			if (obj[7] == null) {
				data.put("chqDate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[7]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("chqDate", nepalimonthyear);
			}
    		
			data.put("penalty", obj[8] == null ? 0 : ((java.math.BigDecimal) obj[8]).doubleValue());
		    data.put("board_adj_amt", obj[9] == null ? 0 : ((java.math.BigDecimal) obj[9]).doubleValue());
		    data.put("penalty_adj_amt", obj[10] == null ? 0 : ((java.math.BigDecimal) obj[10]).doubleValue());
//			data.put("miscellaneous_cost", obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
//			data.put("penalty", obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
//			data.put("rebate", obj[13] == null ? 0 : ((java.math.BigDecimal) obj[13]).doubleValue());
//			data.put("frecamount", obj[14] == null ? 0 : ((java.math.BigDecimal) obj[14]).doubleValue());
//			data.put("advance", obj[15] == null ? 0 : ((java.math.BigDecimal) obj[15]).doubleValue());
//			data.put("advance_rebate", obj[16] == null ? 0 : ((java.math.BigDecimal) obj[16]).doubleValue());
//
//			
//			 * double old; double
//			 * b_amt=(obj[17]==null?0:((java.math.BigDecimal)obj[17]).
//			 * doubleValue()); double
//			 * o_amt=(obj[18]==null?0:((java.math.BigDecimal)obj[18]).
//			 * doubleValue()); old=(o_amt==0?b_amt:o_amt);
//			 
//
//			double amt = (obj[1] == null ? 0 : ((java.math.BigDecimal) obj[1]).doubleValue());
//			double wc = (obj[8] == null ? 0 : ((java.math.BigDecimal) obj[8]).doubleValue());
//			double sc = (obj[9] == null ? 0 : ((java.math.BigDecimal) obj[9]).doubleValue());
//			double mr = (obj[10] == null ? 0 : ((java.math.BigDecimal) obj[10]).doubleValue());
//			double misc = (obj[11] == null ? 0 : ((java.math.BigDecimal) obj[11]).doubleValue());
//			double pen = (obj[12] == null ? 0 : ((java.math.BigDecimal) obj[12]).doubleValue());
//			double reb = (obj[13] == null ? 0 : ((java.math.BigDecimal) obj[13]).doubleValue());
//			double adv = (obj[15] == null ? 0 : ((java.math.BigDecimal) obj[15]).doubleValue());
//			double old = ((amt - (wc + sc + mr + misc + pen - reb)) - adv);
//			data.put("old_balance", df.format(old));
//			data.put("user_id", (String) obj[19]);
//			data.put("chqDate", (String) obj[20]);
			result.add(data);
		}

		return result;
	}

	@Override
	public List<?> boardBalAdjCorrReportDetails(String myn, String col_catagory) {
		List<?> list = reportDAO.boardBalAdjCorrReportDetails(myn,col_catagory);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("CONNECTION_NO", obj[0]);
			data.put("name", obj[1]);
			data.put("area", obj[2]);
			data.put("board_Adj", obj[3]==null?0:obj[3]);
			data.put("con_catagory", obj[4]);
			data.put("mast_bal", obj[5]==null?0:obj[5]);
			/*data.put("bill_adj", obj[5]==null?0:obj[5]);
			data.put("penalty_adj", obj[6]==null?0:obj[6]);
			
			*/data.put("submitted_by", obj[6]==null?"":obj[6]);
			if (obj[7] == null) {
				data.put("submitted_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[7]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("submitted_date", nepalimonthyear);
			}
			
			data.put("aproved_by", obj[8]==null?"":obj[8]);
			if (obj[9] == null) {
				data.put("aproved_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[9]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("aproved_date", nepalimonthyear);
			}
			
			String adj_type=(String)obj[12];
			
			if("BOARD".equalsIgnoreCase(adj_type) || "BRDCR".equalsIgnoreCase(adj_type)){
				data.put("ho_approve_by", obj[10]==null?"":obj[10]);
				if (obj[11] == null) {
					data.put("ho_approve_date", "");
				} else {
					String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[11]);
					String english[] = date.split("-");
					int cday = Integer.parseInt(english[0]);
					if ("02".equalsIgnoreCase(english[1])) {
						cday = cday + 2;
					}
					if ("04".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("06".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					if ("09".equalsIgnoreCase(english[1])) {
						cday = cday + 1;
					}
					int cmonth = Integer.parseInt(english[1]);
					int cyear = Integer.parseInt(english[2]);
					String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					data.put("ho_approve_date", nepalimonthyear);
				}
				
			} else{
				data.put("ho_approve_by", "");
				data.put("ho_approve_date", "");
			}
			result.add(data);
		}
		return result;
		
	}
	@Override
	public List<?> newConnectionReportDetailsCat(String frmDt, String toDt,
			String citeCode, int catagory, String concategory) {
		
		
		
		List<?> list = reportDAO.newConnectionReportDetailsCat(frmDt, toDt,citeCode,catagory,concategory);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("connNo", obj[0]);
			data.put("name", obj[1]);
			data.put("areaNo", obj[2]);
			data.put("approvedBy", obj[5]);
			//data.put("approvedDate",new SimpleDateFormat("yyyy-MM-dd").format(obj[4]));
			if (obj[4] == null) {
				data.put("approvedDate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[4]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("approvedDate", nepalimonthyear);
			}
			//data.put("uname", obj[5]);
			
			result.add(data);
			
		}
		return result;
	}

	@Override
	public List<?> categoryNewConnectionReportDetailsCat(String frmDt,
			String toDt, String citeCode, String conType, String concategory,
			String pipesize) {
		List<?> list = reportDAO.categoryNewConnectionReportDetailsCat(frmDt, toDt,citeCode,conType,concategory,pipesize);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("connNo", obj[0]);
			data.put("con_type", obj[1]);
			data.put("con_cat", obj[2]);
			
			double pip_size=((BigDecimal)obj[3]).doubleValue();
			if(pip_size == 0.5){
		    data.put("pipe_size1",obj[3]);
			}else if(pip_size == 0.75){
				data.put("pipe_size2",obj[3]);	
			}else if(pip_size == 1){
				data.put("pipe_size3",obj[3]);	
			}else if(pip_size == 1.5){
				data.put("pipe_size4",obj[3]);	
			}
			else if(pip_size == 2){
				data.put("pipe_size5",obj[3]);	
			}
			else if(pip_size ==3){
				data.put("pipe_size6",obj[3]);	
			}
			else{
				data.put("pipe_size7",obj[3]);
			}
			
			//data.put("pipe_size", obj[3]);
			
			
			
			/*//data.put("approvedDate",new SimpleDateFormat("yyyy-MM-dd").format(obj[4]));
			if (obj[4] == null) {
				data.put("approvedDate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[4]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 3;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("approvedDate", nepalimonthyear);
				data.put("pipe_size", obj[6]);*/
			
			//data.put("uname", obj[5]);
			
			result.add(data);
		}
			
		
		return result;
	}

	@Override
	public Object meterChangeDetailsReport() {
		List<MeterChangeDetailsEntity> list = reportDAO.meterChangeDetailsReport();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			MeterChangeDetailsEntity mcde =  (MeterChangeDetailsEntity) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("id", i++);
			map.put("slNo", slNo++);
			map.put("con_no",mcde.getConnectionno());
			map.put("ledgno", mcde.getLedgno());
			
			map.put("folio", mcde.getFolio());
			map.put("fr", mcde.getFr());
			map.put("ir", mcde.getIr());
			map.put("oldmeterno",mcde.getOldmeterno());
 
			map.put("newmeterno", mcde.getNewmeterno());
			map.put("username", mcde.getUsername());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String string  = dateFormat.format(mcde.getDatestamp());
			map.put("datestamp", string);
			
			result.add(map);

		}
		return result;
	}

	@Override
	public Object dailyBoardRevenueReportRead(String fromdate, String todate,String counter_no,String myth) {
		List<?> list = reportDAO.boardRevenueReportRead(fromdate, todate, counter_no);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int i = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", i++);
			map.put("ward_no", obj[0]);
			map.put("opening_arrears", obj[1]);// pending
			map.put("water_charge", obj[2]);
			map.put("sw_charge", obj[3]);
			map.put("meter_rent", obj[4]);
			map.put("net_ammount", obj[5]);
			map.put("penalty", obj[6]);
			map.put("rebate", obj[7]);
			map.put("miscellaneous", obj[8]);
			map.put("total_toBePaid", obj[9]);
			map.put("total_paid", obj[10]);
			map.put("advance", obj[11]);
			map.put("advance_rebate", obj[12]);
			map.put("closing_balance", obj[13]);

			String rdate = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[14]);
			String english[] = rdate.split("-");
			int cday = Integer.parseInt(english[0]);
			int cmonth = Integer.parseInt(english[1]);

			if ("02".equalsIgnoreCase(english[1])) {
				cday = cday + 2;
			}
			if ("04".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("06".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			if ("09".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			int cyear = Integer.parseInt(english[2]);

			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);

			map.put("rdate", nepalimonthyear);
			result.add(map);
		}
		return result;}
	
	
	
}
