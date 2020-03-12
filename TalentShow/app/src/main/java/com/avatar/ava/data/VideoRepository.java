package com.avatar.ava.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.avatar.ava.data.api.VideoAPI;
import com.avatar.ava.domain.entities.PersonDTO;
import com.avatar.ava.domain.repository.IVideoRepository;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLConnection;
import java.util.ArrayList;

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
    private ArrayList<PersonDTO> castingPersons = new ArrayList<>();
    private String uploadedVideoName;

    @Inject
    VideoRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.videoAPI = retrofit.create(VideoAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }

    public Completable uploadVideo(Uri fileURI){
        File file = new File(getFilePathFromUri(appContext, fileURI));

        String extension = appContext.getContentResolver().getType(fileURI);
        Log.d("Extension", file.getPath());
        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));

        MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return videoAPI.uploadVideo(preferencesRepository.getToken(), body)
                .doOnSuccess(name -> this.uploadedVideoName = name)
                .ignoreElement();
    }

    @Override
    public Single<PersonDTO> getNewVideoLink() {
        if (castingPersons.size() <= 10){
            Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(arrayList -> this.castingPersons.addAll(arrayList),
                            error -> {});
        }

        return Single.just(this.castingPersons.remove(0));
    }



    @Override
    public Single<ArrayList<PersonDTO>> getUnwatchedVideos(int number) {

        Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 30)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(list -> {
                    ArrayList<PersonDTO> persons = new ArrayList<>();
                    for (PersonDTO person:
                            list) {
                        person.prepareInfo();
                        persons.add(person);
                    }
                    this.castingPersons = persons;

                },
                error -> {});



        return videoAPI.getUnwatched(preferencesRepository.getToken(), number);
    }

    @Override
    public Completable setLiked(boolean liked) {
        return videoAPI.setLiked(preferencesRepository.getToken(), currentPerson.getUsedVideo().getName(), liked);
    }

    @Override
    public Completable setInterval(String fileName, int startTime, int endTime) {
        return videoAPI.setInterval(preferencesRepository.getToken(), fileName, startTime, endTime);
    }

    @Override
    public Single<PersonDTO> getVideoLinkOnCreate() {
//        return videoAPI.getUnwatched(preferencesRepository.getToken(), 10)
//                .doOnSuccess(
//                        arrayList -> {
//                            this.castingPersons = arrayList;
//                            this.currentPerson = arrayList.remove(0);
//                        }
//                );
        return videoAPI.getUnwatched(preferencesRepository.getToken(), 10)
                .flatMap(list -> {
                    ArrayList<PersonDTO> persons = new ArrayList<>();
                    for (PersonDTO person:
                         list) {
                        person.prepareInfo();
                        persons.add(person);
                    }
                    this.castingPersons = persons;
                    this.currentPerson = persons.remove(0);
                    return Single.just(this.currentPerson);
                });
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
            if (extension.equals(".quicktime")) extension = ".mov";
            Log.d("extension", extension);
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
