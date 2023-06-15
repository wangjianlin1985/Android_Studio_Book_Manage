package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.BookType;
public class BookTypeListHandler extends DefaultHandler {
	private List<BookType> bookTypeList = null;
	private BookType bookType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (bookType != null) { 
            String valueString = new String(ch, start, length); 
            if ("bookTypeId".equals(tempString)) 
            	bookType.setBookTypeId(new Integer(valueString).intValue());
            else if ("bookTypeName".equals(tempString)) 
            	bookType.setBookTypeName(valueString); 
            else if ("days".equals(tempString)) 
            	bookType.setDays(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("BookType".equals(localName)&&bookType!=null){
			bookTypeList.add(bookType);
			bookType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		bookTypeList = new ArrayList<BookType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("BookType".equals(localName)) {
            bookType = new BookType(); 
        }
        tempString = localName; 
	}

	public List<BookType> getBookTypeList() {
		return this.bookTypeList;
	}
}
