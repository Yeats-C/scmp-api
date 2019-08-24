package com.aiqin.bms.scmp.api.common;

import com.aiqin.bms.scmp.api.workflow.enumerate.WorkFlow;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className AllocationTypeEnum
 * @date 2019/7/1 17:29
 */
@Getter
public enum AllocationTypeEnum {
    ALLOCATION(Byte.valueOf("1"),"调拨","DB", WorkFlow.APPLY_ALLOCATTION,(byte)2,4),
    MOVE(Byte.valueOf("2"),"移库","YK", WorkFlow.MOVEMENT_ODER,(byte)4,6),
    SCRAP(Byte.valueOf("3"),"报废","BF",WorkFlow.SCRAP,(byte)6,8);
    private Byte type;
    private String typeName;
    private String generateCode;
    private WorkFlow workFlow;
    private Byte inOutType;
    private Integer lockType;
    AllocationTypeEnum(Byte type, String typeName, String generateCode, WorkFlow workFlow,Byte inOutType,Integer lockType) {
        this.type = type;
        this.typeName = typeName;
        this.generateCode = generateCode;
        this.workFlow = workFlow;
        this.inOutType = inOutType;
        this.lockType = lockType;
    }

    public static AllocationTypeEnum getAllocationTypeEnumByType(Byte type){
        for (AllocationTypeEnum allocationTypeEnum : AllocationTypeEnum.values()){
            if(Objects.equals(allocationTypeEnum.getType(),type)){
                return allocationTypeEnum;
            }
        }
        return null;
    }
    public static Map<Byte,AllocationTypeEnum> getAll(){
            return Arrays.stream(values()).collect(Collectors.toMap(AllocationTypeEnum::getType, Function.identity()));
    }
}
