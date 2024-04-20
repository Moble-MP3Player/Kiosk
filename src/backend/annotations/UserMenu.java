package backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 유저 메뉴에 추가할 메뉴에 추가해주세요. <br>
 * ex) @CustomerService("상품 구매 메뉴")
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserMenu {
    String value();
}
