package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.ReaderType;
public class ReaderTypeListHandler extends DefaultHandler {
	private List<ReaderType> readerTypeList = null;
	private ReaderType readerType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (readerType != null) { 
            String valueString = new String(ch, start, length); 
            if ("readerTypeId".equals(tempString)) 
            	readerType.setReaderTypeId(new Integer(valueString).intValue());
            else if ("readerTypeName".equals(tempString)) 
            	readerType.setReaderTypeName(valueString); 
            else if ("number".equals(tempString)) 
            	readerType.setNumber(new Integer(valueString).intValue());
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("ReaderType".equals(localName)&&readerType!=null){
			readerTypeList.add(readerType);
			readerType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		readerTypeList = new ArrayList<ReaderType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("ReaderType".equals(localName)) {
            readerType = new ReaderType(); 
        }
        tempString = localName; 
	}

	public List<ReaderType> getReaderTypeList() {
		return this.readerTypeList;
	}
}
