package my.util.app.utils;

import my.util.app.models.PhotoDetails;

public interface ImageCaptureListener {

    void onClick(PhotoDetails item);
    void onLongClick(PhotoDetails item);
}
