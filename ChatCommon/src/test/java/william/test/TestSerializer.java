package william.test;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

import org.junit.Test;

import william.module.Player;
import william.module.Skill;

public class TestSerializer {
	@Test
	public void testMarshal(){
		Player player = new Player();
		player.setName("詹姆斯");
		player.setAge(33);
		Skill skill1 = new Skill();
		skill1.setName("扣篮");
		player.addSkill(skill1);
		Skill skill2 = new Skill();
		skill2.setName("追帽");
		player.addSkill(skill2);
		Skill skill3 = new Skill();
		skill3.setName("背打");
		player.addSkill(skill3);
		ByteBuf byteBuf = player.writeToLocalBuf();
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		System.err.println(Arrays.toString(bytes));
		
		Player player1 = new Player();
		player1.readFromBytes(bytes);
		System.err.println(player1);
	}
}
