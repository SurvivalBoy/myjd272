package cn.itcast.service;


import cn.itcast.pojo.PageResult;

public interface ProductsService {

	/**
	 * SERVICE:业务层 ---参数封装--分页--高亮
	 * 需求:根据条件查询索引库
	 * 参数:String queryString,String catalog_name,String price,Integer page,Integer rows,String sort
	 * @return
	 */
	public PageResult queryProductsWithCondition(String queryString,
			String catalog_name,
			String price,
			Integer page,
			Integer rows,
			String sort);

}
