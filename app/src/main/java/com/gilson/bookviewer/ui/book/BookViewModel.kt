package com.gilson.bookviewer.ui.book

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.gilson.bookviewer.domain.book.BookUseCase
import com.gilson.bookviewer.domain.book.BookWeekly
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import javax.inject.Inject


class BookViewModel @Inject constructor(private val useCase: BookUseCase,
                                        private val mapper: Function<List<BookWeekly>, List<BookTimeline>>) : ViewModel() {
    private val bookLiveData: MutableLiveData<List<BookTimeline>> = MutableLiveData()
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(retrieveBooks())
    }

    fun bookLiveData(): LiveData<List<BookTimeline>> {
        return bookLiveData
    }

    fun errorLiveData(): LiveData<String> {
        return errorLiveData
    }

    fun reload() {
        compositeDisposable.add(retrieveBooks())
    }

    private fun retrieveBooks(): Disposable {
        compositeDisposable.clear()
        return useCase.retrieveBooks()
                .map(mapper)
                .subscribe({ postResult(it) },
                        { postError(it) })
    }

    private fun postResult(it: List<BookTimeline>) {
        bookLiveData.postValue(it)
    }

    private fun postError(it: Throwable) {
        errorLiveData.postValue(it.message)
    }
}