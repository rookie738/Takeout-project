package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        Long id  = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //插入口味
        if(flavors!=null && flavors.size()>0){
            //获得菜品id

            for (DishFlavor dishFlavor:flavors){
                dishFlavor.setDishId(id);
                dishFlavorService.save(dishFlavor);
            }

        }

    }
}
