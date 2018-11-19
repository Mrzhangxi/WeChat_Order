package com.zx.sell.Service;

import com.zx.sell.dataobject.SellerInfo;

public interface SellerService {

    /**
     * 通过openId获取Seller
     * @param openId
     * @return
     */
    SellerInfo getSellerInfoByOpenId(String openId);

}
