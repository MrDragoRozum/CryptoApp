package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository

class GetFullPriceListUseCase(private val repository: CoinRepository) {
    operator fun invoke(fsym: String) = repository.getPriceAboutCoin(fsym)
}