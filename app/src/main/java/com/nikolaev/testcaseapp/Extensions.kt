package com.nikolaev.testcaseapp

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.nikolaev.testcaseapp.common.BaseFragment
import com.nikolaev.testcaseapp.model.BaseResponse
import com.nikolaev.testcaseapp.model.CoreResponse
import com.nikolaev.testcaseapp.network.ErrorMapper.mapError
import java.util.*
import kotlin.math.roundToInt

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */


fun Activity.findNavController() = findNavController(this, R.id.navHost)

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

suspend fun <T> handleError(call: suspend () -> BaseResponse<T>): CoreResponse<T> {
    return try {
        val result = call()
        if (result.response != null) {
            CoreResponse(success = result.response)
        } else {
                CoreResponse(error = Throwable(""))
        }
    } catch (ex: Throwable) {
        CoreResponse(error = mapError(ex))
    }
}

fun <T> CoreResponse<T>.parseResult(
    success: (T) -> Unit,
    error: (Throwable) -> Unit
) {
    if (this.error != null) {
        error.invoke(this.error!!)
    } else success.invoke(this.success!!)
}

suspend fun <T> CoreResponse<T>.parseResultSuspend(
    success: suspend (T) -> Unit,
    error: suspend (Throwable) -> Unit
) {
    if (this.error != null) {
        error(this.error!!)
    } else success(this.success!!)
}

fun BaseFragment.findNavController() = if (view != null) {
    findNavController(view!!)
} else {
    throw IllegalAccessError("Fragment's view is not ready yet, use after view inflate")
}

fun String.formatName(): String {
    return this.toLowerCase(Locale.getDefault()).capitalize(Locale.getDefault())
}

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

fun ImageView.setImage(url: String, context: Context) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun String?.formatDateOfBirth(context: Context): String? {
    val bdArray = this?.split("-")
    if (bdArray != null) {
        if (bdArray.size == 3) {
            return if (bdArray[0].length > 2) {
                String.format(
                    context.getString(R.string.data_of_birth_template),
                    bdArray[2],
                    bdArray[1],
                    bdArray[0]
                )
            } else {
                String.format(
                    context.getString(R.string.data_of_birth_template),
                    bdArray[0],
                    bdArray[1],
                    bdArray[2]
                )
            }
        }
    }
    return null
}