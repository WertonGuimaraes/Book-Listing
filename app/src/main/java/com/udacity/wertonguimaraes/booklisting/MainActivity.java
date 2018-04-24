package com.udacity.wertonguimaraes.booklisting;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Info>>,
        SwipeRefreshLayout.OnRefreshListener {

    private static int LOADER_ID = 0;

    private Toolbar mToolbar;
    private RecyclerView mRvItem;
    private InfoListAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipe;
    private SwipeRefreshLayout mSwipeEmpty;
    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvItem = findViewById(R.id.recycler_view);
        mSwipe = findViewById(R.id.refresh);
        mSwipeEmpty = findViewById(R.id.refresh_empty);
        mToolbar = findViewById(R.id.toolbar);

        mSwipe.setOnRefreshListener(this);
        mSwipeEmpty.setOnRefreshListener(this);

        setSupportActionBar(mToolbar);

        startOrRestartLoaderCustom(getSupportLoaderManager().initLoader(LOADER_ID, null, this));
    }

    @Override
    public Loader<List<Info>> onCreateLoader(int id, Bundle args) {
        return new URLAsyncTaskLoader(this, mQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Info>> loader, List<Info> data) {
        mSwipe.setRefreshing(false);
        mSwipeEmpty.setRefreshing(false);

        mRvItem.setLayoutManager(new LinearLayoutManager(this));
        if (data != null && data.size() > 0) {
            mSwipeEmpty.setVisibility(View.GONE);
            mSwipe.setVisibility(View.VISIBLE);
            mNewsAdapter = new InfoListAdapter(data);
            mRvItem.setAdapter(mNewsAdapter);
        } else {
            mSwipeEmpty.setVisibility(View.VISIBLE);
            mSwipe.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Info>> loader) {
    }

    @Override
    public void onRefresh() {
        startOrRestartLoaderCustom(getSupportLoaderManager().restartLoader(LOADER_ID, null, this));
    }

    private void startOrRestartLoaderCustom(Loader<List<Info>> loaderManager) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mSwipeEmpty.setVisibility(View.GONE);
            mSwipe.setVisibility(View.VISIBLE);
            loaderManager.forceLoad();
        } else {
            mSwipe.setRefreshing(false);
            mSwipeEmpty.setRefreshing(false);
            mSwipeEmpty.setVisibility(View.VISIBLE);
            mSwipe.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startOrRestartLoaderCustom(getSupportLoaderManager().restartLoader(LOADER_ID, null, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                onRefresh();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}
