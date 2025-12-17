package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    ShoppingCart addShoppingCartItem(ShoppingCartItem item, User user);
    void updateShopping();
    //ShoppingCart removeSpecificCartItem(ShoppingCartItem item, User user, ShoppingCart shoppingCart);
    // add additional method signatures here
}
