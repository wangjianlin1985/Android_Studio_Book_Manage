package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Reader;
import com.mobileclient.util.HttpUtil;

/*读者管理业务逻辑层*/
public class ReaderService {
	/* 添加读者 */
	public String AddReader(Reader reader) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerNo", reader.getReaderNo());
		params.put("password", reader.getPassword());
		params.put("readerType", reader.getReaderType() + "");
		params.put("readerName", reader.getReaderName());
		params.put("sex", reader.getSex());
		params.put("photo", reader.getPhoto());
		params.put("birthday", reader.getBirthday().toString());
		params.put("telephone", reader.getTelephone());
		params.put("email", reader.getEmail());
		params.put("qq", reader.getQq());
		params.put("address", reader.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询读者 */
	public List<Reader> QueryReader(Reader queryConditionReader) throws Exception {
		String urlString = HttpUtil.BASE_URL + "ReaderServlet?action=query";
		if(queryConditionReader != null) {
			urlString += "&readerNo=" + URLEncoder.encode(queryConditionReader.getReaderNo(), "UTF-8") + "";
			urlString += "&readerType=" + queryConditionReader.getReaderType();
			urlString += "&readerName=" + URLEncoder.encode(queryConditionReader.getReaderName(), "UTF-8") + "";
			if(queryConditionReader.getBirthday() != null) {
				urlString += "&birthday=" + URLEncoder.encode(queryConditionReader.getBirthday().toString(), "UTF-8");
			}
			urlString += "&telephone=" + URLEncoder.encode(queryConditionReader.getTelephone(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		ReaderListHandler readerListHander = new ReaderListHandler();
		xr.setContentHandler(readerListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Reader> readerList = readerListHander.getReaderList();
		return readerList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Reader> readerList = new ArrayList<Reader>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Reader reader = new Reader();
				reader.setReaderNo(object.getString("readerNo"));
				reader.setPassword(object.getString("password"));
				reader.setReaderType(object.getInt("readerType"));
				reader.setReaderName(object.getString("readerName"));
				reader.setSex(object.getString("sex"));
				reader.setPhoto(object.getString("photo"));
				reader.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				reader.setTelephone(object.getString("telephone"));
				reader.setEmail(object.getString("email"));
				reader.setQq(object.getString("qq"));
				reader.setAddress(object.getString("address"));
				readerList.add(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readerList;
	}

	/* 更新读者 */
	public String UpdateReader(Reader reader) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerNo", reader.getReaderNo());
		params.put("password", reader.getPassword());
		params.put("readerType", reader.getReaderType() + "");
		params.put("readerName", reader.getReaderName());
		params.put("sex", reader.getSex());
		params.put("photo", reader.getPhoto());
		params.put("birthday", reader.getBirthday().toString());
		params.put("telephone", reader.getTelephone());
		params.put("email", reader.getEmail());
		params.put("qq", reader.getQq());
		params.put("address", reader.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除读者 */
	public String DeleteReader(String readerNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerNo", readerNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "读者信息删除失败!";
		}
	}

	/* 根据读者编号获取读者对象 */
	public Reader GetReader(String readerNo)  {
		List<Reader> readerList = new ArrayList<Reader>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("readerNo", readerNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "ReaderServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Reader reader = new Reader();
				reader.setReaderNo(object.getString("readerNo"));
				reader.setPassword(object.getString("password"));
				reader.setReaderType(object.getInt("readerType"));
				reader.setReaderName(object.getString("readerName"));
				reader.setSex(object.getString("sex"));
				reader.setPhoto(object.getString("photo"));
				reader.setBirthday(Timestamp.valueOf(object.getString("birthday")));
				reader.setTelephone(object.getString("telephone"));
				reader.setEmail(object.getString("email"));
				reader.setQq(object.getString("qq"));
				reader.setAddress(object.getString("address"));
				readerList.add(reader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = readerList.size();
		if(size>0) return readerList.get(0); 
		else return null; 
	}
}
