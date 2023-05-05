package com.example.newsserviceapplication

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsserviceapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    var page=1
    val articles= mutableListOf<Article>()
    lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        val layoutManager=  StackLayoutManager(false, scrollMultiplier = 1f)
        adapter= NewsAdapter(this@MainActivity, articles, this)
        binding.recyclerView.adapter= adapter
        binding.recyclerView.layoutManager= layoutManager
        getNews(page)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    //function that add new elements to my recycler view
                    if(page==1){
                        page=2
                        getNews(page)
                    }
                }

            }
        })
    }

    private fun getNews(page: Int) {
        val news= newsService.newsInstance.getHeadlines("in", page)
        news.enqueue(object : Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news_= response.body()
                if (news_!=null) {
                    articles.addAll(news_.articles)
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility= View.GONE
                } else{
                    Toast.makeText(this@MainActivity, "Null API Response ...", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility= View.GONE
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Please check your Internet Connection ", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility= View.GONE
                binding.failText.visibility= View.VISIBLE
            }

        })
    }


    override fun onItemClick(item: Article) {
        // Custom Chrome Tab
        val url= item.url

        val colorInt: Int = Color.parseColor("#FF0000") //red
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()
        val builder= CustomTabsIntent.Builder()
        builder.setDefaultColorSchemeParams(defaultColors)
        val intent= builder.build()
        intent.launchUrl(this, Uri.parse(url))
    }

    override fun onShareClicked(item: Article) {
        val str= "Hey check out this news: \n${item.url}"
        val intent= Intent(Intent.ACTION_SEND).apply {
            type= "text/plain"
            putExtra(Intent.EXTRA_TEXT,  str)
        }
        val chooser= Intent.createChooser(intent, "Share this news using ...")
        startActivity(chooser)
    }

}