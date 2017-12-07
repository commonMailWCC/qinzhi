package com.qinzhi.security;

import com.qinzhi.bean.Constants;
import com.qinzhi.domain.SysOperator;
import com.qinzhi.service.ISystemService;
import com.qinzhi.utils.EncodeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * shiro的认证授权域
 * 
 * @author liwei
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {
	private static Log log = LogFactory.getLog(ShiroAuthorizingRealm.class);

	private ISystemService systemService;

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * 构造函数，设置安全的初始化信息
	 */
	public ShiroAuthorizingRealm() {
		super();
		setAuthenticationTokenClass(UsernamePasswordToken.class);
	}

	/**
	 * 获取当前认证实体的授权信息（授权包括：角色、权限）
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ShiroPrincipal subject = (ShiroPrincipal) super.getAvailablePrincipal(principals);
		String username = subject.getUsername();
		Long userId = subject.getId();
		try {
			if (!subject.isAuthorized()) {
				// 根据用户名称，获取该用户所有的权限列表
				List<String> authorities = systemService.getAuthoritiesName(userId);
				// List<String> rolelist = systemService.getRolesName(userId);
				subject.setAuthorities(authorities);
				// subject.setRoles(rolelist);
				subject.setAuthorized(true);
				// log.info("用户【" + username + "】 权限列表为：" +
				// subject.getAuthorities());
			} else {
				// log.info("用户【" + username + "】已授权......");
			}
		} catch (RuntimeException e) {
			log.error("用户【" + username + "】授权失败......");
			throw new AuthorizationException("用户【" + username + "】授权失败");
		}
		// 给当前用户设置权限
		info.addStringPermissions(subject.getAuthorities());
		info.addRoles(subject.getRoles());
		return info;
	}

	/**
	 * 根据认证方式（如表单）获取用户名称、密码
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		if (username == null) {
			throw new AccountException("用户名不能为空");
		}

		SysOperator user = null;
		try {
			if (username.contains(",")) {
				user = systemService.getByLoginNameAndState(username);
				upToken.setUsername(username.substring(2, username.length()));
			}else{
				user = systemService.getOperatorByLoginName(username);
			}
		} catch (Exception ex) {
			log.warn("获取用户失败\n" + ex.getMessage());
		}
		if (user == null) {
			log.warn("用户不存在");
			throw new UnknownAccountException("用户不存在");
		}
		log.info("用户【" + username + "】登录成功");
//		byte[] salt = EncodeUtils.hexDecode(user.getSalt());
		ShiroPrincipal subject = new ShiroPrincipal(user);
		List<String> authorities = systemService.getAuthoritiesName(user.getOperatorId());
		// List<String> rolelist = systemService.getRolesName(user.getId());
		subject.setAuthorities(authorities);
		// subject.setRoles(rolelist);
		subject.setAuthorized(true);
//		return new SimpleAuthenticationInfo(subject, user.getOperatorPassword(), ByteSource.Util.bytes(salt),
//				getName());
		return new SimpleAuthenticationInfo(subject, user.getOperatorPassword(), getName());
	}

//	@PostConstruct
//	public void initCredentialsMatcher() {
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
//		matcher.setHashIterations(Constants.HASH_INTERATIONS);
//		setCredentialsMatcher(matcher);
//	}

}
