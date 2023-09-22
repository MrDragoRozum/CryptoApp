package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository

class GetTopCoinsInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke() = repository.getCoinsList()
}