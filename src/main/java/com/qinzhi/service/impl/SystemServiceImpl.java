package com.qinzhi.service.impl;

import com.google.common.collect.Lists;
import com.qinzhi.bean.Constants;
import com.qinzhi.bean.JsonResult;
import com.qinzhi.bean.Tree;
import com.qinzhi.bean.TreeNode;
import com.qinzhi.domain.*;
import com.qinzhi.repository.ISystemRepository;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.Digests;
import com.qinzhi.utils.EncodeUtils;
import com.qinzhi.utils.Pageable;
import com.qinzhi.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SystemServiceImpl implements ISystemService {

	private static final String OPERATOR = "operator";

	private static final String ROLE = "role";

	private static final String NO_AUTHORITY = "温馨提示: 你没有权限登录，请联系管理员!";

	private static final String PASS = "温馨提示: 恭喜您!用户名密码正确!";

	private static final String NAME_NOT_EXISTS = "温馨提示: 用户名不存在!";

	private static final String PASSWORD_ERROR = "温馨提示: 密码不正确!";

	@Autowired
	private ISystemRepository systemRepository;

	@Override
	public boolean addOperator(SysOperator operator, String roles) {
		if (StringUtils.isNotBlank(operator.getOperatorPassword())) {
			entryptPassword(operator);
		}
		boolean operatorBool = this.systemRepository.addOperator(operator);
		// 保存操作员与角色关系
		boolean operatorRoleBool = this.addOperatorRoleMap(operator.getOperatorId(), StringUtil.splitToLong(roles));
		boolean flag = operatorBool && operatorRoleBool;
		return flag;
	}

	@Override
	public boolean updateOperator(SysOperator sysOperator, String operatorState, String pwd, String roles) {

		SysOperator operator = this.systemRepository.getOperatorById(sysOperator.getOperatorId());
		operator.setOperatorName(sysOperator.getOperatorName());
		operator.setOperatorEmail(sysOperator.getOperatorEmail());
		if (StringUtils.isNotBlank(pwd)) {
			operator.setOperatorPassword(pwd);
			entryptPassword(operator);
		}
		boolean operatorBool = this.systemRepository.updateOperator(operator);
		// 保存操作员与角色关系
		boolean operatorRoleBool = addOperatorRoleMap(sysOperator.getOperatorId(), StringUtil.splitToLong(roles));
		boolean flag = operatorBool && operatorRoleBool;
		return flag;
	}

	@Override
	public boolean updateOperator(SysOperator sysOperator) {
		return this.systemRepository.updateOperator(sysOperator);
	}

	@Override
	public boolean deleteOperator(Long id) {
		return this.systemRepository.deleteOperator(id);
	}

	@Override
	public Pageable<SysOperator> findOperators(SysOperator sysOperator) {
		return this.systemRepository.findOperators(sysOperator);
	}

	@Override
	public SysOperator getOperatorById(Long id) {
		return this.systemRepository.getOperatorById(id);
	}

	@Override
	public boolean addRole(SysRole role) {
		boolean flag = false;
		flag = this.systemRepository.addRole(role);
		return flag;
	}

	@Override
	public boolean updateRole(SysRole sysRole) {
		boolean flag = false;

		SysRole role = this.systemRepository.getRoleById(sysRole.getRoleId());
		role.setRoleName(sysRole.getRoleName());
		role.setRoleDesc(sysRole.getRoleDesc());
		flag = this.systemRepository.updateRole(role);
		return flag;
	}

	@Override
	public boolean deleteRole(Long id) {
		return this.systemRepository.deleteRole(id);
	}

	@Override
	public Pageable<SysRole> findRoles(String roleName, int pageNo, int pageSize) {
		return this.systemRepository.findRoles(roleName, pageNo, pageSize);
	}

	@Override
	public SysRole getRoleById(Long id) {
		return this.systemRepository.getRoleById(id);
	}

	@Override
	public boolean addAuthority(SysAuthority authority) {
		boolean flag = false;
		flag = this.systemRepository.addAuthority(authority);
		return flag;
	}

	@Override
	public boolean updateAuthority(SysAuthority sysAuthority) {
		boolean flag = false;
		SysAuthority authority = this.systemRepository.getAuthorityById(sysAuthority.getAuthorityId());
		authority.setAuthorityName(sysAuthority.getAuthorityName());
		authority.setAuthorityDesc(sysAuthority.getAuthorityDesc());
		authority.setAuthorityOrder(sysAuthority.getAuthorityOrder());
		flag = this.systemRepository.updateAuthority(authority);
		return flag;
	}

	@Override
	public boolean deleteAuthority(Long id) {
		List<SysAuthority> authList = new ArrayList<SysAuthority>();
		List<SysAuthority> condition = new ArrayList<SysAuthority>();
		condition.add(this.systemRepository.getAuthorityById(id));
		authList = this.systemRepository.findRelatedAuthorities(condition);
		authList.addAll(condition);
		List<Long> needDel = new ArrayList<Long>();
		for (SysAuthority au : authList) {
			needDel.add(au.getAuthorityId());
		}

		log.info("本次删除权限的总量", needDel.size());

		boolean bool = this.systemRepository.deleteAuthorityByIds(needDel);
		return bool;
	}

	@Override
	public Pageable<SysAuthority> findAuthoritys(int pageNo, int pageSize) {
		return this.systemRepository.findAuthoritys(pageNo, pageSize);
	}

	@Override
	public SysAuthority getAuthorityById(Long id) {
		return this.systemRepository.getAuthorityById(id);
	}

	@Override
	public SysOperator getOperatorByLoginName(String loginName) {
		return this.systemRepository.getOperatorByLoginName(loginName);
	}

	@Override
	public List<SysOperator> findAllOperator() {
		return this.systemRepository.findAllOperator();
	}

	@Override
	public String findByOperatorId(Long operatorId) throws Exception {
		List<SysOperatorRoleMap> operatorRoleMapList = this.systemRepository.findByOperatorId(operatorId);
		StringBuffer buffer = new StringBuffer();
		if (operatorRoleMapList != null && operatorRoleMapList.size() > 0) {
			SysOperatorRoleMap operatorRoleMap = operatorRoleMapList.get(0);
			buffer.append(operatorRoleMap.getSysRole().getRoleId());
			for (int i = 1; i < operatorRoleMapList.size(); i++) {
				SysOperatorRoleMap operatorRole = operatorRoleMapList.get(i);
				buffer.append(",").append(operatorRole.getSysRole().getRoleId());
			}
		}
		return buffer.toString();
	}

	@Override
	public List<SysRole> findAllRole() {
		return this.systemRepository.findAllRole();
	}

	@Override
	public boolean addOperatorRoleMap(SysOperatorRoleMap operatorRoleMap) {
		return this.systemRepository.addOperatorRoleMap(operatorRoleMap);
	}

	@Override
	public SysOperatorRoleMap getOperatorRoleMapByRoleId(String operatorId, String roleId) {
		return this.systemRepository.getOperatorRoleMapByRoleId(operatorId, roleId);
	}

	@Override
	public boolean deleteOperatorRoleMapByOperatorId(Long operatorId) {
		return this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
	}

	@Override
	public List<SysAuthority> findAllAuthority() {
		return this.systemRepository.findAllAuthority();
	}

	@Override
	public List<SysRoleAuthorityMap> findByRoleId(Long roleId) {
		return this.systemRepository.findByRoleId(roleId);
	}

	@Override
	public boolean deleteRoleAuthorityMapByRoleId(Long roleId) {
		return this.systemRepository.deleteRoleAuthorityMapByRoleId(roleId);
	}

	@Override
	public boolean addRoleAuthorityMap(SysRoleAuthorityMap roleAuthorityMap) {
		return this.systemRepository.addRoleAuthorityMap(roleAuthorityMap);
	}

	@Override
	public List<SysAuthority> findByRoleIds(List<Long> roleIds) {
		return this.systemRepository.findByRoleIds(roleIds);
	}

	@Override
	public List<SysAuthority> findAuthorityByOperatorId(Long operatorId) {
		return this.systemRepository.findAuthorityByOperatorId(operatorId);
	}

	@Override
	public List<String> getAuthoritiesName(Long operatorId) {
		List<SysAuthority> authorityList = systemRepository.findAuthorityByOperatorId(operatorId);
		if (!CollectionUtils.isEmpty(authorityList)) {
			List<String> list = Lists.newArrayList();
			for (SysAuthority sysAuthority : authorityList) {
				list.add(sysAuthority.getAuthorityCode());
			}
			return list;
		}
		return null;
	}

	@Override
	public int getOperatorLastId() {
		return this.systemRepository.getOperatorLastId();
	}

	@Override
	public int getRoleLastId() {
		return this.systemRepository.getRoleLastId();
	}

	@Override
	public List<Tree> getAuthorityTree() {
		List<Tree> treeList = new ArrayList<Tree>();
		List<SysAuthority> authList = this.systemRepository.findAllAuthority();
		for (SysAuthority auth : authList) {
			boolean expanded = auth.getAuthorityFid() == 1L ? false : true;
			TreeNode treeNode = new TreeNode(auth.getAuthorityId(), auth.getAuthorityFid(), auth.getAuthorityName(),
					expanded);
			treeList.add(treeNode);
		}
		return treeList;
	}

	@Override
	public Boolean addOperatorRoleMap(Long operatorId, List<Long> roleIds) {
		boolean operatorRoleBool = false;
		if (operatorId != null && !"".equals(operatorId)) {
			operatorRoleBool = this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
			if (roleIds != null && roleIds.size() > 0) {
				for (Long role : roleIds) {
					operatorRoleBool = saveOperatorRoleMap(role.toString(), operatorId.toString());
					if (operatorRoleBool == false) {
						break;
					}
				}
			}
		} else {
			operatorRoleBool = this.systemRepository.deleteOperatorRoleMapByOperatorId(operatorId);
		}
		return operatorRoleBool;
	}

	private boolean saveOperatorRoleMap(String str, String operatorId) {
		SysOperatorRoleMap operatorRoleMap = new SysOperatorRoleMap();
		SysOperator operator = new SysOperator();
		operator.setOperatorId(Long.parseLong(operatorId));
		operatorRoleMap.setSysOperator(operator);
		SysRole role = new SysRole();
		role.setRoleId(Long.parseLong(str));
		operatorRoleMap.setSysRole(role);
		return this.systemRepository.addOperatorRoleMap(operatorRoleMap);
	}

	@Override
	public void assignAuthorityForRole(Long roleId, List<Long> authorityIds) {
		this.systemRepository.deleteRoleAuthorityMapByRoleId(roleId);
		if (!CollectionUtils.isEmpty(authorityIds)) {
			this.systemRepository.saveBatchRoleAuthority(roleId, authorityIds);
		}
	}

	private boolean saveRoleAuthorityMap(Long authId, Long roleId) {
		SysRoleAuthorityMap roleAuthorityMap = new SysRoleAuthorityMap();
		SysRole role = new SysRole();
		role.setRoleId(roleId);
		SysAuthority authority = new SysAuthority();
		authority.setAuthorityId(authId);
		roleAuthorityMap.setSysAuthority(authority);
		roleAuthorityMap.setSysRole(role);
		return this.systemRepository.addRoleAuthorityMap(roleAuthorityMap);
	}

	@Override
	public List<SysRoleAuthorityMap> findAuthorityForDisplayTree(Long roleId) {
		List<SysRoleAuthorityMap> ramList = this.systemRepository.findByRoleId(roleId);
		List<SysRoleAuthorityMap> resultList = new ArrayList<SysRoleAuthorityMap>();
		// ids: 结点ID, pIds: 父节点ID
		List<Long> ids = new ArrayList<Long>();
		List<Long> pIds = new ArrayList<Long>();
		for (SysRoleAuthorityMap ram : ramList) {
			SysAuthority auth = this.systemRepository.getAuthorityById(ram.getSysAuthority().getAuthorityId());
			if (auth != null) {
				ids.add(auth.getAuthorityId());
				pIds.add(auth.getAuthorityFid());
			}
		}
		// 排除所有结点中是父节点的ID, 剩余叶子结点ID
		ids.removeAll(pIds);
		for (SysRoleAuthorityMap ram : ramList) {
			if (ids.contains(ram.getSysAuthority().getAuthorityId())) {
				resultList.add(ram);
			}
		}
		return resultList;
	}

	/**
	 * 删除角色
	 */
	@Override
	public Boolean deleteRole(String ids) throws Exception {
		boolean flag = false;

		if (StringUtils.isNotBlank(ids)) {
			List<Long> idsList = StringUtil.splitToLong(ids);
			flag = systemRepository.deleteRole(idsList);
			return flag;
		} else {
			return flag;
		}
	}

	/**
	 * 删除操作员
	 */
	@Override
	public Boolean deleteOperator(String ids) throws Exception {
		boolean flag = false;
		if (StringUtils.isNotBlank(ids)) {
			List<Long> idsList = StringUtil.splitToLong(ids);
			flag = systemRepository.deleteOperator(idsList);
			return flag;
		} else {
			return flag;
		}
	}

	@Override
	public void findOperatorInfo(String id, ModelMap model) throws Exception {
		SysOperator operator = this.systemRepository.getOperatorById(Long.parseLong(id));
		if (operator != null) {
			model.put(OPERATOR, operator);
			List<SysRole> allRoleList = this.systemRepository.findAllRole();
			List<SysRole> currentList = new ArrayList<SysRole>();
			List<SysOperatorRoleMap> operatorRoleMapList = this.systemRepository
					.findByOperatorId(operator.getOperatorId());
			if (operatorRoleMapList.size() != 0) {
				for (int j = 0; j < allRoleList.size(); j++) {
					SysRole role = (SysRole) allRoleList.get(j);
					for (int i = 0; i < operatorRoleMapList.size(); i++) {
						SysOperatorRoleMap operatorRoleMap = (SysOperatorRoleMap) operatorRoleMapList.get(i);
						if (operatorRoleMap.getSysRole().getRoleId().longValue() == role.getRoleId().longValue()) {
							currentList.add(role);
						}
					}
				}
			}
			model.put("currentList", currentList);
		}
	}

	@Override
	public void toFindAuthorityInfo(String id, ModelMap model) throws Exception {
		List<SysAuthority> listAuthority = new ArrayList<SysAuthority>();
		SysRole role = this.systemRepository.getRoleById(Long.parseLong(id));
		List<SysRoleAuthorityMap> roleAuthorityMapList = this.systemRepository.findByRoleId(Long.parseLong(id));
		if (roleAuthorityMapList.size() != 0) {
			for (int i = 0; i < roleAuthorityMapList.size(); i++) {
				SysRoleAuthorityMap roleAuthorityMap = (SysRoleAuthorityMap) roleAuthorityMapList.get(i);
				SysAuthority authority = this.systemRepository
						.getAuthorityById(roleAuthorityMap.getSysAuthority().getAuthorityId());
				if (authority != null) {
					listAuthority.add(authority);
				}
			}
		}
		model.put(ROLE, role);
		model.put("listAuthority", listAuthority);
	}

	@Override
	public void toUpdateRole(String id, ModelMap model) throws Exception {
		SysRole role = this.systemRepository.getRoleById(Long.parseLong(id));
		model.put(ROLE, role);

		List<SysAuthority> allAuthorityList = this.systemRepository.findAllAuthority();
		List<SysAuthority> currentList = new ArrayList<SysAuthority>();
		List<SysAuthority> lastList = new ArrayList<SysAuthority>();
		if (role != null) {
			List<SysRoleAuthorityMap> roleAuthorityMapList = this.systemRepository.findByRoleId(role.getRoleId());
			if (roleAuthorityMapList.size() != 0) {
				for (int j = 0; j < allAuthorityList.size(); j++) {
					SysAuthority authority = (SysAuthority) allAuthorityList.get(j);
					boolean flag = false;
					for (int i = 0; i < roleAuthorityMapList.size(); i++) {
						SysRoleAuthorityMap roleAuthorityMap = (SysRoleAuthorityMap) roleAuthorityMapList.get(i);
						if (roleAuthorityMap.getSysAuthority().getAuthorityId().longValue() == authority
								.getAuthorityId().longValue()) {
							currentList.add(authority);
							flag = true;
						}
					}
					if (!flag) {
						lastList.add(authority);
					}
				}
			} else {
				if (role.getRoleId() != 0) {
					lastList = allAuthorityList;
				}
			}
		}
		model.put("listCurrentAuthority", currentList);
		model.put("lastAuthority", lastList);
	}

	@Override
	public Object[] dynamicOperatorRole(long operatorId) throws Exception {
		List<SysRole> allRoleList = this.systemRepository.findAllRole();
		List<SysRole> currentList = new ArrayList<SysRole>();
		List<SysRole> lastList = new ArrayList<SysRole>();
		List<SysOperatorRoleMap> operatorRoleMapList = this.systemRepository.findByOperatorId(operatorId);
		if (operatorRoleMapList.size() != 0) {
			for (int j = 0; j < allRoleList.size(); j++) {
				SysRole role = (SysRole) allRoleList.get(j);
				boolean flag = false;
				for (int i = 0; i < operatorRoleMapList.size(); i++) {
					SysOperatorRoleMap operatorRoleMap = (SysOperatorRoleMap) operatorRoleMapList.get(i);
					if (operatorRoleMap.getSysRole().getRoleId().longValue() == role.getRoleId().longValue()) {
						currentList.add(role);
						flag = true;
					}
				}
				if (!flag) {
					lastList.add(role);
				}
			}
		} else {
			if (!"0".equals(operatorId)) {
				lastList = allRoleList;
			}
		}

		Object[] o = new Object[2];
		o[0] = currentList;
		o[1] = lastList;

		return o;
	}

	@Override
	public boolean addOperatorRoleMap(String rId, String operatorId) throws Exception {
		Boolean flag = false;
		if (StringUtils.isNotEmpty(operatorId)) {
			flag = this.systemRepository.deleteOperatorRoleMapByOperatorId(Long.parseLong(operatorId));
			if (StringUtils.isNotEmpty(rId)) {
				String arr[] = rId.split(",");
				if (arr != null) {
					for (int i = 0; i < arr.length; i++) {
						flag = saveOperatorRoleMap(arr[i], operatorId);
					}
				}
			}
		}

		return flag;
	}

	@Override
	public Object[] dynamicRoleAuthority(long roleId) throws Exception {
		List<SysAuthority> allAuthorityList = this.systemRepository.findAllAuthority();
		List<SysAuthority> currentList = new ArrayList<SysAuthority>();
		List<SysAuthority> lastList = new ArrayList<SysAuthority>();
		List<SysRoleAuthorityMap> roleAuthorityMapList = this.systemRepository.findByRoleId(roleId);
		if (roleAuthorityMapList.size() != 0) {
			for (int j = 0; j < allAuthorityList.size(); j++) {
				SysAuthority authority = (SysAuthority) allAuthorityList.get(j);
				boolean flag = false;
				for (int i = 0; i < roleAuthorityMapList.size(); i++) {
					SysRoleAuthorityMap roleAuthorityMap = (SysRoleAuthorityMap) roleAuthorityMapList.get(i);
					if (roleAuthorityMap.getSysAuthority().getAuthorityId().longValue() == authority.getAuthorityId()
							.longValue()) {
						currentList.add(authority);
						flag = true;
					}
				}
				if (!flag) {
					lastList.add(authority);
				}
			}
		} else {
			if (!"0".equals(roleId)) {
				lastList = allAuthorityList;
			}
		}
		Object[] o = new Object[2];
		o[0] = currentList;
		o[1] = lastList;

		return o;
	}

	@Override
	public boolean addRoleAuthorityMap(String rId, String roleId) throws Exception {
		boolean flag = false;
		if (StringUtils.isNotEmpty(roleId)) {
			flag = this.systemRepository.deleteRoleAuthorityMapByRoleId(Long.parseLong(roleId));
			if (StringUtils.isNotEmpty(rId)) {
				String arr[] = rId.split(",");
				if (arr != null) {
					for (int i = 0; i < arr.length; i++) {
						flag = saveRoleAuthorityMap(Long.parseLong(arr[i]), Long.parseLong(roleId));
					}
				}
			}
		}
		return flag;
	}

	private JsonResult getResultStr(SysOperator operator) {
		boolean status = false;
		String str;
		List<SysOperatorRoleMap> operatorRoleMapList = this.systemRepository.findByOperatorId(operator.getOperatorId());
		if (operatorRoleMapList.size() != 0) {
			List<SysRole> listRole = new ArrayList<SysRole>();
			List<SysAuthority> listAuthority = new ArrayList<SysAuthority>();
			if (operatorRoleMapList.size() != 0) {
				for (int i = 0; i < operatorRoleMapList.size(); i++) {
					// 2.找到该角色对应的权限
					SysOperatorRoleMap operatorRoleMap = (SysOperatorRoleMap) operatorRoleMapList.get(i);
					listRole.add(operatorRoleMap.getSysRole());
				}
				for (int j = 0; j < listRole.size(); j++) {
					SysRole role = listRole.get(j);
					List<SysRoleAuthorityMap> roleAuthorityMapList = this.systemRepository
							.findByRoleId(role.getRoleId());
					if (roleAuthorityMapList.size() != 0) {
						for (int k = 0; k < roleAuthorityMapList.size(); k++) {
							SysRoleAuthorityMap roleAuthorityMap = (SysRoleAuthorityMap) roleAuthorityMapList.get(k);
							listAuthority.add(roleAuthorityMap.getSysAuthority());
						}
					}
				}
				if (listAuthority.size() <= 0) {
					str = NO_AUTHORITY;
				} else {
					str = PASS;
					status = true;
				}
			} else {
				str = NO_AUTHORITY;
			}
		} else {
			str = NO_AUTHORITY;
		}
		return new JsonResult(status, str);
	}

	@Override
	public JsonResult checkLoginNameExists(String userName, String pwd) throws Exception {
		JsonResult jr;
		SysOperator operator = this.systemRepository.getOperatorByLoginName(userName);
		if (operator != null) {
			if (userName.trim().equals(operator.getOperatorLoginName()) && (operator.checkPassword(pwd))) {
				jr = getResultStr(operator);
			} else {
				jr = new JsonResult(false, PASSWORD_ERROR);
			}
		} else {
			jr = new JsonResult(false, NAME_NOT_EXISTS);
		}
		return jr;
	}

	@Override
	public Pageable<SysAuthority> findAuthorityByPId(long id, int pageNo, int pageSize) {
		SysAuthority authority = this.systemRepository.getAuthorityById(id);
		if (authority == null) {
			return null;
		}
		return this.systemRepository.findAuthorityByPId(id, pageNo, pageSize);
	}

	@Override
	public List<Long> findRoleIdByOperatorId(Long operatorId) {
		return systemRepository.findRoleIdByOperatorId(operatorId);
	}

	/**
	 * 设定安全的密码，通过md5，生成32位加密密码
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(SysOperator sysOperator) {
//		byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
//		sysOperator.setSalt(EncodeUtils.hexEncode(salt));
//		byte[] hashPassword = Digests.sha1(sysOperator.getOperatorPassword().getBytes(), salt,
//				Constants.HASH_INTERATIONS);
//		sysOperator.setOperatorPassword(EncodeUtils.hexEncode(hashPassword));
		String password32 = null;
		try {
			password32 = Digests.getMd5Bit32(sysOperator.getOperatorPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		sysOperator.setOperatorPassword(password32);
	}

	@Override
	public Boolean deleteDic(String ids) {

		boolean flag = false;

		if (StringUtils.isNotBlank(ids)) {
			List<Long> idsList = StringUtil.splitToLong(ids);
			flag = this.systemRepository.deleteSysDic(idsList);
			return flag;
		} else {
			return flag;
		}
	}

	@Override
	public Pageable<SysDic> findDics(String type, int pageNo, int pageSize) {
		return this.systemRepository.findSysDics(type, pageNo, pageSize);

	}

	@Override
	public boolean updateDic(SysDic dic) {
		return this.systemRepository.updateSysDic(dic);
	}

	@Override
	public boolean addDic(SysDic dic) {
		return this.systemRepository.addSysDic(dic);
	}

	@Override
	public List<SysDic> findDics(String type) {
		return this.systemRepository.findDicsByType(type);
	}

	@Override
	public SysDic findDicByTypeAndKey(String key, String type) {
		return this.systemRepository.getSysDicByKeyAndType(key, type);
	}

	@Override
	public SysOperator getByLoginNameAndState(String username) {
		String[] names = username.split(",");
		Integer state = Integer.valueOf(names[0]);
		SysOperator o = this.systemRepository.getByLoginNameAndState(names[1], state);
		if (o != null) {
			return o;
		}
		return null;
	}
}
