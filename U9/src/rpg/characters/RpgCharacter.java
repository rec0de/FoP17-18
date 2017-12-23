package rpg.characters;

import rpg.items.Item;
import rpg.skills.Skill;

/**
 * RPG Character Base class - implements getter / setter method for attributes as well as attack / defense logic
 * @author Nils Rollshausen
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
	
	/**
	 * Getter method for maximum number of health points
	 * @return Maximum health points of character
	 */
	public int getMaxHp() {
		return this.maxHealth;
	}
	
	/**
	 * Getter method for current health points
	 * @return Current health points of character
	 */
	public int getCurrentHp() {
		return this.health;
	}
	
	/**
	 * Getter method for maximum mana points
	 * @return Maximum mana points of character
	 */
	public int getMaxMp() {
		return this.maxMana;
	}

	/**
	 * Getter method for current mana points
	 * @return Current mana points of character
	 */
	public int getCurrentMp() {
		return this.mana;
	}
	
	/**
	 * Getter method for character base attack value
	 * @return Base attack value of character
	 */
	public int getAttackValue() {
		return this.attackBase;
	}
	
	/**
	 * Getter method for character base defense value
	 * @return Base defense value of character
	 */
	public int getDefenseValue() {
		return this.defenseBase;
	}
	
	/**
	 * Getter method for alive state of character
	 * @return True if character is alive, false otherwise
	 */
	public boolean getAlive() {
		return this.isAlive;
	}
	
	/**
	 * Getter method for the item currently owned by the character
	 * @return Item in character possession, null if none
	 */
	public Item getItem() {
		return this.item;
	}
	
	/**
	 * Getter method for the skill currently owned by the character
	 * @return Skill in character possession, null if none
	 */
	public Skill getSkill() {
		return this.skill;
	}
	
	/**
	 * Calculates the total value of the characters regular attack, accounting for base attack value and item attack value
	 * @return Total attack damage (base + item) of character
	 */
	public int getAttack() {
		return (this.item == null) ? this.attackBase : this.attackBase + this.item.getAttackValue();
	}
	
	/**
	 * Calculates the total value of the characters defense, accounting for base defense value and item defense value
	 * @return Total defense (base + item) of character
	 */
	public int getDefense() {
		return (this.item == null) ? this.defenseBase : this.defenseBase + this.item.getDefenseValue();
	}
	
	/**
	 * Setter method for the item owned by the character
	 * @param item The item to be attached to the character
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	
	/**
	 * Setter method for the skill owned by the character
	 * @param skill The skill to be attached to the character
	 */
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
	
	public void normalAttack(RpgCharacter enemy) {
		// Can't attack dead enemy
		if(!enemy.getAlive())
			return;
		
		enemy.receiveNormalDamage(this.getAttack());
	}
	
	/**
	 * Uses the character's skill (if defined) on a given enemy if character has enough mana and subtracts mana cost from available balance
	 * @param enemy The enemy to apply skill effects to
	 */
	public void useSkill(RpgCharacter enemy) {
		// No effect if character has no skill or too little mana
		if(this.skill == null || this.mana < this.skill.getMpCosts())
			return;
		
		this.mana -= this.skill.getMpCosts();
		this.skill.use(enemy);
		
	}
	
	/**
	 * Serializes current character data (class, health, mana, attack, defense, item, skill), formatted as a human-readable string. Placeholder _ represents empty skill / item
	 * @return Serialized character data string
	 */
	public String getCharacterStats() {
		String res = "Class: " + this.getRpgClass() + " Hp: " + this.getCurrentHp() + " Mp: " + this.getCurrentMp() + " At: " + this.getAttack() + " Def: " + this.getDefense() + " ";
		res += "Item: " + (this.item == null ? "_" : this.item.getName()) + " Skill: " + (this.skill == null ? "_" : this.skill.getName());
		return res;
	}
}
