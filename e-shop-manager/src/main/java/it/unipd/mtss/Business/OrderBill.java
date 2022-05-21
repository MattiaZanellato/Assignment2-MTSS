////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Business;

import it.unipd.mtss.Business.Exception.BillException;
import it.unipd.mtss.Model.EItem;
import it.unipd.mtss.Model.ItemType;
import it.unipd.mtss.Model.User;

import java.util.List;

public class OrderBill implements Bill {

    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double sum = 0;
        if(itemsOrdered.isEmpty()){
            throw new BillException("Empty Bill");
        }

        //Funz1
        for (EItem item : itemsOrdered) {
            sum += item.getPrice();
        }

        //Funz2
        double processorPrice = getPriceOfLessExpensiveItem(itemsOrdered, ItemType.Processor, 5);

        //Funz3
        double mousePrice = getPriceOfLessExpensiveItem(itemsOrdered, ItemType.Mouse, 10);

        return sum - processorPrice/2 - mousePrice;
    }

    public double getPriceOfLessExpensiveItem(List<EItem> itemsOrdered, ItemType type, int n){
        int count = 0;
        double lessExp = 0;
        for (EItem item : itemsOrdered) {
            if (item.getItemType() == type) {
                count++;
                if (lessExp == 0 || lessExp > item.getPrice()) {
                    lessExp = item.getPrice();
                }
            }
        }
        if (count >= n) {
            return lessExp;
        }
        return 0;
    }
}
