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

import org.jsoup.Jsoup;
import org.jsoup.parser.Tag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class makeCollection {
	
	String str;

	public makeCollection(String str) throws ParserConfigurationException, IOException, TransformerException {
		super();
		fromPath(str);
	}
	
	public void fromPath(String str) throws ParserConfigurationException, IOException, TransformerException {
		File path = new File(str);
		File[] files = path.listFiles();
		int fileNum = files.length;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document document = docBuilder.newDocument();
		document.setXmlStandalone(true); //standalone="no" 를 없애준다.
		
		
		Element docs = document.createElement("docs");
		document.appendChild(docs);
		
		for (int i=0; i<fileNum; i++) {
			Element doc = document.createElement("doc");
			docs.appendChild(doc);
			
			doc.setAttribute("id", Integer.toString(i));
			
			org.jsoup.nodes.Document origin = Jsoup.parse(files[i], "UTF-8");
		
			
			String titleText = origin.title();
			
			Element title = document.createElement("title");
			title.appendChild(document.createTextNode(titleText));
			doc.appendChild(title);
			
			org.jsoup.nodes.Element bodyText = origin.body();
			bodyText.replaceWith(new org.jsoup.nodes.Element(Tag.valueOf("p")," "));
			Element body = document.createElement("body");
		
			body.appendChild(document.createTextNode(bodyText.text()));
			doc.appendChild(body);
		}
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new FileOutputStream(new File("Collection.xml")));
		
		transformer.transform(source, result);		
	}

}
