////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Model;

public class EItem {
    private ItemType itemType;
    private String name;
    private double price;

    public EItem(ItemType itemType, String name, double price){
        this.itemType = itemType;
        this.name = name;
        this.price = price;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}