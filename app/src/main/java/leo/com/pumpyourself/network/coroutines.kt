package leo.com.pumpyourself.network

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/** AndroidCoroutineContextElement context, provides an AndroidContinuation, executes everything on the UI Thread.*/
object AndroidCoroutineContextElement : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
  override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> = AndroidContinuation(continuation)
}

/** AndroidCoroutineContextElement Continuation, guarantees that, when resumed, is on the UI Thread.*/
private class AndroidContinuation<T>(val cont: Continuation<T>) : Continuation<T> by cont {

  override fun resumeWith(result: Result<T>) {
    if (Looper.myLooper() == Looper.getMainLooper()) cont.resumeWith(result)
    else Handler(Looper.getMainLooper()).post { cont.resumeWith(result) }
  }

}

inline fun <P> doCoroutineWork(
  crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
  coroutineScope: CoroutineScope,
  context: CoroutineContext
) {
  coroutineScope.launch {
    withContext(context) {
      doOnAsyncBlock.invoke(this)
    }
  }
}