package port.adapter.web.service

import java.net.URL

import domain.model.channel.{Channel, ChannelGroup, ChannelId, ChannelRepository}

import scala.xml.XML

class HttpChannelRepository extends ChannelRepository {

  override def allChannels(): Seq[Channel] = {
    val xml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChLookup"))
    val channels = (xml \\ "ChItem").map { e =>
      Channel(id = ChannelId((e \ "ChID").text.toLong), name = (e \ "ChName").text)
    }.sortWith((c1, c2) => c1.id.value < c2.id.value)
    channels
  }

  override def allChannelsOfGroup(group: ChannelGroup): Seq[Channel] = {
    val url = "http://cal.syoboi.jp/db.php?Command=ChLookup&ChID=%s"
      .format(group.channelIds.map(_.value.toString).mkString(","))
    val xml = XML.load(new URL(url))
    val channels = (xml \\ "ChItem").map { e =>
      Channel(id = ChannelId((e \ "ChID").text.toLong), name = (e \ "ChName").text)
    }.sortWith((c1, c2) => c1.id.value < c2.id.value)
    channels
  }

}
