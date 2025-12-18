package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    ShoppingCart addShoppingCartItem(ShoppingCartItem item, int id);
    ShoppingCart deleteShoppingCart(int userId);
    ShoppingCart removeSpecificCartItem(int productId, int userId);
    // add additional method signatures here
}
