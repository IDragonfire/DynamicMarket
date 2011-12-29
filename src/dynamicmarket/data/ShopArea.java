package dynamicmarket.data;

import java.util.Vector;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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

    @Transient
    private Vector<Player> players;

    public ShopArea() {
	this.players = new Vector<Player>();
    }

    public abstract boolean isShopInArea(Location loc);

    public abstract void removeLocation(Location loc)
	    throws DynamicMarketException;

    public abstract void addLocation(Object locationObject)
	    throws DynamicMarketException;

    public abstract void markArea(Player player, boolean show);

    // don't support reload
    public void toggleArea(Player player) {
	if (this.players.contains(player)) {
	    markArea(player, false);
	    this.players.remove(player);
	} else {
	    markArea(player, true);
	    this.players.add(player);
	}
    }

    // getter & setter

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
