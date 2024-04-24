package backend.util;

import backend.db.DBs;
import kiosk.Kiosk;
import model.Product;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 문자열 처리를 위한 함수가 정의된 클래스
 */
public class Strings {
    /**
     * CamelCase 형식의 문자열을 snake-case로 변환
     * @param str 변환할 문자열 
     * @return snake-case로 변환된 문자열
     */
    public static String toSnakeCase(String str){
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        for(char c :charArray){
            if(c >= 'A' && c<= 'Z'){
                if(c != charArray[0]) sb.append('-');
                c += ('a' - 'A');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 파일을 저장하기 위해 해당 문자열 배열을 하나의 문자열로 변환
     * ex) {data,123,456} => "|data|123|456|"
     * @param arr 변환할 문자열 배열
     * @return 변환된 문자열
     */
    public static String toTableCeil(String[] arr, boolean toSnakeCase){
        StringBuilder builder = new StringBuilder("|");

        for(int i = 0; i<arr.length; i++){
            builder.append("%18s |".formatted(toSnakeCase ? Strings.toSnakeCase(arr[i]) : arr[i]));
        }

        return builder.toString();
    }

    /**
     * N초뒤 해당 STr 실행
     */
    public static void delayAndPrint(int count, String str, Runnable runnable) throws InterruptedException{
            for(int i = count; i>0; i--){
                try {
                    Thread.sleep(1000);
                    System.out.println(i + str);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            runnable.run();
    }

    /**
     * PrintMiniKiosk
     *             ╔════════════KIOSK═══════════╗
     *             ║════════════════════════════║
     *             ║ 🥤 x 01  🚬 x 00  🥤 x 01  ║
     *             ║ 🥤 x 01  🧋 x 00  🍜 x 02  ║
     *             ║ 🥤 x 01  🍪 x 10  🍞 x 05  ║
     *             ║ 🥤 x 01  🍪 x 10  🍞 x 05  ║
     *             ║────────────────────────────║
     *             ║                   ______   ║
     *             ║────────────────────────────║
     *             ║|                           ║
     *             ║────────────────────────────║
     */
    public static String printMiniKiosk(){
        ArrayList<Product> products = DBs.getProducts();

        StringBuilder builder = new StringBuilder();
        builder.append("""
              %10s╔════════════KIOSK═══════════╗
              %10s║════════════════════════════║
                """.formatted("",""));

        for(int i = 0 ; i < products.size()/3*3; i++) {
            if(i%3==0) builder.append("%10s║".formatted(""));
            builder.append(" %1s x %02d ".formatted(
                        products.get(i).getEmoji(),
                        products.get(i).getInventory()));
            if(i%3==2) builder.append("║\n");
        }
        builder.append("""
                %10s║────────────────────────────║
                %10s║                   ______   ║
                %10s║────────────────────────────║
                %10s║|                           ║
                %10s║────────────────────────────║
                """.formatted("","","","",""));
        return builder.toString();
    }

}
