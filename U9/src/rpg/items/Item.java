package rpg.items;

/**
 * Item base class - Represents an item with attack / defense modifiers and a name
 * @author Nils Rollshausen
 */
public abstract class Item {
	private String name;
	private int attackBase;
	private int defenseBase;
	
	/**
	 * Main constructor for items - creates new item with desired name and attack / defense values
	 * @param name Name of the item to be created
	 * @param attackBase Attack value of the item
	 * @param defenseBase Defense value of the item
	 */
	public Item(String name, int attackBase, int defenseBase) {
		this.name = name;
		this.attackBase = attackBase;
		this.defenseBase = defenseBase;
	}
	
	/**
	 * Getter method for item name
	 * @return Name of the item
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter method for the attack value
	 * @return Attack value of the item
	 */
	public int getAttackValue() {
		return this.attackBase;
	}
	
	/**
	 * Getter method for the defense value
	 * @return Defense value of the item
	 */
	public int getDefenseValue() {
		return this.defenseBase;
	}
	
	/**
	 * Lists all existing items
	 * @return Array with Instances of every possible item
	 */
	public static Item[] getAllItems() {
		return new Item[] {null, new Sword(), new Wand(), new Armor()};
	}
}
