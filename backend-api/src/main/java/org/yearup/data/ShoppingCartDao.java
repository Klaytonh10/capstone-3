package org.yearup.data;

import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    ShoppingCart addShoppingCartItem(ShoppingCartItem item, ShoppingCart cart);
    // add additional method signatures here
}
