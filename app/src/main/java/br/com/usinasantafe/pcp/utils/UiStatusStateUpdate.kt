package br.com.usinasantafe.pcp.utils

import br.com.usinasantafe.pcp.lib.Errors
import br.com.usinasantafe.pcp.lib.LevelUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import timber.log.Timber

data class UiStatusStateUpdate(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val failure: String = "",
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

suspend fun Flow<UiStatusStateUpdate>.collectUpdateStep(
    classAndMethod: String,
    currentStatus: UiStatusStateUpdate,
    emitState: suspend (UiStatusStateUpdate) -> Unit
): Boolean {

    var ok = true

    collect { result ->
        val newStatus = result.toUiStatus(classAndMethod, currentStatus)
        emitState(newStatus)

        if (newStatus.flagFailure) {
            ok = false
            return@collect
        }
    }

    return ok
}

fun UiStatusStateUpdate.toUiStatus(
    classAndMethod: String,
    current: UiStatusStateUpdate
): UiStatusStateUpdate {

    val failMsg = failure.takeIf { it.isNotEmpty() }
        ?.let { "$classAndMethod -> $it" }
        ?: ""

    if (failMsg.isNotEmpty()) {
        Timber.e(failMsg)
    }

    return current.copy(
        flagDialog = flagDialog,
        flagFailure = flagFailure,
        errors = errors,
        failure = failMsg,
        flagProgress = flagProgress,
        currentProgress = currentProgress,
        levelUpdate = levelUpdate,
        tableUpdate = tableUpdate
    )
}

fun UiStatusStateUpdate.withFailure(
    classAndMethod: String,
    message: String,
    errors: Errors = Errors.EXCEPTION,
    flagProgress: Boolean = false
): UiStatusStateUpdate {

    val failMsg = "$classAndMethod -> $message"
    Timber.e(failMsg)

    return copy(
        flagDialog = true,
        flagFailure = true,
        failure = failMsg,
        errors = errors,
        flagProgress = flagProgress,
        currentProgress = 1f
    )
}

fun UiStatusStateUpdate.withFailure(
    classAndMethod: String,
    throwable: Throwable,
    errors: Errors = Errors.EXCEPTION,
    flagProgress: Boolean = false
): UiStatusStateUpdate {
    val msg = "${throwable.message} -> ${throwable.cause}"
    return withFailure(classAndMethod, msg, errors, flagProgress)
}

suspend fun FlowCollector<UiStatusStateUpdate>.emitProgress(
    count: Float,
    sizeAll: Float,
    level: LevelUpdate,
    table: String,
    flagProgress: Boolean = true
) {
    val step = when(level){
        LevelUpdate.RECOVERY -> 1f
        LevelUpdate.CLEAN -> 2f
        LevelUpdate.SAVE -> 3f
        else -> 0f
    }
    emit(
        UiStatusStateUpdate(
            flagProgress = flagProgress,
            currentProgress = updatePercentage(step, count, sizeAll),
            tableUpdate = table,
            levelUpdate = level
        )
    )
}

suspend fun FlowCollector<UiStatusStateUpdate>.emitFailure(
    failure: String,
) {
    emit(
        UiStatusStateUpdate(
            flagProgress = false,
            errors = Errors.UPDATE,
            flagDialog = true,
            flagFailure = true,
            failure = failure,
            currentProgress = 1f,
            levelUpdate = null
        )
    )
}

fun <STATE> executeUpdateSteps(
    steps: List<Flow<UiStatusStateUpdate>>,
    getState: () -> STATE,
    getStatus: (STATE) -> UiStatusStateUpdate,
    copyStateWithStatus: (STATE, UiStatusStateUpdate) -> STATE,
    classAndMethod: String,
    flagUpdateFinish: Boolean = true
): Flow<STATE> = flow {

    for (step in steps) {
        val ok = step.collectUpdateStep(
            classAndMethod = classAndMethod,
            currentStatus = getStatus(getState())
        ) { status ->
            val newState = copyStateWithStatus(getState(), status)
            emit(newState)
        }
        if (!ok) return@flow
    }

    if (flagUpdateFinish) {
        val finalStatus = getStatus(getState()).copy(
            flagDialog = true,
            flagProgress = false,
            flagFailure = false,
            levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
            currentProgress = 1f,
        )
        val finalState = copyStateWithStatus(getState(), finalStatus)
        emit(finalState)
    }


}

interface UiStateWithStatusUpdate<T : UiStateWithStatusUpdate<T>> {
    val status: UiStatusStateUpdate

    fun copyWithStatus(status: UiStatusStateUpdate): T

    fun withFailure(
        classAndMethod: String,
        throwable: Throwable,
        errors: Errors = Errors.EXCEPTION,
        flagProgress: Boolean = false
    ): T =
        copyWithStatus(
            status.withFailure(
                classAndMethod,
                throwable,
                errors,
                flagProgress
            )
        )

    fun withAccess(check: Boolean): T =
        copyWithStatus(
            status.copy(
                flagAccess = check,
                flagDialog = !check,
                flagFailure = !check,
                errors = Errors.INVALID
            )
        )

}

fun <T : UiStateWithStatusUpdate<T>> UiStateWithStatusUpdate<T>.withFailure(
    classAndMethod: String,
    error: Errors = Errors.INVALID,
    flagProgress: Boolean = false,
    failure: String = ""
): T =
    copyWithStatus(
        status.withFailure(
            classAndMethod = classAndMethod,
            message = failure.ifEmpty { failure(error) },
            errors = error,
            flagProgress = flagProgress
        )
    )

fun <T : UiStateWithStatusUpdate<T>> Result<*>.onSuccessUpdateAccess(
    updateState: ((T.() -> T)) -> Unit
): Result<*> =
    onSuccess {
        updateState { withAccess(true) }
    }

fun <T : UiStateWithStatusUpdate<T>> Result<Boolean>.onSuccessUpdateCheckAccess(
    updateState: ((T.() -> T)) -> Unit
): Result<Boolean> =
    onSuccess { check ->
        updateState { withAccess(check) }
    }


fun <T : UiStateWithStatusUpdate<T>> Result<*>.onFailureUpdate(
    classAndMethod: String,
    updateState: ((T.() -> T)) -> Unit
): Result<*> =
    onFailure { failure ->
        updateState {
            withFailure(classAndMethod, failure)
        }
    }


suspend inline fun <T : UiStateWithStatusUpdate<T>> Result<*>.onFailureEmit(
    collector: FlowCollector<T>,
    currentState: T,
    classAndMethod: String,
    errorType: Errors = Errors.TOKEN
) {
    this.onFailure { throwable ->
        val newState = currentState.withFailure(
            classAndMethod = classAndMethod,
            throwable = throwable,
            errors = errorType,
            flagProgress = false
        )
        collector.emit(newState)
    }
}