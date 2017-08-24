package com.framgia.wsm.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by tri on 03/07/2017.
 */

public class FileUtils {
    private static final String TAG = "FileUtils";
    private static final int MIN = 0;
    private static final int MAX = 99999;

    private FileUtils() {
        // No-op
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        InputStream inputStream = null;
        String filePath = null;

        if (contentURI.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(contentURI);
                File photoFile = createTemporalFileFrom(inputStream, context);

                filePath = photoFile.getPath();
            } catch (IOException e) {
                Log.e(TAG, "getRealPathFromURI: ", e);
            } finally {
                try {
                    assert inputStream != null;
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "getRealPathFromURI: ", e);
                }
            }
        }
        return filePath;
    }

    private static File createTemporalFileFrom(InputStream inputStream, Context context)
            throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "getRealPathFromURI: ", e);
            }
        }
        return targetFile;
    }

    private static File createTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(),
                randomNumber(MIN, MAX) + randomNumber(MIN, MAX) + randomNumber(MIN, MAX) + ".jpg");
    }

    private static int randomNumber(int min, int max) {
        Random random = new Random();
        int range = max - min + 1;
        return min + random.nextInt(range);
    }
}
