package bg.uni_sofia.fmi.sda.ww1;

public class Main {
	public static void main(String[] args) {
		
		String news[]={
			    "regiment_Stoykov = [1, 2, 3]",
			    "show regiment_Stoykov",
			    "regiment_Chaushev = [13]",
			    "show soldier 13",
			    "division_Dimitroff = []",  
			    "regiment_Stoykov attached to division_Dimitroff",
			    "regiment_Chaushev attached to division_Dimitroff",
			    "show division_Dimitroff",  
			    "show soldier 13",
			    "brigade_Ignatov = []",
			    "regiment_Stoykov attached to brigade_Ignatov",
			    "regiment_Chaushev attached to brigade_Ignatov after soldier 3",
			    "show brigade_Ignatov",  
			    "show division_Dimitroff",
			    "brigade_Ignatov attached to division_Dimitroff",
			    "show division_Dimitroff",
			    "soldiers 2..3 from division_Dimitroff died heroically",
			    "show regiment_Stoykov",
			    "show brigade_Ignatov",
			    "show division_Dimitroff"		    
			  };
		
		String[] news2 = {
				"squad = [1]", 
				"patrol = []", 
				"company = []",
				"battalion = []",
				"squad attached to patrol",
				"patrol attached to company", 
				"company attached to battalion",
				"show soldier 1"
			};
			
		
		System.out.println("First news update: ");
		FrontBookkeeper61786 fbk = new FrontBookkeeper61786();	
		fbk.updateFront(news);
		
		System.out.println();
		
		System.out.println("Second news update: ");
		FrontBookkeeper61786 fbk2 = new FrontBookkeeper61786();
		fbk2.updateFront(news2);
	}
}
