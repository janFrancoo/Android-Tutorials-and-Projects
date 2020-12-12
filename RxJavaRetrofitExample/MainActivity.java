package com.janfranco.rxjavaretrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.function.Predicate;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.janfranco.rxjavaretrofitexample.CryptoService.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView coinListRecyclerView;
    private Retrofit retrofit;
    private CoinListRecyclerViewAdapter coinListRecyclerViewAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private void setup() {
        coinListRecyclerView = findViewById(R.id.coin_list_recycler_view);
        coinListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinListRecyclerViewAdapter = new CoinListRecyclerViewAdapter();
        coinListRecyclerView.setAdapter(coinListRecyclerViewAdapter);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private void getData() {
        CryptoService cryptoService = retrofit.create(CryptoService.class);

        /*Observable<Crypto> cryptoObservable = cryptoService.getCoinData("btc");
        Disposable disposable = cryptoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result.ticker.markets)
                .subscribe(this::handleResults, this::handleError);
        disposables.add(disposable);*/

        /*Observable<Crypto> btcObservable = cryptoService.getCoinData("btc");
        Observable<Crypto> ethObservable = cryptoService.getCoinData("eth");
        Disposable disposable = Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result.ticker.markets)
                .subscribe(this::handleResults, this::handleError);
        disposables.add(disposable);*/

        Observable<List<Crypto.Market>> btcObservable = cryptoService.getCoinData("btc")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x)
                .filter(y -> {
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();

        Observable<List<Crypto.Market>> ethObservable = cryptoService.getCoinData("eth")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x)
                .filter(y -> {
                    y.coinName = "eth";
                    return true;
                }).toList().toObservable();

        Disposable disposable = Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
        disposables.add(disposable);
    }


    private void handleResults(List<Crypto.Market> marketList) {
        if (marketList != null && marketList.size() != 0)
            coinListRecyclerViewAdapter.setData(marketList);
        else
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
    }

    private void handleError(Throwable t) {
        Log.d(TAG, t.getLocalizedMessage());
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
