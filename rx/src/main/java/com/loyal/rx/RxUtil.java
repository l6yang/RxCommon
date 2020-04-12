package com.loyal.rx;
/*subscribeOn
 Observable<T> subscribeOn(Scheduler scheduler)

 subscribeOn通过接收一个Scheduler参数，来指定对数据的处理运行在特定的线程调度器Scheduler上。
 若多次设定，则只有一次起作用。
 observeOn
 Observable<T> observeOn(Scheduler scheduler)

 observeOn同样接收一个Scheduler参数，来指定下游操作运行在特定的线程调度器Scheduler上。
 若多次设定，每次均起作用。

 <p>Scheduler种类<p>
 Schedulers.io( )：
 用于IO密集型的操作，例如读写SD卡文件，查询数据库，访问网络等，具有线程缓存机制，在此调度器接收到任务后，先检查线程缓存池中，是否有空闲的线程，如果有，则复用，如果没有则创建新的线程，并加入到线程池中，如果每次都没有空闲线程使用，可以无上限的创建新线程。
 Schedulers.newThread( )：
 在每执行一个任务时创建一个新的线程，不具有线程缓存机制，因为创建一个新的线程比复用一个线程更耗时耗力，虽然使用Schedulers.io( )的地方，都可以使用Schedulers.newThread( )，但是，Schedulers.newThread( )的效率没有Schedulers.io( )高。
 Schedulers.computation()：
 用于CPU 密集型计算任务，即不会被 I/O 等操作限制性能的耗时操作，例如xml,json文件的解析，Bitmap图片的压缩取样等，具有固定的线程池，大小为CPU的核数。不可以用于I/O操作，因为I/O操作的等待时间会浪费CPU。
 Schedulers.trampoline()：
 在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，再将未完成的任务接着执行。
 Schedulers.single()：
 拥有一个线程单例，所有的任务都在这一个线程中执行，当此线程中有任务执行时，其他任务将会按照先进先出的顺序依次执行。
 Scheduler.from(@NonNull Executor executor)：
 指定一个线程调度器，由此调度器来控制任务的执行策略。
 AndroidSchedulers.mainThread()：
 在Android UI线程中执行任务，为Android开发定制。
 注：
 在RxJava2中，废弃了RxJava1中的Schedulers.immediate( )
 在RxJava1中，Schedulers.immediate( )的作用为在当前线程立即执行任务，功能等同于RxJava2中的Schedulers.trampoline( )。
 而Schedulers.trampoline( )在RxJava1中的作用是当其它排队的任务完成后，在当前线程排队开始执行接到的任务，有点像RxJava2中的Schedulers.single()，但也不完全相同，因为Schedulers.single()不是在当前线程而是在一个线程单例中排队执行任务。
 */

import android.text.TextUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /**
     * @param http      http或者https
     * @param ipAdd     IP地址
     * @param port      端口号
     * @param nameSpace test
     * @return http://192.168.0.155:9080/test/ 必须以"/"结尾
     */
    public static String getBaseUrl(String http, String ipAdd, String port, String nameSpace) {
        if (!portValid(port)) {
            throw new IllegalArgumentException("端口号范围0～65535，当前端口号：" + port);
        } else {
            String url = TextUtils.isEmpty(port) ? ipAdd : String.format("%s:%s", ipAdd, port);
            if (TextUtils.isEmpty(nameSpace)) {
                return String.format("%s://%s/%s", http, url, nameSpace);
            } else
                return String.format("%s://%s/%s/", http, url, nameSpace);
        }
    }

    /**
     * 普通的执行方法
     *
     * @see #rxExecuteAndCompute(Observable, Observer)
     */
    public static <T> void rxExecute(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @see #rxExecuteAndCompute(Observable, Observer)
     * 使用的时候需要在UI线程中更新消息
     * exp：handler，runOnUiThread(Runnable)
     * @deprecated
     */
    public static <T> void rxExecuteByIO(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 可用于下载图片，文件等
     */
    public static <T> void rxExecuteAndCompute(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public static boolean portValid(String port) {
        try {
            if (TextUtils.isEmpty(port))
                return true;
            int portInt = Integer.parseInt(port);
            return (portInt >= 0 && portInt <= 65535);
        } catch (Exception e) {
            return false;
        }
    }
}