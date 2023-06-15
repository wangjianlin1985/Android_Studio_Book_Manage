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

/*图书类型管理业务逻辑层*/
public class BookTypeService {
	/* 添加图书类型 */
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

	/* 查询图书类型 */
	public List<BookType> QueryBookType(BookType queryConditionBookType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BookTypeServlet?action=query";
		if(queryConditionBookType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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

	/* 更新图书类型 */
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

	/* 删除图书类型 */
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
			return "图书类型信息删除失败!";
		}
	}

	/* 根据图书类别获取图书类型对象 */
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
