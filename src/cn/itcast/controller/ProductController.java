package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.pojo.PageResult;
import cn.itcast.service.ProductsService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductsService productsService;
	
	/**
	 *测试代码 
	 */
	@RequestMapping("git")
	public String showGit(){
		System.out.println("这是A程序员写的一段测试代码");
		System.out.println("这是B程序员写的一端测试代码，大家请注意查收");
		System.out.println("A程序员继续开发代码，现在要提交，请大家注意！B程序员忘记了更新,直接开发，提交推送");
		return "git";
	}
	
	@RequestMapping("list")
	public String list(String queryString,
					String catalog_name,
					String price,
					@RequestParam(defaultValue="1") Integer page,
					@RequestParam(defaultValue="60") Integer rows,
					@RequestParam(defaultValue="1") String sort,
					Model model)throws Exception{
		//调用service服务方法
		PageResult result = productsService.queryProductsWithCondition(queryString, catalog_name, price, page, rows, sort);
		//回显主查询条件
		model.addAttribute("queryString", queryString);
		//类别
		model.addAttribute("catalog_name", catalog_name);
		//价格
		model.addAttribute("price", price);
		//排序
		model.addAttribute("sort", sort);
		//分页数据
		model.addAttribute("result", result);
		return "product_list";
	}
}
