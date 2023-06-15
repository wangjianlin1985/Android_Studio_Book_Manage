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

/*�������͹���ҵ���߼���*/
public class ReaderTypeService {
	/* ��Ӷ������� */
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

	/* ��ѯ�������� */
	public List<ReaderType> QueryReaderType(ReaderType queryConditionReaderType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ReaderTypeServlet?action=query";
		if(queryConditionReaderType != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
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
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
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

	/* ���¶������� */
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

	/* ɾ���������� */
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
			return "����������Ϣɾ��ʧ��!";
		}
	}

	/* ���ݶ������ͱ�Ż�ȡ�������Ͷ��� */
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
