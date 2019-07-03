package com.aiqin.bms.scmp.api.common;

import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import lombok.Getter;

import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className AllocationTypeEnum
 * @date 2019/7/1 17:29
 * @description TODO
 */
@Getter
public enum AllocationTypeEnum {
    ALLOCATION(Byte.valueOf("1"),"调拨","DB", WorkFlow.APPLY_ALLOCATTION),
    MOVE(Byte.valueOf("2"),"移库","YK", WorkFlow.MOVEMENT_ODER),
    SCRAP(Byte.valueOf("3"),"报废","BF",WorkFlow.SCRAP),
    ;
    private Byte type;
    private String typeName;
    private String generateCode;
    private WorkFlow workFlow;

    AllocationTypeEnum(Byte type, String typeName, String generateCode, WorkFlow workFlow) {
        this.type = type;
        this.typeName = typeName;
        this.generateCode = generateCode;
        this.workFlow = workFlow;
    }

    public static AllocationTypeEnum getAllocationTypeEnumByType(Byte type){
        for (AllocationTypeEnum allocationTypeEnum : AllocationTypeEnum.values()){
            if(Objects.equals(allocationTypeEnum.getType(),type)){
                return allocationTypeEnum;
            }
        }
        return null;
    }
}
