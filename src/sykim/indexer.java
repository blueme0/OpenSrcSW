package sykim;

//import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class indexer {

	public indexer(String str) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException {
		super();
		doIndexer(str);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "nls" })
	public void doIndexer(String str) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
		docFactory.setIgnoringElementContentWhitespace(true);
		docFactory.setIgnoringElementContentWhitespace(true);


		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document = docBuilder.parse(str);
		document.getDocumentElement().normalize();
		
		Element root = document.getDocumentElement();		
		
		FileOutputStream fileOStream = new FileOutputStream("index.post");
		
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOStream);
		
		HashMap<String, List<String>> keywordMap = new HashMap<>();
		
		NodeList docList = root.getChildNodes();
		int docLength = docList.getLength();
		int newDocLength = docLength;

		for (int i=0; i<docLength; i++) {
//			System.out.println(docList.item(i).getNodeName());
			if (docList.item(i).getNodeName().equals("#text")) {
				newDocLength--;
				
			}
			else {
				Element doc = (Element)docList.item(i);
				String docid = doc.getAttribute("id");
				String body = doc.getElementsByTagName("body").item(0).getTextContent();
				String[] splitWord = body.split("#");
				for (int j=0; j<splitWord.length; j++) {
					String[] splitTime = splitWord[j].split(":");
					if (splitTime[0] != null && splitTime[1] != null) {
						List<String> list = new ArrayList<>();
						// key 값이 현재 문서에 있는지
						if (keywordMap.containsKey(splitTime[0])) {
							keywordMap.get(splitTime[0]).add(docid);			// 정상적으로 추가되었는지 확인, temp 사용해서 해야하는지
							keywordMap.get(splitTime[0]).add(splitTime[1]);
						}
						else {
							list.add(docid);
							list.add(splitTime[1]);
							keywordMap.put(splitTime[0], list);
						}
					}
				}
			}
				
		}
//		System.out.println(newDocLength);
		for (String key : keywordMap.keySet()) {
			List<String> temp = keywordMap.get(key);
			double wordTotalTime = temp.size() / 2;
			for (int i=0; i<wordTotalTime; i++) {
				String str1 = temp.get(i * 2 + 1);
				double tfidf = Integer.parseInt(str1) * Math.log(newDocLength/wordTotalTime);
				tfidf = Math.round(tfidf*100)/100.0;
				temp.set(i*2 +1, Double.toString(tfidf));
			}
		}
		
		objectOutputStream.writeObject(keywordMap);
		objectOutputStream.close();
		
		
/*		출력 예제
		FileInputStream fileIStream = new FileInputStream("src/index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileIStream);
		
		Object ob = objectInputStream.readObject();
		objectInputStream.close();
		
		System.out.println("읽어올 객체의 type -> " + ob.getClass());
		HashMap hashMap = (HashMap)ob;
		Iterator<String> it = hashMap.keySet().iterator();
		
		while(it.hasNext()) {
			String key1 = it.next();
			List value1 = (List)hashMap.get(key1);
			System.out.println(key1 + " -> " + value1);
		}
		*/
	}
	
	

}
