package exceptionHandler;

public class ErrorMessageHandler {

    //Error messages
    private static final String ERRORMESSAGE = "There has been a typo error, please, try again";
    private static final String VALIDNUMBER = "The price must be a valid number.";
    private static final String VALIDCATEGORY = "Invalid category. Allowed values: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS";
    private static final String NOTENOUGHTCHARACTERS = "Not enough characters for the command.";
    private static final String WRONGID="The ID must be a non-negative integer.";

    public static String getERRORMESSAGE() {
        return ERRORMESSAGE;
    }
    public static String getVALIDNUMBER() {
        return VALIDNUMBER;
    }
    public static String getVALIDCATEGORY() {
        return VALIDCATEGORY;
    }
    public static String getNOTENOUGHTCHARACTERS() {
        return NOTENOUGHTCHARACTERS;
    }
    public static String getWRONGID() {
        return WRONGID;
    }

}
