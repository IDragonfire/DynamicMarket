package dynamicmarket.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.bukkit.Location;

import com.avaje.ebean.validation.NotNull;

import dynamicmarket.DynamicMarketException;

@Entity
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
	    getLocs().add((DLocation) locationObject);
	    ((DLocation) locationObject).setArea(this);
	} else {
	    throw new DynamicMarketException("Wrong Location object");
	}

    }

    private int getShopArea(Location loc) {
	int shopIndex = -1;
	DLocation shoploc;
	for (int i = 0; i < getLocs().size(); i++) {
	    shoploc = getLocs().get(i);
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
	    // TODO 0.MessageSystem
	    throw new DynamicMarketException("Shop not found");
	}
	getLocs().remove(index);
    }

    // getter & setter

    public List<DLocation> getLocs() {
	return this.locs;
    }

    public void setLocs(List<DLocation> locs) {
	this.locs = locs;
    }

}
