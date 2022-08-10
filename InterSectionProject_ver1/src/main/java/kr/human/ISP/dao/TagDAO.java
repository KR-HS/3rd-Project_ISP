package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.TagVO;
@Mapper
public interface TagDAO {
	public TagVO selectByIdx(int tag_idx) throws SQLException;
	public List<TagVO> selectByMoim(int moim_idx) throws SQLException;
	public void insert(TagVO tagVO) throws SQLException;
	public void delete(TagVO tagVO) throws SQLException;
}
