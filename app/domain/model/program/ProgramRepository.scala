package domain.model.program

import java.time.LocalDate

import domain.model.channel.Channel

import scala.util.Try

trait ProgramRepository {

  def allPrograms(): Try[Seq[Program]]

  def allProgramsOfDate(date: LocalDate, channels: Seq[Channel] = Seq.empty): Try[Seq[Program]]

}
