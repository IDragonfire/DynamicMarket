package dynamicmarket.data;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

import org.bukkit.Location;

import dynamicmarket.DynamicMarketException;
import dynamicmarket.core.Shop;

// persis not in used
@Entity
@Inheritance
@DiscriminatorColumn(name = "type")
@Table(name = "dm_shoparea")
public interface ShopArea {

    boolean isShopInArea(Location loc);

    void removeLocation(Location loc) throws DynamicMarketException;

    void addLocation(Object locationObject) throws DynamicMarketException;

    void addLocation(Object locationObject, Shop shop)
	    throws DynamicMarketException;

}
