/**
 * Created by Andrei on 15/11/2016.
 */
public class Item {
    public String code;
    public String brand;
    public String name;
    public boolean isLended;
    public String date;

    public String getDate() {
        return date;
    }

    public boolean isLended() {
        return isLended;
    }

    public String getBrand() {
        return brand;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Item()
    {
        brand="not specified";
        name="not specified";
        code="not specified";
        isLended=false;
    }
    public Item(String Code,String Brand,String Name,String Date)
    {
        brand=Brand;
        name=Name;
        code=Code;
        date=Date;
        isLended=false;
        System.out.println("New item has been created "+ brand + name+ code+ date);
    }

    public void LendItem()
    {
            this.isLended=true;
    }

}
