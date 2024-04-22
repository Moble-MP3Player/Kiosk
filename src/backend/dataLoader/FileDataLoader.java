package backend.dataLoader;

import backend.db.DB;
import backend.db.DBs;
import backend.reflections.Reflections;
import backend.util.Strings;
import model.Card;
import model.Product;
import model.Receipt;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 로컬파일의 데이터를 불러와 제공하는 함수를 가진 클래스
 */
public class FileDataLoader implements DataLoader {
    public static final String PRODUCT_FILE_NAME = "products.txt";
    public static final String CARD_FILE_NAME = "cards.txt";
    public static final String RECEIPT_FILE_NAME = "receipt.txt";
    private Path productsFilePath, cardsFilePath, receiptsFilePath;

    private ArrayList<Product> products;
    private ArrayList<Card> cards;
    private ArrayList<Receipt> receipts;

    public FileDataLoader(){
        initFileLoader();

    }

    private void initFileLoader() {
        MockDataLoader mock = new MockDataLoader(); // 값이 존재하지 않을 때, 초기화할 Loader

        try {
            // 데이터 파일 생성 및 경로 가져오기
            productsFilePath = getOrCreateFile(PRODUCT_FILE_NAME, mock.loadProductData(), Product.class);
            cardsFilePath = getOrCreateFile(CARD_FILE_NAME, mock.loadCardData(), Card.class);
            receiptsFilePath = getOrCreateFile(RECEIPT_FILE_NAME, mock.loadReceiptData(), Receipt.class);

            // ArrayList 초기화
            products = new ArrayList<>();
            cards = new ArrayList<>();
            receipts = new ArrayList<>();

            // ArrayList 값 생성
            loadProductFromFile();
            loadCardFromFile();
            loadReceiptFromFile();


        }catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Res 폴더의 경로를 가져옵니다. 해당 Res 가 존재하지 않을 경우, 생성 후 경로를 받아옵니다.
     *
     * @return path of res Directory
     * @throws URISyntaxException URI 오류
     * @throws IOException        IO 오류
     */
    private Path loadOrCreateResDirectoryPath() throws URISyntaxException, IOException {
        URL resource = DB.class.getResource("./");
        if (resource == null) throw new IllegalStateException("DB.class not exist");
        URI dirUri = new URI(resource.toString().replaceAll("out/.+$", "") + "res");
        Path dirPath = Paths.get(dirUri);

        // Create Kiosk/res/ directory
        if (!Files.exists(dirPath)) {
            Files.createDirectory(dirPath);
        }
        return dirPath;
    }

    /**
     * fileName을 해당 res 디렉토리 경로에 생성합니다.
     *
     * @param fileName 생성하거나 가져올 파일의 이름과 확장자
     * @param list     파일에 저장할 List
     * @param clazz    생성할 리스트가 갖고 있는 클래스
     * @return Path 생성된 파일의 경로
     * @throws URISyntaxException URI 오류
     * @throws IOException        IO 오류
     */
    private Path getOrCreateFile(String fileName, ArrayList<?> list, Class<?> clazz) throws URISyntaxException, IOException {
        Path dirPath = loadOrCreateResDirectoryPath();
        URI fileUri = new URI(dirPath.toUri() + "/" + fileName);
        Path filePath = Paths.get(fileUri);

        // Create FileName file
        if (!Files.exists(filePath)) {
            Reflections.checkFirstElementType(list, clazz);
            Files.createFile(filePath); // 파일생성
            updateFile(filePath, list, clazz);
        }
        return filePath;
    }

    /**
     * 파일에 데이터를 입력합니다. CreateAndInitFile() 에서 해당파일이 존재하지 않을 경우, 호출됩니다.
     *
     * @param path  데이터 파일의 Path
     * @param list  초기화할 데이터
     * @param clazz 데이터의 클래스
     */
    private void updateFile(Path path, ArrayList<?> list, Class<?> clazz) throws IOException {

        String[][] lines = Reflections.convertToArray(list, clazz);
        String[] titles = Reflections.getTitles(clazz);

        try (
                FileWriter fileWriter = new FileWriter(path.toString());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
        ) {
            printWriter.println("____________________".repeat(titles.length));
            printWriter.println(Strings.toTableCeil(titles,true));
            printWriter.println("____________________".repeat(titles.length));

            for (String[] line : lines) {
                printWriter.println(Strings.toTableCeil(line,false));
            }
            printWriter.println("____________________".repeat(titles.length));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public void notifyDataSetChanged(){
        try {
            updateFile(productsFilePath,products, Product.class);
            updateFile(cardsFilePath,cards, Card.class);
            updateFile(receiptsFilePath,receipts, Receipt.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<Product> loadProductData() {
        return products;
    }

    @Override
    public ArrayList<Receipt> loadReceiptData() {
        return receipts;
    }

    @Override
    public ArrayList<Card> loadCardData() {
        return cards;
    }

    private void loadProductFromFile(){
        List<String> productFileLines;

        products.clear();
        try {
            productFileLines = Files.readAllLines(productsFilePath);
        }catch (IOException e){
            DBs.log(e.getMessage());
            throw new IllegalStateException("cannot Load Product Data");
        }

        productFileLines.stream().skip(3).forEach(line -> {
            String[] datas = line.replaceFirst("\\|", "").
                    replaceAll("\\s", "").
                    split("\\|");
            if(datas.length > 1) products.add((Product) Reflections.convertDataTypeStringToMemberVarType(Product.class,datas));
        });

    }

    private void loadCardFromFile(){
        List<String> cardFileLines;
        cards.clear();

        try {
            cardFileLines = Files.readAllLines(cardsFilePath);
        }catch (IOException e){
            DBs.log(e.getMessage());
            throw new IllegalStateException("cannot Load Card Data");
        }

        cardFileLines.stream().skip(3).forEach(line -> {
            String[] datas = line.replaceFirst("\\|", "").
                    replaceAll("\\s", "").
                    split("\\|");
            if(datas.length > 1) cards.add((Card) Reflections.convertDataTypeStringToMemberVarType(Card.class,datas));
        });
    }

    private void loadReceiptFromFile(){
        List<String> receiptFileLines;
        receipts.clear();

        try {
            receiptFileLines = Files.readAllLines(receiptsFilePath);
        }catch (IOException e){
            DBs.log(e.getMessage());
            throw new IllegalStateException("cannot Load Receipts Data");
        }

        receiptFileLines.stream().skip(3).forEach(line -> {
            String[] datas = line.replaceFirst("\\|", "").
                    replaceAll("\\s", "").
                    split("\\|");
            if(datas.length > 1) receipts.add((Receipt) Reflections.convertDataTypeStringToMemberVarType(Receipt.class,datas));
        });
    }

}
