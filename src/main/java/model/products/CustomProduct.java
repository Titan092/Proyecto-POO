package model.products;

public class CustomProduct extends BaseProduct implements Cloneable {

    private int maxPers;
    private String[] personalizableTexts;

    public CustomProduct(int id, String name, Category category, float price, int maxPers, String[] personalizableTexts){
        super(id,name,category,price);
        this.maxPers = maxPers;
        this.personalizableTexts = personalizableTexts;
    }

    public int getMaxPers(){
        return maxPers;
    }

    public void setMaxPers(int maxPers){
        this.maxPers = maxPers;
    }

    @Override
    public void setCategory(Category category) {
        super.setCategory(category);
    }

    public void setPersonalizableTexts(String[] personalizableTexts){
        this.personalizableTexts = personalizableTexts;
        int numeroDePersActual = 0;
        for (String personalizableText : personalizableTexts) {
            if (personalizableText != null) {
                numeroDePersActual += 1;
            }
        }
        this.setPrice(getPrice() + (getPrice()*numeroDePersActual/10));
    }

    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        if (personalizableTexts == null){
            return "{class:CustomProduct, id:"+super.getId()+", name:"+ "'" +name+"'"+", category:"+super.getCategory()+", price:"+super.getPrice()+", maxPersonal: "+maxPers+"}\n";
        }else{
            String persTexts = "";
            for (int i=0; i<personalizableTexts.length;i++) {
                persTexts = persTexts + personalizableTexts[i]+", ";
            }
            return "{class:CustomProduct, id:"+super.getId()+", name:"+ "'"+name+"'"+", category:"+super.getCategory()+", price:"+super.getPrice()+", maxPersonal: "+maxPers+
                    ", personalizationList:["+persTexts+"]}\n";
        }
    }

    @Override
    public CustomProduct clone() throws CloneNotSupportedException {
        return (CustomProduct) super.clone();
    }

}
