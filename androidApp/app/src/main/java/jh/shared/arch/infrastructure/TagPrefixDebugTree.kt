package jh.shared.arch.infrastructure

import timber.log.Timber

class TagPrefixDebugTree(private val tagPrefix: String) : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) =
            super.log(priority, tagPrefix + tag, message, throwable)
}