package com.avatar.ava.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.webkit.MimeTypeMap;

import com.arthenica.mobileffmpeg.FFmpeg;
import com.avatar.ava.data.api.VideoAPI;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.repository.IVideoRepository;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private PersonDTO currentPerson;
    private Set<PersonDTO> personDTOSet = new LinkedHashSet<>();
    private String convertedFilePath;
    private Uri loadingVideo;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Inject
    VideoRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext, FirebaseAnalytics mFirebaseAnalytics){
        this.videoAPI = retrofit.create(VideoAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
        this.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    @Override
    public Completable uploadAndSetInterval(Uri fileURI, Float beginTime, Float endTime){
        loadingVideo = fileURI;

        String path = getFilePathFromUri(appContext, fileURI);
//        String path = FileUtils.getPath(appContext, fileURI);
        if(path == null){
            Bundle params = new Bundle();
            params.putString("image_name", "video_repository");
            params.putString("full_text", "file_null");
            mFirebaseAnalytics.logEvent("share_image", params);
            return null;
        }
        File file = new File(path);
        this.convertedFilePath = file.getAbsolutePath().substring(0,
                file.getAbsolutePath().lastIndexOf(".")) + "1"
                + ".mp4";
        File convertedFile = new File(this.convertedFilePath);
        String commands = "-i "
                + file.getAbsolutePath() + " -b:v 250k "
                + convertedFile.getAbsolutePath();
        return Single.fromCallable(() -> FFmpeg.execute(commands)).ignoreElement()
                .andThen(videoAPI.uploadVideo(
                        preferencesRepository.getToken(),
                        MultipartBody.Part.
                                createFormData("file",
                                        convertedFile.getName(),
                                        RequestBody.create(
                                                convertedFile,
                                                MediaType.parse("multipart/form-data")
                                        )
                                )).flatMapCompletable(name -> videoAPI.setInterval(
                        preferencesRepository.getToken(),
                        name,
                        (double) beginTime * 1000,
                        (double) endTime * 1000))).doOnComplete(() -> loadingVideo = null);
    }

    @SuppressWarnings("unused")
    @Override
    public Single<PersonDTO> getNewVideoLink() {
        if (personDTOSet.size() <= 10){
            Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            list -> {
                                list.remove(this.currentPerson);
                                this.personDTOSet = new LinkedHashSet<>(list);
                            },
                            error -> {});
        }
        return  Single.fromCallable(() -> {
            if (personDTOSet.isEmpty()) throw new Exception("Empty list");
            else {
                this.currentPerson = personDTOSet.iterator().next();
                return this.currentPerson;
            }
        });
    }


    @Override
    public Completable setLiked(boolean liked) {
        this.personDTOSet.remove(this.currentPerson);
        return videoAPI.setLiked(preferencesRepository.getToken(), currentPerson.getVideo().getName(), liked);
    }

    @Override
    public Single<PersonDTO> getVideoLinkOnCreate() {
        if (this.personDTOSet.isEmpty())
            return videoAPI.getUnwatched(preferencesRepository.getToken(), 10)
                    .flatMap(list -> {
                        if (list.size() == 0) throw new IllegalStateException("Empty list");
                        this.personDTOSet.addAll(list);
                        this.currentPerson = this.personDTOSet.iterator().next();
                        return Single.just(this.currentPerson);
                    });
        else{
            this.currentPerson = personDTOSet.iterator().next();
            return Single.just(this.currentPerson);
        }
    }

    @Override
    public Completable setActive(String fileName) {
        return videoAPI.setActive(preferencesRepository.getToken(), fileName);
    }

    @SuppressWarnings("CatchMayIgnoreException")
    static String getFilePathFromUri(Context context, Uri uri) {
        if (uri == null) return null;
        ContentResolver resolver = context.getContentResolver();
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(uri, "r");
            if (pfd == null) {
                return null;
            }
            String extension;
            if (resolver.getType(uri) == null){
                extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(resolver.getType(uri));
            }
            else {
                extension = resolver.getType(uri);
                if (extension != null) {
                    extension = "."+extension.substring(extension.lastIndexOf("/")+1);
                }
            }
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);
            if (extension == null || extension.equals("")){
                extension = URLConnection.guessContentTypeFromStream(input);
            }
            if (extension.equals(".quicktime")) extension = ".mov";
            File outputDir = context.getCacheDir();
            File outputFile = File.createTempFile("video",
                    extension, outputDir);
            String tempFilename = outputFile.getAbsolutePath();
            output = new FileOutputStream(tempFilename);
            int read;
            byte[] bytes = new byte[1024];
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


    public Uri getLoadingVideo() {
        return loadingVideo;
    }
}
