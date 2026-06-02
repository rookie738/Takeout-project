package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishFlavorService;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;


    @PostMapping()
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.save(dishDTO);

        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult page = dishService.page(dishPageQueryDTO);
        return Result.success(page);
    }

    @DeleteMapping()
    public Result deleteBatch(@RequestParam List<String> ids) {

        if (dishService.isOnSale(ids)) {//起售的不能删
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        } else if (dishService.isInSetMeal(ids)) {//在套餐内的不能删
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        } else {
            //删菜品
            dishService.deleteBatch(ids);
            //还要删对应的口味
            dishFlavorService.deleteBatch(ids);

        }
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping()
    public Result update(@RequestBody DishVO dishVO) {
        dishService.update(dishVO);
        return Result.success();

    }

}
