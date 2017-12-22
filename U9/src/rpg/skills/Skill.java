package rpg.skills;

import rpg.characters.RpgCharacter;

/**
 * Skill base class - represents a skill with mana cost, owner and name. Use is defined in subclasses specific to a skill
 * @author Nils Rollshausen
 */
public abstract class Skill {
	private String name;
	private RpgCharacter owner;
	private int manaCost;
	
	/**
	 * Main constructor for Skill - creates a new Skill with given name, owner and mana cost
	 * @param name
	 * @param owner
	 * @param manaCost
	 */
	public Skill(String name, RpgCharacter owner, int manaCost) {
		this.name = name;
		this.owner = owner;
		this.manaCost = manaCost;
	}
	
	/**
	 * Getter method for skill name
	 * @return Name of the skill
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter method for skill owner
	 * @return The owner of the skill
	 */
	public RpgCharacter getRpgCharacter() {
		return this.owner;
	}
	
	/**
	 * Getter method for mana cost of skill
	 * @return Mana / Mp cost of skill
	 */
	public int getMpCosts() {
		return this.manaCost;
	}
	
	/**
	 * Lists all existing skills
	 * @return Array with Instances of every possible skill
	 */
	public static Skill[] getAllSkills() {
		return new Skill[] {null, new Fire(), new PowerStrike()};
	}
	
	/**
	 * Applies skill effect to given character
	 * @param enemy The enemy to apply skill effect to
	 */
	public abstract void use(RpgCharacter enemy);
}

