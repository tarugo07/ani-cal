package port.adapter.ui.json

import domain.model.channel.ChannelGroup
import play.api.libs.json._
import port.adapter.ui.json.ChannelGroupIdWriters.channelGroupIdWriters

object ChannelGroupWriters {

  implicit val channelGroupWriters = new Writes[ChannelGroup] {
    override def writes(channelGroup: ChannelGroup): JsValue = {
      Json.obj(
        "id" -> channelGroup.id,
        "name" -> channelGroup.name
      )
    }
  }

}
