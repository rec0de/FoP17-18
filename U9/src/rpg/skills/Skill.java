package rpg.skills;

import rpg.characters.RpgCharacter;

/**
 * @author Nils Rollshausen
 *
 */
public abstract class Skill {
	private String name;
	private RpgCharacter owner;
	private int manaCost;
	
	public Skill(String name, RpgCharacter owner, int manaCost) {
		this.name = name;
		this.owner = owner;
		this.manaCost = manaCost;
	}
	
	public String getName() {
		return this.name;
	}
	
	public RpgCharacter getRpgCharacter() {
		return this.owner;
	}
	
	public int getMpCosts() {
		return this.manaCost;
	}
	
	public static Skill[] getAllSkills() {
		return new Skill[] {null, new Fire(), new PowerStrike()};
	}
}

