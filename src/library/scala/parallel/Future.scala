package scala.parallel







/** A future is a function without parameters that will block the caller if the
 *  parallel computation associated with the function is not completed.
 *  
 *  @since 2.9
 */
trait Future[@specialized +R] extends (() => R) {
  /** Returns a result once the parallel computation completes. If the computation
   *  produced an exception, an exception is forwarded.
   *  
   *  '''Note:''' creating a circular dependency between futures by calling this method will
   *  result in a deadlock.
   *  
   *  @tparam R   the type of the result
   *  @return     the result
   *  @throws     the exception that was thrown during a parallel computation
   */
  def apply(): R
  
  /** Returns `true` if the parallel computation is completed.
   *  
   *  @return     `true` if the parallel computation is completed, `false` otherwise
   */
  def isDone(): Boolean
}



