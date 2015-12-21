package com.IFrontBookkeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Division{
	public String name;
	public Division attachedTo;
	//public int lastInt;
	public ArrayList<Division> attachmets = new ArrayList<Division>();
	public ArrayList<Integer> soldiers = new ArrayList<Integer>();
	public Division(){
		
	}
	public Division(String name) {
		this.name = name;
		this.soldiers = null;
		this.attachedTo = null;
		//this.lastInt = -1;
	}
	public Division(String name, ArrayList<Integer> s){
		this.name = name;
		this.soldiers = s;
		this.attachedTo = null;
		this.attachmets = null;
		//this.lastInt = soldiers.get(soldiers.size()-1);
	}
	public void set(String name, ArrayList<Integer> s){
		this.name = name;
		this.soldiers = s;
		this.attachedTo = null;
		this.attachmets = null;
		//this.lastInt = soldiers.get(soldiers.size()-1);		
	}
	public void set(String name) {
		this.name = name;
		this.soldiers = null;
		this.attachedTo = null;
		//this.lastInt = -1;
	}
	public void append(Division d){
		this.attachmets.add(d);
	}
	public void attachAfter(int soldier, Division d){
		for(int i = 0; i < this.attachmets.size(); i++){
			Division current = this.attachmets.get(i);
			if(current.soldiers.size() > 0 && current.soldiers.get(current.soldiers.size()-1) == soldier){
				this.attachmets.add(i+1, d);
				return;
			}
		}
	}
	public void removeChild(String child){
		int i = 0;
		while(!attachmets.get(i).name.equals(child)){
			i++;
		}
		attachmets.remove(i);
	}
	public void detach(){
		if(this.attachedTo != null){
			this.attachedTo.removeChild(this.name);
		}
	}
}

public class FrontBookkeeper61781 implements IFrontBookkeeper {

	private static Pattern died = Pattern.compile("soldiers ([0-9]+)..([0-9]+) from (.*)");
	private static Pattern number = Pattern.compile("([0-9]+)");
	private static Matcher m;
	private static String equals = " = ",  attachedToStr = " attached to ", after = " after soldier ", space = " ";
	//tree version
	private HashMap<String, Division> divisions = new HashMap<String, Division>();
	private HashMap<Integer, Division> soldiersDivisions = new HashMap<Integer, Division>();
	String splitRez[], attachedRez[];
	StringBuilder taskRez = new StringBuilder();
	private int dieFrom, dieTo;
	private boolean del = false, continueDel = true;
	private void SplitInput(String news){
		splitRez = news.split(equals);
		if(splitRez.length == 2){
			create(splitRez[0], splitRez[1]);
			return;
		}
		splitRez = news.split(attachedToStr);
		if(splitRez.length==2){
			attachedRez = splitRez[1].split(after);
			if(attachedRez.length == 2){
				attachAfter(splitRez[0], attachedRez[0], attachedRez[1]);
			} else {
				attachDivision(splitRez[0], splitRez[1]);
			}
			return;
		}
		m = died.matcher(news);
		if(m.matches()){
			splitRez = m.group(3).split(space);
			die(m.group(1), m.group(2), splitRez[0]);
			return;
		}
		splitRez = news.split(space);
		if(splitRez.length == 3){
			showSoldier(splitRez[2]);
		} else {
			showDivision(splitRez[1]);
		}
		
	}
	private void create(String name, String seq){
		m = number.matcher(seq);
		ArrayList<Integer> soldiers = new ArrayList<Integer>();
		int cnt = 0;
		Division d = new Division();
		while(m.find()){
			String s = m.group(1);
			soldiersDivisions.put(Integer.parseInt(s), d);
			soldiers.add(Integer.parseInt(s));
			cnt++;
		}
		if(cnt == 0){
			d.set(name);
		} else {
			d.set(name, soldiers);
		}
		divisions.put(name, d);
	}
	private void attachAfter(String child, String parent, String soldier){
		Division childD = divisions.get(child), parentD = divisions.get(parent);
		childD.detach();
		childD.attachedTo = parentD;
		parentD.attachAfter(Integer.parseInt(soldier), childD);
	}
	private void attachDivision(String child, String parent) {
		Division childD = divisions.get(child), parentD = divisions.get(parent);
		childD.detach();
		childD.attachedTo = parentD;
		parentD.attachmets.add(childD);
		//parentD.lastInt = childD.lastInt;
	}
	private void die(String from, String to, String div){
		int fromInt = Integer.parseInt(from), toInt = Integer.parseInt(to);
		dieFrom = fromInt;
		dieTo = toInt;
		Division start = divisions.get(div);
		del  = false;
		continueDel = true;
		DieDFS(start);
		
	}
	private void DieDFS(Division d){
		if(d.attachmets == null && d.soldiers.size()>0){
			int i = 0;
			for(; i < d.soldiers.size() && del == false; i++){
				if(dieFrom == d.soldiers.get(i)){
					del = true;
					break;
				}
			}
			while(i < d.soldiers.size() && del == true){
				if(dieTo == d.soldiers.get(i)){
					d.soldiers.remove(i);
					continueDel = false;
					if(d.soldiers.size() == 0){
						d.attachmets = new ArrayList<Division>();
					}
					return;
				}
				d.soldiers.remove(i);
			}
		} else if(d.attachmets != null){
			int size = d.attachmets.size();
			for(int i = 0; i < size && continueDel == true; i++){
				DieDFS(d.attachmets.get(i));
			}			
		}
	}
	private void showSoldier(String soldier) {
		Division d = soldiersDivisions.get(Integer.parseInt(soldier));
		while(d.attachedTo != null){
			taskRez.append(d.name).append(", ");
			d = d.attachedTo;
		}
		taskRez.append(d.name).append("\n");
	}
	private void showDivision(String division) {
		Division start = divisions.get(division);
		//StringBuilder strB = new StringBuilder("[");
		taskRez.append('[');
		DFS(start);
		if(taskRez.charAt(taskRez.length()-1) == '['){
			taskRez.append("]\n");
		} else {
			taskRez.replace(taskRez.length()-2, taskRez.length(), "]\n");
		}
		
	}
	private void DFS(Division d){
		if(d.attachmets == null){
			for(int i = 0; i < d.soldiers.size(); i++){
				taskRez.append(d.soldiers.get(i) + ", ");
			}
		} else {
			int size = d.attachmets.size();
			for(int i = 0; i < size; i++){
				DFS(d.attachmets.get(i));
			}
		}
	}
	public String updateFront(String[] news) {
		for(int i = 0; i < news.length; i++){
			this.SplitInput(news[i]);		
		}
		taskRez.setLength(taskRez.length()-1);
		return taskRez.toString();
	}

}
