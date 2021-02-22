package com.example.spacenewsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacenewsapp.adapter.NewsAdapter
import com.example.spacenewsapp.dialog.AppInfoBottomSheet
import com.example.spacenewsapp.dialog.AppInfoBottomSheet.Companion.TAG_APP_INFO_DIALOG
import com.example.spacenewsapp.dialog.NewsBottomSheet
import com.example.spacenewsapp.dialog.NewsBottomSheet.Companion.TAG_NEWS_DIALOG
import com.example.spacenewsapp.viewmodel.ConnectionLiveData
import com.example.spacenewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemView()
        itemViewModel()
    }

    private fun itemView() {
        connectionLiveData = ConnectionLiveData(this)
        newsAdapter = NewsAdapter(arrayListOf()) {
            NewsBottomSheet.newInstance(it).show(supportFragmentManager, TAG_NEWS_DIALOG)
        }
        newsList.layoutManager = LinearLayoutManager(this)
        newsList.adapter = newsAdapter
        swipe.setOnRefreshListener { newsViewModel.getAllNews() }
    }

    private fun itemViewModel() {
        newsViewModel.newsResponseLiveData.observe(this, { news ->
            if (news.isNotEmpty()) {
                animationView.visibility = View.GONE
                animationView.cancelAnimation()
                connectionTitle.visibility = View.GONE
                newsAdapter.setNewData(news)
            } else {
                animationView.setAnimation(R.raw.astronaut)
                animationView.visibility = View.VISIBLE
            }
            swipe.isRefreshing = false
        })
        newsViewModel.newsResponseErrorLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            swipe.isRefreshing = false
        })

        connectionLiveData.observe(this, { isConnected ->
            if (isConnected) {
                swipe.isRefreshing = true
                newsViewModel.getAllNews()
            } else {
                swipe.isRefreshing = false
                newsAdapter.clearData()
                animationView.setAnimation(R.raw.no_internet_connection)
                animationView.visibility = View.VISIBLE
                connectionTitle.visibility = View.VISIBLE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshNews -> {
                swipe.isRefreshing = true
                newsViewModel.getAllNews()
            }
            R.id.appInfo -> {
                AppInfoBottomSheet.newInstance().show(supportFragmentManager, TAG_APP_INFO_DIALOG)
            }
        }
        return true
    }
}