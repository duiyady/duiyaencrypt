package com.duiya.util;

import java.io.File;
import java.io.FileOutputStream;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class SetMessageUtil {
	private static String fileDefaultType;
	private static String fileDefaultPath;
	private static Element rootElement;
	private static Document document;
	private static Element postfixElement;
	private static Element defaultpathElement;
	private static SAXReader saxReader;
	private static File inputXml;
	public SetMessageUtil(){
		saxReader = new SAXReader(); 
		inputXml = new File("src/resources/configure.xml");
		if(!inputXml.exists()){
			rootElement = DocumentHelper.createElement("basic");
			document = DocumentHelper.createDocument(rootElement);
			postfixElement = rootElement.addElement("postfix");
			defaultpathElement = rootElement.addElement("defaultpath");
			postfixElement.addText("duiya");
			defaultpathElement.addText("default");
			OutputFormat format = new OutputFormat("",true);
			format.setEncoding("UTF-8");//…Ë÷√±‡¬Î∏Ò Ω  
	        XMLWriter xmlWriter = null;
			try {
				xmlWriter = new XMLWriter(new FileOutputStream("src/resources/configure.xml"),format);
				xmlWriter.write(document);
				xmlWriter.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
		try {     
			document = saxReader.read(inputXml);     
			rootElement=document.getRootElement();     
			postfixElement = rootElement.element("postfix");
			defaultpathElement = rootElement.element("defaultpath");
			fileDefaultType = postfixElement.getText();
			fileDefaultPath = defaultpathElement.getText();
		} catch (DocumentException e) {     
			System.out.println(e.getMessage());     
		}
	}
	
	public static void setFileDefaultType(String fileType){
		try {     
			document = saxReader.read(inputXml);     
			rootElement=document.getRootElement();     
			postfixElement = rootElement.element("postfix");
			postfixElement.setText(fileType);
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("src/resources/configure.xml"), format);
			xmlWriter.write(document);
			fileDefaultType = fileType;
		} catch (Exception e) {     
			System.out.println(e.getMessage());     
		}
	}
	public static void setFileDefaultPath(String filePath){
		try {     
			document = saxReader.read(inputXml);     
			rootElement=document.getRootElement();     
			defaultpathElement = rootElement.element("defaultpath");
			defaultpathElement.setText(filePath);
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("src/resources/configure.xml"), format);
			xmlWriter.write(document);
			fileDefaultPath = filePath;
		} catch (Exception e) {     
			System.out.println(e.getMessage());     
		}
	}
	public static String getFileDefaultType(){
		return fileDefaultType;
	}
	public static String getFileDefaultPath(){
		return fileDefaultPath;
	}
}
