package bg.sofia.uni.fmi.sda.frontbookkeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FrontBookkeeper implements IFrontBookkeeper {

	private Map<String,Unit> front;
	
	private StringBuilder sb;
	public FrontBookkeeper() {
		front = new HashMap<String, Unit>();
		sb = new StringBuilder();
	}
	
	private void processLine(String line){
		line = line.trim();
		
		if(line.split("=").length==2){
			String unitName = line.split("=")[0].trim();
			String rawLineSoldiers = line.split("=")[1].trim();
			String rawSoldiers = rawLineSoldiers.replaceAll("[\\[\\]]", "").trim();
			if(rawSoldiers.equals("")){
				Unit u = new Unit();
				front.put(unitName, u);
			}else{
				String[] soldiers;
				if(rawSoldiers.contains(", ")){
					 soldiers= rawSoldiers.split(", ");
				}else{
					 soldiers =new String[]{rawSoldiers};
				}
				Unit unit = new Unit();
				for(int i=0;i<soldiers.length;i++){
					if(soldiers[i]!="" || soldiers[i]!=" "){
						soldiers[i]=soldiers[i].trim();
						unit.addSoldier(Integer.parseInt(soldiers[i]));
					}
				}
				front.put(unitName, unit);
			}
			
		}else if(line.startsWith("show") && !line.startsWith("show soldier")){
			String unitName = line.split(" ")[1].trim();
			sb.append(front.get(unitName).toString());
			sb.append("\n");
		}else if(line.startsWith("show soldier")){
			
			
		}else if(line.contains("died")){
			String unitName = line.split(" ")[3].trim();
			String[] deadSoldiers = line.split(" ")[1].split("\\.+");
			int startIndex = Integer.parseInt(deadSoldiers[0]);
			int endIndex = Integer.parseInt(deadSoldiers[1]);
			front.get(unitName).soldiersDied(startIndex, endIndex);
		}else if(line.contains("attached") && !line.contains("after")){
			String unitToAttach = line.split(" ")[0].trim();
			String attachTo = line.split(" ")[3].trim();
			front.get(attachTo).addUnit(front.get(unitToAttach));
		}else if(line.contains("attached") && line.contains("after")){
			String unitToAttach = line.split(" ")[0].trim();
			String attachTo = line.split(" ")[3].trim();
			int soldierId = Integer.parseInt(line.split(" ")[6].trim());
			front.get(attachTo).addSoldiersAfterSoldier(soldierId, front.get(unitToAttach));
			
		}
	}
	
	public String updateFront(String[] news) {
		
		for(String line : news){
			processLine(line);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		FrontBookkeeper bookkeeper = new FrontBookkeeper();
		List<String> lines = new ArrayList<String>();
		String line=null;
		while(true) {
			line = input.nextLine();
			
			if(line==null || line.length()==0){
				break;
			}else{
				lines.add(line);
			}
		}
		input.close();
		String[] array = new String[lines.size()];
		for(int i=0;i<lines.size();i++){
			array[i]=lines.get(i);
		}
		String result = bookkeeper.updateFront(array);
		if(result!=null){
			System.out.println(result);
		}
		
	}

}
