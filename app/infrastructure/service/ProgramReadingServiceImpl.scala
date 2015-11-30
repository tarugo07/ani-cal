package infrastructure.service

import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.xml.XML

class ProgramReadingServiceImpl extends ProgramReadingService {

  override def readProgramList(): Seq[Program] = {
    val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val xml = XML.load(new URL("http://cal.syoboi.jp/cal_chk.php"))
    (xml \\ "ProgItem").map { e =>
      Program(
        channelId = (e \ "@ChID").text.toInt,
        channelName = (e \ "@ChName").text,
        title = (e \ "@Title").text,
        subTitle = (e \ "@SubTitle").text,
        start = LocalDateTime.parse((e \ "@StTime").text, format),
        end = LocalDateTime.parse((e \ "@EdTime").text, format)
      )
    }
  }

}
