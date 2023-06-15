package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Book;
import com.mobileclient.util.HttpUtil;

/*ͼ�����ҵ���߼���*/
public class BookService {
	/* ���ͼ�� */
	public String AddBook(Book book) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("barcode", book.getBarcode());
		params.put("bookType", book.getBookType() + "");
		params.put("bookName", book.getBookName());
		params.put("bookPhoto", book.getBookPhoto());
		params.put("price", book.getPrice() + "");
		params.put("count", book.getCount() + "");
		params.put("publishDate", book.getPublishDate().toString());
		params.put("publish", book.getPublish());
		params.put("introduction", book.getIntroduction());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯͼ�� */
	public List<Book> QueryBook(Book queryConditionBook) throws Exception {
		String urlString = HttpUtil.BASE_URL + "BookServlet?action=query";
		if(queryConditionBook != null) {
			urlString += "&barcode=" + URLEncoder.encode(queryConditionBook.getBarcode(), "UTF-8") + "";
			urlString += "&bookType=" + queryConditionBook.getBookType();
			urlString += "&bookName=" + URLEncoder.encode(queryConditionBook.getBookName(), "UTF-8") + "";
			if(queryConditionBook.getPublishDate() != null) {
				urlString += "&publishDate=" + URLEncoder.encode(queryConditionBook.getPublishDate().toString(), "UTF-8");
			}
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		BookListHandler bookListHander = new BookListHandler();
		xr.setContentHandler(bookListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Book> bookList = bookListHander.getBookList();
		return bookList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Book> bookList = new ArrayList<Book>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Book book = new Book();
				book.setBarcode(object.getString("barcode"));
				book.setBookType(object.getInt("bookType"));
				book.setBookName(object.getString("bookName"));
				book.setBookPhoto(object.getString("bookPhoto"));
				book.setPrice((float) object.getDouble("price"));
				book.setCount(object.getInt("count"));
				book.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				book.setPublish(object.getString("publish"));
				book.setIntroduction(object.getString("introduction"));
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}

	/* ����ͼ�� */
	public String UpdateBook(Book book) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("barcode", book.getBarcode());
		params.put("bookType", book.getBookType() + "");
		params.put("bookName", book.getBookName());
		params.put("bookPhoto", book.getBookPhoto());
		params.put("price", book.getPrice() + "");
		params.put("count", book.getCount() + "");
		params.put("publishDate", book.getPublishDate().toString());
		params.put("publish", book.getPublish());
		params.put("introduction", book.getIntroduction());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ��ͼ�� */
	public String DeleteBook(String barcode) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("barcode", barcode);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ͼ����Ϣɾ��ʧ��!";
		}
	}

	/* ����ͼ���������ȡͼ����� */
	public Book GetBook(String barcode)  {
		List<Book> bookList = new ArrayList<Book>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("barcode", barcode);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "BookServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Book book = new Book();
				book.setBarcode(object.getString("barcode"));
				book.setBookType(object.getInt("bookType"));
				book.setBookName(object.getString("bookName"));
				book.setBookPhoto(object.getString("bookPhoto"));
				book.setPrice((float) object.getDouble("price"));
				book.setCount(object.getInt("count"));
				book.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				book.setPublish(object.getString("publish"));
				book.setIntroduction(object.getString("introduction"));
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = bookList.size();
		if(size>0) return bookList.get(0); 
		else return null; 
	}
}
