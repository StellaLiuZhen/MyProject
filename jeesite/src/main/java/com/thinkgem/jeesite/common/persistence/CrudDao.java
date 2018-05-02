/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.persistence;

import java.util.List;
import java.util.Set;

/**
 * DAO支持类实现
 * @author ThinkGem
 * @version 2014-05-16
 * @param <T>
 */
public interface CrudDao<T> extends BaseDao {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity);
	
	/**
	 * 查询数据列表，如果需要分页，请设置分页对象，如：entity.setPage(new Page<T>());
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity);
	
	/**
	 * 查询所有数据列表
	 * @see public List<T> findAllList(T entity)
	 * @return
	 */
	@Deprecated
	public List<T> findAllList();
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(T entity);
	
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param id
	 * @see public int delete(T entity)
	 * @return
	 */
	@Deprecated
	public int delete(String id);
	
	/**
	 * 删除数据（一般为逻辑删除，更新del_flag字段为1）
	 * @param entity
	 * @return
	 */
	public int delete(T entity);


	/**
	 * 查询指定数据表的全部记录，并且以集合的形式返回
	 * @return 如果表中有数据，则所有的数据会封装为VO对象后利用List集合返回，<br>
	 * 如果没有数据，那么集合的长度为0（size（）==0，不是null）
	 * @throws Exception SQL执行异常
	 */
	public List<T> findAll() ;
	/**
	 * 分页进行数据的模糊查询，查询结果已集合的形式返回
	 * @param currentPage 当前所在的页
	 * @param lineSize 每行显示数据行数
	 * @param column 要进行模糊查询的数据列
	 * @param keyWord 模糊查询的关键字
	 * @return 如果表中有数据，则所有的数据会封装为VO对象后利用List集合返回，<br>
	 * 如果没有数据，那么集合的长度为0（size（） == 0， 不是null）
	 * @throws Exception SQL执行异常
	 */
	public List<T> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception;
	/**
	 * 进行模糊查询数据量的统计，如果表中没有记录，统计的结果就是0
	 * @param column 要进行模糊查询的数据列
	 * @param keyWord 模糊查询的关键字
	 * @return 返回表中的数据量，如果没有数据返回0
	 * @throws Exception SQL执行异常	 */
	public Integer getAllCount(String column, String keyWord) throws Exception;
}