package william.module;

import william.core.serial.Serializer;

public class Skill extends Serializer{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void unmarshal() {
		this.name = readString();
	}

	@Override
	public void marshal() {
		writeString(name);
	}

	@Override
	public String toString() {
		return "Skill [name=" + name + "]";
	}
	
}
