package com.zx.sell.Service.impl;

import com.zx.sell.Service.SellerService;
import com.zx.sell.dao.SellerInfoDao;
import com.zx.sell.dataobject.SellerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo getSellerInfoByOpenId(String openId) {
        return sellerInfoDao.getSellerByopenId(openId);
    }
}
