package bg.uni_sofia.fmi.sda.ww1;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FrontBookkeeper61786 implements IFrontBookkeeper {

	private List<Unit> units = new LinkedList<>();
	private HashMap<Unit, Unit> unitsAttachment = new HashMap<>();

	@Override
	public String updateFront(String[] news) {

		for (String newsStr : news) {
			if (newsStr.contains("=")) {
				unitAssignment(newsStr);
			} else if (newsStr.contains("show")) {
				if (newsStr.contains("soldier")) {
					showSoldier(newsStr);
				} else {
					showUnit(newsStr);
				}
			} else if (newsStr.contains("attached")) {
				attach(newsStr);
			} else {
				death(newsStr);
			}
		}

		return null;
	}

	private void death(String newsStr) {
		String elements[] = newsStr.split(" ");

		String soldiers[] = elements[1].split("\\..");

		String firstSold = soldiers[0];
		String lastSold = soldiers[1];

		for (int i = Integer.parseInt(firstSold); i < Integer.parseInt(lastSold) + 1; i++) {
			String soldName = Integer.toString(i);
			Soldier soldierIndex = new Soldier(soldName);
			List<Unit> soldierUnit = soldierUnit(soldierIndex);
			for (Unit unit : soldierUnit) {
				unit.detachSoldier(soldierIndex);
			}
		}

	}

	private List<Unit> soldierUnit(Soldier soldierIndex) {
		List<Unit> soldierUnit = units.stream()
				.filter(unit -> unit.getSoldiers().stream().anyMatch(soldier -> soldier.equals(soldierIndex)))
				.collect(Collectors.toList());
		return soldierUnit;
	}

	private void attach(String newsStr) {

		String elements[] = newsStr.split(" ");

		String unit1 = elements[0];
		String unit2 = elements[3];

		Unit toBeAttached = units.stream().filter(unit -> unit.getName().equals(unit1)).findAny().orElse(null);

		Unit toBeAttachedTo = units.stream().filter(unit -> unit.getName().equals(unit2)).findAny().orElseGet(null);

		if (unitsAttachment.containsKey(toBeAttached)) {
			detach(unitsAttachment.get(toBeAttached), toBeAttached);
		}

		unitsAttachment.put(toBeAttached, toBeAttachedTo);

		Collections.reverse(toBeAttached.getSoldiers());

		if (elements.length > 4) {
			String num = elements[elements.length - 1];

			Soldier soldierIndex = new Soldier(num);
			int index = toBeAttachedTo.getIndex(soldierIndex);

			for (Soldier soldier : toBeAttached.getSoldiers()) {
				toBeAttachedTo.addSoldierIndex(soldier, index + 1);
			}
		} else {
			if (toBeAttachedTo.getSoldiers().isEmpty()) {
				for (Soldier soldier : toBeAttached.getSoldiers()) {
					toBeAttachedTo.addSoldierIndex(soldier, 0);
				}
			} else {
				for (Soldier soldier : toBeAttached.getSoldiers()) {
					toBeAttachedTo.addSoldierIndex(soldier, toBeAttachedTo.getSoldiers().size());
				}
			}
		}

		Collections.reverse(toBeAttached.getSoldiers());

	}

	private void detach(Unit toBeRemovedFrom, Unit toBeAttached) {
		for (Soldier soldier : toBeAttached.getSoldiers()) {
			toBeRemovedFrom.detachSoldier(soldier);
		}
	}

	private void showSoldier(String newsStr) {

		String num = newsStr.replaceAll("[^0-9]+", " ").trim();

		Soldier sold = new Soldier(num);

		List<Unit> soldierUnit = soldierUnit(sold);

		String foundUnits = soldierUnit.toString().substring(1, soldierUnit.toString().length() - 1).trim();

		System.out.println(foundUnits);
	}

	private void showUnit(String news) {

		String elements[] = news.split(" ");

		String unitNew = elements[1];

		List<Soldier> unitSoldiers = units.stream().filter(unit -> unit.getName().equals(unitNew))
				.flatMap(unit -> unit.getSoldiers().stream()).collect(Collectors.toList());

		System.out.println(unitSoldiers);

	}

	private void unitAssignment(String news) {

		String elements[] = news.split(" = ");

		String unitName = elements[0];

		Unit unit = new Unit(unitName);

		if (!elements[1].equalsIgnoreCase("[]")) {

			String numbers[] = elements[1].split(", ");

			for (String string : numbers) {
				String number = string.replaceAll("]", "").replace("[", "").trim();
				Soldier soldier = new Soldier(number);
				unit.addSoldier(soldier);
			}

		}
		units.add(unit);
	}
}
