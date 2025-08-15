package com.mahnoosh.foryou.model

data class Category(val categoryName: String, val isSelected: Boolean)

val allCategories = listOf<Category>(
    Category(categoryName = "General", true),
    Category(categoryName = "World", isSelected = false),
    Category(categoryName = "Nation", isSelected = false),
    Category(categoryName = "Business", isSelected = false),
    Category(categoryName = "Technology", isSelected = false),
    Category(categoryName = "Entertainment", isSelected = false),
    Category(categoryName = "Sports", isSelected = false),
    Category(categoryName = "Science", isSelected = false),
    Category(categoryName = "Health", isSelected = false),
)
