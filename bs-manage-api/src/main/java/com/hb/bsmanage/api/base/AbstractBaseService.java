package com.hb.bsmanage.api.base;

import com.hb.mybatis.base.DmlMapper;
import com.hb.mybatis.helper.QueryCondition;
import com.hb.mybatis.helper.WhereCondition;
import com.hb.mybatis.model.PagesResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * service的公共抽象类
 *
 * @version v0.1, 2020/7/24 14:50, create by huangbiao.
 */
public abstract class AbstractBaseService {

    /**
     * 公共数据库操作类
     */
    @Autowired
    private DmlMapper dmlMapper;

    /**
     * 通过QueryCondition来查询
     *
     * @param entityClass 实体类
     * @param query       QueryCondition查询对象
     * @param <T>         实体类
     * @return 集合
     */
    protected <T> List<T> dynamicSelect(Class<T> entityClass, QueryCondition query) {
        return dmlMapper.dynamicSelect(entityClass, query);
    }

    /**
     * 查询总条数
     *
     * @param entityClass 实体类
     * @param query       查询条件
     * @return 总条数
     */
    protected <T> int selectCount(Class<T> entityClass, QueryCondition query) {
        return dmlMapper.selectCount(entityClass, query);
    }

    /**
     * 分页查询集合
     *
     * @param entityClass 实体类
     * @param query       查询对象
     * @return 分页集合
     */
    protected <T> PagesResult<T> selectPages(Class<T> entityClass, QueryCondition query) {
        return dmlMapper.selectPages(entityClass, query);
    }

    /**
     * 自定义sql语句动态查询，要求写全where前面的sql
     *
     * @param sqlStatementBeforeWhere where前面的sql语句
     * @param whereCondition          where条件
     * @return 结果集合
     */
    protected List<Map<String, Object>> customSelect(String sqlStatementBeforeWhere, WhereCondition whereCondition) {
        return dmlMapper.customSelect(sqlStatementBeforeWhere, whereCondition);
    }

    /**
     * 选择性插入
     *
     * @param entity 实体类对象
     * @return 插入行数
     */
    protected <T> int insertBySelective(T entity) {
        return dmlMapper.insertBySelective(entity);
    }

    /**
     * 选择性更新
     *
     * @param entity         实体类对象
     * @param whereCondition 条件
     * @return 更新行数
     */
    protected <T> int updateBySelective(T entity, WhereCondition whereCondition) {
        return dmlMapper.updateBySelective(entity, whereCondition);
    }

    /**
     * 选择性删除
     *
     * @param entityClass    实体类对象
     * @param whereCondition 条件
     * @return 删除行数
     */
    protected <T> int deleteBySelective(Class<T> entityClass, WhereCondition whereCondition) {
        return dmlMapper.deleteBySelective(entityClass, whereCondition);
    }

}

    