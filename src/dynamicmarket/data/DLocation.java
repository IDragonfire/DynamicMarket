package dynamicmarket.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name = "dm_dlocation")
public class DLocation {
    @ManyToOne
    private CuboidShopArea area;
    @Id
    @NotNull
    private int dloctionid;
    @NotNull
    private int maxX;
    @NotNull
    private int maxY;
    @NotNull
    private int maxZ;
    @NotNull
    private int minX;
    @NotNull
    private int minY;
    @NotNull
    private int minZ;
    @NotNull
    private String world;

    public DLocation() {
	// for JPA
    }

    public DLocation(int x1, int x2, int y1, int y2, int z1, int z2,
	    String world) {
	setMinX(Math.min(x1, x2));
	setMaxX(Math.max(x1, x2));
	setMinY(Math.min(y1, y2));
	setMaxY(Math.min(y1, y2));
	setMinZ(Math.min(z1, z2));
	setMaxZ(Math.max(z1, z2));
	setWorld(world);
    }

    // getter & setter

    public CuboidShopArea getArea() {
	return this.area;
    }

    public void setArea(CuboidShopArea area) {
	this.area = area;
    }

    public int getDloctionid() {
	return this.dloctionid;
    }

    public void setDloctionid(int dloctionid) {
	this.dloctionid = dloctionid;
    }

    public int getMaxX() {
	return this.maxX;
    }

    public void setMaxX(int maxX) {
	this.maxX = maxX;
    }

    public int getMaxY() {
	return this.maxY;
    }

    public void setMaxY(int maxY) {
	this.maxY = maxY;
    }

    public int getMaxZ() {
	return this.maxZ;
    }

    public void setMaxZ(int maxZ) {
	this.maxZ = maxZ;
    }

    public int getMinX() {
	return this.minX;
    }

    public void setMinX(int minX) {
	this.minX = minX;
    }

    public int getMinY() {
	return this.minY;
    }

    public void setMinY(int minY) {
	this.minY = minY;
    }

    public int getMinZ() {
	return this.minZ;
    }

    public void setMinZ(int minZ) {
	this.minZ = minZ;
    }

    public String getWorld() {
	return this.world;
    }

    public void setWorld(String world) {
	this.world = world;
    }
}
