public class user
{
    private int ID;
    private String Name, Status, Password, Picture;

    public user(int id, String name, String status, String password, String picture)
    {
        ID = id;
        Name = name;
        Status = status;
        Password = password;
        Picture = picture;
    }

    public user(int id, String name, String status, String picture)
    {
        ID = id;
        Name = name;
        Status = status;
        Password = "defaultPass";
        Picture = picture;
    }

    public int getID() { return ID; }
    public void setID(int id) { ID = id; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public String getStatus() { return Status; }
    public void setStatus(String status) { Status = status; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { Password = password; }

    public String getPicture() { return Picture; }
    public void setPicture(String picture) { Picture = picture; }
}
