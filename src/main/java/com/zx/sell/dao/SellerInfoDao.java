package com.zx.sell.dao;

import com.zx.sell.dataobject.SellerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerInfoDao {

    /**
     * 根据openId获取Seller信息
     * @param openId
     * @return
     */
    SellerInfo getSellerByopenId(String openId);

    /**
     * 根据ID获取Seller信息
     * @param ID
     * @return
     */
    SellerInfo getSellerByID(String ID);

    /**
     * 添加新Seller信息
     * @param sellerInfo
     * @return
     */
    Integer addSeller(SellerInfo sellerInfo);

    /**
     * 修改Seller信息
     * @param sellerInfo
     * @return
     */
    Integer updateSeller(SellerInfo sellerInfo);

}
