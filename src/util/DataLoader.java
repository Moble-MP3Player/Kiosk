package util;

import java.util.List;

/**
 * 파일을 불러오고 저장하는 클래스
 */
public interface DataLoader {
    public List loadProductData();
    public List loadRecipeData();
    public List loadBucketData();

}
