package model.users;

public interface IUser {

    String getName();

    String getId();

    String getEmail();

    void setName(String name);

    void setId(String id);

    void setEmail(String email);
}
