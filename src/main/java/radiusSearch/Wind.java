package radiusSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

@SerializedName("speed")
@Expose
private Double speed;
@SerializedName("deg")
@Expose
private Integer deg;
@SerializedName("gust")
@Expose
private Integer gust;

public Double getSpeed() {
return speed;
}

public void setSpeed(Double speed) {
this.speed = speed;
}

public Integer getDeg() {
return deg;
}

public void setDeg(Integer deg) {
this.deg = deg;
}

public Integer getGust() {
return gust;
}

public void setGust(Integer gust) {
this.gust = gust;
}

}