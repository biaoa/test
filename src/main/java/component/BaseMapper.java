package component;

import java.util.List;

import com.linle.common.util.Page;

public interface BaseMapper<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
    
    public List<T> getAllData(Page<T> page);
}
