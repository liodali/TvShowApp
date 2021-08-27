package dali.hamza.tvshowapp.common

import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.SuccessResponse
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector


fun String.isNotEmptyAndNotBlank(): Boolean {
    return this.isNotEmpty() && this.isNotBlank()
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