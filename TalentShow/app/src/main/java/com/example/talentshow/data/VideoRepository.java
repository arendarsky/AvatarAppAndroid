package com.example.talentshow.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.talentshow.data.api.VideoAPI;
import com.example.talentshow.domain.repository.IVideoRepository;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class VideoRepository implements IVideoRepository {

    private VideoAPI videoAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;
    private String currentVideo;
    private ArrayList<String> videoNames = new ArrayList<>();

    @Inject
    VideoRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.videoAPI = retrofit.create(VideoAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }

    public Completable uploadVideo(Uri fileURI){
        File file = new File(getFilePathFromUri(appContext, fileURI));

        String extension = appContext.getContentResolver().getType(fileURI);
        Log.d("Extension", extension);
        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));

        MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return videoAPI.uploadVideo(preferencesRepository.getToken(), body);
    }

    @Override
    public String getNewVideoLink() {
        AtomicReference<String> video = new AtomicReference<>();
        if (videoNames.size() <= 10){
            Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 20)
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(arrayList -> {
                        this.videoNames.addAll(arrayList);
                        video.set(videoNames.get(0));
                        currentVideo = videoNames.get(0);
                        videoNames.remove(0);
                    });
        }
        else{
            video.set(videoNames.get(0));
            currentVideo = videoNames.get(0);
            videoNames.remove(0);
        }
        return "https://avatarapp.yambr.ru/api/video/" + video.get();
    }

    @Override
    public Single<ArrayList<String>> getUnwatchedVideos(int number) {

        Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 30)
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(arrayList -> this.videoNames.addAll(arrayList));



        return videoAPI.getUnwatched(preferencesRepository.getToken(), number);
    }

    @Override
    public Completable setLiked(boolean liked) {
        return videoAPI.setLiked(preferencesRepository.getToken(), currentVideo, liked);
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
