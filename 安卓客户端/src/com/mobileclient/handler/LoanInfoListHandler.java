package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LoanInfo;
public class LoanInfoListHandler extends DefaultHandler {
	private List<LoanInfo> loanInfoList = null;
	private LoanInfo loanInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (loanInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("loadId".equals(tempString)) 
            	loanInfo.setLoadId(new Integer(valueString).intValue());
            else if ("book".equals(tempString)) 
            	loanInfo.setBook(valueString); 
            else if ("reader".equals(tempString)) 
            	loanInfo.setReader(valueString); 
            else if ("borrowDate".equals(tempString)) 
            	loanInfo.setBorrowDate(Timestamp.valueOf(valueString));
            else if ("returnDate".equals(tempString)) 
            	loanInfo.setReturnDate(Timestamp.valueOf(valueString));
            else if ("memo".equals(tempString)) 
            	loanInfo.setMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LoanInfo".equals(localName)&&loanInfo!=null){
			loanInfoList.add(loanInfo);
			loanInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		loanInfoList = new ArrayList<LoanInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LoanInfo".equals(localName)) {
            loanInfo = new LoanInfo(); 
        }
        tempString = localName; 
	}

	public List<LoanInfo> getLoanInfoList() {
		return this.loanInfoList;
	}
}
