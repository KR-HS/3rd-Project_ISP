package kr.human.ISP.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDAO {
	String selectToday();
}
