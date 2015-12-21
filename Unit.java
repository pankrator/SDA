package bg.uni_sofia.fmi.sda.ww1;

import java.util.ArrayList;
import java.util.List;

public class Unit {

	private String name;
	private List<Soldier> soldiers;

	public Unit(String name) {
		this.name = name;
		soldiers = new ArrayList<>();
	}

	public Unit(String name, List<Soldier> soldiers) {
		this.name = name;
		this.soldiers = soldiers;
	}

	public String getName() {
		return name;
	}

	public List<Soldier> getSoldiers() {
		return soldiers;
	}

	public void addSoldier(Soldier soldier) {
		this.soldiers.add(soldier);
	}

	public void addSoldierIndex(Soldier soldier, int index) {
		this.soldiers.add(index, soldier);
	}

	public int getIndex(Soldier soldier) {
		return soldiers.indexOf(soldier);
	}
	
	public void detachSoldier(Soldier soldier){
		this.soldiers.remove(soldier);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((soldiers == null) ? 0 : soldiers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (soldiers == null) {
			if (other.soldiers != null)
				return false;
		} else if (!soldiers.equals(other.soldiers))
			return false;
		return true;
	}

}
