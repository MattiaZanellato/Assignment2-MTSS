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
        int countP = 0;
        double lessP = 0;
        for (EItem item : itemsOrdered) {
            sum += item.getPrice();
            if (item.getItemType() == ItemType.Processor) {
                countP++;
                if (lessP == 0 || lessP > item.getPrice()) {
                    lessP = item.getPrice();
                }
            }
        }
        if (countP >= 5) {
            return sum-(lessP/2);
        } else if (sum == 0) {
            throw new BillException("Empty Bill");
        }
        return sum;
    }
}
