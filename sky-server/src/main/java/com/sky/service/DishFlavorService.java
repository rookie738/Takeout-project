package com.sky.service;

import com.sky.entity.DishFlavor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishFlavorService {

    void save(DishFlavor dishFlavor);

    void deleteBatch(List<String> dishIds);
}
