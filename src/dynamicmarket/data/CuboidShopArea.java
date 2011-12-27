package dynamicmarket.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.bukkit.Location;

import com.avaje.ebean.validation.NotNull;

import dynamicmarket.DynamicMarketException;
import dynamicmarket.core.Shop;

@Entity
@Table(name = "dm_cuboidshoparea")
public class CuboidShopArea {
    @Id
    private int id;
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    @NotNull
    private List<DLocation> locs = new ArrayList<DLocation>();;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "area")
    private Shop shop;

    public Shop getShop() {
	return this.shop;
    }

    public void setShop(Shop shop) {
	this.shop = shop;
    }

    public CuboidShopArea() {
	// for persist
    }

    public void addLocation(Object locationObject)
	    throws DynamicMarketException {
	if (locationObject instanceof DLocation) {
	    this.locs.add((DLocation) locationObject);
	    // TODO: FIX SAVE
	    ((DLocation) locationObject).setArea(this);
	} else {
	    throw new DynamicMarketException("Wrong Location object");
	}

    }

    public boolean isShopInArea(Location loc) {
	return getShopArea(loc) > -1;
    }

    public void removeLocation(Location loc) throws DynamicMarketException {
	int index = getShopArea(loc);
	if (index < 0) {
	    // TODO 0.Message System
	    throw new DynamicMarketException("Shop not found");
	}
	this.locs.remove(index);
    }

    private int getShopArea(Location loc) {
	int shopIndex = -1;
	DLocation shoploc;
	for (int i = 0; i < this.locs.size(); i++) {
	    System.out.println("##############area" + i);
	    shoploc = this.locs.get(i);
	    if (shoploc.getMinX() <= loc.getX()
		    && loc.getX() <= shoploc.getMaxY()
		    && shoploc.getMinY() <= loc.getY()
		    && loc.getY() <= shoploc.getMaxY()
		    && shoploc.getMinZ() <= loc.getZ()
		    && loc.getZ() <= shoploc.getMaxZ()) {
		shopIndex = i;
		break;
	    }
	}
	return shopIndex;
    }

    public List<DLocation> getLocs() {
	return this.locs;
    }

    public void setLocs(ArrayList<DLocation> locs) {
	this.locs = locs;
    }

    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }
}
