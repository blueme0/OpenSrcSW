package sykim;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		if (args.length == 2) {
			if (args[0].equals("-c")) {
				@SuppressWarnings("unused")
				makeCollection mc = new makeCollection(args[1]);
			}
			else if (args[0].equals("-k")) {
				@SuppressWarnings("unused")
				makeKeyword mk = new makeKeyword(args[1]);
			}
			else if (args[0].equals("-i")) {
				@SuppressWarnings("unused")
				indexer in = new indexer(args[1]);
			}
			
		}
		else if (args.length == 4) {
			if (args[0].equals("-s") && args[2].equals("-q")) {
				searcher sc = new searcher(args[1], args[3]);
			}
		}
		else {
			System.err.println("올바른 옵션을 입력하세요");
			System.exit(1);
		}
		
		
		/*
		
		zsh (cmd) 창에서 실행할 때
		
		kimseyeon@gimseyeon-ui-MacBookPro ~ % cd SimpleIR/src
		kimseyeon@gimseyeon-ui-MacBookPro src % cd sykim
		kimseyeon@gimseyeon-ui-MacBookPro sykim % javac -cp ".:../../jsoup-1.13.1.jar:../../kkma-2.1.jar" *.java
		kimseyeon@gimseyeon-ui-MacBookPro sykim % cd ..
		kimseyeon@gimseyeon-ui-MacBookPro src % java -cp ../jsoup-1.13.1.jar: sykim.kuir -c ../data
		kimseyeon@gimseyeon-ui-MacBookPro src % java -cp ../kkma-2.1.jar: sykim.kuir -k Collection.xml
		kimseyeon@gimseyeon-ui-MacBookPro src % java sykim.kuir -i index.xml
		kimseyeon@gimseyeon-ui-MacBookPro src % java -cp ../kkma-2.1.jar: sykim.kuir -s index.post -q "라면에는 면, 분말 스프가 있다."
		 
		*/


	}

}
