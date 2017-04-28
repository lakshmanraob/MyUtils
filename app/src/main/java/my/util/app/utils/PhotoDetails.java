package my.util.app.utils;

import android.graphics.drawable.Drawable;

public class PhotoDetails {

    private String imageName;
    private Drawable image;

    public PhotoDetails(String imageName, Drawable image) {
        this.imageName = imageName;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
