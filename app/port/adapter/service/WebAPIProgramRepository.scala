package port.adapter.service

import java.time.LocalDate

import domain.model.channel.Channel
import domain.model.program.{Program, ProgramRepository}

class WebAPIProgramRepository extends ProgramRepository {

  override def addPrograms(): Seq[Program] = ???

  override def allPrograms(date: LocalDate, channels: Seq[Channel]): Seq[Program] = ???

}
