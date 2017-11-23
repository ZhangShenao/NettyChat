package william.module;

import java.util.ArrayList;
import java.util.List;

import william.core.serial.Serializer;

public class Player extends Serializer{
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
		this.name = readString();
		this.age = readInt();
		this.skills = readList(Skill.class);
	}
	
	@Override
	public void marshal() {
		writeString(name);
		writeInt(age);
		writeList(skills);
	}
	
	@Override
	public String toString() {
		return "Player [name=" + name + ", age=" + age + ", skills=" + skills
				+ "]";
	}
	
	
}
