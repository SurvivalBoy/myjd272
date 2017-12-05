package cn.itcast.service.Impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.ProductsDao;
import cn.itcast.pojo.PageResult;
import cn.itcast.service.ProductsService;
@Service
public class ProductsServiceImpl implements ProductsService {
	
	@Autowired
	private ProductsDao ProductsDao;

	/**
	 * SERVICE:业务层 ---参数封装--分页--高亮
	 * 需求:根据条件查询索引库
	 * 参数:String queryString,String catalog_name,String price,Integer page,Integer rows,String sort
	 * @return
	 */
	public PageResult queryProductsWithCondition(String queryString,
			String catalog_name, String price, Integer page, Integer rows,
			String sort) {
		//创建SolrQuery封装所有查询参数
		SolrQuery solrQuery = new SolrQuery();
		//线进行判断。判断请求参数是否存在
		if(null != queryString && !"".equals(queryString)){
			solrQuery.setQuery(queryString);
		}else{
			solrQuery.setQuery("*:*");
		}
		//分类过滤查询
		if(null != catalog_name && !"".equals(catalog_name)){
			solrQuery.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		//价格过滤 -- 0-9,10-19,20-29   50-*
		if(null != price && !"".equals(price)){
			String[] prices = price.split("-");
			solrQuery.addFilterQuery("product_price:["+prices[0]+" TO "+prices[1]+"]");
		}
		//分页设置
		//计算起始页
		int startNo = (page - 1)*rows;
		solrQuery.setStart(startNo);
		solrQuery.setRows(rows);
		//排序
		//排序
		if("1".equals(sort)){
			solrQuery.setSort("product_price", ORDER.asc);
		}else{
			solrQuery.setSort("product_price", ORDER.desc);
		}
		
		//高亮设置
		//开启高亮
		solrQuery.setHighlight(true);
		//设置高亮字段
		solrQuery.addHighlightField("product_name");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<font color='red'>");
		//后缀
		solrQuery.setHighlightSimplePost("</font>");
		//设置默认域
		solrQuery.set("df", "product_keywords");
		//调用dao查询
		PageResult result = ProductsDao.queryProductsWithCondition(solrQuery);
		//设置当前页值到分页包装类对象
		result.setCurPage(page);
		//计算总页码
		Integer count = result.getTotalRecord();		//获取到总记录数
		int pages = count/rows;			//用总记录数除以每页总条数=总页码
		if(count%rows>0){
			pages++;
		}
		//把总页码设置分页包装类对象
		result.setTotalPages(pages);
		
		return result;
	}

	
	
}
