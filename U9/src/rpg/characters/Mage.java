package rpg.characters;

/**
 * 
 * @author Nils Rollshausen
 */
public class Mage extends RpgCharacter {

	/**
	 * Constructor for Mage class - calls constructor for Character class with rpgClass parameter fixed to Mage
	 * @param maxHealth Maximum number of health points
	 * @param maxMana Maximum number of magic points
	 * @param attackBase Base value for attacks
	 * @param defenseBase Base value for defense
	 */
	public Mage(int maxHealth, int maxMana, int attackBase, int defenseBase) {
		super("Mage", maxHealth, maxMana, attackBase, defenseBase);
	}

}
