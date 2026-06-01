package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void deleteBatch(List<String> dishIds);

    void insert(DishFlavor dishFlavor);
}
