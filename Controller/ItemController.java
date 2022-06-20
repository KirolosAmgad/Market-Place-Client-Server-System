package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.MyListener;
import model.Fruit;

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

    private Fruit fruit;
    private MyListener myListener;

    public void setData(Fruit fruit, MyListener myListener) {//, Image prdImg
        this.fruit = fruit;
        this.myListener = myListener;
        nameLabel.setText(fruit.getName());
        priceLable.setText(fruit.getPrice() + " " +Main.CURRENCY);
        Image image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        img.setImage(image); //prdImg
    }
}
