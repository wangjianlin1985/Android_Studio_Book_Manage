package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Reader;
public class ReaderListHandler extends DefaultHandler {
	private List<Reader> readerList = null;
	private Reader reader;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (reader != null) { 
            String valueString = new String(ch, start, length); 
            if ("readerNo".equals(tempString)) 
            	reader.setReaderNo(valueString); 
            else if ("password".equals(tempString)) 
            	reader.setPassword(valueString); 
            else if ("readerType".equals(tempString)) 
            	reader.setReaderType(new Integer(valueString).intValue());
            else if ("readerName".equals(tempString)) 
            	reader.setReaderName(valueString); 
            else if ("sex".equals(tempString)) 
            	reader.setSex(valueString); 
            else if ("photo".equals(tempString)) 
            	reader.setPhoto(valueString); 
            else if ("birthday".equals(tempString)) 
            	reader.setBirthday(Timestamp.valueOf(valueString));
            else if ("telephone".equals(tempString)) 
            	reader.setTelephone(valueString); 
            else if ("email".equals(tempString)) 
            	reader.setEmail(valueString); 
            else if ("qq".equals(tempString)) 
            	reader.setQq(valueString); 
            else if ("address".equals(tempString)) 
            	reader.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Reader".equals(localName)&&reader!=null){
			readerList.add(reader);
			reader = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		readerList = new ArrayList<Reader>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Reader".equals(localName)) {
            reader = new Reader(); 
        }
        tempString = localName; 
	}

	public List<Reader> getReaderList() {
		return this.readerList;
	}
}
