package model;

public class EventProduct extends Product{

    private int maxAmountContestants;
    //Atributo de la fecha

    public EventProduct(int id, String name, float price , int maxAmountContestants){
        super(id, name, price);
        this.maxAmountContestants = maxAmountContestants;
    }



}
