package com.example.talentshow.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.loader.content.CursorLoader;

import com.example.talentshow.data.api.VideoAPI;
import com.example.talentshow.domain.repository.IVideoRepository;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class VideoRepository implements IVideoRepository {

    private VideoAPI videoAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;

    @Inject
    VideoRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.videoAPI = retrofit.create(VideoAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }

    public Completable uploadVideo(Uri fileURI){
        File file = new File(getFilePathFromUri(appContext, fileURI));

        String extension = appContext.getContentResolver().getType(fileURI);
//        appContext.getContentResolver()
        Log.d("Extension", extension);
        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));

        MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return videoAPI.uploadVideo("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjdkMWYzZTYwLTU4MjUtNDI3NC04OWFlLTMyNDMwNDgxOWVhOCIsImlzcyI6IkF2YXRhckFwcCIsImF1ZCI6IkF2YXRhckFwcENsaWVudCJ9.izjDevXXplhlNSifE_OBE8V3xHfh02T83ysATVgYCAo",
                body);
//        return videoAPI.uploadVideo("Bearer "+preferencesRepository.getToken(), requestFile);
    }

    @Override
    public Single<ArrayList<String>> getUnwatchedVideos(int number) {
        return videoAPI.getUnwatched("Bearer "+preferencesRepository.getToken(), number);
    }


    public static String getFilePathFromUri(Context context, Uri uri) {
        if (uri == null) return null;
        ContentResolver resolver = context.getContentResolver();
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            if (pfd == null) {
                return null;
            }
            String extension = "";
            if (resolver.getType(uri) == null){
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(resolver.getType(uri));
            }
            else {
                extension = resolver.getType(uri);
                extension = "."+extension.substring(extension.lastIndexOf("/")+1);
            }
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);
            if (extension == null || extension == ""){
                extension = URLConnection.guessContentTypeFromStream(input);
            }
            File outputDir = context.getCacheDir();
            File outputFile = File.createTempFile("video",
                    extension, outputDir);
            String tempFilename = outputFile.getAbsolutePath();
            output = new FileOutputStream(tempFilename);
            int read;
            byte[] bytes = new byte[input.available()];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return new File(tempFilename).getAbsolutePath();
        } catch (Exception ignored) {
            ignored.getStackTrace();
        } finally {
            try {
                if (input != null){
                    input.close();
                }
                if (output != null){
                    output.close();
                }
            } catch (Throwable t) {
                // Do nothing
            }
        }
        return null;
    }
}
