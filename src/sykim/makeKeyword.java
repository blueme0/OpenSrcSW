package sykim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {

	String str;

	public makeKeyword(String str) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		super();
		doKeyword(str);
	}
	
	public void doKeyword(String str) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document document = docBuilder.parse(str);
		document.getDocumentElement().normalize();
		
		Document nDocument = docBuilder.newDocument();
		nDocument.setXmlStandalone(true); //standalone="no" 를 없애준다.
		
		Element docs = nDocument.createElement("docs");
		nDocument.appendChild(docs);

		NodeList doclist = document.getElementsByTagName("doc");
		
		for (int i=0; i<doclist.getLength(); i++) {
			Node doclistNode = doclist.item(i);
			
			if (doclistNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element doclistElement = (Element) doclistNode;
				
				Element eDoc = nDocument.createElement("doc");
				docs.appendChild(eDoc);
				eDoc.setAttribute("id", doclistElement.getAttribute("id")); // 옵션도 다시 설정
				

				NodeList titleList = doclistElement.getElementsByTagName("title");
				Element titleElement = (Element) titleList.item(0);
				Node title = titleElement.getFirstChild();
				
				Element eTitle = nDocument.createElement("title");
				eTitle.appendChild(nDocument.createTextNode(title.getNodeValue()));
				eDoc.appendChild(eTitle);
				
				NodeList bodyList = doclistElement.getElementsByTagName("body");
				Element bodyElement = (Element) bodyList.item(0);
				Node body = bodyElement.getFirstChild();
				
				// 여기에 꼬꼬마 ^^.....
				
				String testString = body.getNodeValue();
				String finString = "";
				
				KeywordExtractor ke = new KeywordExtractor();
				KeywordList kl = ke.extractKeyword(testString, true);
				
				for (int j=0; j<kl.size(); j++) {
					Keyword kwrd = kl.get(j);
					finString += kwrd.getString() + ":" + kwrd.getCnt() + "#";
				}
				
				Element eBody = nDocument.createElement("body");
				eBody.appendChild(nDocument.createTextNode(finString));
				eDoc.appendChild(eBody);
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
		DOMSource source = new DOMSource(nDocument);
		StreamResult result = new StreamResult(new FileOutputStream(new File("index.xml")));
		
		transformer.transform(source, result);	
	}
}
