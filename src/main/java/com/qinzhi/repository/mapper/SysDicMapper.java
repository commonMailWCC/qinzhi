package com.qinzhi.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qinzhi.domain.SysDic;

/**
 * @className: SysDicMapper
 * @description: TODO
 * @author: liwei
 * @date: 2017-03-26 下午2:19:32
 */
public interface SysDicMapper extends BaseMapper<SysDic, Long> {
	/**
	 * 查询系统字典信息(支持分页)
	 *
	 * @param pageNo
	 *            起始页
	 * @param pageSize
	 *            每页显示多少条
	 * @return List <Operator>
	 */
	List<SysDic> findSysDics(@Param("dicType") String type, @Param("currentPage") int currentPage,
			@Param("pageSize") int pageSize);

	/**
	 * 根据名称获取总数
	 *
	 * @param operatorName
	 * @param loginName
	 * @return
	 */
	int getCountByType(@Param("dicType") String type);

	/**
	 * @param ids
	 * @return
	 * @description: 删除字典
	 * @author liwei
	 * @date 2014-12-1 下午7:27:54
	 * @return: int
	 */
	int deleteSysDic(@Param("ids") List<Long> ids);

	List<SysDic> findSysDicsByType(@Param("dicType") String type);

	String getSysDicValueByKey(@Param("key") String key);

	SysDic getSysDicByKeyAndType(@Param("dicKey") String key, @Param("dicType") String type);

}