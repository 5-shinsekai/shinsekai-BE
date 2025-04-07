package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.entity.Size;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryListCustomRepository {
    List<Size> findSizesByMainCategory(Long mainCategoryId);
    List<Color> findColorsByMainCategory(Long mainCategoryId);
}
