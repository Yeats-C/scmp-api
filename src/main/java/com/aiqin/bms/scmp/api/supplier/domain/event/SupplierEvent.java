package com.aiqin.bms.scmp.api.supplier.domain.event;

import com.aiqin.bms.scmp.api.common.DataBaseType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class SupplierEvent {
    private DataBaseType type;

    private String code;

    private String name;
}
