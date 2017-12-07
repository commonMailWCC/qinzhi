package com.qinzhi.repository.impl;

import com.qinzhi.domain.*;
import com.qinzhi.repository.ISystemRepository;
import com.qinzhi.repository.mapper.*;
import com.qinzhi.utils.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统仓库层
 */
@Repository
public class SystemRepositoryImpl implements ISystemRepository {

	@Autowired
	private SysOperatorMapper operatorMapper;

	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private SysDicMapper dicMapper;

	@Autowired
	private SysAuthorityMapper authorityMapper;

	@Autowired
	private SysOperatorRoleMapMapper operatorRoleMapMapper;

	@Autowired
	private SysRoleAuthorityMapMapper roleAuthorityMapMapper;

	@Autowired
	private LevelMapper mapper;

	@Override
	public boolean addOperator(SysOperator sysOperator) {
		return 1 == this.operatorMapper.save(sysOperator);
	}

	@Override
	public boolean updateOperator(SysOperator sysOperator) {
		return 1 == this.operatorMapper.update(sysOperator);
	}

	@Override
	public boolean deleteOperator(Long id) {
		return 0 <= this.operatorMapper.delete(id);
	}

	@Override
	public Pageable<SysOperator> findOperators(SysOperator sysOperator) {
		List<Level> levelList = mapper.findAll();
		List<SysOperator> operatorList = this.operatorMapper.findOperators(sysOperator);
		for (SysOperator o : operatorList) {
			for (Level l : levelList) {
				if (null != o && null != o.getLevelId() && l.getId() == o.getLevelId()) {
					o.setOperatorLevel(l.getLevelName());
				}
			}
		}
		int total = this.operatorMapper.getCountByName(sysOperator);
		return new Pageable<SysOperator>().instanceByPageNo(operatorList, total, sysOperator.getPage(),
				sysOperator.getRows());
	}

	@Override
	public SysOperator getOperatorById(Long id) {
		List<Level> levelList = mapper.findAll();
		SysOperator o = this.operatorMapper.get(id);
		for (Level l : levelList) {
			if (null != o && null != o.getLevelId() && l.getId() == o.getLevelId()) {
				o.setOperatorLevel(l.getLevelName());
			}
		}
		return o;
	}

	@Override
	public boolean addRole(SysRole sysRole) {
		return 1 == this.roleMapper.save(sysRole);
	}

	@Override
	public boolean updateRole(SysRole sysRole) {
		return 1 == this.roleMapper.update(sysRole);
	}

	@Override
	public boolean deleteRole(Long id) {
		return 0 <= this.roleMapper.delete(id);
	}

	@Override
	public Pageable<SysRole> findRoles(String roleName, int pageNo, int pageSize) {
		List<SysRole> roleList = this.roleMapper.findRoles(roleName, pageNo, pageSize);
		int total = this.roleMapper.getCountByCondition(roleName);
		return new Pageable<SysRole>().instanceByPageNo(roleList, total, pageNo, pageSize);
	}

	@Override
	public SysRole getRoleById(Long id) {
		return this.roleMapper.get(id);
	}

	@Override
	public boolean addAuthority(SysAuthority sysAuthority) {
		return 1 == this.authorityMapper.save(sysAuthority);
	}

	@Override
	public boolean updateAuthority(SysAuthority sysAuthority) {
		return 1 == this.authorityMapper.update(sysAuthority);
	}

	@Override
	public boolean deleteAuthority(Long id) {
		return 0 <= this.authorityMapper.delete(id);
	}

	@Override
	public boolean deleteAuthorityByIds(List<Long> ids) {
		boolean flag = ids.size() == this.authorityMapper.deleteByIds(ids);
		return flag;
	}

	@Override
	public Pageable<SysAuthority> findAuthoritys(int pageNo, int pageSize) {
		List<SysAuthority> authorityList = this.authorityMapper.findAuthoritys(pageNo, pageSize);
		int total = this.authorityMapper.getCount();
		return new Pageable<SysAuthority>().instanceByPageNo(authorityList, total, pageNo, pageSize);
	}

	@Override
	public SysAuthority getAuthorityById(Long id) {
		return this.authorityMapper.get(id);
	}

	@Override
	public SysOperator getOperatorByLoginName(String loginName) {
		return this.operatorMapper.getOperatorByLoginName(loginName);
	}

	@Override
	public SysOperator getByLoginNameAndState(String loginName, Integer state) {
		return this.operatorMapper.getByLoginNameAndState(loginName, state);
	}

	@Override
	public List<SysOperator> findAllOperator() {
		return this.operatorMapper.findAll();
	}

	@Override
	public List<SysOperatorRoleMap> findByOperatorId(Long operatorId) {
		return this.operatorRoleMapMapper.findByOperatorId(operatorId);
	}

	@Override
	public List<SysRole> findAllRole() {
		return this.roleMapper.findAll();
	}

	@Override
	public boolean addOperatorRoleMap(SysOperatorRoleMap operatorRoleMap) {
		return 1 == this.operatorRoleMapMapper.save(operatorRoleMap);
	}

	@Override
	public SysOperatorRoleMap getOperatorRoleMapByRoleId(String operatorId, String roleId) {
		return this.operatorRoleMapMapper.getOperatorRoleMapByRoleId(operatorId, roleId);
	}

	@Override
	public boolean deleteOperatorRoleMapByOperatorId(Long operatorId) {
		return 0 <= this.operatorRoleMapMapper.deleteOperatorRoleMapByOperatorId(operatorId);
	}

	@Override
	public List<SysAuthority> findAllAuthority() {
		return this.authorityMapper.findAll();
	}

	@Override
	public List<SysRoleAuthorityMap> findByRoleId(Long roleId) {
		return this.roleAuthorityMapMapper.findByRoleId(roleId);
	}

	@Override
	public boolean deleteRoleAuthorityMapByRoleId(Long roleId) {
		return 0 <= this.roleAuthorityMapMapper.deleteRoleAuthorityMapByRoleId(roleId);
	}

	@Override
	public boolean addRoleAuthorityMap(SysRoleAuthorityMap roleAuthorityMap) {
		return 0 <= this.roleAuthorityMapMapper.save(roleAuthorityMap);
	}

	@Override
	public List<SysAuthority> findByRoleIds(List<Long> roleIds) {
		return this.authorityMapper.findByRoleIds(roleIds);
	}

	@Override
	public List<SysAuthority> findAuthorityByOperatorId(Long operatorId) {
		return this.authorityMapper.findByOperatorId(operatorId);
	}

	@Override
	public int getOperatorLastId() {
		return this.operatorMapper.getOperatorLastId();
	}

	@Override
	public int getRoleLastId() {
		return this.roleMapper.getRoleLastId();
	}

	@Override
	public List<SysAuthority> findRelatedAuthorities(List<SysAuthority> condition) {
		if (condition != null && condition.size() > 0) {
			List<SysAuthority> subResult = new ArrayList<SysAuthority>();
			for (SysAuthority authority : condition) {
				List<SysAuthority> auth = new ArrayList<SysAuthority>();
				auth = this.authorityMapper.findAuthoritiesByParentId(authority.getAuthorityId());
				subResult.addAll(auth);
			}
			subResult.addAll(this.findRelatedAuthorities(subResult));
			return subResult;
		} else {
			return new ArrayList<SysAuthority>();
		}
	}

	@Override
	public List<SysAuthority> findAuthoritiesByParentId(Long parentId) {
		return this.authorityMapper.findAuthoritiesByParentId(parentId);
	}

	/**
	 * 删除角色
	 */
	@Override
	public Boolean deleteRole(List<Long> ids) {
		return 1 <= roleMapper.deleteRole(ids);
	}

	@Override
	public Boolean deleteOperator(List<Long> ids) {
		operatorRoleMapMapper.deleteByOperatorIds(ids);
		return 1 <= operatorMapper.deleteOperator(ids);
	}

	@Override
	public Pageable<SysAuthority> findAuthorityByPId(long id, int pageNo, int pageSize) {
		List<SysAuthority> list = new ArrayList<SysAuthority>();
		int total = 0;
		if (id == 1) {
			list = authorityMapper.findAuthoritys(pageNo, pageSize);
			total = authorityMapper.getCount();
		} else {
			findAuthorityF(list, id);
			total = list.size();
			int to = pageNo + pageSize;
			list = list.subList(pageNo, to > total ? total : to);
		}

		return new Pageable<SysAuthority>().instanceByPageNo(list, total, pageNo, pageSize);
	}

	private void findAuthorityF(List<SysAuthority> list, long id) {
		List<SysAuthority> listAuthority = authorityMapper.findAuthoritiesByParentId(id);
		list.addAll(listAuthority);

		for (SysAuthority sysAuthority : listAuthority) {
			findAuthorityF(list, sysAuthority.getAuthorityId());
		}
	}

	@Override
	public boolean saveBatchRoleAuthority(Long roleId, List<Long> authorityIds) {
		return authorityIds.size() == this.roleAuthorityMapMapper.saveBatchRoleAuthority(roleId, authorityIds);
	}

	@Override
	public List<Long> findRoleIdByOperatorId(Long operatorId) {
		return operatorRoleMapMapper.findRoleIdByOperatorId(operatorId);
	}

	@Override
	public boolean addSysDic(SysDic sysDic) {
		return 1 == this.dicMapper.save(sysDic);
	}

	@Override
	public boolean updateSysDic(SysDic sysDic) {
		return 1 == this.dicMapper.update(sysDic);
	}

	@Override
	public boolean deleteSysDic(List<Long> ids) {
		return 1 <= dicMapper.deleteSysDic(ids);
	}

	@Override
	public Pageable<SysDic> findSysDics(String type, int pageNo, int pageSize) {
		List<SysDic> dicList = this.dicMapper.findSysDics(type, pageNo, pageSize);
		int total = this.dicMapper.getCountByType(type);
		return new Pageable<SysDic>().instanceByPageNo(dicList, total, pageNo, pageSize);
	}

	@Override
	public List<SysDic> findDicsByType(String type) {
		List<SysDic> dicList = this.dicMapper.findSysDicsByType(type);
		return dicList;
	}

	@Override
	public String getSysDicValueByKey(String key) {
		return this.dicMapper.getSysDicValueByKey(key);
	}

	@Override
	public SysDic getSysDicByKeyAndType(String key, String type) {
		return this.dicMapper.getSysDicByKeyAndType(key, type);
	}
}
