package model.users;

public class Client extends User{
    private String cashId;
    //id is the DNI of the client
    //cashId is the ID of the casher
    public Client(String name, String id, String email, String cashId){
        super(id,name,email);
        this.cashId = cashId;
    }

    String getCashId(String cashId){
        return cashId;
    }

    public void setCashId(String cashId){
        this.cashId = cashId;
    }

    @Override
    public String toString() {
        return "Client{identifier='%s', name='%s', email='%s', cash=%s}"
                .formatted(super.getId(), super.getName(), super.getEmail(), cashId);
    }
}
