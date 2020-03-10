// See LICENSE.SiFive for license details.

package freechips.rocketchip

import chisel3._
import freechips.rocketchip.util._

package object amba {
  class AMBAProtBundle extends Bundle {
    val cacheable  = Bool() // false => no need to probe other cores
    val bufferable = Bool() // writeback caching ok?
    val modifiable = Bool() // legal to read/write-combine/expand this request?
    val privileged = Bool() // machine_mode=true,   user_mode=false
    val secure     = Bool() // secure_master=true,  normal=false
    val fetch      = Bool() // instruct_fetch=true, load/store=false
  }

  case object AMBAProt extends ControlRequestKey[AMBAProtBundle]("amba_prot")
  case class AMBAProtField() extends BundleField(AMBAProt) {
    def data = Output(new AMBAProtBundle)
    def default(x: AMBAProtBundle) {
      x.cacheable  := true.B
      x.bufferable := true.B
      x.modifiable := true.B
      x.privileged := false.B
      x.secure     := false.B
      x.fetch      := false.B
    }
  }
}
