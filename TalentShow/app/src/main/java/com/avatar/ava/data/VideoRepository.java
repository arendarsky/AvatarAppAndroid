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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.avatar.ava.DataModule.SERVER_NAME;

public class VideoRepository implements IVideoRepository {

    private VideoAPI videoAPI;
    private SharedPreferencesRepository preferencesRepository;
    private Context appContext;
    private PersonDTO currentPerson;
    private List<PersonDTO> personDTOList = new ArrayList<>();
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
        String commands = "-threads 0 -i "
                + file.getAbsolutePath() + " -b:v 2500k -preset veryfast "
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
                        (int) ((double) beginTime * 1000),
                        (int)((double) endTime * 1000)))).doOnComplete(() -> loadingVideo = null);
    }

    @Override
    public Completable setInterval(String fileName, Float beginTime, Float endTime) {
        return videoAPI.setInterval(preferencesRepository.getToken(), fileName, (int)1000 * beginTime, (int)1000 * endTime);
    }

    @SuppressWarnings("unused")
    @Override
    public Single<PersonDTO> getNewVideoLink() {
        if (personDTOList.size() <= 10){
            Disposable disposable = videoAPI.getUnwatched(preferencesRepository.getToken(), 20)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            list -> {
                                list.remove(this.currentPerson);
                                this.personDTOList = list;
                            },
                            error -> {});
        }
        return  Single.fromCallable(() -> {
            if (personDTOList.isEmpty()) throw new Exception("Empty list");
            else {

                this.currentPerson = personDTOList.get(0);
                return this.currentPerson;
            }
        });
    }


    @Override
    public Completable setLiked(boolean liked) {
        PersonDTO tmp = this.currentPerson;
        this.currentPerson = null;
        if(this.personDTOList.size() > 0)
            this.personDTOList.add(this.personDTOList.size() - 1, this.personDTOList.remove(0));
        return videoAPI.setLiked(preferencesRepository.getToken(), tmp.getVideo().getName(), liked)
                .doOnComplete(() -> this.personDTOList.remove(tmp));
    }

    @Override
    public Single<PersonDTO> getVideoLinkOnCreate() {
        if (this.personDTOList.isEmpty())
            return videoAPI.getUnwatched(preferencesRepository.getToken(), 10)
                    .flatMap(list -> {
                        if (list.size() == 0) throw new IllegalStateException("Empty list");
                        this.personDTOList.addAll(list);
                        this.currentPerson = this.personDTOList.get(0);
                        return Single.just(this.currentPerson);
                    });
        else{
            if (this.currentPerson == null)
                this.currentPerson = personDTOList.get(0);
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

    @Override
    public Observable<Float> downloadVideo(String videoToDownload, Uri fileToWrite) {
        return videoAPI.downloadFile(SERVER_NAME + "/api/video/" + videoToDownload).
                flatMap((Function<Response<ResponseBody>, ObservableSource<Float>>) responseBodyResponse -> Observable.create(sub -> {
                        InputStream input = null;
                        OutputStream output = null;
                    try {
                        if (responseBodyResponse.isSuccessful()) {
                            input = responseBodyResponse.body().byteStream();
                            long tlength= responseBodyResponse.body().contentLength();
                            String t = String.valueOf(fileToWrite);
                            File file = new File(t);
                            output = new FileOutputStream(file.getAbsolutePath());
                            byte data[] = new byte[1024];

                            sub.onNext((float) 0);
                            long total = 0;
                            int count;
                            while ((count = input.read(data)) != -1) {
                                total += count;

                                sub.onNext((float) (total * 100 / tlength));

                                output.write(data, 0, count);
                            }
                            output.flush();
                            output.close();
                            input.close();
                        }
                    } catch(IOException e){
                        sub.onError(e);
                    } finally {
                        if (input != null){
                            try {
                                input.close();
                            }catch(IOException ioe){}
                        }
                        if (output != null){
                            try{
                                output.close();
                            }catch (IOException e){}
                        }
                    }
                    sub.onComplete();

                }));
    }

    @Override
    public void clearVideoList() {
        this.personDTOList.clear();
    }
}
