package model.products;
/**
 * Class representing a customizable product.
 * It extends the BaseProduct class and implements the Cloneable interface.
 */
public class CustomProduct extends BaseProduct {

    private int maxPers;
    private String[] personalizableTexts;
    /**
     * Constructor for CustomProduct.
     * @param id Product ID.
     * @param name Product name.
     * @param category Product category.
     * @param price Product price.
     * @param maxPers Maximum number of personalizations.
     * @param personalizableTexts Array of personalizable texts.
     */
    public CustomProduct(int id, String name, Category category, float price, int maxPers, String[] personalizableTexts){
        super(id,name,category,price);
        this.maxPers = maxPers;
        this.personalizableTexts = personalizableTexts;
    }

    public CustomProduct(CustomProduct other, String[] personalizableTexts) {
        super(other.getId(), other.getName(), other.getCategory(), other.getPrice());
        this.maxPers = other.getMaxPers();
        this.personalizableTexts = personalizableTexts;
    }

    public int getMaxPers(){
        return maxPers;
    }

    public void setMaxPers(int maxPers){
        this.maxPers = maxPers;
    }

    public String[] getPersonalizableTexts() {
        return personalizableTexts;
    }

    @Override
    public void setCategory(Category category) {
        super.setCategory(category);
    }
    /**
     * Get personalizable texts.
     * @return Array of personalizable texts.
     */
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
    public float getPrice() {
        int textos = 0;
        for (String personalizableText : personalizableTexts) {
            if (personalizableText != null) {
                textos++;
            }
        }
        return super.getPrice() + (super.getPrice()*textos/10);
    }

    /**
     * String representation of the CustomProduct.
     * format: {class:CustomProduct, id:ID, name:'NAME', category:CATEGORY, price:PRICE, maxPersonal: MAXPERSONAL, personalizationList:[TEXT1, TEXT2, ...]}
     * or if no personalizations:
     * format: {class:CustomProduct, id:ID, name:'NAME', category:CATEGORY, price:PRICE, maxPersonal: MAXPERSONAL}
     * @return String representation.
     */
    @Override
    public String toString() {
        String name = super.getName();
        //if starts and end by " then we delete it using the subString
        if (name.startsWith("\"") && name.endsWith("\"")) {
            name = name.substring(1, name.length() - 1);
        }
        if (personalizableTexts == null || personalizableTexts[0] == null) {
            return "{class:CustomProduct, id:"+super.getId()+", name:"+ "'" +name+"'"+", category:"+super.getCategory()+", price:"+super.getPrice()+", maxPersonal: "+maxPers+"}";
        }else{
            StringBuilder persTexts = new StringBuilder();
            for (int i=0; i<personalizableTexts.length;i++) {
                if (i < personalizableTexts.length-1) {
                    persTexts.append(personalizableTexts[i]).append(", ");
                } else {
                    persTexts.append(personalizableTexts[i]);
                }

            }
            return "{class:ProductPersonalized, id:"+super.getId()+", name:"+ "'"+name+"'"+", category:"+super.getCategory()+", price:"+this.getPrice()+", maxPersonal: "+maxPers+
                    ", personalizationList:["+persTexts+"]}";
        }
    }
}
