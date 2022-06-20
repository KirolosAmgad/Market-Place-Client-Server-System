package Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import main.Main;
import main.MyListener;
import model.Fruit;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class MarketController implements Initializable {
    
         private Stage stage;
 private Scene scene;
 private Parent root;
    @FXML
    private Label wallet;
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;
    @FXML
    private Button search_button;



    @FXML
    private Spinner<Integer> spinner;

    int currentValue;
    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    @FXML
    private TextField search_text;
    public static List<Image> images = new ArrayList<>();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    void add_to_cart(MouseEvent event) throws IOException {
        String product_name = fruitNameLable.getText();
        Main.SendToServer("get product id from name");
        String product_id = Main.SendToServer(product_name);
        Main.SendToServer("add to cart");
        //Main.SendToServer(product_id);
        Main.SendToServer(product_id);
        String response = Main.SendToServer(Integer.toString(spinner.getValue()));
        if (response.equals("Unavailable entry")){
            Main.msgbox("Cannot Perform operation","Your requested quantity of items is not available in stock, please enter");
        }
        else if (response.equals("Done")) {
            Main.msgbox("Operation Successful","Items added to the cart with the specified quantity");
        }

    }
    @FXML
    void display_wallet_balance() throws IOException {
        wallet.setText("Balance: "+Main.SendToServer("get wallet balance") + " EGP");
    }

    
        @FXML
    void openaccount(MouseEvent event) throws IOException {
      root = FXMLLoader.load(getClass().getResource("../views/MyAccount.fxml"));
      stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    }

    @FXML
    void openLogin(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void opencart(MouseEvent event) throws IOException {


        root = FXMLLoader.load(getClass().getResource("../views/Cart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private List<Fruit> getData() throws IOException {
        List<Integer> product_IDs = new ArrayList<Integer>();;
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<String> product_category = new ArrayList<String>();
        List<String> price = new ArrayList<String>();
        List<String> imgsrc = new ArrayList<String>();
        List<String> color = new ArrayList<String>();

        Main.SendToServer("view products");
        while (!Main.ReadFromServer().equals("Done")){
            qty.add(Integer.parseInt(Client.server_sent_message));
            product_category.add(Main.ReadFromServer());
            price.add(Main.ReadFromServer());
            product_name.add(Main.ReadFromServer());
            product_IDs.add(Integer.parseInt(Main.ReadFromServer()));
            imgsrc.add(Main.ReadFromServer());
            color.add(Main.ReadFromServer());
            //for the image
//            Client.bufferedImage = ImageIO.read(Client.inpStr);
//            images.add(SwingFXUtils.toFXImage(Client.bufferedImage,null));
//            //clear buffers
//            Client.inpStr = Client.s.getInputStream();
//            Client.bufferedInputStream = new BufferedInputStream(Client.inpStr);
//            Client.bf = new BufferedReader(new InputStreamReader(Client.s.getInputStream()));
        }

        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        for (int i=0;i<product_IDs.size();i++){
            fruit = new Fruit();
            fruit.setName(product_name.get(i));
            fruit.setPrice(price.get(i));
            fruit.setImgSrc(imgsrc.get(i));
            fruit.setColor(color.get(i));
            fruit.setID(product_IDs.get(i));
            fruits.add(fruit);
        }

//        fruit = new Fruit();
//        fruit.setName("Chipsey");
//        fruit.setPrice(8.5);
//        fruit.setImgSrc("/img/chipsey.png");
//        fruit.setColor("E7C00F");
//        fruits.add(fruit);
        return fruits;
    }

    private void setChosenFruit(Fruit fruit) {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(fruit.getPrice() + " " +Main.CURRENCY);
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    void search_click(MouseEvent event) throws IOException {
        List<Integer> product_IDs = new ArrayList<Integer>();
        ;
        List<Integer> qty = new ArrayList<Integer>();
        List<String> product_name = new ArrayList<String>();
        List<String> product_category = new ArrayList<String>();
        List<String> price = new ArrayList<String>();
        List<String> imgsrc = new ArrayList<String>();
        List<String> color = new ArrayList<String>();

        Main.SendToServer("search");
        System.out.println(search_text.getText());
        Main.SendToServer(search_text.getText());
        String s = Main.ReadFromServer();
        while (!s.equals("Done") && !s.equals("Not Found")) { //|| !Client.server_sent_message.equals("Not Found")
            qty.add(Integer.parseInt(Client.server_sent_message));
            product_category.add(Main.ReadFromServer());
            price.add(Main.ReadFromServer());
            product_name.add(Main.ReadFromServer());
            product_IDs.add(Integer.parseInt(Main.ReadFromServer()));
            imgsrc.add(Main.ReadFromServer());
            color.add(Main.ReadFromServer());
            s = Main.ReadFromServer();
        }
        if(Client.server_sent_message.equals("Not Found")){
            Main.msgbox("Search error","No Results Found");
            return;
        }
        grid.getChildren().clear();
        List<Fruit> fruits = new ArrayList<>();
        Fruit fruit;

        for (int i=0;i<product_IDs.size();i++){
            fruit = new Fruit();
            fruit.setName(product_name.get(i));
            fruit.setPrice(price.get(i));
            fruit.setImgSrc(imgsrc.get(i));
            fruit.setColor(color.get(i));
            fruit.setID(product_IDs.get(i));
            fruits.add(fruit);
        }

        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));

            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i),myListener); //images.get(i)

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
        try {
            fruits.addAll(getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));

            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i),myListener); //images.get(i)

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            display_wallet_balance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
