package com.example.dietooo.view.home;



import  com.example.dietooo.model.Categories;
import  com.example.dietooo.model.Meals;

import java.util.List;

public interface HomeView {
    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);
}
