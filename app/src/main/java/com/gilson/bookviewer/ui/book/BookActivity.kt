package com.gilson.bookviewer.ui.book

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.gilson.bookviewer.R
import com.gilson.bookviewer.application.BookViewerApplication
import com.gilson.bookviewer.di.DaggerActivityComponent
import com.gilson.bookviewer.ui.RecyclerDecoratorSpace
import com.gilson.bookviewer.ui.book.adapter.BookViewAdapter
import kotlinx.android.synthetic.main.activity_book.*
import javax.inject.Inject

class BookActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        inject()
        viewModel = viewModelFactory.create(BookViewModel::class.java)
        setupAdapter()
        observeDataUpdates()
        setupReload()
    }

    private fun inject() {
        DaggerActivityComponent.builder().applicationComponent((application as BookViewerApplication).component()).build().inject(this)
    }

    private fun setupAdapter() {
        listBook.layoutManager = LinearLayoutManager(this)
        listBook.addItemDecoration(RecyclerDecoratorSpace())
        listBook.adapter = BookViewAdapter(mutableListOf())
    }

    private fun addBooksToAdapter(input: List<BookTimeline>) {
        swipeLayout.isRefreshing = false
        adapter().addBooks(input)
        listBook.scrollToPosition(0)
    }

    private fun displayError(it: String?) {
        swipeLayout.isRefreshing = false
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

    private fun observeDataUpdates() {
        viewModel.bookLiveData()
                .observe(this, Observer { addBooksToAdapter(it!!) })
        viewModel.errorLiveData()
                .observe(this, Observer { displayError(it) })
    }

    private fun setupReload() {
        swipeLayout.setOnRefreshListener {
            adapter().clear()
            viewModel.reload()
        }
    }

    private fun adapter() = (listBook.adapter as BookViewAdapter)
}