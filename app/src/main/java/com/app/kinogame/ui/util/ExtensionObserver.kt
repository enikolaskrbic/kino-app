package com.app.kinogame.ui.util

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.app.kinogame.data.error.GlobalError
import com.app.kinogame.data.wrapper.ErrorState
import com.app.kinogame.data.wrapper.State
import com.app.kinogame.data.wrapper.StateHandler
import com.app.kinogame.data.wrapper.SuccessState
import com.app.kinogame.ui.view.base.BaseDialogFragment
import com.app.kinogame.ui.view.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun <T : Any, L : LiveData<StateHandler<T?>>> BaseDialogFragment.observeSingleNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observeNullable(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<StateHandler<T?>>> BaseDialogFragment.observeNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T?> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

inline fun <reified T : Any> Flow<State<T>>.asStateHandlerLiveData(): LiveData<StateHandler<T>> =
    this.map {
        StateHandler(it)
    }.asLiveData()

inline fun <reified T : Any?> Flow<State<T?>>.asStateHandlerLiveDataNullable(): LiveData<StateHandler<T?>> =
    this.map {
        StateHandler(it)
    }.asLiveData()

inline fun <reified T : Any, reified Y : Any> State<T>.mapToStateHandler(mapping: (T?) -> Y?): StateHandler<Y> =
    when (this) {
        is SuccessState -> {
            if (this.isLoading) {
                StateHandler(SuccessState(isLoading = true))
            } else {
                StateHandler(SuccessState(mapping(this.data)))
            }
        }
        is ErrorState -> StateHandler.error(this.error)
    }

inline fun <reified T : Any, reified Y : Any> State<T?>.mapToStateHandlerUnit(mapping: (T?) -> Y?): StateHandler<Y?> =
    when (this) {
        is SuccessState -> {
            if (this.isLoading) {
                StateHandler(SuccessState(isLoading = true))
            } else {
                StateHandler(SuccessState(mapping(this.data)))
            }
        }
        is ErrorState -> StateHandler.error(this.error)
    }

fun <T : Any, L : LiveData<StateHandler<T>>> FragmentActivity.observeSingle(
    liveData: L,
    onSuccess: (data: T) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<StateHandler<T>>> FragmentActivity.observe(
    liveData: L,
    onSuccess: (data: T) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleEvent: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T> -> {
                    val stateHandler =
                        if (isSingleEvent) it.getContentIfNotHandled() else it.peekContent()
                    stateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
//                                        (this as? HomeActivity)?.showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
//                                onHideLoading.isNull { (this as? HomeActivity)?.showProgress(false) }
                                (it.state as SuccessState<out T?>).data?.run {
                                    onSuccess(this)
                                }
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
//                                onHideLoading.isNull { (this as? HomeActivity)?.showProgress(false) }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
//                                    (this as? HomeActivity)?.showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

fun <T : Any, L : LiveData<StateHandler<T>>> BaseDialogFragment.observeSingle(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<StateHandler<T>>> BaseDialogFragment.observe(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

fun <T : Any, L : LiveData<StateHandler<T>>> BaseFragment.observeSingle(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

fun <T : Any, L : LiveData<StateHandler<T?>>> BaseFragment.observeSingleNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observeNullable(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<StateHandler<T>>> BaseFragment.observe(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

private fun <T : Any, L : LiveData<StateHandler<T?>>> BaseFragment.observeNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T?> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}


fun <T : Any, L : LiveData<StateHandler<T?>>> BottomSheetDialogFragment.observeSingleNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observeNullable(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

fun <T : Any, L : LiveData<StateHandler<T>>> BottomSheetDialogFragment.observeSingle(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<StateHandler<T?>>> BottomSheetDialogFragment.observeNullable(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T?> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
//                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
//                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
//                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
//                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

private fun <T : Any, L : LiveData<StateHandler<T>>> BottomSheetDialogFragment.observe(
    liveData: L,
    onSuccess: (data: T?) -> Unit,
    onError: ((error: GlobalError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleState: Boolean
) {
    liveData.observe(
        this,
        Observer {
            when (it) {
                is StateHandler<T> -> {
                    val StateHandler = if (isSingleState) it.getContentIfNotHandled() else it.peekContent()
                    StateHandler?.let {
                        when (it.state) {
                            is SuccessState -> {
                                if ((it.state as SuccessState<out T?>).isLoading) {
                                    onLoading?.run {
                                        invoke()
                                    }.isNull {
//                                        showProgress(true)
                                    }
                                    return@Observer
                                }
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
//                                    showProgress(false)
                                }
                                onSuccess((it.state as SuccessState<out T?>).data)
                            }

                            is ErrorState -> {
                                onHideLoading?.invoke()
                                onHideLoading.isNull {
//                                    showProgress(false)
                                }
                                onError?.run {
                                    invoke((it.state as ErrorState<out T?>).error)
                                }.isNull {
//                                    showError((it.state as ErrorState<out T?>).error)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

fun Any?.isNull(onNull: () -> Unit) {
    if (this == null) {
        onNull.invoke()
    }
}

fun Any?.isNull(): Boolean {
    return this == null
}
