package sykim;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {
		// TODO Auto-generated method stub
//		makeCollection mc = new makeCollection("textsample");
//		makeKeyword mk = new makeKeyword("src/Collection.xml");

		
		System.out.println(args.length);
		
		if (args.length == 2) {
			System.out.println(args[0]);
			System.out.println(args[1]);
			if (args[0].equals("-c")) {
				@SuppressWarnings("unused")
				makeCollection mc = new makeCollection(args[1]);
			}
			else if (args[0].equals("-k")) {
				@SuppressWarnings("unused")
				makeKeyword mk = new makeKeyword(args[1]);
			}
			
		}
		else {
			System.err.println("올바른 옵션을 입력하세요");
			System.exit(1);
		}


	}

}
