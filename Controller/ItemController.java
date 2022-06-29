package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.MyListener;
import model.Product;

import java.io.IOException;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(fruit);
    }

    private Product fruit;
    private MyListener myListener;

    public void setData(Product product, MyListener myListener) {//, Image prdImg
        this.fruit = product;
        this.myListener = myListener;
        nameLabel.setText(product.getName());
        priceLable.setText(product.getPrice() + " " +Main.CURRENCY);
        Image image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        img.setImage(image); //prdImg
    }
}
