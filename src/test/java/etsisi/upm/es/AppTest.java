package etsisi.upm.es;

import static model.Category.BOOK;
import static model.Category.CLOTHES;
import static org.junit.Assert.*;

import org.junit.Test;
import model.Product;
import service.ProductService;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */

    @Test
    public void Test1(){
        ProductService productService = new ProductService();

        //Producto 1
        productService.prodAdd(1,"Libro POO",BOOK, 25);
        //Producto 2
        productService.prodAdd(2,"Camiseta talla:M UPM",CLOTHES,15);
        Product [] products = productService.getProducts();

        //Test producto 1
        assertNotNull(products[1]); //objeto no es nulo
        assertEquals("Libro POO", products[1].getName());
        assertEquals(BOOK, products[1].getCategory());
        assertEquals(25,products[1].getPrice(),2);

        //Test producto 2
        assertNotNull(products[2]); //objeto no es nulo
        assertEquals("Camiseta talla:M UPM", products[2].getName());
        assertEquals(CLOTHES, products[2].getCategory());
        assertEquals(15, products[2].getPrice(), 2);

        ProductService productServiceCopy = new ProductService();
        productServiceCopy = productService;
        Product product1ExpectedChange = new Product(1,"Libro POO V2",BOOK, 25);
        productService.productUpdate(1,"NAME","Libro POO V2");
        assertEquals(product1ExpectedChange.getId(),products[1].getId());
        assertEquals(product1ExpectedChange.getName(), products[1].getName());
        assertEquals(product1ExpectedChange.getCategory(), products[1].getCategory());
        assertEquals(product1ExpectedChange.getPrice(), products[1].getPrice(), 2);

        //Cambiamos el precio al nuevo esperado
        product1ExpectedChange.setPrice(30);
        productService.productUpdate(1,"PRICE","30");
        assertEquals(product1ExpectedChange.getId(),products[1].getId());
        assertEquals(product1ExpectedChange.getName(), products[1].getName());
        assertEquals(product1ExpectedChange.getCategory(), products[1].getCategory());
        assertEquals(product1ExpectedChange.getPrice(), products[1].getPrice(), 2);
    }



}
