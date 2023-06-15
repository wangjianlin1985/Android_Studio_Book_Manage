package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LoanInfo;
import com.mobileclient.util.HttpUtil;

/*借阅信息管理业务逻辑层*/
public class LoanInfoService {
	/* 添加借阅信息 */
	public String AddLoanInfo(LoanInfo loanInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("loadId", loanInfo.getLoadId() + "");
		params.put("book", loanInfo.getBook());
		params.put("reader", loanInfo.getReader());
		params.put("borrowDate", loanInfo.getBorrowDate().toString());
		params.put("returnDate", loanInfo.getReturnDate().toString());
		params.put("memo", loanInfo.getMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LoanInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询借阅信息 */
	public List<LoanInfo> QueryLoanInfo(LoanInfo queryConditionLoanInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LoanInfoServlet?action=query";
		if(queryConditionLoanInfo != null) {
			urlString += "&book=" + URLEncoder.encode(queryConditionLoanInfo.getBook(), "UTF-8") + "";
			urlString += "&reader=" + URLEncoder.encode(queryConditionLoanInfo.getReader(), "UTF-8") + "";
			if(queryConditionLoanInfo.getBorrowDate() != null) {
				urlString += "&borrowDate=" + URLEncoder.encode(queryConditionLoanInfo.getBorrowDate().toString(), "UTF-8");
			}
			if(queryConditionLoanInfo.getReturnDate() != null) {
				urlString += "&returnDate=" + URLEncoder.encode(queryConditionLoanInfo.getReturnDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LoanInfoListHandler loanInfoListHander = new LoanInfoListHandler();
		xr.setContentHandler(loanInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LoanInfo> loanInfoList = loanInfoListHander.getLoanInfoList();
		return loanInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LoanInfo> loanInfoList = new ArrayList<LoanInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setLoadId(object.getInt("loadId"));
				loanInfo.setBook(object.getString("book"));
				loanInfo.setReader(object.getString("reader"));
				loanInfo.setBorrowDate(Timestamp.valueOf(object.getString("borrowDate")));
				loanInfo.setReturnDate(Timestamp.valueOf(object.getString("returnDate")));
				loanInfo.setMemo(object.getString("memo"));
				loanInfoList.add(loanInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanInfoList;
	}

	/* 更新借阅信息 */
	public String UpdateLoanInfo(LoanInfo loanInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("loadId", loanInfo.getLoadId() + "");
		params.put("book", loanInfo.getBook());
		params.put("reader", loanInfo.getReader());
		params.put("borrowDate", loanInfo.getBorrowDate().toString());
		params.put("returnDate", loanInfo.getReturnDate().toString());
		params.put("memo", loanInfo.getMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LoanInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除借阅信息 */
	public String DeleteLoanInfo(int loadId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("loadId", loadId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LoanInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "借阅信息信息删除失败!";
		}
	}

	/* 根据借阅编号获取借阅信息对象 */
	public LoanInfo GetLoanInfo(int loadId)  {
		List<LoanInfo> loanInfoList = new ArrayList<LoanInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("loadId", loadId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LoanInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setLoadId(object.getInt("loadId"));
				loanInfo.setBook(object.getString("book"));
				loanInfo.setReader(object.getString("reader"));
				loanInfo.setBorrowDate(Timestamp.valueOf(object.getString("borrowDate")));
				loanInfo.setReturnDate(Timestamp.valueOf(object.getString("returnDate")));
				loanInfo.setMemo(object.getString("memo"));
				loanInfoList.add(loanInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = loanInfoList.size();
		if(size>0) return loanInfoList.get(0); 
		else return null; 
	}
}
