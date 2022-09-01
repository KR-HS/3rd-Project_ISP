package kr.human.ISP.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityDAO {
	public void insert(String user_id) throws SQLException;
}
