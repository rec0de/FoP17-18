/**
 * 
 */
package rpg.items;

/**
 * @author Nils Rollshausen
 *
 */
public abstract class Item {
	private String name;
	private int attackBase;
	private int defenseBase;
	
	public Item(String name, int attackBase, int defenseBase) {
		this.name = name;
		this.attackBase = attackBase;
		this.defenseBase = defenseBase;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAttackValue() {
		return this.attackBase;
	}
	
	public int getDefenseValue() {
		return this.defenseBase;
	}
	
	public static Item[] getAllItems() {
		return new Item[] {null, new Sword(), new Wand(), new Armor()};
	}
}
