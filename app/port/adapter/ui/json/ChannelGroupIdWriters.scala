package port.adapter.ui.json

import domain.model.channel.ChannelGroupId
import play.api.libs.json._

object ChannelGroupIdWriters {

  implicit val channelGroupIdWriters = new Writes[ChannelGroupId] {
    override def writes(id: ChannelGroupId): JsValue = Json.toJson(id.value)
  }

}
