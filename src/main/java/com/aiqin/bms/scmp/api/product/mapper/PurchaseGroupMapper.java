//package com.aiqin.bms.scmp.api.product.mapper;
//
//import dto.PurchaseGroup;
//
//public interface PurchaseGroupMapper {
//    /**
//     *  根据主键删除数据库的记录,purchase_group
//     *
//     * @param id
//     */
//    int deleteByPrimaryKey(Long id);
//
//    /**
//     *  新写入数据库记录,purchase_group
//     *
//     * @param record
//     */
//    int insert(PurchaseGroup record);
//
//    /**
//     *  动态字段,写入数据库记录,purchase_group
//     *
//     * @param record
//     */
//    int insertSelective(PurchaseGroup record);
//
//    /**
//     *  根据指定主键获取一条数据库记录,purchase_group
//     *
//     * @param id
//     */
//    PurchaseGroup selectByPrimaryKey(Long id);
//
//    /**
//     *  动态字段,根据主键来更新符合条件的数据库记录,purchase_group
//     *
//     * @param record
//     */
//    int updateByPrimaryKeySelective(PurchaseGroup record);
//
//    /**
//     *  根据主键来更新符合条件的数据库记录,purchase_group
//     *
//     * @param record
//     */
//    int updateByPrimaryKey(PurchaseGroup record);
//}