package dali.hamza.tvshowapp.common

import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.tvshowapp.models.MovieForm
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


fun String.isNotEmptyAndNotBlank(): Boolean {
    return this.isNotEmpty() && this.isNotBlank()
}

fun MovieForm.isValidMovieForm(): Boolean {
    return this.dateRelease.isNotEmptyAndNotBlank() && title.isNotEmptyAndNotBlank() && (season.isNotEmpty() && season.toInt() >= 1)
}

@OptIn(InternalCoroutinesApi::class)
suspend inline fun Flow<IResponse?>.onData(
    crossinline error: (value: Any) -> Unit,
    crossinline success: suspend (value: SuccessResponse<*>) -> Unit,
): Unit =
    collect(object : FlowCollector<IResponse?> {
        override suspend fun emit(value: IResponse?) {
            if (value != null && value is ErrorResponse) {
                error(value)
            }
            if (value != null && value is SuccessResponse<*>) {
                success(value)
            }
        }
    })