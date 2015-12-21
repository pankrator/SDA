package bookkeeper;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class FrontBookkeeper61852 implements IFrontBookkeeper {
	public String updateFront(String[] news) {
		String output = "";
		
		HashMap<String, LinkedList<String>> units = new HashMap<String, LinkedList<String>>();
		for(String knowledge : news) {
			String[] split = knowledge.split(" ");
			if(split[1].equals("=")){
				LinkedList<String> soldiers = new LinkedList<String>();
				for(int i = 0; i < split.length - 2; i++) {
					String[] soldier = split[i+2].split("[|\\,|\\]");
					soldiers.add(soldier[0]);
				}
				
				units.put(split[0], soldiers);				
			}
			
			else if(split[1].equals("attached")) {
				if(split.length == 4){
					if(units.get(split[3]).addAll(units.get(split[0]))){
						units.put(split[3],units.get(split[3]));
					}					
				}
				else {
					for(int i =0; i < units.get(split[3]).size(); i++) {
						if(split[6].equals(units.get(split[3]).get(i))){
							if(units.get(split[3]).addAll(i+1,units.get(split[0]))){
								units.put(split[3],units.get(split[3]));
							}	
						}
					}
				}					
			}
			
			else if(split[0].equals("soldiers")) {
				String[] soldiers = split[1].split("..");
				LinkedList<String> soldiersToRemove = new LinkedList<String>();
				for(int i = 0; i < units.get(split[3]).size(); i++) {
					if(soldiers[0].equals(units.get(split[3]).get(i))){
						for(int j = i; j < units.get(split[3]).size(); j++) {
							if(soldiers[2].equals(units.get(split[3]).get(j))) {
								for(int k = i; k <= j; k++) {
									soldiersToRemove.add(units.get(split[3]).get(k));
									units.get(split[3]).remove(k);
								}
							}
						}
					}
				}
				for(LinkedList<String> list : units.values()) {
					if(list.equals(soldiersToRemove)) {
						list.clear();
					}
				}
			}
			
			else {
				if(split[1].equals("soldier")){
					for(LinkedList<String> list : units.values()){
						if(list.contains(split[2])){
							output += units.get(list) + ", ";
						}
					}
				}
				
				else {
					output = "[";
					for(int i = 0; i < units.get(split[1]).size(); i++) {
						if(i != units.get(split[1]).size() - 1) {
							output += units.get(split[1]).get(i) + ",";
						}
						else {
							output += units.get(split[1]).get(i) + "]";
						}
					}
				}
			}
		}
		
		return output;
	}
	
	public void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String[] news = new String[100000];
		String line = input.nextLine();
		for(int i = 0; i < 100000 && line.length() != 0; i++) {
			news[i] = line;
			line = input.nextLine();			
		}
		System.out.println(updateFront(news));
		
	}
}
