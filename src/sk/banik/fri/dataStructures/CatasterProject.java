package sk.banik.fri.dataStructures;
import java.util.Scanner;

import sk.banik.fri.dataStructures.model.CatastralArea;
import sk.banik.fri.dataStructures.model.CatastralOffice;
import sk.banik.fri.dataStructures.model.Owner;


public class CatasterProject {

	public static void main(String[] args) {
		boolean running = true;
		CatasterProject app = new CatasterProject();
		System.out.println("#########################");
		System.out.println("## hello                #");
		System.out.println("#########################");
		app.printHelp();
		
		while (running){
			System.out.print(">");
			Scanner in = new Scanner(System.in);
			String line = in.nextLine();
			if (line.equals("exit")) {
				break;
			}
			// TODO implement console
			app.processConsoleInput(line);
		}
	}
	
	
	private CatastralOffice cataster;
	
	public CatasterProject() {
		cataster = new CatastralOffice();
	}

	private void processConsoleInput(String line) {
		switch (line) {
		case "1": // init DB
			initDB();
			break;
		case "2": // delete DB
			
			break;
		case "3": // feature 7
			
			break;
		case "4": // feature 8
			
			break;
		case "5": // feature 15
			
			break;
		case "6": // feature 16
			
			break;
		case "7": // feature 17\
			deleteDB();
			break;
			
		default:
			// show help
			printHelp();
			break;
		}
	}

	private void deleteDB() {
		cataster = new CatastralOffice();
	}

	private void initDB() {
		BasicMapCollection<Integer, CatastralArea> areasById = cataster.getAreasById();
		
		BasicMapCollection<String, Owner> owners = cataster.getOwners();
		Owner o = new Owner();
		o.setFirstName("user");
		o.setLastname("first");
		o.setBornNumber("1111111111111111");
		owners.insert(o.getBornNumber(), o);
	}

	private void printHelp() {
		String helper = "1 - init DB\n"
				+ "2 - delete DB\n"
				+ "3 - feature 7\n"
				+ "4 - feature 8\n"
				+ "5 - feature 15\n"
				+ "6 - feature 16\n"
				+ "7 - feature 17\n";
		System.out.println(helper);
	}

}
