package dali.hamza.tvshowapp.models

data class UiState<T>(
    val isLoading: Boolean,
    val data: T?,
    val error: Any?,
) {
    companion object {
        fun <T : Any> initialization(): UiState<T> {
            return UiState(isLoading = true, data = null, error = null)
        }
    }
}


