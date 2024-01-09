package com.example.mywhether.data

sealed class Either<out L, out R> {
    class onSuccess<R>(val data: R) : Either<Nothing, R>()
    class onError<L>(val error: L) : Either<L, Nothing>()
}