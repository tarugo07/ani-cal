package port.adapter.web.service

import java.net.URL

import domain.model.channel._

import scala.xml.XML

class HttpChannelGroupRepository extends ChannelGroupRepository {

  private def allChannelIdsOfGroup(channelGroupId: ChannelGroupId): Seq[ChannelId] = {
    val xml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChLookup"))
    val channelIds = (xml \\ "ChItem").filter { e =>
      (e \ "ChGID").text.toLong == channelGroupId.value
    }.map { e =>
      ChannelId(value = (e \ "ChID").text.toLong)
    }
    channelIds
  }

  override def channelGroupOfId(id: ChannelGroupId): ChannelGroup = {
    val channelIds = allChannelIdsOfGroup(id)
    val url = "http://cal.syoboi.jp/db.php?Command=ChGroupLookup&ChGID=%d".format(id.value)
    val xml = XML.load(new URL(url))
    val channelGroup = (xml \\ "ChGroupItem").map { e =>
      ChannelGroup(id = id, name = (e \ "ChGroupName").text, channelIds = channelIds)
    }
    channelGroup.head
  }

  override def allChannelGroup(): Seq[ChannelGroup] = {
    val xml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChGroupLookup"))
    val channelGroups = (xml \\ "ChGroupItem").map { e =>
      channelGroupOfId(ChannelGroupId(value = (e \ "ChGID").text.toLong))
    }
    channelGroups
  }

}
