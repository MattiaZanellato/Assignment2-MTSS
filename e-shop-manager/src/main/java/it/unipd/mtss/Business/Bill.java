////////////////////////////////////////////////////////////////////
// [Mattia] [Zanellato] [1222398]
// [Federico] [Marchi] [1224445]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.Business;

import java.util.List;

import it.unipd.mtss.Business.Exception.BillException;
import it.unipd.mtss.Model.EItem;
import it.unipd.mtss.Model.User;

public interface Bill {

    double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException;

}