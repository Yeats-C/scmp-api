package com.aiqin.bms.scmp.api.purchase.dao;

import com.aiqin.bms.scmp.api.purchase.domain.BiAClassification;

import java.util.List;

public interface BiAClassificationDao {

    List<BiAClassification> aCategoryList(String data);
}