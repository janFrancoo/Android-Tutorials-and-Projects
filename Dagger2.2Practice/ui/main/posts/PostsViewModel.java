package com.janfranco.daggerpractice.ui.main.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.janfranco.daggerpractice.SessionManager;
import com.janfranco.daggerpractice.models.Post;
import com.janfranco.daggerpractice.network.main.MainAPI;
import com.janfranco.daggerpractice.ui.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {

    private final SessionManager sessionManager;
    private final MainAPI mainAPI;
    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainAPI mainAPI) {
        this.sessionManager = sessionManager;
        this.mainAPI = mainAPI;
    }

    public LiveData<Resource<List<Post>>> observePosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading(null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainAPI.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                    .onErrorReturn(throwable -> {
                        Post post = new Post();
                        post.setId(-1);
                        ArrayList<Post> posts = new ArrayList<>();
                        posts.add(post);
                        return posts;
                    })
                    .map((Function<List<Post>, Resource<List<Post>>>) posts -> {
                        if (posts.size() > 0)
                            if (posts.get(0).getId() == -1)
                                return Resource.error("Error", null);

                        return Resource.success(posts);
                    })
                    .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, listResource -> {
                posts.setValue(listResource);
                posts.removeSource(source);
            });
        }

        return posts;
    }

}
