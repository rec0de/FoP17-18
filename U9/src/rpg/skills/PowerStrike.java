package rpg.skills;

import rpg.characters.RpgCharacter;

/**
 * Power Strike Class - Instance of Skill that deals 2 * owner's attack in normal damage to an enemy on use
 * @author Nils Rollshausen
 */
public class PowerStrike extends Skill {

	/**
	 * Main constructor for PowerStrike - creates new PowerStrike class with given owner
	 * @param owner Owner of the Skill
	 */
	public PowerStrike(RpgCharacter owner) {
		super("PowerStrike", owner, 10);
	}
	
	/**
	 * Executes the Skill - deals 2x normal owner attack to the enemy
	 * @param enemy The enemy to attack
	 */
	public void use(RpgCharacter enemy) {
		enemy.receiveNormalDamage(this.getRpgCharacter().getAttack() * 2);
	}

}
