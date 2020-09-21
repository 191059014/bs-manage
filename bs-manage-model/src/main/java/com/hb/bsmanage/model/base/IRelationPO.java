package com.hb.bsmanage.model.base;

/**
 * 关系实体
 *
 * @version v0.1, 2020/9/16 10:32, create by huangbiao.
 */
public interface IRelationPO extends ITimestampPO {

    /**
     * 获取ParentId
     */
    String getParentId();

    /**
     * 设置ParentId
     */
    void setParentId(String parentId);

    /**
     * 获取Path
     */
    String getPath();

    /**
     * 设置Path
     */
    void setPath(String path);

}

    