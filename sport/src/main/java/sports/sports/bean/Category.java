package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by invinjun on 2015/9/17.
 */
public class Category implements Serializable{
    private Integer categoryId;
    private String categoryTitle;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
