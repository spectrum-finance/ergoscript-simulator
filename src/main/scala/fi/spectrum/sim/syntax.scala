package fi.spectrum.sim

import fi.spectrum.sim.runtime.NonRunnable
import scorex.crypto.hash.Blake2b256
import scorex.util.encode.Base16

import scala.util.Try

object syntax {

  implicit class ToValidatorOps[F[_]](box: BoxSpec[F]) {

    def R4[T]: Option[T] = getR(4)

    def R5[T]: Option[T] = getR(5)

    def R6[T]: Option[T] = getR(6)

    def R7[T]: Option[T] = getR(7)

    def R8[T]: Option[T] = getR(8)

    def R9[T]: Option[T] = getR(9)

    private def getR[T](i: Int): Option[T] =
      box.registers.get(i).flatMap(a => Try(a.asInstanceOf[T]).toOption)
  }

  def OUTPUTS(i: Int)(implicit ctx: RuntimeCtx): BoxSpec[NonRunnable] =
    ctx.outputs(i)

  def OUTPUTS(implicit ctx: RuntimeCtx): Coll[BoxSpec[NonRunnable]] =
    ctx.outputs.toVector

  def INPUTS(i: Int)(implicit ctx: RuntimeCtx): BoxSpec[NonRunnable] =
    ctx.inputs(i)

  def INPUTS(implicit ctx: RuntimeCtx): Coll[BoxSpec[NonRunnable]] =
    ctx.inputs.toVector

  def HEIGHT(implicit ctx: RuntimeCtx): Int = ctx.height

  def sigmaProp(x: Boolean): Boolean = x

  def allOf(coll: Coll[Boolean]): Boolean = !coll.inner.contains(false)

  def blake2b256(xs: Coll[Byte]): Coll[Byte] = Blake2b256.hash(xs.inner.toArray).toVector

  def getVar[T](i: Byte)(implicit ctx: RuntimeCtx): Option[T] =
    ctx.vars.get(i.toInt).flatMap(a => Try(a.asInstanceOf[T]).toOption)

  def fromBase16(arg: String): Coll[Byte] = Base16.decode(arg).get.toVector

  def max(x: Int, y: Int): Int = math.max(x, y)

  def max(x: Long, y: Long): Long = math.max(x, y)

  implicit class ToBigIntOps[N: Numeric](private val n: N) {
    def toBigInt: BigInt = BigInt(implicitly[Numeric[N]].toLong(n))
  }

  @inline def min(x: BigInt, y: BigInt): BigInt =
    if (y < x) y else x

  implicit def sigmaPropIsBoolean(prop: SigmaProp)(implicit ctx: RuntimeCtx): Boolean =
    ctx.signatories.contains(prop)

  implicit class ToSigmaPropOps(prop: SigmaProp) {
    def propBytes: Coll[Byte] = prop.value
  }
}
