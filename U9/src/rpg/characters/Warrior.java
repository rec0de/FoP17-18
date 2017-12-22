package rpg.characters;

/**
 * @author Nils Rollshausen
 *
 */
public class Warrior extends RpgCharacter {

	/**
	 * Constructor for Warrior class - calls constructor for Character class with rpgClass parameter fixed to Warrior
	 * @param maxHealth Maximum number of health points
	 * @param maxMana Maximum number of magic points
	 * @param attackBase Base value for attacks
	 * @param defenseBase Base value for defense
	 */
	public Warrior(int maxHealth, int maxMana, int attackBase, int defenseBase) {
		super("Warrior", maxHealth, maxMana, attackBase, defenseBase);
	}

}
