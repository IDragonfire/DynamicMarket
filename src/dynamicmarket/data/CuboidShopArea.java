package dynamicmarket.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;

import org.bukkit.Location;

import com.avaje.ebean.validation.NotNull;

import dynamicmarket.DynamicMarketException;

@Entity
@Inheritance
@DiscriminatorValue("CuboidShopArea")
public class CuboidShopArea extends ShopArea {

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    @NotNull
    private List<DLocation> locs;

    public CuboidShopArea() {
	this.locs = new ArrayList<DLocation>();
    }

    @Override
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

    public List<DLocation> getLocs() {
	return this.locs;
    }

    private int getShopArea(Location loc) {
	int shopIndex = -1;
	DLocation shoploc;
	System.out.println("######### area size" + this.locs.size());
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

    @Override
    public boolean isShopInArea(Location loc) {
	return getShopArea(loc) > -1;
    }

    @Override
    public void removeLocation(Location loc) throws DynamicMarketException {
	int index = getShopArea(loc);
	if (index < 0) {
	    // TODO 0.Message System
	    throw new DynamicMarketException("Shop not found");
	}
	this.locs.remove(index);
    }

    public void setLocs(List<DLocation> locs) {
	this.locs = locs;
    }

}
