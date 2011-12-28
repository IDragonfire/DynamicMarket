package dynamicmarket.data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.bukkit.Location;

import dynamicmarket.DynamicMarketException;
import dynamicmarket.core.Shop;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@Table(name = "dm_shoparea")
public abstract class ShopArea {

    @Id
    private int id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "area")
    private Shop shop;

    public ShopArea() {
	// TODO Auto-generated constructor stub
    }

    public abstract boolean isShopInArea(Location loc);

    public abstract void removeLocation(Location loc)
	    throws DynamicMarketException;

    public abstract void addLocation(Object locationObject)
	    throws DynamicMarketException;

    public Shop getShop() {
	return this.shop;
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setShop(Shop shop) {
	this.shop = shop;
    }

}
