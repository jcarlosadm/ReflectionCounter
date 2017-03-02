package com.reflectCounter.maven.explorer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MavenExplorer {

	private File pom = null;

	public MavenExplorer(File pom) {
		this.pom = pom;
	}
	
	private String getXmlInfo(String elementName) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(this.pom);
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName(elementName);
		
		Node nNode = nList.item(0);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nNode;
			return eElement.getTextContent().trim();
		}
		
		return null;
	}

	public String getGroup() {
		try {
			return this.getXmlInfo("groupId");
		} catch (Exception e) {
			return null;
		}
	}

	public String getArtifact() {
		try {
			return this.getXmlInfo("artifactId");
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getVersion() {
		try {
			return this.getXmlInfo("version");
		} catch (Exception e) {
			return null;
		}
	}

}
