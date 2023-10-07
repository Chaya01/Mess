package ppp.mess;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
class Restaurant {

    @Id @GeneratedValue
    private Long idRestaurant;
    private String name;
    private String email;
    private String phone;
    private String rut;
    private String pswd;
    private String owner;

    Restaurant(){}
    Restaurant(String name, String email, String phone, String rut, String pswd, String owner){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rut = rut;
        this.pswd = pswd;
        this.owner = owner;
    }
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Branches> branches = new ArrayList<>();
    //create a list that contains all the branches of the restaurant

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long id) {
        this.idRestaurant = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

@Entity
class Mesas {
    Mesas(){}

    @Id @GeneratedValue
    private Long id;
    private String number;
    private String capacity;
    private Boolean split;
    private Boolean avariable;
    @ManyToOne
    @JoinColumn(name="branches_id") // tabla e id a la que hace referencia
    private Branches branch;
    @ManyToOne
    @JoinColumn(name="restaurant_id") // tabla a la que hace referencia
    private Restaurant restaurant;

    public Mesas(String number, String capacity, Boolean split, Boolean avariable) {
        this.number = number;
        this.capacity = capacity;
        this.split = split;
        this.avariable = avariable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Boolean getSplit() {
        return split;
    }

    public void setSplit(Boolean split) {
        this.split = split;
    }

    public Boolean getAvariable() {
        return avariable;
    }

    public void setAvariable(Boolean avariable) {
        this.avariable = avariable;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
@Entity
class Branches {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String adress;
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
    public Branches(Long id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Mesas> mesas = new ArrayList<>();
    public Long getId() {
        return id;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
@Entity
class Waiter{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne
    @PrimaryKeyJoinColumn(name="branches_id")
    private Branches branches;
    private String avaraible;
    @OneToOne @PrimaryKeyJoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    public Waiter(Long id, String name, String avaraible) {
        this.id = id;
        this.name = name;
        this.avaraible = avaraible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvaraible() {
        return avaraible;
    }

    public void setAvaraible(String avaraible) {
        this.avaraible = avaraible;
    }
}

@Entity
class Order{
    @Id @GeneratedValue
    private Long id;
    @OneToOne @PrimaryKeyJoinColumn(name="restaurant_id")
    private Restaurant restaurant;
    @OneToOne @PrimaryKeyJoinColumn(name="mesas_id")
    private Mesas mesas;
    @OneToOne @PrimaryKeyJoinColumn(name="waiter_id")
    private Waiter waiter;
    @OneToOne @PrimaryKeyJoinColumn(name="dishes_id")
    private Dishes dishes;
    private int rawPrice;
    private boolean delivered;
    @OneToOne @PrimaryKeyJoinColumn(name="status_id")
    private Status status;

    public Order(Long id, int rawPrice, boolean delivered) {
        this.id = id;
        this.rawPrice = rawPrice;
        this.delivered = delivered;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRawPrice() {
        return rawPrice;
    }

    public void setRawPrice(int rawPrice) {
        this.rawPrice = rawPrice;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}

@Entity
class Dishes{
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
    private String name;
    private String description;
    private String ingredients;
    private Boolean avariable;
    @OneToOne @JoinColumn(name="photo_id")
    private Photo photo;

    public Dishes() {

    }
    public Dishes(Long id, String name, String description, String ingredients, Boolean avariable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.avariable = avariable;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public boolean getAvariable() {
        return avariable;
    }

    public void setAvariable(boolean avariable) {
        this.avariable = avariable;
    }
}

@Entity
class Photo{
    @Id @GeneratedValue
    private Long id;
    @OneToOne @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
    private String name;
    private String url;

    public Photo() {

    }
    public Photo(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

@Entity
class Status{
    @Id @GeneratedValue
    private Long id;
    private String statusName;

    public Status(Long id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
