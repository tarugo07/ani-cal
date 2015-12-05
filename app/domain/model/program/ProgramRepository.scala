package domain.model.program

import java.time.LocalDate

import domain.model.channel.Channel

trait ProgramRepository {

  def allPrograms(date: LocalDate, channels: Seq[Channel]): Seq[Program]

}
