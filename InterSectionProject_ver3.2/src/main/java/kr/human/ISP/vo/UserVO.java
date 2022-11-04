package kr.human.ISP.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO implements UserDetails {
	private static final long serialVersionUID = 1L;
	public int user_idx;
	public String user_id;
	public String user_pwd;
	public String user_name;
	public Date user_birth;
	public String user_gender;
	public String user_phone;
	public String user_use;
	public String user_UUID;
	public String user_isPublic;
	public String user_isDelete;
	public String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return Collections.singletonList(new SimpleGrantedAuthority(role));
		List<GrantedAuthority> authorities = new ArrayList<>();
		// ROLE_ADMIN, ROLE_USER 형태로 저장되었을떄
		String[] roles = role.split(",");
		for (int i = 0; i < roles.length; i++) {
			authorities.add(new SimpleGrantedAuthority(roles[i].trim()));
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return user_pwd;
	}

	@Override
	public String getUsername() {
		return user_id;
	}

}
