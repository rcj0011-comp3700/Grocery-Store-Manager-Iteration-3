public class order
{
    private int ID;
    private String Day;
    private double Price;

    public order(int id, String day, double price)
    {
        ID = id;
        Day = day;
        Price = price;
    }

    public int getID() { return ID; }
    public void setID(int id) { ID = id; }

    public String getDate() { return Day; }
    public void setDate(String day) { Day = day; }

    public double getPrice() { return Price; }
    public void setPrice(double price) { Price = price; }
}
