package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Product;

public class MarketAdminController implements Initializable {


    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private GridPane grid;

    private List<Product> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label quantity_label;
    @FXML
    private TextField search_text;
    @FXML
    private Label balance;
    void display_wallet_balance() throws IOException {
        balance.setText("Balance: "+Main.SendToServer("get admin balance") + " EGP");
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openAdmins(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/ManageAdmins.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openProducts(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/Products.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openReports(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/PreviewReports.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void openaccount(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../views/MyAccountAdmin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private List<Product> getData() throws IOException {
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

        List<Product> fruits = new ArrayList<>();
        Product fruit;

        for (int i=0;i<product_IDs.size();i++){
            fruit = new Product();
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

    private void setChosenFruit(Product fruit) throws IOException {
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(fruit.getPrice() + " " +Main.CURRENCY);
        image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
        Main.SendToServer("get quantity from name");
        quantity_label.setText(Main.SendToServer(fruit.getName()));
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
        List<Product> fruits = new ArrayList<>();
        Product fruit;

        for (int i=0;i<product_IDs.size();i++){
            fruit = new Product();
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
                public void onClickListener(Product fruit) throws IOException {
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
        try {
            display_wallet_balance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fruits.addAll(getData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fruits.size() > 0) {
            try {
                setChosenFruit(fruits.get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product fruit) throws IOException {
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
                itemController.setData(fruits.get(i), myListener); //,MarketController.images.get(i)

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
}
