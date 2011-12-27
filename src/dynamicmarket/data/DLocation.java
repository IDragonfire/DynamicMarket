package dynamicmarket.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name = "dm_dlocation")
public class DLocation {
    @Id
    @NotNull
    private int dloctionid;
    @NotNull
    private int minX;
    @NotNull
    private int maxX;
    @NotNull
    private int minY;
    @NotNull
    private int maxY;
    @NotNull
    private int minZ;
    @NotNull
    private int maxZ;
    @NotNull
    private String world;

    // TODO: FIX SAVE

    @ManyToOne
    private CuboidShopArea area;

    public CuboidShopArea getArea() {
	return this.area;
    }

    public void setArea(CuboidShopArea area) {
	this.area = area;
    }

    public DLocation() {
	// for persist
    }

    public DLocation(int x1, int x2, int y1, int y2, int z1, int z2,
	    String world) {
	this.minX = Math.min(x1, x2);
	this.maxX = Math.max(x1, x2);
	this.minY = Math.min(y1, y2);
	this.maxY = Math.min(y1, y2);
	this.minZ = Math.min(z1, z2);
	this.maxZ = Math.max(z1, z2);
	this.world = world;
    }

    public int getDloctionid() {
	return this.dloctionid;
    }

    public void setDloctionid(int dloctionid) {
	this.dloctionid = dloctionid;
    }

    public int getMinX() {
	return this.minX;
    }

    public int getMaxX() {
	return this.maxX;
    }

    public int getMinY() {
	return this.minY;
    }

    public int getMaxY() {
	return this.maxY;
    }

    public int getMinZ() {
	return this.minZ;
    }

    public int getMaxZ() {
	return this.maxZ;
    }

    public String getWorld() {
	return this.world;
    }

    public void setWorld(String world) {
	this.world = world;
    }

    public void setMinX(int minX) {
	this.minX = minX;
    }

    public void setMaxX(int maxX) {
	this.maxX = maxX;
    }

    public void setMinY(int minY) {
	this.minY = minY;
    }

    public void setMaxY(int maxY) {
	this.maxY = maxY;
    }

    public void setMinZ(int minZ) {
	this.minZ = minZ;
    }

    public void setMaxZ(int maxZ) {
	this.maxZ = maxZ;
    }

}
