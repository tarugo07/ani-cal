package port.adapter.web.service

import java.net.URL

import domain.model.channel.{Channel, ChannelId, ChannelRepository}

import scala.xml.XML

class HttpChannelRepository extends ChannelRepository {

  override def allChannels(): Seq[Channel] = {
    val xml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChLookup"))
    val list = (xml \\ "ChItem").map { e =>
      Channel(id = ChannelId((e \\ "@id").text.toLong), name = (e \ "ChName").text)
    }.sortWith((c1, c2) => c1.id.value < c2.id.value)
    list
  }

}
