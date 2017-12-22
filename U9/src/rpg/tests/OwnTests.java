package rpg.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rpg.characters.*;
import rpg.items.*;
import rpg.skills.*;

/**
 * Own tests for RPG implementation
 * @author Nils Rollshausen
 */
public class OwnTests {
	
	private RpgCharacter mage, warrior;
	
	@Before
	public void initCharacters() {
		this.mage = new Mage(10, 9, 3, 5);
		this.warrior = new Warrior(10, 10, 6, 1);
	}

	@Test
	public void testUseSkill() {
		// No skill defined yet, nothing should happen
		this.mage.useSkill(this.warrior);
		assertEquals(this.mage.getCurrentMp(), 9);
		assertEquals(this.warrior.getCurrentHp(), 10);
		
		// Too little mana for skill, nothing should happen
		this.mage.setSkill(new PowerStrike(this.mage));
		this.mage.useSkill(this.warrior);
		assertEquals(this.mage.getCurrentMp(), 9);
		assertEquals(this.warrior.getCurrentHp(), 10);
		
		// Actually execute skill
		this.warrior.setSkill(new PowerStrike(this.warrior));
		this.warrior.useSkill(this.mage);
		assertEquals(this.warrior.getCurrentMp(), 0);
		assertEquals(this.mage.getCurrentHp(), 3);
	}
	
	@Test
	public void testNormalAttack() {
		this.mage.normalAttack(this.warrior);
		assertEquals(this.warrior.getCurrentHp(), 8); // 3 atk - 1 def
		assertTrue(this.warrior.getAlive());
		
		this.warrior.normalAttack(this.mage);
		assertEquals(this.mage.getCurrentHp(), 9); // 6 atk - 5 def
		
		this.mage.setItem(new Sword());
		this.mage.normalAttack(this.warrior);
		assertEquals(this.warrior.getCurrentHp(), -9);
		assertFalse("Warrior is undead! Zombie apocalypse incoming!", this.warrior.getAlive());
		
		// Warrior is already dead
		this.mage.normalAttack(this.warrior);
		assertEquals(this.warrior.getCurrentHp(), -9);
		assertFalse(this.warrior.getAlive());
	}
	
	@Test
	public void testCharacterStats() {
		this.mage.setItem(new Wand());
		this.mage.setSkill(new Fire());
		this.warrior.setItem(new Armor());
		
		assertEquals("Class: Mage Hp: 10 Mp: 9 At: 5 Def: 5 Item: Wand Skill: Fire", this.mage.getCharacterStats());
		assertEquals("Class: Warrior Hp: 10 Mp: 10 At: 6 Def: 4 Item: Armor Skill: _", this.warrior.getCharacterStats());
	}
	
	@Test
	public void testSetSkill() {
		assertNull(this.mage.getSkill());
		
		this.mage.setSkill(new Fire());
		assertEquals(this.mage.getSkill().getName(), "Fire");
		
		this.mage.setSkill(null);
		assertNull(this.mage.getSkill());
	}
	
	@Test
	public void testSetItem() {
		assertNull(this.mage.getItem());
		
		this.mage.setItem(new Armor());
		assertEquals(this.mage.getItem().getName(), "Armor");
		
		this.mage.setItem(null);
		assertNull(this.mage.getItem());
	}
	
	@Test
	public void testReceiveMagicDamage() {
		this.warrior.setSkill(new Fire());
		assertTrue("Test precondition not met: Warrior doesnt have enough MP", this.warrior.getCurrentMp() >= this.warrior.getSkill().getMpCosts());
		this.warrior.useSkill(this.mage);
		
		assertFalse(this.mage.getAlive());
		assertEquals(this.mage.getCurrentHp(), -10);
	}
	
	@Test
	public void testGetAllItems() {
		Item[] items = Item.getAllItems();
		
		assertNull(items[0]);
		assertEquals(items.length, 4);
		assertEquals(items[2].getName(), "Wand");
	}
	
	@Test
	public void testGetAllSkills() {
		Skill[] skills = Skill.getAllSkills();
		
		assertNull(skills[0]);
		assertEquals(skills.length, 3);
		assertEquals(skills[1].getName(), "Fire");
	}

}