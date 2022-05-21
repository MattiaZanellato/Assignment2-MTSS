////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EItemTest {
    EItem item;

    @Before
    public void setup() {
        item = new EItem(ItemType.Mouse, "Mouse1", 13.00);
    }

    @Test
    public void testGetItemTypeEItem() {
        ItemType itemType = item.getItemType();
        assertEquals(ItemType.Mouse, itemType);
    }

    @Test
    public void testGetNameEItem() {
        String name = item.getName();
        assertEquals("Mouse1", name);
    }

    @Test
    public void testGetPriceEItem() {
        double price = item.getPrice();
        assertEquals(13.00, price, 0.001);
    }
}
