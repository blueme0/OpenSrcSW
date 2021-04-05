package sykim;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {

	public searcher(String route, String query) throws ClassNotFoundException, IOException, SAXException, ParserConfigurationException {
		super();
		double[] querySim = CalcSim(route, query);
		int size = querySim.length;
		int[] seq = new int[size];
		for (int i=0 ; i<seq.length; i++) {
			seq[i] = i;
		}
		
		/* 확인용
		System.out.println("===========");
		System.out.println("모든 문서 출력");
		for (int i=0; i<size; i++) {
			System.out.println(querySim[i]);
		} 
		System.out.println("===========");
		*/

		// 큰 수부터 순서대로 정렬
		
		for (int i=0; i<size; i++) {
			for (int j=i+1; j<size; j++) {
				if (querySim[i] < querySim[j]) {
					double tempDouble = querySim[i];
					querySim[i] = querySim[j];
					querySim[j] = tempDouble;
					int tempInt = seq[i];
					seq[i] = seq[j];
					seq[j] = tempInt;
				}
				else if(querySim[i] == querySim[j]) {
					if (seq[i] > seq[j]) {
						double tempDouble = querySim[i];
						querySim[i] = querySim[j];
						querySim[j] = tempDouble;
						int tempInt = seq[i];
						seq[i] = seq[j];
						seq[j] = tempInt;
					}
						//원래 i<j 임, 근데 seq[i] < seq[j]가 아니면 바꾸기 (같을 경우 인덱스 순으로 정렬)
				}
			}
		}
		System.out.println("==========");
		System.out.println("상위 3개 문서");
		for (int i=0; i<3 ;i++) {
			System.out.println(i+1 + ") " + getName(seq[i]));
		}
		System.out.println("==========");
		
	}
	
	public String getName(int id) throws ParserConfigurationException, SAXException, IOException {
		
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document document = docBuilder.parse("Collection.xml");
		document.getDocumentElement().normalize();

		NodeList doclist = document.getElementsByTagName("doc");
		
		Node doclistNode = doclist.item(id);
		
		if (doclistNode.getNodeType() == Node.ELEMENT_NODE) {
			
			Element doclistElement = (Element) doclistNode;
			NodeList titleList = doclistElement.getElementsByTagName("title");
			Element titleElement = (Element) titleList.item(0);
			Node title = titleElement.getFirstChild();
			return title.getNodeValue();
			
		}
		return "fail";
		
	}
	
	@SuppressWarnings("unchecked")
	public double[] CalcSim(String route, String query) throws IOException, ClassNotFoundException, SAXException, ParserConfigurationException {
		
		FileInputStream fileIStream = new FileInputStream(route);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileIStream);
		
		Object ob = objectInputStream.readObject();
		objectInputStream.close();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document document = docBuilder.parse("Collection.xml");
		document.getDocumentElement().normalize();
		
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(query, true);
				
		String[][] klArr = new String[kl.size()][2];
		
		for (int j=0; j<kl.size(); j++) {
			Keyword kwrd = kl.get(j);
			klArr[j][0] = kwrd.getString();
			klArr[j][1] = Integer.toString(kwrd.getCnt());
		}

		NodeList doclist = document.getElementsByTagName("doc");
		
		double[] simArr = new double[doclist.getLength()];
		
		HashMap<String, List<String>> hashMap = (HashMap<String, List<String>>)ob;
		
		for (int i=0; i<klArr.length; i++) {
			if (hashMap.containsKey(klArr[i][0])) {
				List<String> temp = hashMap.get(klArr[i][0]);
				for (int j=0; j<temp.size()/2; j++) {
					simArr[Integer.parseInt(temp.get(j*2))] += Double.parseDouble(temp.get(j*2 + 1));
				}
			}
		}
		
		for (int i=0; i<simArr.length; i++) {
			simArr[i] = Math.round(simArr[i]*100)/100.0;
		}
		
		return simArr;

		// CalcSim : query를 입력받고 각 document 사이의 유사도를 return
	}

}