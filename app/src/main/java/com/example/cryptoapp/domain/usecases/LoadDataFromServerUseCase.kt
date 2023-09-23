package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository

class LoadDataFromServerUseCase(private val repository: CoinRepository) {
    suspend operator fun invoke() {
        repository.loadDataFromServer()
    }
}