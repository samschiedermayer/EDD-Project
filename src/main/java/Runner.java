import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {

        //initialization of variables
        Database.initializeDatabase();
        MainQRDecoder.init();
        Map<Long, String> map = new HashMap<>();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber("/dev/video0");

        try {

            frameGrabber.start();

            System.out.println("Starting frame grabbing");

            Frame frame = frameGrabber.grab();
            while (frame != null) {

                BufferedImage image = Java2DFrameUtils.toBufferedImage(frame);
                Pair result = MainQRDecoder.decodeQRCode(image);

                if (result.value != null) {
                    map.put(result.key, result.value);
                    System.out.println("Result is " + result.toString());
                }

                frame = frameGrabber.grab();

            }

        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

    }

}
