package sykim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class midterm {
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String[] queryArr;
		if (args.length == 4) {
			if (args[0].equals("-f") && args[2].equals("-q")) {
				queryArr = args[3].split(" ");
				String nowSen = "";
				int count = 0;
				int lastCount = 0;
				try {
					File textFile = new File(args[1]);
					@SuppressWarnings("resource")
					Scanner fileScan = new Scanner(textFile);
					while(fileScan.hasNextLine()) {
						String textTemp = fileScan.nextLine();
						String[] textArr = textTemp.split(" ");
						for (int i=0; i<queryArr.length; i++) {
							for(int j=0; j<textArr.length; j++) {
								if(queryArr[i].equals(textArr[j]))
									count++;
							}
						}
						if (count > lastCount) nowSen = textTemp;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(nowSen);
			}
		}
		else {
			System.err.println("올바른 옵션을 입력하세요");
			System.exit(1);
		}

	}


}
