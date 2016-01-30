package port.adapter.service

import java.net.URL
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import domain.model.channel.{Channel, ChannelId}
import domain.model.program.{Program, ProgramId, ProgramRepository}

import scala.util.Try
import scala.xml.XML

class HttpProgramRepository extends ProgramRepository {

  override def allPrograms(): Try[Seq[Program]] = Try {
    val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val xml = XML.load(new URL("http://cal.syoboi.jp/cal_chk.php"))
    val list = (xml \\ "ProgItem").map { e =>
      Program(
        id = ProgramId((e \ "@PID").text.toLong),
        channel = Channel(id = ChannelId((e \ "@ChID").text.toLong), name = (e \ "@ChName").text),
        name = (e \ "@Title").text,
        title = (e \ "@SubTitle").text,
        episode = if ((e \ "@Count").text.isEmpty) 0 else (e \ "@Count").text.toInt,
        startTime = LocalDateTime.parse((e \ "@StTime").text, format),
        endTime = LocalDateTime.parse((e \ "@EdTime").text, format)
      )
    }
    list
  }

  override def allProgramsOfDate(date: LocalDate, channels: Seq[Channel] = Seq.empty): Try[Seq[Program]] = Try {
    val url = "http://cal.syoboi.jp/cal_chk.php?start=%s&days=1".format(date)
    val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val xml = XML.load(new URL(url))
    val list = (xml \\ "ProgItem").map { e =>
      Program(
        id = ProgramId((e \ "@PID").text.toLong),
        channel = Channel(id = ChannelId((e \ "@ChID").text.toLong), name = (e \ "@ChName").text),
        name = (e \ "@Title").text,
        title = (e \ "@SubTitle").text,
        episode = if ((e \ "@Count").text.isEmpty) 0 else (e \ "@Count").text.toInt,
        startTime = LocalDateTime.parse((e \ "@StTime").text, format),
        endTime = LocalDateTime.parse((e \ "@EdTime").text, format)
      )
    }.filter(p => channels.contains(p.channel)).sortWith((p1, p2) => p1.startTime.isBefore(p2.startTime))
    list
  }

}
