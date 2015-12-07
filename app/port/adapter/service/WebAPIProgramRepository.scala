package port.adapter.service

import java.net.URL
import java.time.{LocalDateTime, LocalDate}
import java.time.format.DateTimeFormatter

import domain.model.channel.{ChannelId, Channel}
import domain.model.program.{ProgramId, Program, ProgramRepository}

import scala.xml.XML

class WebAPIProgramRepository extends ProgramRepository {

  override def addPrograms(): Seq[Program] = {
    val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val xml = XML.load(new URL("http://cal.syoboi.jp/cal_chk.php"))
    val list = (xml \\ "ProgItem").map { e =>
      Program(
        id = ProgramId((e \ "@PID").text.toLong),
        channel = Channel(id = ChannelId((e \ "@ChID").text.toLong), name = (e \ "@ChName").text),
        name = (e \ "@Title").text,
        title = (e \ "@SubTitle").text,
        episode = (e \ "@Count").text.toInt,
        startTime = LocalDateTime.parse((e \ "@StTime").text, format),
        endTime = LocalDateTime.parse((e \ "@EdTime").text, format)
      )
    }
    list
  }

  override def allPrograms(date: LocalDate, channels: Seq[Channel]): Seq[Program] = ???

}
