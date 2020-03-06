// See LICENSE.SiFive for license details.

package freechips.rocketchip.system

import chisel3.stage.{ChiselCli, ChiselStage}
import firrtl.options.PhaseManager.PhaseDependency
import firrtl.options.{Phase, PreservesAll, Shell, StageMain}
import firrtl.stage.FirrtlCli
import freechips.rocketchip.stage.RocketChipCli

class RocketChipStage extends ChiselStage with PreservesAll[Phase] {

  override val shell = new Shell("rocket-chip") with RocketChipCli with ChiselCli with FirrtlCli
  override val targets: Seq[PhaseDependency] = Seq(
    classOf[freechips.rocketchip.stage.phases.Checks],
    classOf[freechips.rocketchip.stage.phases.TransformAnnotations],
    classOf[freechips.rocketchip.stage.phases.PreElaboration],
    classOf[chisel3.stage.phases.Checks],
    classOf[chisel3.stage.phases.Elaborate],
    classOf[freechips.rocketchip.stage.phases.GenerateROMs],
    classOf[chisel3.stage.phases.AddImplicitOutputFile],
    classOf[chisel3.stage.phases.AddImplicitOutputAnnotationFile],
    classOf[chisel3.stage.phases.MaybeAspectPhase],
    classOf[chisel3.stage.phases.Emitter],
    classOf[chisel3.stage.phases.Convert],
    classOf[freechips.rocketchip.stage.phases.GenerateFirrtlAnnos],
    classOf[freechips.rocketchip.stage.phases.AddDefaultTests],
    classOf[freechips.rocketchip.stage.phases.GenerateTestSuiteMakefrags],
    classOf[freechips.rocketchip.stage.phases.GenerateArtefacts],
  )

  // TODO: need a RunPhaseAnnotation to inject phases into ChiselStage
}

object Generator extends StageMain(new RocketChipStage)
