package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.BookType;
import com.mobileclient.util.HttpUtil;

/*ͼ�����͹���ҵ���߼���*/
public class BookTypeService {
	/* ���ͼ������ */
	public String AddBookType(BookType bookType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("bookTypeId", bookType.getBookTypeId() + "");
		params.put("bookTypeName", bookType.getBookTypeName());
		params.put("days", bookType.getDays() + "");
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯͼ������ */
	public List<BookType> QueryBookType(BookType queryConditionBookType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BookTypeServlet?action=query";
		if(queryConditionBookType != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		BookTypeListHandler bookTypeListHander = new BookTypeListHandler();
		xr.setContentHandler(bookTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<BookType> bookTypeList = bookTypeListHander.getBookTypeList();
		return bookTypeList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<BookType> bookTypeList = new ArrayList<BookType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BookType bookType = new BookType();
				bookType.setBookTypeId(object.getInt("bookTypeId"));
				bookType.setBookTypeName(object.getString("bookTypeName"));
				bookType.setDays(object.getInt("days"));
				bookTypeList.add(bookType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookTypeList;
	}

	/* ����ͼ������ */
	public String UpdateBookType(BookType bookType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("bookTypeId", bookType.getBookTypeId() + "");
		params.put("bookTypeName", bookType.getBookTypeName());
		params.put("days", bookType.getDays() + "");
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ͼ������ */
	public String DeleteBookType(int bookTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("bookTypeId", bookTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ͼ��������Ϣɾ��ʧ��!";
		}
	}

	/* ����ͼ������ȡͼ�����Ͷ��� */
	public BookType GetBookType(int bookTypeId)  {
		List<BookType> bookTypeList = new ArrayList<BookType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("bookTypeId", bookTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				BookType bookType = new BookType();
				bookType.setBookTypeId(object.getInt("bookTypeId"));
				bookType.setBookTypeName(object.getString("bookTypeName"));
				bookType.setDays(object.getInt("days"));
				bookTypeList.add(bookType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = bookTypeList.size();
		if(size>0) return bookTypeList.get(0); 
		else return null; 
	}
}
