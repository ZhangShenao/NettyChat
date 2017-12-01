package william.module;

import java.util.ArrayList;
import java.util.List;

import william.core.serial.Serializer;

public class Player extends Serializer{
	private long playerKey;
	private String name;
	private int age;
	private List<Skill> skills = new ArrayList<Skill>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Skill> getSkills() {
		return skills;
	}
	
	public void addSkill(Skill skill){
		skills.add(skill);
	}
	
	@Override
	public void unmarshal() {
		this.playerKey = readLong();
		this.name = readString();
		this.age = readInt();
		this.skills = readList(Skill.class);
	}
	
	@Override
	public void marshal() {
		writeLong(playerKey);
		writeString(name);
		writeInt(age);
		writeList(skills);
	}
	
	public long getPlayerKey() {
		return playerKey;
	}
	public void setPlayerKey(long playerKey) {
		this.playerKey = playerKey;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	@Override
	public String toString() {
		return "Player [playerKey=" + playerKey + ", name=" + name + ", age="
				+ age + ", skills=" + skills + "]";
	}
	
	
	
}
