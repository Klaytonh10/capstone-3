# <center>Artisanal Shop

---
### <center> This program is an e-commerce site for buying and selling goods from the Artisanal Shop

---

<div style="text-align:center;">
<img src="frontend-ui/images/logo2.png" width="800" alt="Artisanal Shop Logo"/>
</div>


## <center>My Important Classes
| Class                                 | Purpose                                                                                                                                                                      |
|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <center> ShoppingCart(controller/dao) | <center> These classes are where the logic for manipulating shopping cart information happens. A request is made every time someone adds or removes a shopping cart item.    |
| <center> Product(controller/dao)      | <center> These classes receive, create, and update product information such as name, price, and category. Also used in conjunction with category classes to filter products. |
| <center> Category(controller/dao)     | <center> These classes work in conjunction with product classes to not only filter products but add, remove, or update existing categories in the database.                  |

## <center>Interesting Code
<div style="text-align:center;">
<img src="frontend-ui/images/dynamic-button.PNG" width="600" alt="Code block for dynamically creating delete buttons"/>
</div>

### <center>So that users won't have to completely wipe their cart each time they add an accidental item, I implemented a button onto each shopping cart item that allows users to individually remove unwanted items.

---

<div style="text-align:center;">
<img src="frontend-ui/images/remove-func.PNG" width="600" alt="Function for calling to api"/>
</div>

### <center>This function was implemented to handle each shopping cart item's remove button. After calling to the api, the shopping cart page is refreshed with the updated cart.