package rrdl.be4care.Utils;

import android.app.Activity;
import android.content.Context;

import com.mindorks.paracamera.Camera;

public class CameraService {
    private Context mContext;
    private Camera camera;
    private Activity mActivity;
    public CameraService(Context context,Activity activity) {
        mContext = context;
        mActivity=activity;
    }

    public Camera init() {

        return camera;
    }
}
