package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.CategoryDAO;
import kr.human.ISP.dao.LikeMoimDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.SignUpDAO;
import kr.human.ISP.dao.UserCategoryDAO;
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.SignUpVO;
import kr.human.ISP.vo.UserCategoryVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private LikeMoimDAO likeMoimDAO;

	@Autowired
	private UserCategoryDAO userCategoryDAO;

	@Autowired
	private MoimDAO moimDAO;

	@Autowired
	private SignUpDAO signUpDAO;

	@Autowired
	private CategoryDAO categoryDAO;
	
	
	@Override
	public boolean insertUser(UserVO userVO) {
		boolean result = false;
		if (userVO != null) {
			try {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				userVO.setUser_pwd(bCryptPasswordEncoder.encode(userVO.getUser_pwd()));
				userVO.setUser_UUID(UUID.randomUUID().toString());
				userDAO.insert(userVO);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public void update(UserVO userVO, String newPassword) {
		try {
			if (userVO != null) {
				UserVO dbVO = userDAO.selectByIdx(userVO.getUser_idx());
				if (dbVO != null && dbVO.getUser_pwd().equals(userVO.getUser_pwd())) {
					userVO.setUser_pwd(newPassword);
					userDAO.update(userVO);
					/*
					 * if(userVO.getFile() != null) {
					 * userVO.getFile().setUser_idx(userVO.getUser_idx());
					 * upFileDAO.insert(userVO.getFile()); }
					 */
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(UserVO userVO) {
		try {
			if (userVO != null) {
				UserVO dbVO = userDAO.selectByIdx(userVO.getUser_idx());
				if (dbVO.getUser_pwd().equals(userVO.getUser_pwd())) {
					userDAO.updateIsDelete(userVO.getUser_idx());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public PagingVO<UserVO> selectList(CommVO commVO) {
		PagingVO<UserVO> pagingVO = null;
		try {
			int totalCount = userDAO.selectCount();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("endNo", pagingVO.getEndNo());
			List<UserVO> list = userDAO.selectList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public String searchUserid(String user_name, String user_phone) {
		String user_id = "";
		if (user_name != null && user_phone != null) {
			try {
				user_id = userDAO.selectByPhone(user_name, user_phone);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user_id;
	}

	@Override
	public UserVO searchPassword(String user_id) {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByUserid(user_id);
			if (userVO != null && userVO.getUser_id().equals(user_id)) {
				// 임시비번을 만들어서 DB의 비번을 변경하고 변경된 비번을 메일로 보낸다.
				String newPassword = PasswordService.makeNewPassword();
				// DB의 비번을 변경하고
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("user_id", user_id);
				map.put("user_pwd", newPassword);
				userDAO.updatePassword(map);
				// 변경된 비번을 메일로 발송해준다.
				// EmailService.sendMail(userVO.getEmail(), id + "님의 비밀번호 변경입니다.", id + "님의 임시
				// 비밀번호입니다<br>" + "임시비밀번호는 \""
				// + newPassword + "\"입니다.<br>" + "로그인 하신후 반드시 변경하시기 바랍니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userVO;
	}

	@Override
	public void emailConfirm(int user_idx) {
		try {
			userDAO.updateUse(user_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean idCheck(String user_id) {
		boolean flag = false;
		if (user_id != null) {
			try {
				if (userDAO.selectUseridCount(user_id) > 0) {
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public void supervisorDelete(UserVO userVO) {
		try {
			userDAO.delete(userVO.getUser_idx());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void supervisorUpdate(UserVO userVO) {
		try {
			userDAO.supervisorUpdate(userVO);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int likeMoimCount(UserVO userVO) {
		int count = 0;
		try {
			if (userVO != null) {
				count = likeMoimDAO.selectCountByUser(userVO.getUser_idx());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int moimCount(UserVO userVO) {
		int count = 0;
		try {
			if (userVO != null) {
				count = moimDAO.selectCountByUser(userVO.getUser_idx());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int joinMoimCount(UserVO userVO) {
		int count = 0;
		try {
			if (userVO != null) {
				count = signUpDAO.selectCountByUserApply(userVO.getUser_idx());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<CategoryVO> selectCategory(UserVO userVO) {
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			if (userVO != null) {
				List<UserCategoryVO> list1 = userCategoryDAO.selectByCategory(userVO.getUser_idx());
				if (list1 != null) {
					for (UserCategoryVO userCategoryVO : list1) {
						list.add(categoryDAO.selectByIdx(userCategoryVO.getCategory_idx()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public UserVO selectByIdx(int user_idx) {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByIdx(user_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userVO;
	}

	@Override
	public List<MoimVO> selectMoimList(int user_idx) {
		List<MoimVO> list = null;
		try {
			list = moimDAO.selectByUser(user_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PagingVO<UserVO> selectUserList(MoimVO moimVO, CommVO commVO) {
		PagingVO<UserVO> pv = null;
		try {
			int totalCount = signUpDAO.selectCountByMoim(moimVO.getMoim_idx());
			pv = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pv.getStartNo());
			hashMap.put("endNo", pv.getEndNo());
			hashMap.put("moim_idx", moimVO.getMoim_idx());
			List<UserVO> list = userDAO.selectSignUpUser(hashMap);
			System.out.println(list);
			pv.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pv;
	}

	@Override
	public void apply(int signUp_idx) {
		try {
			signUpDAO.updateApply(signUp_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refuse(int signUp_idx) {
		try {
			signUpDAO.updateRefuse(signUp_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void insertSignUp(UserVO userVO, MoimVO moimVO, SignUpVO signUpVO) {
		/*
		try {
			if (userVO != null) {
				if (moimVO != null) {
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					map.put("user_idx", userVO.getUser_idx());
					map.put("moimVO_idx", moimVO.getMoim_idx());
					signUpDAO.insert(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/   // 새롬이만 쓰는걸로 알고 있는데 만들어져 있어서 주석처리함
	}
	
	

	@Override
	public int selectCountByMoim(MoimVO moimVO) {
		int count = 0;
		try {
			count = signUpDAO.selectCountByMoim(moimVO.getMoim_idx());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<UserVO> selectByMoimIdx(MoimVO moimVO) {
		List<UserVO> list = null;
		try {
			list = signUpDAO.selectByMoim(moimVO.getMoim_idx());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int selectSignUpIdx(HashMap<String, Integer> map) {
		int signUp_idx = 0;
		try {
			signUp_idx = signUpDAO.selectSignUpIdx(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return signUp_idx;
	}

	@Override
	public List<SignUpVO> selectSignUpVOByMoimIdx(int moim_idx) {
		List<SignUpVO> list = null;
		try {
			list = signUpDAO.selectByMoimIdx(moim_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int findCurrentUserIdx() {
		int idx = 0;
		try {
			idx = userDAO.selectCurrentUserIdx();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idx;
	}

	@Override
	public int userInfoCheck(String user_id, String user_name) {
		int count = 0;
		try {
			count = userDAO.userInfoCheck(user_id, user_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void UserTempPassword(String user_id, String user_name, String tempPassword) {
		String user_pwd = tempPassword;
		try {
			userDAO.updateTempPassword(user_id, user_name, user_pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int selectCategoryIdxByScName(String sc_name) {
		int categoryIdx = 0;
		try {
			categoryIdx = categoryDAO.selectCategoryIdxByScName(sc_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryIdx;
	}

	@Override
	public void insertUserCategory(HashMap<String, Integer> map) {
		try {
			userCategoryDAO.insert(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkUserLogin(HashMap<String, String> map) {
		boolean flag = false;
		try {
			UserVO userVO = userDAO.selectByUserid(map.get("user_id"));
			if (userVO != null) {
				if (userVO.getUser_pwd().equals(map.get("user_pwd"))) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public UserVO selectById(String user_id) {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByUserid(user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info(userVO.toString());
		return userVO;
	}

	@Override
	public List<UserVO> selectSignUpList(int moim_idx) {
		List<UserVO> list=null;
		try {
			list = userDAO.selectSignUpList(moim_idx);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public UserVO loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByUserid(username);
			if (userVO == null){
	            throw new UsernameNotFoundException("User not authorized.");
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userVO;
	}


}
