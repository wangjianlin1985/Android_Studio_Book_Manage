package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Book;
public class BookListHandler extends DefaultHandler {
	private List<Book> bookList = null;
	private Book book;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (book != null) { 
            String valueString = new String(ch, start, length); 
            if ("barcode".equals(tempString)) 
            	book.setBarcode(valueString); 
            else if ("bookType".equals(tempString)) 
            	book.setBookType(new Integer(valueString).intValue());
            else if ("bookName".equals(tempString)) 
            	book.setBookName(valueString); 
            else if ("bookPhoto".equals(tempString)) 
            	book.setBookPhoto(valueString); 
            else if ("price".equals(tempString)) 
            	book.setPrice(new Float(valueString).floatValue());
            else if ("count".equals(tempString)) 
            	book.setCount(new Integer(valueString).intValue());
            else if ("publishDate".equals(tempString)) 
            	book.setPublishDate(Timestamp.valueOf(valueString));
            else if ("publish".equals(tempString)) 
            	book.setPublish(valueString); 
            else if ("introduction".equals(tempString)) 
            	book.setIntroduction(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Book".equals(localName)&&book!=null){
			bookList.add(book);
			book = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		bookList = new ArrayList<Book>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Book".equals(localName)) {
            book = new Book(); 
        }
        tempString = localName; 
	}

	public List<Book> getBookList() {
		return this.bookList;
	}
}
