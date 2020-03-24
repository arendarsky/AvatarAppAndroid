package com.avatar.ava.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
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

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

public class VideoRepository implements IVideoRepository {

    private VideoAPI videoAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;
    private PersonDTO currentPerson;
    private ArrayList<PersonDTO> castingPersons = new ArrayList<>();
    private String convertedFilePath;


    @Inject
    VideoRepository(Retrofit retrofit, SharedPreferencesRepository preferencesRepository, Context appContext){
        this.videoAPI = retrofit.create(VideoAPI.class);
        this.preferencesRepository = preferencesRepository;
        this.appContext = appContext;
    }

    public Completable composeVideo(Uri fileURI){

        File file = new File(getFilePathFromUri(appContext, fileURI));

//        String compressedFilePath = null;
//        try {
//            compressedFilePath = SiliCompressor.with(appContext).compressVideo(file.getAbsolutePath(), file.getParentFile().getAbsolutePath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

//        try {

        this.convertedFilePath = file.getAbsolutePath().substring(0,
                file.getAbsolutePath().lastIndexOf("."))
                + ".mp4";

//        File convertedFile = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "test.mp4");
        File convertedFile = new File(this.convertedFilePath);
        String commands = "-i "
                + file.getAbsolutePath() + " -q:v 20 "
//                + " -c:v libx264 "
                + convertedFile.getAbsolutePath();

//        int rc = FFmpeg.execute(commands);
//
//        if (rc == RETURN_CODE_SUCCESS) {
//            Log.i(Config.TAG, "Command execution completed successfully.");
//        } else {
//            Log.i(Config.TAG, String.format("Command execution failed with rc=%d and the output below.", rc));
//            Config.printLastCommandOutput(Log.INFO);
//        }



        return Single.fromCallable(() -> FFmpeg.execute(commands)).ignoreElement();
//                .andThen(videoAPI.composeVideo(
//                preferencesRepository.getToken(),
//                MultipartBody.Part.
//                        createFormData("file",
//                                convertedFile.getName(),
//                                RequestBody.create(
//                                        convertedFile,
//                                        MediaType.parse("multipart/form-data")
//                                )
//                        )
//        ).doOnSuccess(name -> uploadedVideoName = name).ignoreElement());


//        return Completable.fromAction(() -> ffmpeg.execute(commands,
//        new ExecuteBinaryResponseHandler() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//
//            @Override
//            public void onSuccess(String message) {
//                Log.d("Converting", "success");
//            }
//
//            @Override
//            public void onProgress(String message) {
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//                Log.d("Converting", "failure");
//            }
//        })).andThen(videoAPI.composeVideo(
//                preferencesRepository.getToken(),
//                MultipartBody.Part.
//                        createFormData("file",
//                                file.getName(),
//                                RequestBody.create(
//                                        new File(convertedFilePath),
//                                        MediaType.parse("multipart/form-data")
//                                )
//                        )
//        ).doOnSuccess(name -> uploadedVideoName = name).ignoreElement());

//            return Single.fromCallable(() -> SiliCompressor.with(appContext).compressVideo(file.getAbsolutePath(), file.getParentFile().getAbsolutePath()))
//                    .flatMap(
//                            filePath ->
//                                    videoAPI.composeVideo(
//                                            preferencesRepository.getToken(),
//                                            MultipartBody.Part.
//                                                    createFormData("file",
//                                                            file.getName(),
//                                                            RequestBody.create(
//                                                                    new File(filePath),
//                                                                    MediaType.parse("multipart/form-data")
//                                                            )
//                                                    )
//                                    ).doOnSuccess(name -> uploadedVideoName = name)).ignoreElement();
//        } catch (URISyntaxException e) {
//            return Completable.error(new IllegalStateException());
//        }

//
//        String extension = appContext.getContentResolver().getType(fileURI);
//        Log.d("Extension", file.getPath());
//        File compressedFile = new File(compressedFilePath);
//        RequestBody requestFile =
//                RequestBody.create(compressedFile, MediaType.parse("multipart/form-data"));
//
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//        return videoAPI.composeVideo(preferencesRepository.getToken(), body)
//                .doOnSuccess(name -> uploadedVideoName = name)
//                .ignoreElement();
    }

    @Override
    public Completable uploadAndSetInterval(Uri fileURI, Float beginTime, Float endTime){
        File file = new File(getFilePathFromUri(appContext, fileURI));
        this.convertedFilePath = file.getAbsolutePath().substring(0,
                file.getAbsolutePath().lastIndexOf("."))
                + ".mp4";
        File convertedFile = new File(this.convertedFilePath);
        String commands = "-i "
                + file.getAbsolutePath() + " -q:v 20 "
//                + " -c:v libx264 "
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
                        (double) endTime * 1000)));
    }

    @Override
    public Single<PersonDTO> getNewVideoLink() {
        if (castingPersons.size() <= 10){
            Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> this.castingPersons = list,
                            error -> {});
        }
        return  Single.fromCallable(() -> {
            if (castingPersons.isEmpty()) throw new Exception("Empty list");
            else return castingPersons.remove(0);
        });
//        if (castingPersons.isEmpty())
//            throw new IllegalStateException("Empty list");
//        return Single.just(this.castingPersons.remove(0));
    }


    @Override
    public Single<ArrayList<PersonDTO>> getUnwatchedVideos(int number) {
        Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->
                                this.castingPersons = list,
                        error -> {});



        return videoAPI.getUnwatched(preferencesRepository.getToken(), number);
    }

    @Override
    public Completable setLiked(boolean liked) {
        return videoAPI.setLiked(preferencesRepository.getToken(), currentPerson.getVideo().getName(), liked);
    }

//    @Override
//    public Completable setInterval(float startTime, float endTime) {
//        return videoAPI.setInterval(preferencesRepository.getToken(), uploadedVideoName, startTime, endTime);
//    }

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
                    if (list.size() == 0) throw new IllegalStateException("Empty list");
                    this.castingPersons = list;
                    this.currentPerson = this.castingPersons.remove(0);
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
