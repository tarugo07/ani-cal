package port.adapter.service

import java.net.URL

import domain.model.EntityNotFoundException
import domain.model.channel._

import scala.util.Try
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

  override def channelGroupOfId(id: ChannelGroupId): Try[ChannelGroup] = Try {
    val channelIds = allChannelIdsOfGroup(id)
    val url = "http://cal.syoboi.jp/db.php?Command=ChGroupLookup&ChGID=%d".format(id.value)
    val xml = XML.load(new URL(url))
    val channelGroup = (xml \\ "ChGroupItem").map { e =>
      ChannelGroup(id = id, name = (e \ "ChGroupName").text, channelIds = channelIds)
    }
    if (channelGroup.nonEmpty)
      channelGroup.head
    else
      throw EntityNotFoundException(s"ChannelGroup not found: id = ${id.value}")
  }

  override def allChannelGroup(): Try[Seq[ChannelGroup]] = Try {
    val groupXml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChGroupLookup"))
    val channelXml = XML.load(new URL("http://cal.syoboi.jp/db.php?Command=ChLookup"))
    val channelGroups = (groupXml \\ "ChGroupItem").map { group =>
      val channelGroupId = ChannelGroupId(value = (group \ "ChGID").text.toLong)
      val channelIds = (channelXml \\ "ChItem").filter { channel =>
        (channel \ "ChGID").text.toLong == channelGroupId.value
      }.map { e =>
        ChannelId(value = (e \ "ChID").text.toLong)
      }
      ChannelGroup(id = channelGroupId, name = (group \ "ChGroupName").text, channelIds = channelIds)
    }
    channelGroups
  }

}
