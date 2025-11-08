package model.users;

public class Client extends User{

    private String cashId;
    //id is the DNI of the client
    //chasId is the ID of the casher
    public Client(String id, String cashId, String name, String email){
        super(id,name,email);
        this.cashId = cashId;
    }

    String getCashId(String cashId){
        return cashId;
    }

    public void setCashId(String cashId){
        this.cashId = cashId;
    }
}
