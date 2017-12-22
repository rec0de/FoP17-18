package rpg.skills;

import rpg.characters.RpgCharacter;

/**
 * Fire Class - Instance of Skill that deals 20 magic damage to an enemy on use
 * @author Nils Rollshausen
 */
public class Fire extends Skill {
	
	/**
	 * Main constructor for Fire - creates new Fire class with given owner
	 * @param owner Owner of the Skill
	 */
	public Fire(RpgCharacter owner) {
		super("Fire", owner, 7);
	}
	
	/**
	 * Empty constructor for use in getAllSkills without specified owner
	 */
	public Fire() {
		super("Fire", null, 7);
	}
	
	/**
	 * Executes the Skill - deals 20 magic damage to the enemy
	 * @param enemy The enemy to attack
	 */
	public void use(RpgCharacter enemy) {
		enemy.receiveMagicDamage(20);
	}

}
