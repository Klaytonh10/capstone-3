package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController {

    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping(path="")
    @PreAuthorize("permitAll()")
    public List<Category> getAll() {
        // receive all categories
        return this.categoryDao.getAllCategories();
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id) {
        // select a category based on it's id
        return this.categoryDao.getById(id);
    }

    @GetMapping(path="{id}/products")
    public List<Product> getProductsById(@PathVariable int id) {
        // select all products under a specified category id
        return this.productDao.listByCategoryId(id);
    }

    // make so only an admin can do this
    @PostMapping()
    @ResponseStatus(value=HttpStatus.CREATED)
    @PreAuthorize("hasRole(ROLE_ADMIN)")
    public Category addCategory(@RequestBody Category category) {
        return this.categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @PutMapping("{id}")
    @PreAuthorize("hasRole(ROLE_ADMIN)")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        this.categoryDao.update(id, category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole(ROLE_ADMIN)")
    public void deleteCategory(@PathVariable int id) {
        this.categoryDao.delete(id);
    }
}
