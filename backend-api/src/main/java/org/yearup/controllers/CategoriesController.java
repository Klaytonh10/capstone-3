package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/categories
// add annotation to allow cross site origin requests
@RestController
public class CategoriesController {

    CategoryDao categoryDao;
    ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @RequestMapping(path="/categories",method=RequestMethod.GET)
    public List<Category> getAll() {
        // receive all categories
        return this.categoryDao.getAllCategories();
    }

    @RequestMapping(path="/categories/{id}",method=RequestMethod.GET)
    public Category getById(@PathVariable int id) {
        // select a category based on it's id
        return this.categoryDao.getById(id);
    }

    @RequestMapping(path="/categories/{id}/products",method=RequestMethod.GET)
    public List<Product> getProductsById(@PathVariable int id) {
        // select all products under a specified category id
        return this.productDao.listByCategoryId(id);
    }

    // make so only an admin can do this
    @RequestMapping(path="/categories",method=RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.CREATED)
    public Category addCategory(@RequestBody Category category) {
        // insert the category
        return this.categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @RequestMapping(path="/categories/{id}",method=RequestMethod.PUT)
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        this.categoryDao.update(id, category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @RequestMapping(path="/categories/{id}",method=RequestMethod.DELETE)
    public void deleteCategory(@PathVariable int id) {
        this.categoryDao.delete(id);
    }
}
