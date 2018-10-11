package co.devhack.homecommunity.util

import android.net.Uri

fun String.uri(): Uri {
    return Uri.parse(this)
}
