package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.ReaderType;
import com.mobileclient.util.HttpUtil;

/*读者类型管理业务逻辑层*/
public class ReaderTypeService {
	/* 添加读者类型 */
	public String AddReaderType(ReaderType readerType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerTypeId", readerType.getReaderTypeId() + "");
		params.put("readerTypeName", readerType.getReaderTypeName());
		params.put("number", readerType.getNumber() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询读者类型 */
	public List<ReaderType> QueryReaderType(ReaderType queryConditionReaderType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ReaderTypeServlet?action=query";
		if(queryConditionReaderType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ReaderTypeListHandler readerTypeListHander = new ReaderTypeListHandler();
		xr.setContentHandler(readerTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<ReaderType> readerTypeList = readerTypeListHander.getReaderTypeList();
		return readerTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<ReaderType> readerTypeList = new ArrayList<ReaderType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ReaderType readerType = new ReaderType();
				readerType.setReaderTypeId(object.getInt("readerTypeId"));
				readerType.setReaderTypeName(object.getString("readerTypeName"));
				readerType.setNumber(object.getInt("number"));
				readerTypeList.add(readerType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readerTypeList;
	}

	/* 更新读者类型 */
	public String UpdateReaderType(ReaderType readerType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerTypeId", readerType.getReaderTypeId() + "");
		params.put("readerTypeName", readerType.getReaderTypeName());
		params.put("number", readerType.getNumber() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除读者类型 */
	public String DeleteReaderType(int readerTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerTypeId", readerTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "读者类型信息删除失败!";
		}
	}

	/* 根据读者类型编号获取读者类型对象 */
	public ReaderType GetReaderType(int readerTypeId)  {
		List<ReaderType> readerTypeList = new ArrayList<ReaderType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerTypeId", readerTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				ReaderType readerType = new ReaderType();
				readerType.setReaderTypeId(object.getInt("readerTypeId"));
				readerType.setReaderTypeName(object.getString("readerTypeName"));
				readerType.setNumber(object.getInt("number"));
				readerTypeList.add(readerType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = readerTypeList.size();
		if(size>0) return readerTypeList.get(0); 
		else return null; 
	}
}
