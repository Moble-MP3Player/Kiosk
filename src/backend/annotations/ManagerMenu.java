package backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 관리자모드 메뉴에 추가할 함수에 입력해주세요. <br>
 * ex) @ManagementService("상품추가")
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ManagerMenu {
    String value();
}
