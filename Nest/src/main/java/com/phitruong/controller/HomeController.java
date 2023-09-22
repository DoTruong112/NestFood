package com.phitruong.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.phitruong.entity.Category;
import com.phitruong.entity.Product;
import com.phitruong.repository.CategoryRepository;
import com.phitruong.repository.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	private ProductRepository productDao;
	@Autowired
	private CategoryRepository categoryDao;
	
	@GetMapping("/home")
	public String index(Model model) {
		Product item = new Product();
		model.addAttribute("item", item);
		
		List<Object[]> items = productDao.findAllProductsWithCategoryName();
		model.addAttribute("items", items);
		
		List<Category> categories = categoryDao.findAll();
		model.addAttribute("categories", categories);
		
		return "user/index";
	}
	

}
