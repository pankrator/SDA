package bg.sofia.uni.fmi.sda.frontbookkeeper;

import java.util.ArrayList;
import java.util.List;

public class Unit {
	
	
	private List<Integer> soldiers;
	private List<Unit> attachedUnits;
	
	public Unit(){
		soldiers = new ArrayList<Integer>();
		attachedUnits = new ArrayList<Unit>();
	}

	public List<Integer> getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(List<Integer> soldiers) {
		this.soldiers = soldiers;
	}

	public List<Unit> getAttachedUnits() {
		return attachedUnits;
	}

	public void setAttachedUnits(List<Unit> attachedUnits) {
		this.attachedUnits = attachedUnits;
	}
	
	public void addSoldier(int soldier){
		soldiers.add(soldier);
	}
	
	public void addUnit(Unit unit){
		
		soldiers.addAll(unit.getSoldiers());
	}
	
	public void addSoldiersAfterSoldier(int soldier,Unit unit){
		
		soldiers.addAll(soldiers.indexOf(soldier)+1, unit.getSoldiers());
		
	}
	
	public void removeSoldiers(int start,int end,Unit unit){
		int startIndex = unit.getSoldiers().indexOf(start);
		int endIndex = unit.getSoldiers().indexOf(end);
		if(startIndex!=-1 && endIndex!=-1){
			for(int i=startIndex;i<=endIndex;i++){
				unit.getSoldiers().remove(i);
			}
		}
	}
	
	public void soldiersDied(int start,int end){
		
		removeSoldiers(start, end, this);
		if(attachedUnits.size()>0){
			for(Unit u:attachedUnits){
				removeSoldiers(start, end, u);
			}
		}	
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(soldiers.size()==0){
			sb.append("[]");
		}else{
			sb.append("[");
			for(int i=0;i<soldiers.size()-1;i++){
				sb.append(soldiers.get(i));
				sb.append(", ");
			}
			sb.append(soldiers.get(soldiers.size()-1));
			sb.append("]");
		}
		return sb.toString();
	}
	
	
}
