package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.page(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

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

    @Override
    public boolean isOnSale(List<String> ids) {
        if (dishMapper.isOnSale(ids)>0)
            return true;
        else return false;
    }

    @Override
    public boolean isInSetMeal(List<String> ids) {
        if (dishMapper.isInSetMeal(ids)>0)
            return true;
        return false;
    }

    @Override
    public void deleteBatch(List<String> ids) {
        dishMapper.deleteBatch(ids);
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO =  dishMapper.getById(id);
        return dishVO;
    }
}
