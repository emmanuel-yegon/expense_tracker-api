package com.expensetracker.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryWrapper {

     Integer id;
     String title;
     String description;
     Integer userId;

    public CategoryWrapper(Integer id, String title, String description, Integer userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId  = userId;
    }

    public CategoryWrapper(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }


}
