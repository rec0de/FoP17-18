package rpg.characters;

import rpg.items.Item;
import rpg.skills.Skill;

/**
 * @author Nils Rollshausen
 *
 */
public abstract class RpgCharacter implements ICharacter {
	
	private String rpgClass;
	private int maxHealth;
	private int health;
	private int maxMana;
	private int mana;
	private int attackBase;
	private int defenseBase;
	private boolean isAlive;
	private Item item;
	private Skill skill;
	
	/**
	 * RPG Character Constructor - initializes new Character object with given values
	 * @param rpgClass Class of the new character
	 * @param maxHealth Maximum number of health points
	 * @param maxMana Maximum number of magic points
	 * @param attackBase Base value for attacks
	 * @param defenseBase Base value for defense
	 */
	public RpgCharacter(String rpgClass, int maxHealth, int maxMana, int attackBase, int defenseBase) {
		this.isAlive = true;
		this.rpgClass = rpgClass;
		
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.maxMana = maxMana;
		this.mana = maxMana;
		
		this.attackBase = attackBase;
		this.defenseBase = defenseBase;
	}

	/**
	 * Getter method for character class
	 * @return The Class (Warrior/Mage) of the character
	 */
	public String getRpgClass() {
		return this.rpgClass;
	}
	
	public int getMaxHp() {
		return this.maxHealth;
	}
	
	public int getCurrentHp() {
		return this.health;
	}
	
	public int getMaxMp() {
		return this.maxMana;
	}

	public int getCurrentMp() {
		return this.mana;
	}
	
	public int getAttackValue() {
		return this.attackBase;
	}
	
	public int getDefenseValue() {
		return this.defenseBase;
	}
	
	public boolean getAlive() {
		return this.isAlive;
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public Skill getSkill() {
		return this.skill;
	}
	
	public int getAttack() {
		return (this.item == null) ? this.attackBase : this.attackBase + this.item.getAttackValue();
	}
	
	public int getDefense() {
		return (this.item == null) ? this.defenseBase : this.defenseBase + this.item.getDefenseValue();
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	public void receiveNormalDamage(int normalDamage) {
		this.health += Math.min(0, this.getDefense() - normalDamage);
		if(this.health <= 0) {
			this.isAlive = false;
		}
	}
	
	public void receiveMagicDamage(int magicDamage) {
		this.health -= Math.max(0, magicDamage);
		if(this.health <= 0) {
			this.isAlive = false;
		}
	}
}
