package main;

import model.Product;

import java.io.IOException;

public interface MyListener {
    public void onClickListener(Product fruit) throws IOException;
}
