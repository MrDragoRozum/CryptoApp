package com.example.cryptoapp.di

import com.example.cryptoapp.presentation.activity.CoinPriceListActivity
import com.example.cryptoapp.presentation.fragment.CoinDetailFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModel::class])
interface ActivityComponent {

    fun injectCoinPriceList(activity: CoinPriceListActivity)
    fun injectCoinDetail(fragment: CoinDetailFragment)

    @Subcomponent.Factory
    interface ActivityComponentFactory {
        fun create(
            @BindsInstance fSym: String = DEFAULT_EMPTY_PARAM
        ): ActivityComponent
    }

    companion object {
        private const val DEFAULT_EMPTY_PARAM = ""
    }
}