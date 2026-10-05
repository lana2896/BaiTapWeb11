package shop.service;

import shop.dao.CategoryDAO_24110347;
import shop.entity.Category_24110347;

import java.util.List;

public class CategoryService_24110347 {
    private final CategoryDAO_24110347 categoryDAO = new CategoryDAO_24110347();

    public List<Category_24110347> getAll() {
        return categoryDAO.findAll();
    }

    public Category_24110347 findById(Integer id) {
        return categoryDAO.findById(id);
    }
}
